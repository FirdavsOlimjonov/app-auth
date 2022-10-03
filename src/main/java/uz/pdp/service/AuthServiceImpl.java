package uz.pdp.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.SignDTO;
import uz.pdp.payload.TokenDTO;
import uz.pdp.repository.UserRepository;
import uz.pdp.security.JWTFilter;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.access.key}")
    private String ACCESS_TOKEN_KEY;

    //    @Value("${server.port}")
//    private String API_PORT = "8090";

    private final String API = " http://localhost:";

    @Value("${jwt.refresh.key}")
    private String REFRESH_TOKEN_KEY;

    @Value("${jwt.access.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private final UserRepository userRepository;

    private final JWTFilter jwtFilter;

    //    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
//    private final JavaMailSender javaMailSender;
//    private final AuthenticationManager authenticationManager;

//    @Value("${spring.mail.username}")
//    private String sender;

    public AuthServiceImpl(UserRepository userRepository,
                           JWTFilter jwtFilter,
                           @Lazy PasswordEncoder passwordEncoder
//                           @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender javaMailSender,
//                           @Lazy AuthenticationManager authenticationManager,
//                           RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
//        this.javaMailSender = javaMailSender;
//        this.authenticationManager = authenticationManager;
//        this.roleRepository = roleRepository;
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository
//                .findByPhoneNumber(username)
//                .orElseThrow(
//                        () -> RestException.restThrow(String.format("%s email not found", username), HttpStatus.UNAUTHORIZED));
//
//    }

    @Override
    @Transactional
    public ApiResult<Boolean> signUp(SignDTO signDTO) {

        if (userRepository.existsByPhoneNumber(signDTO.getPhoneNumber()))
            throw RestException.restThrow(
                    "EMAIL_ALREADY_EXIST",
                    HttpStatus.CONFLICT);


        User user = new User(
                signDTO.getPhoneNumber(),
                passwordEncoder.encode(signDTO.getPassword()));

        CompletableFuture.runAsync(() -> sendVerificationCodeToPhoneNumber(user));

        userRepository.save(user);
        return ApiResult.successResponse();
    }


    @Override
    public ApiResult<?> verificationPhoneNumber(String email) {
        User user = userRepository.findByPhoneNumber(email)
                .orElseThrow(() -> RestException.restThrow("EMAIL_NOT_EXIST", HttpStatus.NOT_FOUND));

        if (user.isEnabled()) {
            return ApiResult.successResponse("ALREADY_VERIFIED");
        }

        user.setEnabled(true);
        userRepository.save(user);
        return ApiResult.successResponse("SUCCESSFULLY_VERIFIED");
    }


    @Override
    public ApiResult<TokenDTO> signIn(SignDTO signDTO) {

        User user = new User(signDTO.getPhoneNumber(),signDTO.getPassword());
        String accessToken = generateToken(user.getPhoneNumber(), true);
        String refreshToken = generateToken(user.getPhoneNumber(), false);

        TokenDTO tokenDTO = TokenDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResult.successResponse(
                "SUCCESSFULLY_TOKEN_GENERATED",
                tokenDTO);
    }


    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        accessToken = accessToken.substring(accessToken.indexOf("Bearer") + 6).trim();
        try {
            Jwts
                    .parser()
                    .setSigningKey(ACCESS_TOKEN_KEY)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            try {
                String email = Jwts
                        .parser()
                        .setSigningKey(REFRESH_TOKEN_KEY)
                        .parseClaimsJws(refreshToken)
                        .getBody()
                        .getSubject();
                User user = userRepository.findByPhoneNumber(email).orElseThrow(() ->
                        RestException.restThrow("EMAIL_NOT_EXIST", HttpStatus.NOT_FOUND));

                if (!user.isEnabled()
                        || !user.isAccountNonExpired()
                        || !user.isAccountNonLocked()
                        || !user.isCredentialsNonExpired())
                    throw RestException.restThrow("USER_PERMISSION_RESTRICTION", HttpStatus.UNAUTHORIZED);

                String newAccessToken = generateToken(email, true);
                String newRefreshToken = generateToken(email, false);
                TokenDTO tokenDTO = TokenDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                return ApiResult.successResponse(tokenDTO);
            } catch (Exception e) {
                throw RestException.restThrow("REFRESH_TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow("WRONG_ACCESS_TOKEN", HttpStatus.UNAUTHORIZED);
        }

        throw RestException.restThrow("ACCESS_TOKEN_NOT_EXPIRED", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ApiResult<User> getUserByToken(String token) {
        String phoneNumber = jwtFilter.getEmailFromToken(token);
        return ApiResult.successResponse(userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> RestException.restThrow("NOT_FOUND",HttpStatus.NOT_FOUND)));
    }

    public String generateToken(String email, boolean accessToken) {

        Date expiredDate = new Date(new Date().getTime() +
                (accessToken ? ACCESS_TOKEN_EXPIRATION_TIME : REFRESH_TOKEN_EXPIRATION_TIME));

        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, (
                        accessToken ? ACCESS_TOKEN_KEY : REFRESH_TOKEN_KEY
                ))
                .compact();
    }

    /**
     * Send Verification Code To PhoneNumber
     */
    private void sendVerificationCodeToPhoneNumber(User user) {
//        SimpleMailMessage mailMessage
//                = new SimpleMailMessage();
//
//        // Setting up necessary details
//        mailMessage.setFrom(sender);
//        mailMessage.setTo(user.getPhoneNumber());
//        mailMessage.setSubject("");
//        mailMessage.setText("CLICK_LINK" + API + API_PORT + "/api/auth/verification-email/" + user.getEmail());
//
//        // Sending the mail
//        javaMailSender.send(mailMessage);
    }

}
