package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.pdp.payload.add_DTO.AddClientDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Client extends AbsUser {

    private Long birthDate;

    @Column(nullable = false)
    private String name;

    public Client(User user, Long birthDate, String name) {
        super(user);
        this.birthDate = birthDate;
        this.name = name;
    }

    public Client(User user, AddClientDTO addClientDTO) {
        super(user);
        this.birthDate = addClientDTO.getBirthDate();
        this.name = addClientDTO.getName();
    }
}
