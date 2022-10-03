package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Employee;
import uz.pdp.payload.add_DTO.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.EmployeeDTO;
import uz.pdp.util.RestConstants;


import java.util.List;
import java.util.UUID;


@RequestMapping(EmployeeController.BASE_PATH)
public interface EmployeeController {
    String BASE_PATH = RestConstants.SERVICE_BASE_PATH + "employee";

    @GetMapping("/list")
    ApiResult<List<EmployeeDTO>> getAll();

    @GetMapping("/{id}")
    ApiResult<EmployeeDTO> getOne(@PathVariable UUID id);

    @PostMapping
    ApiResult<String> add(@RequestBody AddEmployeeDTO employee);

    @PutMapping("/{id}")
    ApiResult<Boolean> edit(@RequestBody EmployeeDTO employeeDTO, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<String> delete(@PathVariable UUID id);


}
