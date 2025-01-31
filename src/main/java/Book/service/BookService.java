package Book.service;

import Book.dto.BookDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BookService {
    List<BookDto> selectBookList();
    void insertBook(BookDto bookDto, MultipartHttpServletRequest request);
    BookDto selectBookDetail(int bookId);
    void updateBook(BookDto bookDto);
    void deleteBook(int bookId);
}
