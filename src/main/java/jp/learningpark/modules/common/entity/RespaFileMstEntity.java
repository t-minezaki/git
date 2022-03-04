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
 * 解答集ファイル管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("respa_file_mst")
public class RespaFileMstEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 教材コード
     */
    @NotNull
    private String textBookCod;

    /**
     * 教科コード
     */
    @NotNull
    private String subjtCod;

    /**
     * PDFファイル名
     */
    @NotNull
    private String pdfFileNm;

    /**
     * PDFファイルパス
     */
    @NotNull
    private String pdfFilePath;

    /**
     * 級
     */
    @NotNull
    private String gradeDiv;

    /**
     * 集
     */
    @NotNull
    private String collDiv;

    /**
     * 枝番
     */
    private String brandDiv;

    /**
     * ページ
     */
    @NotNull
    private Integer pageNum;

    //add at 2021/08/19 for V9.02 by NWT LiGX START
    /**
     * 級名
     */
    private String gradeName;

    /**
     * 集名
     */
    private String collName;
    //add at 2021/08/19 for V9.02 by NWT LiGX END
    //add at 2021/09/08 for V9.02 by NWT LiGX START
    /**
     * 表示順_級
     */
    private Integer disGradeSort;

    /**
     * 表示順_集
     */
    private Integer disCollSort;
    //add at 2021/09/08 for V9.02 by NWT LiGX END

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
     * 教材コードを設定する
     */
    public void setTextBookCod(String textBookCod) {
        this.textBookCod = textBookCod;
    }
    
    /**
     * 教材コードを取得する
     */
    public String getTextBookCod() {
        return textBookCod;
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
     * PDFファイル名を設定する
     */
    public void setPdfFileNm(String pdfFileNm) {
        this.pdfFileNm = pdfFileNm;
    }
    
    /**
     * PDFファイル名を取得する
     */
    public String getPdfFileNm() {
        return pdfFileNm;
    }
    /**
     * PDFファイルパスを設定する
     */
    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }
    
    /**
     * PDFファイルパスを取得する
     */
    public String getPdfFilePath() {
        return pdfFilePath;
    }
    /**
     * 級を設定する
     */
    public void setGradeDiv(String gradeDiv) {
        this.gradeDiv = gradeDiv;
    }
    
    /**
     * 級を取得する
     */
    public String getGradeDiv() {
        return gradeDiv;
    }
    /**
     * 集を設定する
     */
    public void setCollDiv(String collDiv) {
        this.collDiv = collDiv;
    }
    
    /**
     * 集を取得する
     */
    public String getCollDiv() {
        return collDiv;
    }
    /**
     * 枝番を設定する
     */
    public void setBrandDiv(String brandDiv) {
        this.brandDiv = brandDiv;
    }
    
    /**
     * 枝番を取得する
     */
    public String getBrandDiv() {
        return brandDiv;
    }
    /**
     * ページを設定する
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    
    /**
     * ページを取得する
     */
    public Integer getPageNum() {
        return pageNum;
    }
    //add at 2021/08/19 for V9.02 by NWT LiGX START
    /**
     * 級名を設定する
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * 級名を取得する
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * 集名を設定する
     */
    public void setCollName(String collName) {
        this.collName = collName;
    }

    /**
     * 集名を取得する
     */
    public String getCollName() {
        return collName;
    }
    //add at 2021/08/19 for V9.02 by NWT LiGX END

    //add at 2021/09/08 for V9.02 by NWT LiGX START
    /**
     * 表示順_級を設定する
     */
    public void setDisGradeSort(Integer disGradeSort) {
        this.disGradeSort = disGradeSort;
    }

    /**
     * 表示順_級を取得する
     */
    public Integer getDisGradeSort() {
        return disGradeSort;
    }

    /**
     * 表示順_集を設定する
     */
    public void setDisCollSort(Integer disCollSort) {
        this.disCollSort = disCollSort;
    }

    /**
     * 表示順_集を取得する
     */
    public Integer getDisCollSort() {
        return disCollSort;
    }
    //add at 2021/09/08 for V9.02 by NWT LiGX END

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
