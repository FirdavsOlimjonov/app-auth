package uz.pdp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Client extends AbsUUIDEntity {

    @JoinColumn(unique = true)
    @OneToOne(optional = false)
    private User user;

    private LocalDate birthDate;

    @Column(nullable = false)
    private String name;
}
