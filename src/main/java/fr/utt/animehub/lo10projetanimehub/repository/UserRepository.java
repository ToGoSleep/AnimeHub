package fr.utt.animehub.lo10projetanimehub.repository;

import fr.utt.animehub.lo10projetanimehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
