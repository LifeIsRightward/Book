package Book.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String publishedDt;
    private String isbn;
    private String description;
    private String createdDt;
    private String updatedDt;
    private List<BookFileDto> fileInfoList;

    public String getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishedDt() {
        return publishedDt;
    }

    public void setPublishedDt(String publishedDt) {
        this.publishedDt = publishedDt;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public List<BookFileDto> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<BookFileDto> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }
}
