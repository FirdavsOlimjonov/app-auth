package uz.pdp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.entity.PermissionEnum;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.repository.RoleRepository;
import uz.pdp.repository.UserRepository;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(ddlMode, "create")) {

            Role role = new Role();
//            role.setName(RoleEnum.ROLE_ADMIN.name());
            role.setDescription("Project egasi");
            role.setPermissions(Set.of(PermissionEnum.values()));
            roleRepository.save(role);


            Role roleUser = new Role();
//            roleUser.setName(RoleEnum.ROLE_USER.name());
            roleUser.setDescription("Foydalanuvchui");
            roleUser.setPermissions(Set.of(PermissionEnum.SOLVE_PROBLEM));
            roleRepository.save(roleUser);

            Role roleCurrier = new Role();
//            roleCurrier.set

            User admin = new User(
                    adminUsername,
                    passwordEncoder.encode(adminPassword));
//            admin.setRole(role);
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);

            userRepository.save(admin);
        }
    }
}
