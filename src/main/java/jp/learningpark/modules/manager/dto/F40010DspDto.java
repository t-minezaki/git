package jp.learningpark.modules.manager.dto;

/**
 * <p></p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 1.0
 */
public class F40010DspDto {

    // ユーザID
    private String userId;

    // ユーザ名
    private String name;

    /**
     * ユーザID
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ユーザID
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * ユーザ名
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * ユーザ名
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
