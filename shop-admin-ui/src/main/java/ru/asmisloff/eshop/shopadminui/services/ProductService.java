package ru.asmisloff.eshop.shopadminui.services;

import ru.asmisloff.eshop.shopadminui.controller.repr.ProductRepr;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(Long id);

    void deleteById(Long id);

    void save(ProductRepr product) throws IOException;
}
