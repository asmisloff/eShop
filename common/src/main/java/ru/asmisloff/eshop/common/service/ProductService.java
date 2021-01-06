package ru.asmisloff.eshop.common.service;

import ru.asmisloff.eshop.common.repr.ProductRepr;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductRepr> findAll();

    Optional<ProductRepr> findById(Long id);

    void deleteById(Long id);

    void save(ProductRepr product) throws IOException;
}
