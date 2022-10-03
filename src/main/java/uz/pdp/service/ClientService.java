package uz.pdp.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.ClientDTO;
import uz.pdp.payload.filterPayload.ClientDTOFilter;
import uz.pdp.payload.filterPayload.ViewDTO;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ResponseEntity<List<Client>> getAll();
    ResponseEntity<Client> getOne(UUID id);
    ResponseEntity<Boolean> save(ClientDTO clientDTO);

    ResponseEntity<Boolean> edit(Client client, UUID id);

    ResponseEntity<Boolean> delete(UUID id);

    ApiResult<List<ClientDTOFilter>> getAllClients(ViewDTO viewDTO, int page, int size);

}
