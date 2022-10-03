package uz.pdp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEmployeeDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Integer roleId;
}