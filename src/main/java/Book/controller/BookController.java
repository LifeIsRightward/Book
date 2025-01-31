package Book.controller;

import java.util.List;

import Book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import Book.dto.BookDto;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/openBookList.do")
    public ModelAndView openBookList() throws Exception {
        ModelAndView mv = new ModelAndView("book/bookList");
        List<BookDto> list = bookService.selectBookList();
        System.out.println(">>>>>>>>>>>");
        System.out.println("Book List: " + list);
        mv.addObject("list", list);

        return mv;
    }
    // 글쓰기 화면 요청을 처리하는 메서드
    @GetMapping("/book/openBookRegister.do")
    public String openBookWrite() throws Exception {
        return "/book/bookRegister";
    }

    // 글 저장 요청을 처리하는 메서드
    @PostMapping("/book/insertBook.do")
    public String insertBook(BookDto bookDto, MultipartHttpServletRequest request) throws Exception {
        System.out.println("여긴, 컨트롤러의 인서트 북이야! >>>>>> ");
        System.out.println(bookDto);
        System.out.println(request);
        bookService.insertBook(bookDto, request);
        return "redirect:/book/openBookList.do";
    }
    // 상세 조회 요청을 처리하는 메서드
    // /board/openBoardDetail.do?boardIdx=1234
    @GetMapping("/book/openBookDetail.do")
    public ModelAndView openBookDetail(@RequestParam("bookId") int bookId) throws Exception {
        BookDto bookDto = bookService.selectBookDetail(bookId);

//        System.out.println("여긴, 컨트롤러의 디테일 북이야! >>>>>> ");
//        System.out.println(bookDto);
        ModelAndView mv = new ModelAndView("/book/bookDetail");
        mv.addObject("book", bookDto);
        return mv;
    }
    // 수정 요청을 처리할 메서드
    @PostMapping("/book/updateBook.do")
    public String updateBook(BookDto bookDto) throws Exception {
//        System.out.println("여긴, 컨트롤러의 업데이트 북이야! >>>>>> ");
//        System.out.println(bookDto);
        bookService.updateBook(bookDto);
        return "redirect:/book/openBookList.do";
    }

    // 삭제 요청을 처리할 메서드
    @PostMapping("/book/deleteBook.do")
    public String deleteBook(@RequestParam("bookId") int bookId) throws Exception {
        bookService.deleteBook(bookId);
        return "redirect:/book/openBookList.do";
    }




}

