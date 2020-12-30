package ru.asmisloff.eshop.shopdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asmisloff.eshop.shopdatabase.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
