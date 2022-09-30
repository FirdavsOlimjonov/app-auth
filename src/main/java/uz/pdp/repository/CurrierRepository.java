package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Currier;

import java.util.Optional;
import java.util.UUID;

public interface CurrierRepository extends JpaRepository<Currier, UUID> {

    boolean existsByCarNumber(String carNumber);

    boolean existsByDriverLicense(String driverLicense);

    Optional<Currier> findById(UUID id);
}
