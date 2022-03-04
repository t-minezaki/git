package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstUsrEntity;

/**
 * <p>F00055 ユーザー選択画面（POP）MentorDto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/20 : wen: 新規<br />
 * @version 1.0
 */
public class F00055MentorDto extends MstUsrEntity {

    /**
     * メンターID
     */
    private String mentorId;
    /**
     * メンター名前
     */
    private String mentorNm;

    /**
     * メンターIDを取得する
     *
     * @return mentorId メンターID
     */
    public String getMentorId() {
        return this.mentorId;
    }

    /**
     * メンターIDを設定する
     *
     * @param mentorId メンターID
     */
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    /**
     * メンター名前を取得する
     *
     * @return mentorNm メンター名前
     */
    public String getMentorNm() {
        return this.mentorNm;
    }

    /**
     * メンター名前を設定する
     *
     * @param mentorNm メンター名前
     */
    public void setMentorNm(String mentorNm) {
        this.mentorNm = mentorNm;
    }
}
