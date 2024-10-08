package dao;

public class ModerationResultDao {
    public String bookId;
    public String moderationResultId;
    public boolean moderationSuccess;

    public ModerationResultDao() {
    }

    public ModerationResultDao(String bookId, String moderationResultId, boolean moderationSuccess) {
        this.bookId = bookId;
        this.moderationResultId = moderationResultId;
        this.moderationSuccess = moderationSuccess;
    }
}
