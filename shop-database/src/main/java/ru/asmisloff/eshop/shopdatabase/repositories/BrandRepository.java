package ru.asmisloff.eshop.shopdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asmisloff.eshop.shopdatabase.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
