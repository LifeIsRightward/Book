package Book.controller;

import Book.dto.BookInsertRequest;
import Book.entity.BookEntity;
import Book.entity.BookFileEntity;
import Book.service.JpaBookService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/jpa")
public class JPABookController {
    @Autowired
    private JpaBookService bookService;

        // 목록 조회
        @GetMapping("/book")
        public ModelAndView openBookList() throws Exception {
            ModelAndView mv = new ModelAndView("/book/jpaBookList");

            List<BookEntity> list = bookService.selectBookList();
            mv.addObject("list", list);

            return mv;
        }

        // 작성 화면
        @GetMapping("/book/write")
        public String openBookWrite() throws Exception {
            return "/book/jpaBookWrite";
        }

        // 저장 처리
        @PostMapping("/book/write")
        public String insertBook(BookInsertRequest bookInsertRequest, MultipartHttpServletRequest request) throws Exception {
            BookEntity bookEntity = new ModelMapper().map(bookInsertRequest, BookEntity.class);
            System.out.println("Author: " + bookInsertRequest.getAuthor());
            System.out.println("Published_DT: " + bookInsertRequest.getPublishedDt());
            bookService.insertBook(bookEntity, request);
            return "redirect:/jpa/book";
        }

        // 상세 조회
        @GetMapping("/book/{bookId}")
        public ModelAndView openBookDetail(@PathVariable("bookId") int bookId) throws Exception {
            BookEntity bookEntity = bookService.selectBookDetail(bookId);
            ModelAndView mv = new ModelAndView("/book/jpaBookDetail");
            mv.addObject("book", bookEntity);
            return mv;
        }

        // 수정 처리
        @PutMapping("/book/{bookId}")
        public String updateBook(@PathVariable("bookId") int bookId, BookEntity bookEntity) throws Exception {
            bookEntity.setBookId(bookId);
            bookService.updateBook(bookEntity);
            System.out.println("여긴 컨트롤러의 Put Update 메서드" + bookEntity.getBookId());
            return "redirect:/jpa/book";
        }

        // 삭제 처리
        @DeleteMapping("/book/{bookId}")
        public String deleteBook(@RequestParam("bookId") int bookId) throws Exception {
            bookService.deleteBook(bookId);
            System.out.println("여긴 컨트롤러의 Delete 메서드");
            return "redirect:/jpa/book";
        }

        // 첨부파일 다운로드
        @GetMapping("/book/file")
        public void downloadBookFile(@RequestParam("idx") int idx, @RequestParam("bookId") int bookId, HttpServletResponse response) throws Exception {
            BookFileEntity bookFileEntity = bookService.selectBookFileInfo(idx, bookId);
            if (ObjectUtils.isEmpty(bookFileEntity)) {
                return;
            }

            Path path = Paths.get(bookFileEntity.getStoredFilePath());
            byte[] file = Files.readAllBytes(path);

            response.setContentType("application/octet-stream");
            response.setContentLength(file.length);
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(bookFileEntity.getOriginalFileName(), "UTF-8") + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.getOutputStream().write(file);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

    }
