import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.asmisloff.eshop.common.repr.ProductRepr;
import ru.asmisloff.eshop.shopdatabase.entities.Brand;
import ru.asmisloff.eshop.shopdatabase.entities.Category;
import ru.asmisloff.eshop.shopui.model.OrderEntry;
import ru.asmisloff.eshop.shopui.service.CartService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;
    private OrderEntry orderEntry;

    private TestProductData addProduct(
            Long id, String name, String categoryName, String brandName, BigDecimal price, int qty
    ) {
        ProductRepr testProduct = new ProductRepr();

        testProduct.setId(id);
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setCategory(new Category(categoryName));
        testProduct.setBrand(new Brand(brandName));

        cartService.add(testProduct, qty);

        return new TestProductData(testProduct, name, categoryName, brandName, price, qty);
    }

    @BeforeEach
    public void init() {
        cartService = new CartService();
        orderEntry = new OrderEntry(1L, 1);
    }

    @Test
    public void testEmptyCart() {
        assertNotNull(cartService);
        assertTrue(cartService.getOrders().isEmpty());
    }

    @Test
    public void testOrderEntry() {
        assertNotNull(orderEntry);
        assertEquals(orderEntry.getId(), 1L);
        assertEquals(orderEntry.getQty(), 1);
    }

    @Test
    public void testAddingOfOneOrder() {
        TestProductData testProductData = addProduct(
                1L,"testName", "testCatName", "testBrandName",
                BigDecimal.valueOf(100500), 5
        );

        Map<ProductRepr, Integer> orders = cartService.getOrders();

        assertDoesNotThrow(() -> orders.keySet().iterator().next());
        ProductRepr productInCart = orders.keySet().iterator().next();
        assertFalse(orders.isEmpty());
        assertEquals(orders.size(), 1);
        assertSame(testProductData.testProduct, productInCart);
        assertEquals(orders.get(testProductData.testProduct), testProductData.qty);
        assertEquals(cartService.total(), testProductData.price.multiply(BigDecimal.valueOf(testProductData.qty)));
    }

    @Test
    public void testAddingOfTwoOrders() {
        TestProductData testProductData1 = addProduct(
                1L,"testName1", "testCatName", "testBrandName",
                BigDecimal.valueOf(100500), 5
        );
        TestProductData testProductData2 = addProduct(
                2L,"testName2", "testCatName", "testBrandName",
                BigDecimal.valueOf(100500), 5
        );

        Map<ProductRepr, Integer> orders = cartService.getOrders();

        assertFalse(orders.isEmpty());
        assertEquals(orders.size(), 2);
        assertEquals(
                cartService.total(),
                (
                        testProductData1.price.multiply(BigDecimal.valueOf(testProductData1.qty))
                        .add(
                                testProductData2.price.multiply(BigDecimal.valueOf(testProductData2.qty)
                                )
                        )
                )
        );

        for (TestProductData testProductData : List.of(testProductData1, testProductData2)) {
            assertTrue(orders.containsKey(testProductData.testProduct));
            assertEquals(orders.get(testProductData.testProduct), testProductData.qty);
        }
    }

    private class TestProductData {
        private final ProductRepr testProduct;
        private final String name;
        private final String categoryName;
        private final String brandName;
        private final BigDecimal price;
        private final int qty;

        public TestProductData(ProductRepr testProduct, String name, String categoryName, String brandName, BigDecimal price, int qty) {
            this.testProduct = testProduct;
            this.name = name;
            this.categoryName = categoryName;
            this.brandName = brandName;
            this.price = price;
            this.qty = qty;
        }
    }
}
