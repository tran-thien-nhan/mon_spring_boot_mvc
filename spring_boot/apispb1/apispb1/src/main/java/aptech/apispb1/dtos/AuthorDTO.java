package aptech.apispb1.dtos;

import aptech.apispb1.models.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    private Date dob;
}
