// package et.com.gebeya.user_service.service;

// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.util.Map;
// import java.util.UUID;

// @Service
// @RequiredArgsConstructor
// public class CloudinaryService {
//     private final Cloudinary cloudinary;

//     public String uploadImage(MultipartFile file) throws IOException{
// //        return cloudinary.uploader()
// //                .upload(file.getBytes(),
// //                        Map.of("public_id", UUID.randomUUID().toString()))
// //                .get("url")
// //                .toString();
//         Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//         return (String) uploadResult.get("url");
//     }

// }
