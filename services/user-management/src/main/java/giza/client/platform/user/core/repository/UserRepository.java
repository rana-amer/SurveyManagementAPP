package giza.client.platform.user.core.repository;

import giza.client.platform.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
    @Query("SELECT user FROM User user LEFT JOIN user.roles role WHERE role.ID = ?1")
    List<User> findUsersByRoles(Long roleID);
}
