package uz.pdp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.entity.Employee;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.repository.EmployeeRepository;
import uz.pdp.repository.RoleRepository;
import uz.pdp.repository.UserRepository;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Value("${app.admin.phoneNumber}")
    private String adminPhoneNumber;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(ddlMode, "create") || Objects.equals(ddlMode, "create-drop")) {
            User admin = new User(
                    adminPhoneNumber,
                    passwordEncoder.encode(adminPassword));
            admin.setEnabled(true);
            admin = userRepository.save(admin);

            Role roleSuperAdmin = new Role();
            roleSuperAdmin.setName("SUPER_ADMIN");
            roleSuperAdmin.setDescription("Owner of this project");
            roleSuperAdmin.setPermissions(Set.of(PermissionEnum.values()));
            roleSuperAdmin = roleRepository.save(roleSuperAdmin);

            Employee superAdmin = new Employee();
            superAdmin.setUser(admin);
            superAdmin.setRole(roleSuperAdmin);
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            employeeRepository.save(superAdmin);
        }
    }
}
