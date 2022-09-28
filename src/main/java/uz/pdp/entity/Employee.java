package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class Employee implements Serializable {

    @Id
    @OneToOne
    private User user;

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

}
