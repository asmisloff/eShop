package ru.asmisloff.eshop.shoppictureservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.asmisloff.eshop.shopdatabase.entities.Picture;
import ru.asmisloff.eshop.shopdatabase.entities.PictureData;
import ru.asmisloff.eshop.shopdatabase.repositories.PictureRepository;

import java.util.Optional;

@Service
@ConditionalOnProperty(name = "pictureService.implementation", havingValue = "BLOB")
public class PictureServiceBlobImpl implements PictureService {

    private final PictureRepository repository;

    @Autowired
    public PictureServiceBlobImpl(PictureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return repository.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        return repository.findById(id)
                .map(pic -> pic.getPictureData().getData());
    }

    @Override
    public PictureData createPictureData(byte[] picture) {
        return new PictureData(picture);
    }
}
