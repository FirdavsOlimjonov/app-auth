package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.entity.enums.CurrierStatusEnum;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Currier  extends AbsUser {

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
