package uz.pdp.payload;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
public class ClientDTO {

    private UUID id;

    private String phoneNumber;

    private String name;

    private LocalDate birthDate;
}
