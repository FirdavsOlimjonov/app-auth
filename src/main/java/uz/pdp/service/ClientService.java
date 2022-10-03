package uz.pdp.service;

import uz.pdp.payload.ApiResult;
import uz.pdp.payload.filterPayload.ClientDTO;
import uz.pdp.payload.filterPayload.ViewDTO;

import java.util.List;

public interface ClientService {

    ApiResult<List<ClientDTO>> getAllClients(ViewDTO viewDTO, int page, int size);
}
