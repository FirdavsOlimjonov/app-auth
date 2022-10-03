package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.entity.Employee;
import uz.pdp.payload.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.EmployeeDTO;


import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    ApiResult<List<EmployeeDTO>> getAll();

    ApiResult<EmployeeDTO> getOne(UUID id);

    ApiResult<String> add(AddEmployeeDTO employee);

    ApiResult<Boolean> edit(EmployeeDTO employeeDTO, UUID id);

    ApiResult<String> delete(UUID id);

}
