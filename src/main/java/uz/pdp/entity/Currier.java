package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Currier extends AbsUser {

    @Column(nullable = false)
    private Long birthDate;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String carNumber;

    private String driverLicense;

    @NotNull
    private boolean online;


    public Currier(User user, Long birthDate, String firstName, String lastName, String carNumber, String driverLicense) {
        super(user);
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.carNumber = carNumber;
        this.driverLicense = driverLicense;
    }
}
