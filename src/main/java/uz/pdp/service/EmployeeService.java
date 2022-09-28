package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.entity.Employee;
import uz.pdp.payload.EmployeeDTO;


import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    ResponseEntity<List<EmployeeDTO>> getAll();

    ResponseEntity<EmployeeDTO> getOne(UUID id);

    ResponseEntity<Boolean> add(Employee employee);

    ResponseEntity<Boolean> edit(Employee employee, UUID id);

    ResponseEntity<Boolean> delete(UUID id);

}
