package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.entity.Client;
import uz.pdp.payload.filterPayload.ClientDTOView;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query(value = "SELECT * FROM get_query_result(?1)", nativeQuery = true)
    List<ClientDTOView> getAllUsersByStringQuery(String query);
}
