package aptech.apispb2.controllers;

import aptech.apispb2.helper.ApiResponse;
import aptech.apispb2.helper.FileUpload;
import aptech.apispb2.models.Product;
import aptech.apispb2.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;
    private FileUpload fileUpload;

    private String subFolder = "productImage";
    private String uploadFolder = "uploads";

    //private String root = System.getProperty("user.dir");
    private String root = "http://localhost:8080/";
    private String urlImage = root + uploadFolder + File.separator + subFolder;
    public ProductController(ProductService productService, FileUpload fileUpload) {
        this.productService = productService;
        this.fileUpload = fileUpload;
    }

    @GetMapping
    public ApiResponse<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                return ApiResponse.notFound(products, "No products found", null);
            }
            return ApiResponse.success(products, "Get all products successfully");
        } catch (Exception e) {
            return ApiResponse.errorServer(null, "Error server", null);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getProductById(@PathVariable Long id) {
        try {
            return productService.findProductById(id)
                    .map(product -> ApiResponse.success(product, "Get product by id successfully"))
                    .orElseGet(() -> ApiResponse.notFound(null, "Product not found", null));
        } catch (Exception e) {
            return ApiResponse.errorServer(null, "Error server", null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteProductById(@PathVariable Long id) {
        try {

            Optional<Product> productExisted = productService.findProductById(id);
            String imageUrl = productExisted.get().getImage();
            if (!productExisted.isPresent()) {
                return ApiResponse.notFound(null, "Product not found", null);
            }

            //nếu xóa product mà id không tìm thấy thì trả về not found
            if (!productService.findProductById(id).isPresent()) {
                return ApiResponse.notFound(null, "Product not found", null);
            }

            productService.deleteProductById(id);
            //xóa hình trong folder

            if (imageUrl != null) {
                fileUpload.deleteImage(imageUrl.substring(root.length()));
            }

            return ApiResponse.success(null, "Delete product successfully");
        } catch (Exception e) {
            return ApiResponse.errorServer(null, "Error server", null);
        }
    }

    @PostMapping("/create")
    public ApiResponse<?> createProduct(@Valid @ModelAttribute Product product,
                                        BindingResult bindingResult,
                                        MultipartFile fileImage) { //@ModelAttribute để lấy hình ảnh từ client, test post man dạng form, khác với @RequestBody
        try {
            if (bindingResult.hasErrors()) {
                return ApiResponse.badRequest(bindingResult);
            }

            if(fileImage.getSize() > 0){
                String imageName = fileUpload.storeImage(subFolder, fileImage);
                //check duplicate imageName
//                if (productService.findProductByImage(imageName)) {
//                    return ApiResponse.duplicateValue(null, "Image already exists", null);
//                }
                String exactImageUrl = urlImage + File.separator + imageName;
                product.setImage(exactImageUrl.replace("\\", "/"));
            }
            else {
                bindingResult.rejectValue("image", "image", "Image is required"); //nghĩa là nếu không có hình ảnh thì thông báo lỗi
                return ApiResponse.badRequest(bindingResult);
            }

//            return productService.findProductById(product.getId())
//                    .map(p -> ApiResponse.duplicateValue(null, "Product already exists", null))
//                    .orElseGet(() -> ApiResponse.success(productService.saveProduct(product), "Create product successfully"));

                return ApiResponse.success(productService.saveProduct(product), "Create product successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.errorServer(null, "Error server", null);
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<?> updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult bindingResult, MultipartFile fileImage) {
        try {
            if (bindingResult.hasErrors()) {
                return ApiResponse.badRequest(bindingResult);
            }

            Optional<Product> productExisted = productService.findProductById(id);
            if (!productExisted.isPresent()) {
                return ApiResponse.notFound(null, "Product not found", null);
            }

            if(fileImage.getSize() > 0){
                String imageName = fileUpload.storeImage(subFolder, fileImage);
                String exactImageUrl = urlImage + File.separator + imageName;
                product.setImage(exactImageUrl.replace("\\", "/"));
                String imageUrl = productExisted.get().getImage();
                if (imageUrl != null) {
                    fileUpload.deleteImage(imageUrl.substring(root.length()));
                }
            } else {
                product.setImage(productExisted.get().getImage());
            }
            product.setId(id);
            Product saveProduct = productService.saveProduct(product);
            return ApiResponse.success(saveProduct, "Update product successfully");

//            return productService.findProductById(id)
//                    .map(p -> ApiResponse.success(productService.saveProduct(product), "Update product successfully"))
//                    .orElseGet(() -> ApiResponse.notFound(null, "Product not found", null));
        } catch (Exception e) {
            return ApiResponse.errorServer(null, "Error server", null);
        }
    }
}
