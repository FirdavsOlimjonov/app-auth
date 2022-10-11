package uz.pdp.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.pdp.entity.Employee;
import uz.pdp.payload.add_DTO.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.filterPayload.enums.SearchEmployeeDTO;
import uz.pdp.payload.response_DTO.EmployeeDTO;


import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    ApiResult<List<EmployeeDTO>> getAll();

    ApiResult<EmployeeDTO> get(UUID id);

    ApiResult<String> add(AddEmployeeDTO employee);

    ApiResult<Boolean> edit(AddEmployeeDTO addEmployeeDTO, UUID id);

    ApiResult<String> delete(UUID id);

    ApiResult<Page<Employee>> filter(SearchEmployeeDTO searchEmployeeDTO);

}
