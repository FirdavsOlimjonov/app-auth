package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Client;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.add_DTO.AddClientDTO;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.repository.ClientRepository;
import uz.pdp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public ApiResult<List<ClientDTO>> getAll() {
        List<Client> all = clientRepository.findAll();
        return ApiResult.successResponse(all
                .stream()
                .map(ClientDTO::mapping)
                .toList());
    }

    @Override
    public ApiResult<ClientDTO> get(UUID id) {

        return ApiResult.successResponse(ClientDTO.mapping(clientRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow("", HttpStatus.NOT_FOUND))));
    }

    @Override
    public ApiResult<Boolean> add(AddClientDTO addClientDTO) {
        if (clientRepository.existsByUser_PhoneNumber(addClientDTO.getPhoneNumber()))
            throw RestException.restThrow("ALREADY_REPORTED", HttpStatus.ALREADY_REPORTED);
        User user = userRepository.findByPhoneNumber(addClientDTO.getPhoneNumber())
                .orElseGet(() -> userRepository.save(new User(addClientDTO.getPhoneNumber())));

        Client client = new Client(user, addClientDTO);
        clientRepository.save(client);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> edit(ClientDTO clientDTO) {
        Optional<Client> optionalClient = clientRepository.findById(clientDTO.getId());
        if (optionalClient.isEmpty())
            throw RestException.restThrow("NOT_FOUND", HttpStatus.NOT_FOUND);
        Client client = optionalClient.get();
        clientDTO.setToClient(client);
        clientRepository.save(client);
        return ApiResult.successResponse(true);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        if (!clientRepository.existsById(id))
            throw RestException.restThrow("NOT_FOUND",HttpStatus.NOT_FOUND);

        return ApiResult.successResponse(true);
    }

}

