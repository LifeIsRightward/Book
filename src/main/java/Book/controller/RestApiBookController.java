package Book.controller;

import Book.dto.BookDto;
import Book.dto.BookFileDto;
import Book.dto.BookListResponse;
import Book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestApiBookController {
    @Autowired
    private BookService bookService;


    // 목록 조회
//    @Operation(summary = "책 리스트 조회", description = "등록된 책 정보 목록을 조회해서 반환합니다.")
//    @GetMapping("/book")
//    public List<BookDto> openBookList() throws Exception {
//        return bookService.selectBookList();
//    }

    @Operation(summary = "책 리스트 조회", description = "등록된 책 정보 목록을 조회해서 반환합니다.")
    @GetMapping("/book")
    public List<BookListResponse> openBookList() throws Exception {
        List<BookDto> bookList = bookService.selectBookList();

        // Front에서 필요한 것만 넣어서 주겠다. -> 다 보내주는 것은 정보 유출이기 때문이다.
        // 그래서 BookListResponse라는 파일을 따로 만들어서 거기에 만들어 넣고 보내준 것.
        List<BookListResponse> results = new ArrayList<>();
        for (BookDto dto : bookList) {
//            BookListResponse res = new BookListResponse();
//            res.setBookId(dto.getBookId());
//            res.setTitle(dto.getTitle());
//            res.setAuthor(dto.getAuthor());
//            res.setPublisher(dto.getPublisher());
//            res.setDescription(dto.getDescription());
//            res.setIsbn(dto.getIsbn());
//            res.setUpdatedDt(dto.getUpdatedDt());
//            res.setCreatedDt(dto.getCreatedDt());
//            results.add(res);
            // 위에거를 한 번에 해주는애가 바로
            BookListResponse res = new ModelMapper().map(dto, BookListResponse.class);
            results.add(res);
        }
//        이렇게 위에 for-Each문을 람다식으로도 표현이 가능함.
//        bookList.forEach(dto -> results.add(new ModelMapper().map(dto, BookListResponse.class)));
        return results;

    }

    //저장 처리
    @PostMapping(value = "/book", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public void insertBook(@RequestParam("book") String bookData, MultipartHttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookDto bookDto = objectMapper.readValue(bookData, BookDto.class);
        bookService.insertBook(bookDto, request);
    }

    // 상세 조회
    @Operation(summary = "책 리스트 상세 조회", description = "책 정보 아이디와 일치하는 상세 정보를 조회해서 반환합니다.")
    @Parameter(name = "bookId", description = "책 아이디", required = true)
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Object> openBookDetail(@PathVariable("bookId") int bookId) throws Exception {
//        return bookService.selectBookDetail(bookId);
        BookDto bookDto = bookService.selectBookDetail(bookId);

        if(bookDto == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("name", HttpStatus.NOT_FOUND.name());
            result.put("message", "게시판 번호 " + bookId + "와 일치하는 책 정보가 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(bookDto);
        }
    }

    // 수정 처리
    @PutMapping("/book/{bookId}")
    public void updateBook(@PathVariable("bookId") int bookId, @RequestBody BookDto bookDto) throws Exception {
        bookDto.setBookId(bookId);
        bookService.updateBook(bookDto);
    }

    // 삭제 처리
    @DeleteMapping("/book/{bookId}")
    public void deleteBook(@PathVariable("bookId") int bookId) throws Exception {
        bookService.deleteBook(bookId);
    }

    // 첨부파일 다운로드
    @GetMapping("/book/file")
    public void downloadBookFile(@RequestParam("idx") int idx, @RequestParam("bookId") int bookId, HttpServletResponse response) throws Exception {
        BookFileDto bookFileDto = bookService.selectBookFileInfo(idx, bookId);
        if(ObjectUtils.isEmpty(bookFileDto)) {
            return;
        }

        Path path = Paths.get(bookFileDto.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(bookFileDto.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }
}
