package ru.asmisloff.eshop.shopdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asmisloff.eshop.shopdatabase.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
