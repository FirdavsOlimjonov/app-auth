package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.payload.ApiResult;
import uz.pdp.service.ClientService;
import uz.pdp.payload.filterPayload.ClientDTO;
import uz.pdp.payload.filterPayload.ViewDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController{

    private final ClientService clientService;

    @Override
    public ApiResult<List<ClientDTO>> getALl(ViewDTO viewDTO, int page, int size) {

        return clientService.getAllClients(viewDTO, page, size);
    }
}
