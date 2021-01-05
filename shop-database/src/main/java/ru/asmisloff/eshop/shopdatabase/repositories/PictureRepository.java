package ru.asmisloff.eshop.shopdatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.asmisloff.eshop.shopdatabase.entities.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findAllByProductId(Long id);
}
