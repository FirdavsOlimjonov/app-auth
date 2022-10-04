package uz.pdp.service;

import uz.pdp.entity.User;
import uz.pdp.payload.ApiResult;

import java.util.Optional;

public interface UserService {

    User findByPhoneNumberIfNotCreate(String phoneNumber);

    User findByPhoneNumberIfNotCreate(String phoneNumber, String password);


    ApiResult<User> getUserByToken();
}
