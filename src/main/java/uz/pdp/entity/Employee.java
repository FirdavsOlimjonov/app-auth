package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Employee extends AbsUser {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Role role;

    public Employee(String firstName, String lastName, User user, Role role) {
        super(user);
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
