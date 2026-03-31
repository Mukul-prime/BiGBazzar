package com.example.BigBazzarServer.Service;

import com.example.BigBazzarServer.DAO.ProductDAO;
import com.example.BigBazzarServer.DAO.SellerDAO;
import com.example.BigBazzarServer.DTO.Request.ProductRequest;
import com.example.BigBazzarServer.DTO.Response.ProductResponse;
import com.example.BigBazzarServer.Model.Product;
import com.example.BigBazzarServer.Model.ProductImages;
import com.example.BigBazzarServer.Model.Seller;
import com.example.BigBazzarServer.utlity.Enum.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private SellerDAO sellerDAO;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProductShouldSaveEachUploadedImageAsSeparateEntity() throws Exception {
        String email = "seller@test.com";
        Seller seller = Seller.builder()
                .sellerId(1)
                .email(email)
                .build();

        MockMultipartFile firstImage = new MockMultipartFile(
                "banner",
                "first.png",
                "image/png",
                "first-image".getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile secondImage = new MockMultipartFile(
                "banner",
                "second.png",
                "image/png",
                "second-image".getBytes(StandardCharsets.UTF_8)
        );

        ProductRequest request = new ProductRequest();
        request.setName("Milk");
        request.setPrice(50);
        request.setDescription("Fresh milk");
        request.setCategory(Category.DAIRY);
        request.setBirthDateBody("2026-04-05");
        request.setQuantity(10);
        request.setBanner(List.of(firstImage, secondImage));

        when(sellerDAO.findByEmail(email)).thenReturn(seller);
        when(productDAO.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String response = productService.CreateProduct(request, email);

        assertEquals("Product loaded", response);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productDAO).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertSame(seller, savedProduct.getSeller());
        assertEquals(2, savedProduct.getProductImagesList().size());

        ProductImages savedFirstImage = savedProduct.getProductImagesList().get(0);
        ProductImages savedSecondImage = savedProduct.getProductImagesList().get(1);

        assertNotSame(savedFirstImage, savedSecondImage);
        assertSame(savedProduct, savedFirstImage.getProduct());
        assertSame(savedProduct, savedSecondImage.getProduct());
        assertArrayEquals(firstImage.getBytes(), savedFirstImage.getImage());
        assertArrayEquals(secondImage.getBytes(), savedSecondImage.getImage());
    }

    @Test
    void getAllProductsShouldPopulateBannerUrls() {
        Product product = Product.builder()
                .productId(10)
                .name("Milk")
                .price(50)
                .description("Fresh milk")
                .category(Category.DAIRY)
                .quantity(10)
                .build();

        ProductImages firstImage = ProductImages.builder()
                .image("first-image".getBytes(StandardCharsets.UTF_8))
                .product(product)
                .build();
        ProductImages secondImage = ProductImages.builder()
                .image("second-image".getBytes(StandardCharsets.UTF_8))
                .product(product)
                .build();
        product.setProductImagesList(List.of(firstImage, secondImage));

        when(productDAO.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(product)));

        Page<ProductResponse> response = productService.getAllProducts(PageRequest.of(0, 10));

        assertEquals(1, response.getTotalElements());
        assertEquals(
                List.of(
                        Base64.getEncoder().encodeToString(firstImage.getImage()),
                        Base64.getEncoder().encodeToString(secondImage.getImage())
                ),
                response.getContent().get(0).getBannerurl()
        );
    }
}
