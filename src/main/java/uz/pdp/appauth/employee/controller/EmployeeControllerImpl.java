package uz.pdp.appauth.employee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appauth.employee.entity.Employee;
import uz.pdp.appauth.employee.payload.EmployeeDTO;
import uz.pdp.appauth.employee.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController{

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return employeeService.getAll();
    }

    @Override
    public ResponseEntity<EmployeeDTO> getOne(Integer id) {
        return employeeService.getOne(id);
    }

    @Override
    public ResponseEntity<Boolean> add(Employee employee) {
        return employeeService.add(employee);
    }

    @Override
    public ResponseEntity<Boolean> edit(EmployeeDTO employeeDTO, Integer id) {
        return employeeService.edit(employeeDTO, id);
    }

    @Override
    public ResponseEntity<Boolean> delete(Integer id) {
        return employeeService.delete(id);
    }
}
