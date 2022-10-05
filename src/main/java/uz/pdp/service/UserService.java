package uz.pdp.service;

import uz.pdp.entity.User;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.UserDTO;

import java.util.UUID;

public interface UserService {

    User findByPhoneNumberIfNotCreate(String phoneNumber);

    User findByPhoneNumberIfNotCreate(String phoneNumber, String password);


    ApiResult<UserDTO> getUserByToken();

    ApiResult<UserDTO> getClientById(UUID userId);
}
