package Book.mapper;

import java.util.List;
import Book.dto.BookDto;
import Book.dto.BookFileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    List<BookDto> selectBookList();
    void insertBook(BookDto bookDto);
    BookDto selectBookDetail(int bookId);
    void updateBook(BookDto bookDto);
    void deleteBook(BookDto bookDto);
    void insertBookFileList(List<BookFileDto> fileInfoList);
    List<BookFileDto> selectBookFileList(int bookId);
}
