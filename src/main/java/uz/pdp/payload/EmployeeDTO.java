package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Integer roleId;
}
