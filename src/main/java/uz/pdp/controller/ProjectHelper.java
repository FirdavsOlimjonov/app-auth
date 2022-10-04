package uz.pdp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.EmployeeDTO;

@RequestMapping(path = ProjectHelper.PROJECT_HELPER_CONTROLLER_BASE_PATH)
public interface ProjectHelper {
    String PROJECT_HELPER_CONTROLLER_BASE_PATH = "/api/project-helper";
    String GET_EMPLOYEE_WITH_UUID = "/get-employee-with-uuid/{uuid}";

    @GetMapping(value = GET_EMPLOYEE_WITH_UUID)
    ApiResult<EmployeeDTO> getEmployeeWithUUID(@PathVariable String uuid);

}
