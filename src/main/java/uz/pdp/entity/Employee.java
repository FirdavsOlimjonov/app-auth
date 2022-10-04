package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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
