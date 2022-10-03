package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entity.Client;
import uz.pdp.payload.filterPayload.ClientDTOView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByUserId(UUID id);

    @Query(value = "SELECT * FROM get_query_result(?1)", nativeQuery = true)
    List<ClientDTOView> getAllUsersByStringQuery(String query);
}
