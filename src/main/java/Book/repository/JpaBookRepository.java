package Book.repository;

import Book.entity.BookEntity;
import Book.entity.BookFileEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBookRepository extends CrudRepository<BookEntity, Integer> {
    // 쿼리 메서드
    List<BookEntity> findAllByOrderByBookIdDesc();

    // @Query 어노테이션으로 실행할 쿼리를 직접 정의 (JPQL)
    @Query("Select file FROM BookFileEntity file where file.book.bookId = :bookId AND file.idx = :idx")
    BookFileEntity findBookFile(@Param("bookId") int bookId, @Param("idx") int idx);

}
