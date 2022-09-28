package uz.pdp.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Employee;

import java.util.UUID;

@Configuration
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);

}
