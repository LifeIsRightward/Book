package Book.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookInsertRequest {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publishedDt;
    private String isbn;
    private String description;
    private String createdDt;
    private String updatedDt;
}
