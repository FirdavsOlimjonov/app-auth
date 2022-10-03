package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Client;
import uz.pdp.entity.Employee;
import uz.pdp.payload.ClientDTO;
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

    @Override
    public ResponseEntity<List<Client>> getAll() {
        List<Client> all = clientRepository.findAll();
        return ResponseEntity.ok(all);

    }

    @Override
    public ResponseEntity<Client> getOne(UUID id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Override
    public ResponseEntity<Boolean> save(ClientDTO clientDTO) {
        if (userRepository.existsByPhoneNumber(clientDTO.getUser().getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }
        Client client=new Client();
        client.setName(client.getName());
        client.setUser(clientDTO.getUser());
        clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Boolean> edit(Client client, UUID id) {

        if (clientRepository.existsById(id)) {
            if (userRepository.existsByPhoneNumber(client.getUser().getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
            }

            clientRepository.save(client);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @Override
    public ResponseEntity<Boolean> delete(UUID id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

