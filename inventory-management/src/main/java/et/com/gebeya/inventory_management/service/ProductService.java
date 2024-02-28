package et.com.gebeya.inventory_management.service;

import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import et.com.gebeya.inventory_management.repos.ProductRepository;
import et.com.gebeya.inventory_management.utility.CustomMappingFunctions;
import et.com.gebeya.inventory_management.utility.MappingFunctions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private final CustomMappingFunctions customMappingFunctions;

    private MappingFunctions mappingFunctions;
    public ProductCreationResponse createProduct(ProductCreationRequest request, MultipartFile imageFile) {
        return customMappingFunctions.createProductAndConvertToResponse(request, imageFile);
    }
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setDiscount(product.getDiscount());
        dto.setImageUrl(product.getImageUrl());
        dto.setCalories(product.getCalories());
        dto.setFat(product.getFat());
        dto.setProtein(product.getProtein());
        dto.setWeight(product.getWeight());
        dto.setSize(product.getSize());
        dto.setVolume(product.getVolume());
        dto.setBrands(product.getBrands());
        dto.setCategory(product.getCategory());
        return dto;
    }
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mappingFunctions.convertToDTOForProduct(product);
    }
    public ProductDTO updateProduct(Long id, RequestForUpdate update) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        mappingFunctions.updateEntityWithDTOForProduct(update, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return mappingFunctions.convertToDTOForProduct(updatedProduct);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    public List<Product> getProductsUnderVendor(Long id) {
        return null;
    }
    public void updateStock(Long productId, int quantity) {

    }

}