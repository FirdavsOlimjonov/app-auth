package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.EmployeeDTO;
import uz.pdp.payload.response_DTO.UserDTO;
import uz.pdp.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ApiResult<EmployeeDTO> getEmployeeWithId(UUID id) {
        return null;
    }

    @Override
    public ApiResult<UserDTO> getUserByToken() {
        return userService.getUserByToken();
    }

    @Override
    public ApiResult<UserDTO> getClientById(UUID userId) {
        return userService.getClientById(userId);
    }
}
