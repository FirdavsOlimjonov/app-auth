package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.entity.Employee;
import uz.pdp.payload.EmployeeDTO;
import uz.pdp.payload.SearchDTO;
import uz.pdp.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController{

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAll() {
        return employeeService.getAll();
    }

    @Override
    public ResponseEntity<Employee> getOne(UUID id) {
        return employeeService.getOne(id);
    }

    @Override
    public ResponseEntity<Boolean> add(Employee employee) {
        return employeeService.add(employee);
    }

    @Override
    public ResponseEntity<Boolean> edit(Employee employee, UUID id) {
        return employeeService.edit(employee, id);
    }

    @Override
    public ResponseEntity<Boolean> delete(UUID id) {
        return employeeService.delete(id);
    }

    @Override
    public ResponseEntity<?> filter(SearchDTO searchDTO) {
        return employeeService.filter(searchDTO);
    }
}
