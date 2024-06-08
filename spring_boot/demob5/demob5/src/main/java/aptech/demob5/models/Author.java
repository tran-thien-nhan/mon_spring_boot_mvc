package aptech.demob5.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 18, min = 3, message = "Name must be between 3 and 18 characters")
    private String name;

    @Min(value = 18, message = "Age must be greater than 18")
    @Max(value = 35, message = "Age must be less than 35")
    private int age;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Date is mandatory")
    @Past(message = "DOB should be in the past")
    private Date dob;

    //1-n
    @OneToMany(mappedBy = "author",cascade = CascadeType.MERGE)
    private List<Book> books;
}
