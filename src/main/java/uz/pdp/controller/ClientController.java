package uz.pdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Client;
import uz.pdp.entity.Employee;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.ClientDTO;
import uz.pdp.payload.filterPayload.ClientDTOFilter;
import uz.pdp.payload.filterPayload.ViewDTO;
import uz.pdp.util.Pagination;

import java.util.List;
import java.util.UUID;


@RequestMapping("/api/client")
public interface ClientController {

    @GetMapping("/getAll")
    ResponseEntity<List<Client>> getAll();

    @GetMapping("/getOne/{id}")
    ResponseEntity<Client> getOne(@PathVariable UUID id);

    @PostMapping("/add")
    ResponseEntity<Boolean> save(@RequestBody ClientDTO clientDTO);


    @PutMapping("/edit/{id}")
    ResponseEntity<Boolean> edit(@RequestBody Client client, @PathVariable UUID id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Boolean> delete(@PathVariable UUID id);

    @PostMapping("/filter-list")
    ApiResult<List<ClientDTOFilter>> getALl(@RequestBody(required = false) ViewDTO viewDTO,
                                            @RequestParam(defaultValue = Pagination.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(defaultValue = Pagination.DEFAULT_PAGE_SIZE) int size);

}
