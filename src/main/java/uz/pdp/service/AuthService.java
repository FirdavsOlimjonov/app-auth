package uz.pdp.service;

import uz.pdp.entity.User;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.SignDTO;
import uz.pdp.payload.TokenDTO;


public interface AuthService {      //extends UserDetailsService
    ApiResult<Boolean> signUp(SignDTO signDTO);

    ApiResult<?> verificationPhoneNumber(String email);

    ApiResult<TokenDTO> signIn(SignDTO signDTO);

    ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken);

    User getUserByToken(String token);
}
