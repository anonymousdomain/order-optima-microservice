package et.com.gebeya.inventory_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import et.com.gebeya.inventory_management.dto.request.StockAdjustmentDTO;
import et.com.gebeya.inventory_management.dto.response.ListAllProductUnderCategoryResponse;
import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
import et.com.gebeya.inventory_management.dto.response.ProductUpdateResponse;
import et.com.gebeya.inventory_management.dto.response.QuantityResponse;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import et.com.gebeya.inventory_management.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private ObjectMapper objectMapper;
    private ProductRepository productRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ProductCreationResponse> registerProduct(@Valid @RequestBody ProductCreationRequest productRequest) {
        ProductCreationResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<ProductCreationResponse> createProducts(
            @RequestPart("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            ProductCreationRequest request = objectMapper.readValue(productJson, ProductCreationRequest.class);
            ProductCreationResponse response = productService.createProducts(request, imageFile);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping("/get/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        try {
            return productService.getProductById(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody RequestForUpdate update) {
        try {
            return productService.updateProduct(id, update);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.searchByName(name);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ListAllProductUnderCategoryResponse> listAllProductsUnderCategory(@PathVariable Long categoryId) {
        ListAllProductUnderCategoryResponse response = productService.listAllProductsUnderCategory(categoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{productId}/stock")
    public ResponseEntity<ResponseEntity<QuantityResponse>> getTotalStock(@PathVariable Long productId) {
        ResponseEntity<QuantityResponse> totalQuantity = productService.getTotalQuantity(productId);
        return ResponseEntity.ok(totalQuantity);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/restock")
    public ResponseEntity<Void> restockProduct(@RequestBody StockAdjustmentDTO stockAdjustment) {
        productService.restockProduct(stockAdjustment.getProductId(), stockAdjustment.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/order")
    public ResponseEntity<ProductUpdateResponse> orderProduct(@RequestBody StockAdjustmentDTO stockAdjustment) {
        Product product = productService.decreaseStock(stockAdjustment.getProductId(), stockAdjustment.getQuantity());
        ProductUpdateResponse response = new ProductUpdateResponse(product.getName(), product.getQuantity());
        return ResponseEntity.ok(response);
}
    @PostMapping("/byIds")
    public ResponseEntity<List<Product>> getProductsByIds(@RequestBody List<Long> ids) {
        List<Product> products = productRepository.findByIdIn(ids);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }
}