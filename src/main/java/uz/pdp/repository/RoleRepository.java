package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
