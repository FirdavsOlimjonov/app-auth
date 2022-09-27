package uz.pdp.controller.cotract;

import org.springframework.web.bind.annotation.*;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.SignDTO;
import uz.pdp.payload.TokenDTO;

import javax.validation.Valid;


@RequestMapping(path = AuthController.AUTH_CONTROLLER_BASE_PATH)
public interface AuthController {

    String AUTH_CONTROLLER_BASE_PATH = "/api/auth";
    String SIGN_IN_PATH = "/sign-in";
    String SIGN_UP_PATH = "/sign-up";
    String VERIFICATION_PATH = "/verification-phone-number/{phoneNumber}";
    String REFRESH_TOKEN_PATH = "/refresh-token";


    @PostMapping(value = SIGN_UP_PATH)
    ApiResult<Boolean> signUp(@RequestBody @Valid SignDTO signDTO);


    @GetMapping(value = VERIFICATION_PATH)
    ApiResult<?> verificationPhoneNumber(@PathVariable String phoneNumber);


    @PostMapping(value = SIGN_IN_PATH)
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignDTO signDTO);


    @GetMapping(value = REFRESH_TOKEN_PATH)
    ApiResult<TokenDTO> refreshToken(@RequestHeader(value = "Authorization") String accessToken,
                                     @RequestHeader(value = "RefreshToken") String refreshToken);
}
