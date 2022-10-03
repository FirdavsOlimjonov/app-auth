package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.AddClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;

import java.util.List;
import java.util.UUID;


@RequestMapping("/api/client")
public interface ClientController {

    @GetMapping("/get-all")
    ApiResult<List<ClientDTO>> getAll();

    @GetMapping("/{id}")
    ApiResult<ClientDTO> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<Boolean> add(@RequestBody AddClientDTO addClientDTO);


    @PutMapping
    ApiResult<Boolean> edit(@RequestBody ClientDTO clientDTO);

    @DeleteMapping("/{id}")
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
