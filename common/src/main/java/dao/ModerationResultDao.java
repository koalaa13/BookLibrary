package dao;

public class ModerationResultDao {
    public String bookId;
    public String moderationResultId;

    public ModerationResultDao() {
    }

    public ModerationResultDao(String bookId, String moderationResultId) {
        this.bookId = bookId;
        this.moderationResultId = moderationResultId;
    }
}
