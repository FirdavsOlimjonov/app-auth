package uz.pdp.appauth.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appauth.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

}
