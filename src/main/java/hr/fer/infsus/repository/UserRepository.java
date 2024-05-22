package hr.fer.infsus.repository;

import hr.fer.infsus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
