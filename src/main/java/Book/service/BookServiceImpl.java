package Book.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Book.dto.BookDto;
import Book.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookDto> selectBookList() {
        return bookMapper.selectBookList();
    }

    @Override
    public void insertBook(BookDto bookDto) {
        // 지금 생성된 날짜를
//        bookDto.setCreatedDt();
//        bookMapper.insertBook(bookDto);
        // 현재 시간 포맷팅
        String currentDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));

        // 생성된 날짜를 설정
        bookDto.setCreatedDt(currentDateTime);

        // MyBatis로 데이터 삽입
        bookMapper.insertBook(bookDto);
    }

    @Override
    public BookDto selectBookDetail(int bookId) {
        return bookMapper.selectBookDetail(bookId);
    }
    @Override
    public void updateBook(BookDto bookDto) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(bookDto);
        bookMapper.updateBook(bookDto);
    }

    @Override
    public void deleteBook(int bookId) {
        BookDto bookDto = new BookDto();
        bookDto.setBookId(bookId);
        bookMapper.deleteBook(bookDto);
    }



}
