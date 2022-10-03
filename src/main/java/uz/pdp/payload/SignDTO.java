package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Getter
@AllArgsConstructor
public class SignDTO implements Serializable {

    @NotBlank(message = "{MUST_NOT_BE_BLANK_EMAIL}")
    @Email
    private String phoneNumber;

    @NotBlank(message = "{MUST_NOT_BE_BLANK_PASSWORD}")
    private String password;
}
