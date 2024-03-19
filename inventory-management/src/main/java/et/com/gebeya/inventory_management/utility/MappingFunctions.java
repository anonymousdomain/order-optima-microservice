package et.com.gebeya.inventory_management.utility;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.cloudinary.ImageModel;
import et.com.gebeya.inventory_management.cloudinary.ImageServiceImpl;
import et.com.gebeya.inventory_management.dto.CreateProductRequest;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.CategoryDto;
import et.com.gebeya.inventory_management.dto.request.CategoryRegistrationRequest;
import et.com.gebeya.inventory_management.dto.request.ProductCreationRequest;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import et.com.gebeya.inventory_management.dto.response.ProductCreationResponse;
import et.com.gebeya.inventory_management.exceptions.ResourceNotFoundException;
import et.com.gebeya.inventory_management.repos.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
@AllArgsConstructor
public class MappingFunctions {
    private final CategoryRepository categoryRepository;

    public CategoryRegistrationRequest convertToDTOForCategory(Category category) {
        CategoryRegistrationRequest request = new CategoryRegistrationRequest();
        request.setName(category.getName());
        request.setTitle(category.getTitle());
        request.setMetaTitle(category.getMetaTitle());
        request.setDescription(category.getDescription());
        request.setImageUrl(category.getImageUrl());
        return request;
    }
    public Category convertToEntityForCategory(CategoryRegistrationRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setTitle(request.getTitle());
        category.setMetaTitle(request.getMetaTitle());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        return category;
    }
    public Category updateEntityWithDtoForCategory(CategoryRegistrationRequest dto, Category category) {
        category.setName(dto.getName());
        category.setTitle(dto.getTitle());
        category.setMetaTitle(dto.getMetaTitle());
        category.setDescription(dto.getDescription());
        return category;
    }


    public ProductDTO convertToDTOForProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setQuantity(product.getQuantity());
        dto.setCalories(product.getCalories());
        dto.setFat(product.getFat());
        dto.setProtein(product.getProtein());
        dto.setWeight(product.getWeight());
        dto.setSize(product.getSize());
        dto.setVolume(product.getVolume());
        dto.setBrands(product.getBrands());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
        return dto;
    }

    public Product convertToEntityForProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }
    public Product convertToEntityForProductWithCategory(CreateProductRequest dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    public void updateEntityWithDTOForProduct(RequestForUpdate dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setQuantity(dto.getQuantity());
        //product.setCategory(dto.getCategory());
        product.setCalories(dto.getCalories());
        product.setFat(dto.getFat());
        product.setProtein(dto.getProtein());
        product.setWeight(dto.getCalories());
        product.setSize(dto.getSize());
        product.setVolume(dto.getVolume());
        product.setBrands(dto.getBrands());
        product.setImageUrl(dto.getImageUrl());
    }
    public Product mapToProduct(ProductCreationRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("The Category you provided is not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setDescription(request.getDescription());
        product.setImageUrl(request.getImageUrl());
        product.setCalories(request.getCalories());
        product.setFat(request.getFat());
        product.setProtein(request.getProtein());
        product.setWeight(request.getWeight());
        product.setSize(request.getSize());
        product.setVolume(request.getVolume());
        product.setBrands(request.getBrands());
        product.setCategory(category);
        return product;
    }
    public ProductCreationResponse mapToProductCreationResponse(Product product) {
        ProductCreationResponse response = new ProductCreationResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setCalories(product.getCalories());
        response.setFat(product.getFat());
        response.setProtein(product.getProtein());
        response.setWeight(product.getWeight());
        response.setSize(product.getSize());
        response.setVolume(product.getVolume());
        response.setBrands(product.getBrands());
        response.setCategory(new CategoryDto(product.getCategory().getId(), product.getCategory().getName()));
        return response;
    }

}
