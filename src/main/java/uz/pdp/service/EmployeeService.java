package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.entity.Employee;
import uz.pdp.payload.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;


import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    ResponseEntity<List<Employee>> getAll();

    ResponseEntity<Employee> getOne(UUID id);

    ApiResult<String> add(AddEmployeeDTO employee);

    ResponseEntity<Boolean> edit(Employee employee, UUID id);

    ResponseEntity<Boolean> delete(UUID id);

}
