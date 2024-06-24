package aptech.apispb2.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;



public class AppConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Ánh xạ tất cả các đường dẫn
                        .allowedOrigins("http://localhost:3000") // Cho phép từ domain của ứng dụng React
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Cho phép các phương thức
                        .allowedHeaders("*") // Cho phép tất cả các header
                        .allowCredentials(true) // Cho phép gửi cookie
                        .maxAge(3600); // Thời gian lưu trữ request trong cache (giây)
            }
        };
    }
}
