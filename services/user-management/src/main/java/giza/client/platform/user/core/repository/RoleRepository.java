package giza.client.platform.user.core.repository;

import giza.client.platform.user.model.entity.Role;
import giza.client.platform.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
//    Set<User> getRolesByU
}
