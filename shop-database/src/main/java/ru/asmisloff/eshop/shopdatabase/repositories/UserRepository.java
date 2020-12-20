package ru.asmisloff.eshop.shopdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asmisloff.eshop.shopdatabase.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
