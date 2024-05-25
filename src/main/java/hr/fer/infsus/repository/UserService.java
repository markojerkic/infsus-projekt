package hr.fer.infsus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.infsus.model.User;

public interface UserService extends JpaRepository<User, Long> {

}
