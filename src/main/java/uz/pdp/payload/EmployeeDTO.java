package uz.pdp.payload;

import lombok.Data;
import uz.pdp.entity.Role;

@Data
public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private String middleName;

    private String phoneNumber;

    private Role role;
}
