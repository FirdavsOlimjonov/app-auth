package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.util.UserFields;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate // y we need this?
@DynamicInsert
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        accountNonExpired = accountNonLocked = credentialsNonExpired = true;
        enabled = UserFields.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AbsUser.authorities;
    }

    @Override
    public String getPassword() {
        return phoneNumber;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

}
