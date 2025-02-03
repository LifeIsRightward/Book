package Book.controller;

import Book.dto.BookDto;
import Book.dto.BookFileDto;
import Book.service.BookService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
public class RestController {

    @Autowired
    private BookService bookService;

    //목록 조회
    @GetMapping("/book")
    public ModelAndView openBookList() throws Exception {
        ModelAndView mv = new ModelAndView("/book/restBookList");

        List<BookDto> list = bookService.selectBookList();
        mv.addObject("list", list);

        return mv;
    }

    // 책쓰기 화면 요청을 처리하는 메서드
    @GetMapping("/book/write")
    public String openBookWrite() throws Exception {
        return "/book/restBookRegister";
    }

    // 책 정보 저장 요청을 처리하는 메서드
    @PostMapping("/book/write")
    public String insertBook(BookDto bookDto, MultipartHttpServletRequest request) throws Exception {
        System.out.println("책 데이터: " + bookDto);

        // 업로드된 파일 확인
        List<MultipartFile> fileList = request.getFiles("files");
        System.out.println("업로드된 파일 개수: " + fileList.size());
        for (MultipartFile file : fileList) {
            System.out.println("파일 이름: " + file.getOriginalFilename());
        }

        // 서비스 호출
        bookService.insertBook(bookDto, request);

        return "redirect:/book";
    }


    // 상세 조회 요청을 처리하는 메서드
    // /board/openBoardDetail.do?boardIdx=1234
    @GetMapping("/book/{bookId}")
    public ModelAndView openBookDetail(@PathVariable("bookId") int bookId) throws Exception {
        BookDto bookDto = bookService.selectBookDetail(bookId);

//        System.out.println("여긴, 컨트롤러의 디테일 북이야! >>>>>> ");
//        System.out.println(bookDto);
        ModelAndView mv = new ModelAndView("/book/restBookDetail");
        mv.addObject("book", bookDto);
        return mv;
    }


    // 수정 요청을 처리할 메서드
    @PutMapping("/book/{bookId}")
    public String updateBook(@PathVariable("bookId") int bookId, BookDto bookDto) throws Exception {
//        System.out.println("여긴, 컨트롤러의 업데이트 북이야! >>>>>> ");
//        System.out.println(bookDto);
        bookService.updateBook(bookDto);
        return "redirect:/book";
    }

    // 삭제 요청을 처리할 메서드
    @DeleteMapping("/book/{bookId}")
    public String deleteBook(@RequestParam("bookId") int bookId) throws Exception {
        bookService.deleteBook(bookId);
        return "redirect:/book";
    }

    @GetMapping("/book/file")
    public void downloadBookFile(@RequestParam("idx") int idx, @RequestParam("bookId") int bookId, HttpServletResponse response) throws Exception {
        // 파일 정보 조회
        BookFileDto bookFileDto = bookService.selectBookFileInfo(idx, bookId);
        if (ObjectUtils.isEmpty(bookFileDto)) {
            throw new FileNotFoundException("File not found for idx: " + idx + ", bookId: " + bookId);
        }

        // 파일 경로 유효성 검사
        Path path = Paths.get(bookFileDto.getStoredFilePath());
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found at path: " + path.toString());
        }

        // 파일 읽기
        byte[] file = Files.readAllBytes(path);

        // 응답 헤더 설정
        String encodedFileName = URLEncoder.encode(bookFileDto.getOriginalFileName(), "UTF-8").replaceAll("\\+", "%20");
        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + encodedFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        // 파일 스트림 전송
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(file);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file to response", e);
        }
    }




}
