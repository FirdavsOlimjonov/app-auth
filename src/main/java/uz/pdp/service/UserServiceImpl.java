package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.bouncycastle.jcajce.spec.RawEncodedKeySpec;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByPhoneNumberIfNotCreate(String phoneNumber) {
        return findByPhoneNumberIfNotCreate(phoneNumber, null);
    }

    @Override
    public User findByPhoneNumberIfNotCreate(String phoneNumber, String password) {
        User user = Optional.of(userRepository
                        .findByPhoneNumber(phoneNumber)
                        .orElseGet(() -> userRepository.save(new User(phoneNumber, password))))
                .orElseThrow(() -> RestException.restThrow("Error in saving", HttpStatus.CONFLICT));

        if (Objects.isNull(user.getPassword()))
            user.setPassword(password);
        return user;
    }
}
