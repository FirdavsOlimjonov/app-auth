package uz.pdp.appauth.employee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appauth.employee.entity.Employee;
import uz.pdp.appauth.employee.payload.EmployeeDTO;

import java.util.List;


@RequestMapping("/employee")
public interface EmployeeController {

    @GetMapping("/getAll")
    ResponseEntity<List<EmployeeDTO>> getAll();

    @GetMapping("/getOne/{id}")
    ResponseEntity<EmployeeDTO> getOne(@PathVariable Integer id);

    @PostMapping("/add")
    ResponseEntity<Boolean> add(@RequestBody Employee employee);

    @PutMapping("/edit/{id}")
    ResponseEntity<Boolean> edit(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Boolean> delete(@PathVariable Integer id);


}
