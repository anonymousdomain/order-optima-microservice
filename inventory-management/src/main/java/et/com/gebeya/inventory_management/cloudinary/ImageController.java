package et.com.gebeya.inventory_management.cloudinary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("api/v1/image")
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @PostMapping("/upload")
    public ResponseEntity<Map> upload(ImageModel imageModel) {
        try {
            return imageService.uploadImage(imageModel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}