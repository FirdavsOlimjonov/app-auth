package uz.pdp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.entity.User;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.EmployeeDTO;
import uz.pdp.payload.response_DTO.UserDTO;
import uz.pdp.util.RestConstants;

import java.util.UUID;

@RequestMapping(path = UserController.PROJECT_HELPER_CONTROLLER_BASE_PATH)
public interface UserController {
    String PROJECT_HELPER_CONTROLLER_BASE_PATH = RestConstants.SERVICE_BASE_PATH+"user";
    String GET_EMPLOYEE_WITH_UUID = "/employee/{id}";
    String USER_ME_PATH = "/me";

    @GetMapping(value = GET_EMPLOYEE_WITH_UUID)
    ApiResult<EmployeeDTO> getEmployeeWithId(@PathVariable UUID id);

    @GetMapping(value = USER_ME_PATH)
    ApiResult<UserDTO> getUserByToken();
}
