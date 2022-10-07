package uz.pdp.payload.response_DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.entity.enums.PermissionEnum;

import javax.persistence.Column;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    private String phoneNumber;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private Set<PermissionEnum> permissions;

    private String name;

    public UserDTO(User user, Role role) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
        if (Objects.nonNull(role))
            this.permissions = role.getPermissions();
    }

    public static UserDTO mapping(User user, Role role) {
        return new UserDTO(user, role);
    }

}

