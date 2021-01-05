package ru.asmisloff.eshop.shoppictureservice.service;

import ru.asmisloff.eshop.shopdatabase.entities.PictureData;
import ru.asmisloff.eshop.shoppictureservice.controller.repr.PictureRepr;

import java.util.List;
import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    PictureData createPictureData(byte[] picture);

    void delete(long id);

    List<PictureRepr> findPicturesForProductId(Long id);
}
