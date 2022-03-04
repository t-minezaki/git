/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>コース区分管理</p>
 * <p></p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@TableName("mst_course_div")
public class MstCourseDivEntity implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;
    /**
     * コース区分
     */
    @NotNull
    private String courseDiv;
    /**
     * コース名
     */
    private String courseDivNm;
    /**
     * 作成日時
     */
    @NotNull
    private Date cretDatime;
    /**
     * 作成ユーザＩＤ
     */
    @NotNull
    private String cretUsrId;
    /**
     * 更新日時
     */
    @NotNull
    private Date updDatime;
    /**
     * 更新ユーザＩＤ
     */
    @NotNull
    private String updUsrId;
    /**
     * 削除フラグ
     */
    @NotNull
    private Integer delFlg;

    /**
     * IDを取得する
     *
     * @return id ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * IDを設定する
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * コース区分を取得する
     *
     * @return courseDiv コース区分
     */
    public String getCourseDiv() {
        return this.courseDiv;
    }

    /**
     * コース区分を設定する
     *
     * @param courseDiv コース区分
     */
    public void setCourseDiv(String courseDiv) {
        this.courseDiv = courseDiv;
    }


    /**
     * 作成日時を取得する
     *
     * @return cretDatime 作成日時
     */
    public Date getCretDatime() {
        return this.cretDatime;
    }

    /**
     * 作成日時を設定する
     *
     * @param cretDatime 作成日時
     */
    public void setCretDatime(Date cretDatime) {
        this.cretDatime = cretDatime;
    }

    /**
     * 作成ユーザＩＤを取得する
     *
     * @return cretUsrId 作成ユーザＩＤ
     */
    public String getCretUsrId() {
        return this.cretUsrId;
    }

    /**
     * 作成ユーザＩＤを設定する
     *
     * @param cretUsrId 作成ユーザＩＤ
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    /**
     * 更新日時を取得する
     *
     * @return updDatime 更新日時
     */
    public Date getUpdDatime() {
        return this.updDatime;
    }

    /**
     * 更新日時を設定する
     *
     * @param updDatime 更新日時
     */
    public void setUpdDatime(Date updDatime) {
        this.updDatime = updDatime;
    }

    /**
     * 更新ユーザＩＤを取得する
     *
     * @return updUsrId 更新ユーザＩＤ
     */
    public String getUpdUsrId() {
        return this.updUsrId;
    }

    /**
     * 更新ユーザＩＤを設定する
     *
     * @param updUsrId 更新ユーザＩＤ
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    /**
     * 削除フラグを取得する
     *
     * @return delFlg 削除フラグ
     */
    public Integer getDelFlg() {
        return this.delFlg;
    }

    /**
     * 削除フラグを設定する
     *
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * コース名を取得する
     *
     * @return courseDivNm コース名
     */
    public String getCourseDivNm() {
        return this.courseDivNm;
    }

    /**
     * コース名を設定する
     *
     * @param courseDivNm コース名
     */
    public void setCourseDivNm(String courseDivNm) {
        this.courseDivNm = courseDivNm;
    }
}
