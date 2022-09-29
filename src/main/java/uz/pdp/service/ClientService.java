package uz.pdp.service;

import uz.pdp.payload.filterPayload.ClientDTO;
import uz.pdp.payload.filterPayload.ViewDTO;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getAllClients(ViewDTO viewDTO, int page, int size);
}
