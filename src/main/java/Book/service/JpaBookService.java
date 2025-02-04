package Book.service;

import Book.dto.BookDto;
import Book.dto.BookFileDto;
import Book.entity.BookEntity;
import Book.entity.BookFileEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface JpaBookService {
    List<BookEntity> selectBookList();
    void insertBook(BookEntity bookEntity, MultipartHttpServletRequest request) throws Exception;
    void updateBook(BookEntity bookEntity);
    //위에 두 개를 묶어서
//    void saveBook(BookEntity bookEntity, MultipartHttpServletRequest request);
    BookEntity selectBookDetail(int bookId);

    void deleteBook(int bookId);
    BookFileEntity selectBookFileInfo(int idx, int bookId);
}
