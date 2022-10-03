package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String password;

    private User user;
}
