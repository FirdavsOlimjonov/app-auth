package uz.pdp.payload;

import lombok.Data;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;

@Data
public class EmployeeDTO {

    private String firstName;

    private String lastName;

    private User user;

    private Role role;
}
