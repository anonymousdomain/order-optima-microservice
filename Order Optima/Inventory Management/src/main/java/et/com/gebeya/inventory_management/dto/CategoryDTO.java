package et.com.gebeya.inventory_management.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {
    private String name;
    private String description;
    private String imageUrl;
}