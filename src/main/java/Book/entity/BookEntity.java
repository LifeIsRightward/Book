package Book.entity;


import Book.dto.BookFileDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@DynamicUpdate // 변경된 필드만 업데이트시킬 수 있음 -> 다이나믹업데이트 어노테이션
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    // 여기 아래는 다 nullable true -> 널 가능 컬럼을 별도 지정해주지 않아도 됨.
    private String publisher;

    private LocalDateTime publishedDt;

    private String isbn;

    private String description;

    @UpdateTimestamp
    private String createdDt;

    @UpdateTimestamp
    private String updatedDt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // 일대 다 관계에 있다.(테이블과 테이블에 매핑 관계가 -> BookEntity (1) : BookFileEntity(다))
    // EAGER -> 즉시 가저온다. (부모 엔티티가 조회될때, 자식도 같이 즉시 조회된다.)
    // 참조일때 쓰는 것 같다.
    // Lazy 인가 그거는 부모 엔티티가 조회되도, 자식 엔티티가 조회되지 않음.
    @JoinColumn(name="book_id")
    // book_id로 조인되어있다는 뜻임.
    private List<BookFileEntity> fileInfoList;
}
