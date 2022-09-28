package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class Employee{

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
