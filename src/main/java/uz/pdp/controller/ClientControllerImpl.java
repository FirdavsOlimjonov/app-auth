package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.ClientDTO;
import uz.pdp.payload.filterPayload.ClientDTOFilter;
import uz.pdp.payload.filterPayload.ViewDTO;
import uz.pdp.service.ClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController{

    private final ClientService clientService;

    @Override
    public ResponseEntity<List<Client>> getAll() {
        return clientService.getAll();
    }

    @Override
    public ResponseEntity<Client> getOne(UUID id) {
        return clientService.getOne(id);
    }

    @Override
    public ResponseEntity<Boolean> save(ClientDTO clientDTO) {
        return clientService.save(clientDTO);
    }

    @Override
    public ResponseEntity<Boolean> edit(Client client, UUID id) {
        return clientService.edit(client, id);
    }


    @Override
    public ResponseEntity<Boolean> delete(UUID id) {
        return clientService.delete(id);
    }

    @Override
    public ApiResult<List<ClientDTOFilter>> getALl(ViewDTO viewDTO, int page, int size) {

        return clientService.getAllClients(viewDTO, page, size);
    }
}
