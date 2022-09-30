package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @JoinColumn(unique = true)
    @OneToOne(optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Role role;

    public String getPassword() {
        return user.getPassword();//
    }

    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }

//   @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return role.getPermissions();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.user.getPhoneNumber();
//    }

}
