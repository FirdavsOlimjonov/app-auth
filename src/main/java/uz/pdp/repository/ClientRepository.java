package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Client;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByUserId(UUID id);
    Optional<Client> findByUser_PhoneNumber(String user_phoneNumber);
    boolean existsByUser_PhoneNumber(String user_phoneNumber);
}
