package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String username);
    boolean existsByPhoneNumber(String phoneNumber);


    @Query(value = "SELECT * FROM get_result_of_query(:query)", nativeQuery = true)
    List<User> getAllUsersByStringQuery(String query);
}
