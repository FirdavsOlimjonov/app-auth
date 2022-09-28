package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate///
public class Employee {

    //
    @Id
    @OneToOne
    private User user;
    //salom
    @ManyToOne(fetch = FetchType.LAZY)
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
