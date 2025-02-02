package Book.dto;

import lombok.Data;

@Data
public class BookFileDto {
    private int bookId;               // book_id와 매핑
    private String storedFilePath;    // image_url과 매핑
    private String originalFileName;  // 원본 파일 이름 (필요하면 사용)
    private String createdAt;         // created_at과 매핑
}
