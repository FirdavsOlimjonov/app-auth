package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Employee;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.CurrierDTO;
import uz.pdp.payload.EmployeeDTO;

import java.util.List;
import java.util.UUID;


@RequestMapping("/currier")
public interface CurrierController {
    @PostMapping("/add")
    ApiResult<CurrierDTO> add(@RequestBody CurrierDTO currierDTO);

    @GetMapping("/getAll")
    ApiResult<List<CurrierDTO>> getAll();

    @GetMapping("/getOne/{id}")
    ApiResult<CurrierDTO> getOne(@PathVariable UUID id);

    @PutMapping("/edit/{id}")
    ApiResult<Boolean> edit(@RequestBody CurrierDTO currierDTO, @PathVariable UUID id);

    @DeleteMapping("/delete/{id}")
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
