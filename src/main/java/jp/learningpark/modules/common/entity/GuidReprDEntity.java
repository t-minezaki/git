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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("guid_repr_d")
public class GuidReprDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 指導報告書ヘーダのID
     */
    @NotNull
    private Integer guidReprId;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 出欠ステータス区分
     */
    private String absStsDiv;

    /**
     * 使用テキスト
     */
    private String useCont;

    /**
     * 指導内容
     */
    private String guidCont;

    /**
     * 宿題内容
     */
    private String hwkCont;

    /**
     * 小テスト単元名
     */
    private String testUnitNm;

    /**
     * 前回宿題完成区分
     */
    private String lastTimeHwkDiv;

    /**
     * 進捗ステータス区分
     */
    private String schStsDiv;

    /**
     * 授業様子区分
     */
    private String lectShapeDiv;

    /**
     * 連絡事項内容
     */
    private String concItemCont;

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
     * 指導報告書ヘーダのIDを設定する
     */
    public void setGuidReprId(Integer guidReprId) {
        this.guidReprId = guidReprId;
    }
    
    /**
     * 指導報告書ヘーダのIDを取得する
     */
    public Integer getGuidReprId() {
        return guidReprId;
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
     * 出欠ステータス区分を設定する
     */
    public void setAbsStsDiv(String absStsDiv) {
        this.absStsDiv = absStsDiv;
    }
    
    /**
     * 出欠ステータス区分を取得する
     */
    public String getAbsStsDiv() {
        return absStsDiv;
    }
    /**
     * 使用テキストを設定する
     */
    public void setUseCont(String useCont) {
        this.useCont = useCont;
    }
    
    /**
     * 使用テキストを取得する
     */
    public String getUseCont() {
        return useCont;
    }
    /**
     * 指導内容を設定する
     */
    public void setGuidCont(String guidCont) {
        this.guidCont = guidCont;
    }
    
    /**
     * 指導内容を取得する
     */
    public String getGuidCont() {
        return guidCont;
    }
    /**
     * 宿題内容を設定する
     */
    public void setHwkCont(String hwkCont) {
        this.hwkCont = hwkCont;
    }
    
    /**
     * 宿題内容を取得する
     */
    public String getHwkCont() {
        return hwkCont;
    }
    /**
     * 小テスト単元名を設定する
     */
    public void setTestUnitNm(String testUnitNm) {
        this.testUnitNm = testUnitNm;
    }
    
    /**
     * 小テスト単元名を取得する
     */
    public String getTestUnitNm() {
        return testUnitNm;
    }
    /**
     * 前回宿題完成区分を設定する
     */
    public void setLastTimeHwkDiv(String lastTimeHwkDiv) {
        this.lastTimeHwkDiv = lastTimeHwkDiv;
    }
    
    /**
     * 前回宿題完成区分を取得する
     */
    public String getLastTimeHwkDiv() {
        return lastTimeHwkDiv;
    }
    /**
     * 進捗ステータス区分を設定する
     */
    public void setSchStsDiv(String schStsDiv) {
        this.schStsDiv = schStsDiv;
    }
    
    /**
     * 進捗ステータス区分を取得する
     */
    public String getSchStsDiv() {
        return schStsDiv;
    }
    /**
     * 授業様子区分を設定する
     */
    public void setLectShapeDiv(String lectShapeDiv) {
        this.lectShapeDiv = lectShapeDiv;
    }
    
    /**
     * 授業様子区分を取得する
     */
    public String getLectShapeDiv() {
        return lectShapeDiv;
    }
    /**
     * 連絡事項内容を設定する
     */
    public void setConcItemCont(String concItemCont) {
        this.concItemCont = concItemCont;
    }
    
    /**
     * 連絡事項内容を取得する
     */
    public String getConcItemCont() {
        return concItemCont;
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
