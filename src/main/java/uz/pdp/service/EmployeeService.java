package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.entity.Employee;
import uz.pdp.payload.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<List<EmployeeDTO>> getAll();

    ResponseEntity<EmployeeDTO> getOne(Integer id);

    ResponseEntity<Boolean> add(Employee employee);

    ResponseEntity<Boolean> edit(EmployeeDTO employeeDTO, Integer id);

    ResponseEntity<Boolean> delete(Integer id);

}
