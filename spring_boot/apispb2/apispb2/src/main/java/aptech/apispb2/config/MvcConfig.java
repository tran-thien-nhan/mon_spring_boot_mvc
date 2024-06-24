package aptech.apispb2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	  registry.addResourceHandler("/uploads/**")
                  .addResourceLocations("file:uploads/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Ánh xạ tất cả các đường dẫn
//                .allowedOrigins("http://localhost:3000") // Cho phép từ domain của ứng dụng React
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // Cho phép các phương thức
//                .allowedHeaders("*") // Cho phép tất cả các header
//                .allowCredentials(true) // Cho phép gửi cookie
//                .maxAge(3600); // Thời gian lưu trữ request trong cache (giây)
        registry.addMapping("/**");
    }
}
