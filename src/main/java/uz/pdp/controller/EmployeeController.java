package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Employee;
import uz.pdp.payload.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.util.RestConstants;


import java.util.List;
import java.util.UUID;


@RequestMapping(EmployeeController.BASE_PATH)
public interface EmployeeController {
    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "employee";

    @GetMapping("/list")
    ResponseEntity<List<Employee>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<Employee> getOne(@PathVariable UUID id);

    @PostMapping
    ApiResult<String> add(@RequestBody AddEmployeeDTO employee);

    @PutMapping("/{id}")
    ResponseEntity<Boolean> edit(@RequestBody Employee employee, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> delete(@PathVariable UUID id);


}
