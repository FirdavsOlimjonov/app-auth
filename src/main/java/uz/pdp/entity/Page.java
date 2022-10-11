package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.enums.PageEnum;

import javax.persistence.*;


/**
 * Me: muhammadqodir
 * Project: app-auth/IntelliJ IDEA
 * Date:Tue 11/10/22 16:01
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Page {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private PageEnum page;

    @ManyToOne
    private Role role;

    private int priority;

    public Page(PageEnum page, Role role, int priority) {
        this.page = page;
        this.role = role;
        this.priority = priority;
    }
}
