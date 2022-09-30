package uz.pdp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Employee;
import uz.pdp.payload.EmployeeDTO;
import uz.pdp.payload.SearchDTO;


import java.util.List;
import java.util.UUID;


@RequestMapping("/employee")
public interface EmployeeController {

    @GetMapping("/getAll")
    ResponseEntity<List<Employee>> getAll();

    @GetMapping("/getOne/{id}")
    ResponseEntity<Employee> getOne(@PathVariable UUID id);

    @PostMapping("/add")
    ResponseEntity<Boolean> add(@RequestBody Employee employee);

    @PutMapping("/edit/{id}")
    ResponseEntity<Boolean> edit(@RequestBody Employee employee, @PathVariable UUID id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Boolean> delete(@PathVariable UUID id);

    @GetMapping("/getFilter")
    ResponseEntity<?>  filter(SearchDTO searchDTO);


}
