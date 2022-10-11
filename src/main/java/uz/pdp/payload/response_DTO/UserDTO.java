package uz.pdp.payload.response_DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.entity.enums.PermissionEnum;

import java.util.*;

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

    private Map<String, Boolean> capable;

    public UserDTO(User user, Role role) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
        setRole(role);
    }

    public static UserDTO mapping(User user, Role role) {
        return new UserDTO(user, role);
    }

    public void setRole(Role role) {
        if (Objects.isNull(role) || Objects.isNull(role.getPermissions()))
            return;
        this.permissions = role.getPermissions();
        this.capable = new HashMap<>(permissions.size(), 3f);

        for (PermissionEnum p : permissions)
            capable.put(p.getFieldName(), Boolean.TRUE);

    }

}

