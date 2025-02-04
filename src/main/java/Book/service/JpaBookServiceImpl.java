package Book.service;

import Book.common.BookUtils;
import Book.entity.BookEntity;
import Book.entity.BookFileEntity;
import Book.repository.JpaBookRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
public class JpaBookServiceImpl implements JpaBookService {
    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Autowired
    private BookUtils fileUtils;

    @Override
    public List<BookEntity> selectBookList() {
        return jpaBookRepository.findAllByOrderByBookIdDesc();
    }

//    @Override
//    public void saveBook(BookEntity bookEntity, MultipartHttpServletRequest request) {
//
//    }

    @Override
    public BookEntity selectBookDetail(int bookId) {
        Optional<BookEntity> optional = jpaBookRepository.findById(bookId);
        if (optional.isPresent()) {
            BookEntity book = optional.get();

//            book.setHitCnt(board.getHitCnt() + 1);
            jpaBookRepository.save(book);

            return book;
        } else {
            throw new RuntimeException();
        }

    }

    /*
    @Override
    public void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception {
        boardEntity.setCreatedId("admin");
        BoardEntity existingBoard = jpaBoardRepository.findById(boardEntity.getBoardIdx()).orElse(null);
        if (existingBoard != null) {
            // boardEntity.setFileInfoList(existingBoard.getFileInfoList());
        } else {
            List<BoardFileEntity> list = fileUtils.parseFileInfo2(boardEntity.getBoardIdx(), request);
            if (!CollectionUtils.isEmpty(list)) {
                boardEntity.setFileInfoList(list);
            }
        }
        jpaBoardRepository.save(boardEntity);
    }
    */

//    @Override
//    public void insertBook(BookEntity bookEntity, MultipartHttpServletRequest request) throws Exception {
//        bookEntity.setCreatedDt("admin");
//        List<BookFileEntity> list = fileUtils.parseFileInfo2(bookEntity.getBookId(), request);
//        if (!CollectionUtils.isEmpty(list)) {
//            bookEntity.setFileInfoList(list);
//        }
//        jpaBookRepository.save(bookEntity);
//    }

    @Transactional
    @Override
    public void insertBook(BookEntity bookEntity, MultipartHttpServletRequest request) throws Exception {
        // Step 1: 부모 엔티티 저장
        BookEntity savedBookEntity = jpaBookRepository.save(bookEntity);
        System.out.println("여기는 서비스단 insertBook의 PublishedDt야: " + bookEntity.getPublishedDt());
        System.out.println(bookEntity.getBookId());
        System.out.println(bookEntity.getTitle());
        // Step 2: 파일 정보를 생성하면서 bookId를 설정
        List<BookFileEntity> fileEntities = fileUtils.parseFileInfo2(savedBookEntity.getBookId(), request);

        // Step 3: 자식 엔티티에 부모 엔티티 설정
        if (!CollectionUtils.isEmpty(fileEntities)) {
            for (BookFileEntity fileEntity : fileEntities) {
                fileEntity.setBook(savedBookEntity); // 부모 엔티티 설정
            }
            savedBookEntity.setFileInfoList(fileEntities);
        }

        // Step 4: 부모 엔티티 저장 (CascadeType.ALL로 인해 자식 엔티티도 저장됨)
        jpaBookRepository.save(savedBookEntity);
    }

    @Override
    public void updateBook(BookEntity bookEntity) {
        BookEntity existingBoard = jpaBookRepository.findById(bookEntity.getBookId()).orElse(null);
        if (existingBoard != null) {
            bookEntity.setFileInfoList(existingBoard.getFileInfoList());
        }
        jpaBookRepository.save(bookEntity);
    }



    @Override
    public void deleteBook(int bookId) {
        jpaBookRepository.deleteById(bookId);
    }

    @Override
    public BookFileEntity selectBookFileInfo(int idx, int bookId) {
        return jpaBookRepository.findBookFile(bookId, idx);
    }
}
