/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.learningpark.framework.utils.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 生徒テスト目標結果_ヘッダ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_test_goal_result_h")
public class StuTestGoalResultHEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 学年区分
     */
    @NotNull
    private String schyDiv;

    /**
     * テスト分類区分
     */
    @NotNull
    private String testTypeDiv;

    /**
     * テスト種別区分
     */
    private String testKindDiv;

    /**
     * テスト対象年
     */
    private Integer testTgtY;

    /**
     * テスト対象月
     */
    private Integer testTgtM;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新ユーザＩＤ
     */
    private String updUsrId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;


    /**
     * IDを設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * IDを取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * 生徒IDを設定する
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    
    /**
     * 生徒IDを取得する
     */
    public String getStuId() {
        return stuId;
    }
    /**
     * 学年区分を設定する
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }
    
    /**
     * 学年区分を取得する
     */
    public String getSchyDiv() {
        return StringUtils.trim(schyDiv);
    }
    /**
     * テスト分類区分を設定する
     */
    public void setTestTypeDiv(String testTypeDiv) {
        this.testTypeDiv = testTypeDiv;
    }
    
    /**
     * テスト分類区分を取得する
     */
    public String getTestTypeDiv() {
        return testTypeDiv;
    }
    /**
     * テスト種別区分を設定する
     */
    public void setTestKindDiv(String testKindDiv) {
        this.testKindDiv = testKindDiv;
    }
    
    /**
     * テスト種別区分を取得する
     */
    public String getTestKindDiv() {
        return testKindDiv;
    }
    /**
     * テスト対象年を設定する
     */
    public void setTestTgtY(Integer testTgtY) {
        this.testTgtY = testTgtY;
    }
    
    /**
     * テスト対象年を取得する
     */
    public Integer getTestTgtY() {
        return testTgtY;
    }
    /**
     * テスト対象月を設定する
     */
    public void setTestTgtM(Integer testTgtM) {
        this.testTgtM = testTgtM;
    }
    
    /**
     * テスト対象月を取得する
     */
    public Integer getTestTgtM() {
        return testTgtM;
    }
    /**
     * 作成日時を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }
    
    /**
     * 作成日時を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }
    /**
     * 作成ユーザＩＤを設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }
    
    /**
     * 作成ユーザＩＤを取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }
    /**
     * 更新日時を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * 更新ユーザＩＤを設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }
    
    /**
     * 更新ユーザＩＤを取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }
    /**
     * 削除フラグを設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
    
    /**
     * 削除フラグを取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }
}
