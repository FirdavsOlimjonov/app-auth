package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.AddClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.util.RestConstants;

import java.util.List;
import java.util.UUID;


@RequestMapping(ClientController.CLIENT_CONTROLLER_BASE_PATH)
public interface ClientController {

    String CLIENT_CONTROLLER_BASE_PATH = RestConstants.SERVICE_BASE_PATH + "client";
    String GET_ALL_PATH = "/get-all";///
    String GET_BY_ID_PATH = "/{id}";
    String DELETE_BY_ID_PATH = "/{id}";

    @GetMapping(GET_ALL_PATH)
    ApiResult<List<ClientDTO>> getAll();

    @GetMapping(GET_BY_ID_PATH)
    ApiResult<ClientDTO> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<Boolean> add(@RequestBody AddClientDTO addClientDTO);

    @PutMapping
    ApiResult<Boolean> edit(@RequestBody ClientDTO clientDTO);

    @DeleteMapping(DELETE_BY_ID_PATH)
    ApiResult<Boolean> delete(@PathVariable UUID id);

}
