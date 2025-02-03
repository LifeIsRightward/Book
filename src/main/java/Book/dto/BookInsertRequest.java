package Book.dto;

import lombok.Data;

@Data
public class BookInsertRequest {
    private String title;
    private String description;
}
