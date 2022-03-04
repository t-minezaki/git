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
import java.sql.Timestamp;

/**
 * 生徒メモ情報
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_memo")
public class StuMemoEntity implements Serializable {
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
     * メモ内容
     */
    @NotNull
    private String memoCont;

    /**
     * テスト結果
     */
    @NotNull
    private String testResult;

    /**
     * 分類区分１
     */
    @NotNull
    private String typeDiv1;

    /**
     * 分類区分２
     */
    @NotNull
    private String typeDiv2;

    /**
     * メンターID
     */
    @NotNull
    private String mentorId;

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
     * メモ内容を設定する
     */
    public void setMemoCont(String memoCont) {
        this.memoCont = memoCont;
    }
    
    /**
     * メモ内容を取得する
     */
    public String getMemoCont() {
        return memoCont;
    }
    /**
     * テスト結果を設定する
     */
    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
    
    /**
     * テスト結果を取得する
     */
    public String getTestResult() {
        return testResult;
    }
    /**
     * 分類区分１を設定する
     */
    public void setTypeDiv1(String typeDiv1) {
        this.typeDiv1 = typeDiv1;
    }
    
    /**
     * 分類区分１を取得する
     */
    public String getTypeDiv1() {
        return typeDiv1;
    }
    /**
     * 分類区分２を設定する
     */
    public void setTypeDiv2(String typeDiv2) {
        this.typeDiv2 = typeDiv2;
    }
    
    /**
     * 分類区分２を取得する
     */
    public String getTypeDiv2() {
        return typeDiv2;
    }
    /**
     * メンターIDを設定する
     */
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }
    
    /**
     * メンターIDを取得する
     */
    public String getMentorId() {
        return mentorId;
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
