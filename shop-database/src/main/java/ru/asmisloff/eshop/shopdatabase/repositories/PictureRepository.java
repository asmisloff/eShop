package ru.asmisloff.eshop.shopdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asmisloff.eshop.shopdatabase.entities.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
