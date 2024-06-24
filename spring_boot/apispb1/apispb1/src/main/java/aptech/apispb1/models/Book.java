package aptech.apispb1.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    //@Past(message = "Publish date must be in the past")
    @NotNull(message = "Publish date is required")
    private int year;

    @ManyToOne(fetch = FetchType.EAGER) //eager loading là khi lấy dữ liệu từ 2 bảng mà không cần phải join
    //@JsonIgnore
    @JoinColumn(name = "author_id")
    private Author author;
}
