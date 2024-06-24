package aptech.apispb2.helper;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileUpload {
    @Value("${upload.folder}")
    private String uploadFolder;
    public String storeImage(String subFolder, MultipartFile multipartFile) throws IOException {
        String exactSubFolder = uploadFolder + File.separator + subFolder; //vd: /uploads/products
        File directory = new File(exactSubFolder); //tạo thư mục nếu chưa tồn tại
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename(); //vd: 1234567890_image.jpg
        Path path = Path.of(exactSubFolder + File.separator + fileName); //vd: /uploads/products/1234567890_image.jpg
        Files.copy(multipartFile.getInputStream(), path); //copy file từ client lên server
        //return fileName;
        return fileName;
    }

    public void deleteImage(String imageEisted) throws IOException {
        try {
            Path imageDelete = Path.of(imageEisted);
            Files.delete(imageDelete);
        } catch (IOException e) {
            throw new IOException("Cannot delete image");
        }
    }
}
