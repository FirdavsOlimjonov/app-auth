package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.entity.enums.CurrierStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Currier {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @JoinColumn(unique = true)
    @OneToOne(optional = false)
    private User user;
    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String carNumber;

    private String driverLicense;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CurrierStatusEnum role = CurrierStatusEnum.ONLINE;

    public Currier(LocalDate birthDate, String firstName, String lastName, String carNumber, String driverLicense) {
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.carNumber = carNumber;
        this.driverLicense = driverLicense;
    }
}
