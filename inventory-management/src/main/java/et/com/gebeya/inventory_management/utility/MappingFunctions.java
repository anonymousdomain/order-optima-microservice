package et.com.gebeya.inventory_management.utility;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.Models.Product;
import et.com.gebeya.inventory_management.dto.CategoryDTO;
import et.com.gebeya.inventory_management.dto.CreateProductRequest;
import et.com.gebeya.inventory_management.dto.ProductDTO;
import et.com.gebeya.inventory_management.dto.request.CategoryRegistrationRequest;
import et.com.gebeya.inventory_management.dto.request.RequestForUpdate;
import org.springframework.stereotype.Component;

@Component
public class MappingFunctions {

    public CategoryRegistrationRequest convertToDTOForCategory(Category category) {
        CategoryRegistrationRequest dto = new CategoryRegistrationRequest();
        dto.setName(category.getName());
        dto.setTittle(category.getTittle());
        dto.setMetaTittle(category.getMetaTittle());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        return dto;
    }
    public Category convertToEntityForCategory(CategoryRegistrationRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setTittle(request.getTittle());
        category.setMetaTittle(request.getMetaTittle());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        return category;
    }
    public void updateEntityWithDtoForCategory(CategoryDTO dto, Category category) {
        category.setName(dto.getName());
        category.setTittle(dto.getTittle());
        category.setMetaTittle(dto.getMetaTittle());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
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
        //dto.setCategory(product.getCategory());
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

}
