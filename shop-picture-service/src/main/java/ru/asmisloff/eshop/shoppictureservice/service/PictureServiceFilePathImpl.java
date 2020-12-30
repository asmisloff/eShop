package ru.asmisloff.eshop.shoppictureservice.service;

import org.springframework.stereotype.Service;
import ru.asmisloff.eshop.shopdatabase.entities.Picture;
import ru.asmisloff.eshop.shopdatabase.entities.PictureData;
import ru.asmisloff.eshop.shopdatabase.repositories.PictureRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureServiceFilePathImpl implements PictureService {

    private final PictureRepository repository;

    public PictureServiceFilePathImpl(PictureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return repository.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        Optional<String> filePath = repository.findById(id).map(pic -> pic.getPictureData().getPath());
        if (filePath.isPresent()) {
            Path nioPath = Paths.get(filePath.get());
            try {
                byte[] data = Files.readAllBytes(nioPath);
                return Optional.of(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Optional.empty();
    }

    @Override
    public PictureData createPictureData(byte[] picture) {

        String filePath = "img/" + UUID.randomUUID().toString();
        try {
            Path nioPath = Paths.get(filePath);
            Files.write(nioPath, picture);
            return new PictureData(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PictureData();
    }

}
