package uz.pdp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Getter
@ToString
@AllArgsConstructor
public class SignDTO implements Serializable {

    @NotBlank(message = "{MUST_NOT_BE_BLANK_EMAIL}")
    @Email
    private String phoneNumber;

    @NotBlank(message = "{MUST_NOT_BE_BLANK_PASSWORD}")
    private String password;


    @Override
    public boolean equals(Object obj) {
        SignDTO other = (SignDTO) obj;

        if (!other.getPhoneNumber().equals(getPhoneNumber()))
            return false;

        return other.getPassword().equals(getPassword());
    }
}
