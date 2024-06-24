package aptech.apispb1.dtos;

import aptech.apispb1.models.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private int year;
    AuthorDTO authorDto;
}
