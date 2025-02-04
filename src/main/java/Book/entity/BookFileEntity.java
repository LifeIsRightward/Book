package Book.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name= "bookimages")
@Data
@DynamicUpdate
public class BookFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;                  // image_id와 매핑

    //private int bookId;               // book_id와 매핑

    @Column(name = "image_url", nullable = false)
    private String storedFilePath;    // image_url과 매핑

    @Column(nullable = false)
    private String originalFileName;  // 원본 파일 이름 (필요하면 사용)

    @UpdateTimestamp
    private String createdAt;         // created_at과 매핑

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id") //
    private BookEntity book;

}
