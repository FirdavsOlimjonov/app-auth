package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Employee;
import uz.pdp.payload.EmployeeDTO;


import java.util.List;
import java.util.UUID;


@RequestMapping("/employee")
public interface EmployeeController {

    @GetMapping("/getAll")
    ResponseEntity<List<EmployeeDTO>> getAll();

    @GetMapping("/getOne/{id}")
    ResponseEntity<EmployeeDTO> getOne(@PathVariable UUID id);

    @PostMapping("/add")
    ResponseEntity<Boolean> add(@RequestBody Employee employee);

    @PutMapping("/edit/{id}")
    ResponseEntity<Boolean> edit(@RequestBody Employee employee, @PathVariable UUID id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Boolean> delete(@PathVariable UUID id);


}
