package aptech.apispb1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required") // why not blank? because it allows spaces
//    @Min(value = 3, message = "Name must be at least 3 characters")
//    @Max(value = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
//    @Min(value = 5, message = "Email must be at least 5 characters")
//    @Max(value = 50, message = "Email must be less than 50 characters")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than 100")
    @NotNull(message = "Age is required")
    private int age;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    private Date dob;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
//    @JsonIgnore
    @JsonIgnore
    private List<Book> books;
}
