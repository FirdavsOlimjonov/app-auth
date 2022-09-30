package uz.pdp.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.entity.Employee;

import java.util.Optional;
import java.util.UUID;

@Configuration
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByUserId(UUID user_id);

    Optional<Employee> findEmployeeByUserId(UUID uuid);

    @Query(value = "select empu.*\n" +
            "from (select e.*\n" +
            "      from employee e\n" +
            "               join users u on e.user_id = u.id\n" +
            "      where e.last_name like concat('%', :searchingField, '%')\n" +
            "         or e.first_name like concat('%', :searchingField, '%')\n" +
            "         or u.phone_number like concat('%', :phoneNumber, '%')) as empu\n" +
            "         join role r on empu.role_id = r.id\n" +
            "where r.name = :empType\n", nativeQuery = true)
    Page<Employee> searchBy(@Param("empType") String employeeType, String searchingField, Pageable pageRequest);
}
