package com.clogs.matriculaspring.repository;

import com.clogs.matriculaspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);

    // Optional vai garantir que o usuário exista antes de retorná-lo
    public Optional<User> findByEmail(String email);
}
