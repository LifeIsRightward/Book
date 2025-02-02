package Book.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Book.common.BookUtils;
import Book.dto.BookDto;
import Book.dto.BookFileDto;
import Book.mapper.BookMapper;
import ch.qos.logback.core.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Iterator;



@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookUtils fileUtils;

    @Override
    public List<BookDto> selectBookList() {
        return bookMapper.selectBookList();
    }

    @Transactional
    public void insertBook(BookDto bookDto, MultipartHttpServletRequest request) {
        // 지금 생성된 날짜를
        //        bookDto.setCreatedDt();
        //        bookMapper.insertBook(bookDto);

        // 1. Books 테이블에 책 데이터 삽입
//        bookMapper.insertBook(bookDto);


        // MyBatis로 데이터 삽입
        bookMapper.insertBook(bookDto);

        if (!ObjectUtils.isEmpty(request)) {
            // <input type="file" name="이 속성의 값" />
            Iterator<String> fileTagNames = request.getFileNames();
            while(fileTagNames.hasNext()) {
                String fileTagName = fileTagNames.next();
                // 하나의 <input type="file" multiple="multiple"> 태그를 통해서 전달된 파일들을 가져옮
                List<MultipartFile> files = request.getFiles(fileTagName);
                for (MultipartFile file : files) {
                    log.debug("File Information");
                    log.debug("- file name: " + file.getOriginalFilename());
                    log.debug("- file size: " + file.getSize());
                    log.debug("- content type: " + file.getContentType());
                }
            }
        }
        try {
            // 첨부 파일을 디스크에 저장하고, 첨부 파일 정보를 반환
            List<BookFileDto> fileInfoList = fileUtils.parseFileInfo(bookDto.getBookId(), request);

            // 첨부 파일 정보를 DB에 저장
            if (!CollectionUtils.isEmpty(fileInfoList)) {
                bookMapper.insertBookFileList(fileInfoList);
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }

        // 현재 시간 포맷팅
        String currentDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));


        // 생성된 날짜를 설정
        bookDto.setCreatedDt(currentDateTime);


        int bookId = bookDto.getBookId(); // 자동 생성된 book_id 가져오기
        System.out.println("생성된 bookId: " + bookId); // 로그로 확인
    }

    @Override
    public BookDto selectBookDetail(int bookId) {
//        return bookMapper.selectBookDetail(bookId);

        BookDto bookDto = bookMapper.selectBookDetail(bookId);
        List<BookFileDto> bookFileInfoList = bookMapper.selectBookFileList(bookId);
        bookDto.setFileInfoList(bookFileInfoList);

        return bookDto;

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
