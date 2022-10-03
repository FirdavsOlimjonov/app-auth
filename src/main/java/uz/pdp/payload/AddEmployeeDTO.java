package uz.pdp.payload;

import lombok.Getter;

@Getter
public class AddEmployeeDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Integer roleId;
}