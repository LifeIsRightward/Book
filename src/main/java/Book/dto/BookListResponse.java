package Book.dto;

import lombok.Data;

@Data
public class BookListResponse {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String publishedDt;
    private String isbn;
    private String description;
    private String createdDt;
    private String updatedDt;
}
