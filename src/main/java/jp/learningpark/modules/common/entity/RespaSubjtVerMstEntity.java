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
 * 解答集教科バージョン管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("respa_subjt_ver_mst")
public class RespaSubjtVerMstEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 教科コード
     */
    @NotNull
    private String subjtCod;

    /**
     * 級
     */
    @NotNull
    private String gradeDiv;

    /**
     * 教科名
     */
    @NotNull
    private String subjtNm;

    /**
     * 最新バージョン日時
     */
    @NotNull
    private Timestamp newVerDatime;

    /**
     * ダウンロード開始日時
     */
    @NotNull
    private Timestamp downStartDatime;

    /**
     * 教科ZIPファイル名
     */
    @NotNull
    private String subjtZipFileNm;

    /**
     * 教科ZIPファイルパス
     */
    @NotNull
    private String subjtZipFilePath;

    /**
     * ダウンロード用URL
     */
    @NotNull
    private String downldUrl;

    /**
     * ZIP解凍PW
     */
    @NotNull
    private String fileGetPassword;

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
     * 教科コードを設定する
     */
    public void setSubjtCod(String subjtCod) {
        this.subjtCod = subjtCod;
    }

    /**
     * 教科コードを取得する
     */
    public String getSubjtCod() {
        return subjtCod;
    }
    /**
     * 教科名を設定する
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }

    /**
     * 教科名を取得する
     */
    public String getSubjtNm() {
        return subjtNm;
    }
    /**
     * 最新バージョン日時を設定する
     */
    public void setNewVerDatime(Timestamp newVerDatime) {
        this.newVerDatime = newVerDatime;
    }

    /**
     * 最新バージョン日時を取得する
     */
    public Timestamp getNewVerDatime() {
        return newVerDatime;
    }
    /**
     * ダウンロード開始日時を設定する
     */
    public void setDownStartDatime(Timestamp downStartDatime) {
        this.downStartDatime = downStartDatime;
    }

    /**
     * ダウンロード開始日時を取得する
     */
    public Timestamp getDownStartDatime() {
        return downStartDatime;
    }
    /**
     * 教科ZIPファイル名を設定する
     */
    public void setSubjtZipFileNm(String subjtZipFileNm) {
        this.subjtZipFileNm = subjtZipFileNm;
    }

    /**
     * 教科ZIPファイル名を取得する
     */
    public String getSubjtZipFileNm() {
        return subjtZipFileNm;
    }
    /**
     * 教科ZIPファイルパスを設定する
     */
    public void setSubjtZipFilePath(String subjtZipFilePath) {
        this.subjtZipFilePath = subjtZipFilePath;
    }

    /**
     * 教科ZIPファイルパスを取得する
     */
    public String getSubjtZipFilePath() {
        return subjtZipFilePath;
    }
    /**
     * ダウンロード用URLを設定する
     */
    public void setDownldUrl(String downldUrl) {
        this.downldUrl = downldUrl;
    }

    /**
     * ダウンロード用URLを取得する
     */
    public String getDownldUrl() {
        return downldUrl;
    }
    /**
     * ZIP解凍PWを設定する
     */
    public void setFileGetPassword(String fileGetPassword) {
        this.fileGetPassword = fileGetPassword;
    }

    /**
     * ZIP解凍PWを取得する
     */
    public String getFileGetPassword() {
        return fileGetPassword;
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

    /**
     * 級を取得する
     *
     * @return gradeDiv 級
     */
    public String getGradeDiv() {
        return this.gradeDiv;
    }

    /**
     * 級を設定する
     *
     * @param gradeDiv 級
     */
    public void setGradeDiv(String gradeDiv) {
        this.gradeDiv = gradeDiv;
    }
}
