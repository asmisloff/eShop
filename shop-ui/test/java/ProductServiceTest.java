import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.asmisloff.eshop.common.error.NotFoundException;
import ru.asmisloff.eshop.common.repr.ProductRepr;
import ru.asmisloff.eshop.common.service.ProductService;
import ru.asmisloff.eshop.common.service.ProductServiceImpl;
import ru.asmisloff.eshop.shopdatabase.entities.Brand;
import ru.asmisloff.eshop.shopdatabase.entities.Category;
import ru.asmisloff.eshop.shopdatabase.entities.Product;
import ru.asmisloff.eshop.shopdatabase.repositories.ProductRepository;
import ru.asmisloff.eshop.shoppictureservice.service.PictureService;
import ru.asmisloff.eshop.shoppictureservice.service.PictureServiceFilePathImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        PictureService pictureService = mock(PictureServiceFilePathImpl.class);
        productService = new ProductServiceImpl(productRepository, pictureService);
    }

    @Test
    public void testFindById() {
        Product expectedProduct = createMockProduct(1L, "1", "cat1", "brand1", BigDecimal.valueOf(1333));

        when(productRepository.findById(eq(1L)))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductRepr> opt = productService.findById(1L);
        assertTrue(opt.isPresent());
        assertProductReprEquality(opt.get(), new ProductRepr(expectedProduct));
    }

    @Test
    public void testFindAll() {
        List<Product> expected = List.of(createMockProduct(), createMockProduct(), createMockProduct());

        when(productRepository.findAll())
                .thenReturn(expected);

        List<ProductRepr> actual = productService.findAll();
        assertNotNull(actual);
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertProductReprEquality(new ProductRepr(expected.get(i)), actual.get(i));
        }
    }

    @Test
    public void deleteById() {
        List<Product> expected = new ArrayList<>(
                List.of(createMockProduct(), createMockProduct(), createMockProduct())
        );
        Product productToDelete = expected.get(0);

        doAnswer((o) -> expected.remove(0))
                .when(productRepository)
                .deleteById(eq(expected.get(0).getId()));


        productService.deleteById(productToDelete.getId());

        assertFalse(expected.contains(productToDelete));
    }

    @Test
    public void testSaveAlreadyKeptInDb() throws IOException {
        Product productKeptInDb = createMockProduct();

        when(productRepository.findById(productKeptInDb.getId()))
                .thenReturn(Optional.of(productKeptInDb));
        when(productRepository.save(eq(productKeptInDb)))
                .thenReturn(productKeptInDb);

        assertDoesNotThrow(
                () -> productService.save(new ProductRepr(productKeptInDb))
        );
    }

    @Test
    public void testSaveNotKeptInDb() {
        Product productNotKeptInDb = createMockProduct();

        when(productRepository.findById(productNotKeptInDb.getId()))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> productService.save(new ProductRepr(productNotKeptInDb))
        );
    }

    @Test
    public void testSaveProductWithNullId() {
        Product p = createMockProduct();
        p.setId(null);
    }

    private Product createMockProduct(Long id, String name, String categoryName, String brandName, BigDecimal price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);

        Category cat = new Category(categoryName);
        cat.setId(1L);
        cat.setName("mockCat");
        product.setCategory(cat);

        Brand brand = new Brand(brandName);
        brand.setId(1L);
        brand.setName("mockBrand");

        product.setBrand(brand);
        product.setPrice(price);

        product.setPictures(new ArrayList<>());

        return product;
    }

    private Product createMockProduct() {
        return createMockProduct(
                ThreadLocalRandom.current().nextLong(),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                RandomStringUtils.random(10),
                BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(500, 100500)));
    }

    private void assertProductReprEquality(ProductRepr pr1, ProductRepr pr2) {
        assertEquals(pr1.getId(), pr2.getId());
        assertEquals(pr1.getName(), pr2.getName());
        assertEquals(pr1.getBrand(), pr2.getBrand());
        assertEquals(pr1.getCategory(), pr2.getCategory());
        assertEquals(pr1.getPrice(), pr2.getPrice());
    }

}
