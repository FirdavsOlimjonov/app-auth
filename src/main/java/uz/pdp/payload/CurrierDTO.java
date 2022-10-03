package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
@Data
@AllArgsConstructor
public class CurrierDTO {

    private LocalDate birthDate;

    private String firstName;

    private String lastName;

    private String carNumber;

    private String driverLicense;
}
