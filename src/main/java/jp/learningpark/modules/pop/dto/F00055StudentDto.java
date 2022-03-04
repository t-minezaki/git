package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstUsrEntity;

/**
 * <p>F00055 ユーザー選択画面（POP）StudentDto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/20 : wen: 新規<br />
 * @version 1.0
 */
public class F00055StudentDto extends MstUsrEntity {

    /**
     * 学生id
     */
    private String stuId;
    /**
     * 学生名前
     */
    private String studentNm;
    /**
     * 学年
     */
    private String schyDiv;

    /**
     * 学生idを取得する
     *
     * @return stuId 学生id
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 学生idを設定する
     *
     * @param stuId 学生id
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 学生名前を取得する
     *
     * @return studentNm 学生名前
     */
    public String getStudentNm() {
        return this.studentNm;
    }

    /**
     * 学生名前を設定する
     *
     * @param studentNm 学生名前
     */
    public void setStudentNm(String studentNm) {
        this.studentNm = studentNm;
    }

    /**
     * 学年を取得する
     *
     * @return schyDiv 学年
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年を設定する
     *
     * @param schyDiv 学年
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }
}
