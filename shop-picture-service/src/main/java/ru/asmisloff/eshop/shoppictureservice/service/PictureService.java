package ru.asmisloff.eshop.shoppictureservice.service;

import ru.asmisloff.eshop.shopdatabase.entities.PictureData;

import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    PictureData createPictureData(byte[] picture);
}
