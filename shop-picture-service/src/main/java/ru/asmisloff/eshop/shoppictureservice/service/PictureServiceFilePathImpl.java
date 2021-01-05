package ru.asmisloff.eshop.shoppictureservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.asmisloff.eshop.shopdatabase.entities.Picture;
import ru.asmisloff.eshop.shopdatabase.entities.PictureData;
import ru.asmisloff.eshop.shopdatabase.repositories.PictureRepository;
import ru.asmisloff.eshop.shoppictureservice.controller.repr.PictureRepr;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "pictureService.implementation", havingValue = "file")
public class PictureServiceFilePathImpl implements PictureService {

    private static final Logger logger = LoggerFactory.getLogger(PictureServiceFilePathImpl.class);

    private final PictureRepository pictureRepository;

    public PictureServiceFilePathImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return pictureRepository.findById(id)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        Optional<String> filePath = pictureRepository.findById(id).map(pic -> pic.getPictureData().getPath());
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
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        Picture picture = pictureRepository.findById(id).orElse(null);
        String filePath = picture.getPictureData().getPath();
        try {
            Files.delete(Paths.get(filePath));
            pictureRepository.deleteById(id);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<PictureRepr> findPicturesForProductId(Long id) {
        return pictureRepository.findAllByProductId(id)
                .stream()
                .map(PictureRepr::new)
                .collect(Collectors.toList());
    }
}
