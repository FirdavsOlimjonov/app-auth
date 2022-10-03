package uz.pdp.payload.response_DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.entity.Client;

import java.time.LocalDate;
import java.util.UUID;
@Data
@AllArgsConstructor
public class ClientDTO {

    private UUID id;

    private String phoneNumber;

    private String name;

    private Long birthDate;

    public static ClientDTO mapping(Client client) {
        return new ClientDTO(client.getId(),client.getUser().getPhoneNumber(),
                client.getName(), client.getBirthDate());
    }

}
