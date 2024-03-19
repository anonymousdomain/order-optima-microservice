package et.com.gebeya.inventory_management.controller;

import et.com.gebeya.inventory_management.Models.Category;
import et.com.gebeya.inventory_management.dto.CategoryDTO;
import et.com.gebeya.inventory_management.dto.request.CategoryRegistrationRequest;
import et.com.gebeya.inventory_management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/all")
    public List<CategoryDTO> getCategory(){
        return categoryService.listAllCategory();
    }
    @GetMapping("/get/{id}")
    public CategoryRegistrationRequest getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }
    @PostMapping("/create")
    public CategoryRegistrationRequest addCategory(@RequestBody @Valid CategoryRegistrationRequest request){
         return categoryService.createCategory(request);
    }
    @PutMapping("/put/{id}")
    public CategoryRegistrationRequest updateCategory(@PathVariable Long id, @RequestBody CategoryRegistrationRequest request){
        return categoryService.updateCategory(id, request);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
