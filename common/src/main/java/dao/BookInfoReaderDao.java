package dao;

public class BookInfoReaderDao {
    public String bookId;
    public String shortDescription;
    public String author;
    public String title;
    public String downloadUrl;

    public BookInfoReaderDao() {
    }

    public BookInfoReaderDao(String bookId, String shortDescription, String author, String title, String downloadUrl) {
        this.bookId = bookId;
        this.shortDescription = shortDescription;
        this.author = author;
        this.title = title;
        this.downloadUrl = downloadUrl;
    }
}
