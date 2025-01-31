package Book.dto;

import lombok.Data;


@Data
public class BookFileDto {
    private int idx;
    private int bookIdx;
    private String originalFileName;
    private String storedFilePath;
    private String fileSize;
}
