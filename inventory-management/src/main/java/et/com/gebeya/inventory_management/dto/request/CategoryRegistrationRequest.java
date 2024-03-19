package et.com.gebeya.inventory_management.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRegistrationRequest {
    @Valid
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Title cannot be null")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    @Size(min = 4, max = 255, message = "Meta title must be up to 255 characters")
    private String metaTitle;

    @Size(min = 4, max = 500, message = "Description must be up to 500 characters")
    private String description;
    private String imageUrl;
}
