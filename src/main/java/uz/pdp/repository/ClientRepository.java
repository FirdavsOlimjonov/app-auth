package uz.pdp.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Client;
import uz.pdp.entity.Employee;

import java.util.Optional;
import java.util.UUID;

@Configuration
public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByUserId(UUID user_id);


}
