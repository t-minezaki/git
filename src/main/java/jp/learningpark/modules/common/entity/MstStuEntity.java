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
import java.util.Date;

/**
 * 生徒基本マスタ
 *
 * @author NWT
 */
@TableName("mst_stu")
public class MstStuEntity implements Serializable {
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
     * 学校名
     */
    private String sch;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 姓名_姓
     */
    private String flnmNm;

    /**
     * 姓名_名
     */
    private String flnmLnm;

    /**
     * 姓名_カナ姓
     */
    private String flnmKnNm;

    /**
     * 姓名_カナ名
     */
    private String flnmKnLnm;

    /**
     * 性別区分
     */
    private String gendrDiv;

    /**
     * 生年月日
     */
    private Date birthd;

    /**
     * 写真パス
     */
    private String photPath;

    /**
     * 学年区分
     */
    private String schyDiv;

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
     * QRコード
     */
    private String qrCod;

    /**
     * オリジナルCD
     */
    private String oriaCd;

    /**
     * 保護者１ID
     */
    private String guard1Id;

    /**
     * 保護者２ID
     */
    private String guard2Id;

    /**
     * 保護者３ID
     */
    private String guard3Id;

    /**
     * 保護者４ID
     */
    private String guard4Id;

    /**
     * 英語氏名
     */
    private String englishNm;

    /**
     * 通塾曜日区分
     */
    private String dayweekDiv;

    /**
     * メモ
     */
    private String memoCont;

    /**
     * 担当者氏名
     */
    private String resptyNm;

    /**
     * 習い事
     */
    private String studyCont;

    /**
     * 得意科目区分
     */
    private String goodSubjtDiv;

    /**
     * 不得意科目区分
     */
    private String nogoodSubjtDiv;

    /**
     * 希望職種
     */
    private String hopeJobNm;

    /**
     * 希望大学
     */
    private String hopeUnityNm;

    /**
     * 希望学部学科
     */
    private String hopeLearnSub;
    /**
     * 特記事項
     */
    private String specCont;

    /**
     * 会員コード
     */
    private String memberCd;

    /**
     * コース
     */
    private String courseId;

    /**
     * 入会年月日
     */
    private Timestamp admissionDate
    /**
     * 生涯番号
    */;
    private Integer  careeresNum;
    /**
     * 生涯番号を設定する
     */
    public void setCareeresNum(Integer careeresNum) {
        this.careeresNum = careeresNum;
    }
    /**
     * 生涯番号を取得する
     */
    public Integer getCareeresNum() {
        return careeresNum;
    }
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
     * 学校名を設定する
     */
    public void setSch(String sch) {
        this.sch = sch;
    }

    /**
     * 学校名を取得する
     */
    public String getSch() {
        return sch;
    }

    /**
     * 保護者IDを設定する
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 保護者IDを取得する
     */
    public String getGuardId() {
        return guardId;
    }

    /**
     * 姓名_姓を設定する
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }

    /**
     * 姓名_姓を取得する
     */
    public String getFlnmNm() {
        return flnmNm;
    }

    /**
     * 姓名_名を設定する
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }

    /**
     * 姓名_名を取得する
     */
    public String getFlnmLnm() {
        return flnmLnm;
    }

    /**
     * 姓名_カナ姓を設定する
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }

    /**
     * 姓名_カナ姓を取得する
     */
    public String getFlnmKnNm() {
        return flnmKnNm;
    }

    /**
     * 姓名_カナ名を設定する
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }

    /**
     * 姓名_カナ名を取得する
     */
    public String getFlnmKnLnm() {
        return flnmKnLnm;
    }

    /**
     * 性別区分を設定する
     */
    public void setGendrDiv(String gendrDiv) {
        this.gendrDiv = gendrDiv;
    }

    /**
     * 性別区分を取得する
     */
    public String getGendrDiv() {
        return gendrDiv;
    }

    /**
     * 生年月日を設定する
     */
    public void setBirthd(Date birthd) {
        this.birthd = birthd;
    }

    /**
     * 生年月日を取得する
     */
    public Date getBirthd() {
        return birthd;
    }

    /**
     * 写真パスを設定する
     */
    public void setPhotPath(String photPath) {
        this.photPath = photPath;
    }

    /**
     * 写真パスを取得する
     */
    public String getPhotPath() {
        return photPath;
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
     * QRコードを設定する
     */
    public void setQrCod(String qrCod) {
        this.qrCod = qrCod;
    }

    /**
     * QRコードを取得する
     */
    public String getQrCod() {
        return qrCod;
    }

    /**
     * オリジナルCDを設定する
     */
    public void setOriaCd(String oriaCd) {
        this.oriaCd = oriaCd;
    }

    /**
     * オリジナルCDを取得する
     */
    public String getOriaCd() {
        return oriaCd;
    }

    /**
     * 保護者１IDを設定する
     */
    public void setGuard1Id(String guard1Id) {
        this.guard1Id = guard1Id;
    }

    /**
     * 保護者１IDを取得する
     */
    public String getGuard1Id() {
        return guard1Id;
    }

    /**
     * 保護者２IDを設定する
     */
    public void setGuard2Id(String guard2Id) {
        this.guard2Id = guard2Id;
    }

    /**
     * 保護者２IDを取得する
     */
    public String getGuard2Id() {
        return guard2Id;
    }

    /**
     * 保護者３IDを設定する
     */
    public void setGuard3Id(String guard3Id) {
        this.guard3Id = guard3Id;
    }

    /**
     * 保護者３IDを取得する
     */
    public String getGuard3Id() {
        return guard3Id;
    }

    /**
     * 保護者４IDを設定する
     */
    public void setGuard4Id(String guard4Id) {
        this.guard4Id = guard4Id;
    }

    /**
     * 保護者４IDを取得する
     */
    public String getGuard4Id() {
        return guard4Id;
    }

    /**
     * 英語氏名を設定する
     */
    public void setEnglishNm(String englishNm) {
        this.englishNm = englishNm;
    }

    /**
     * 英語氏名を取得する
     */
    public String getEnglishNm() {
        return englishNm;
    }

    /**
     * 通塾曜日区分を設定する
     */
    public void setDayweekDiv(String dayweekDiv) {
        this.dayweekDiv = dayweekDiv;
    }

    /**
     * 通塾曜日区分を取得する
     */
    public String getDayweekDiv() {
        return dayweekDiv;
    }

    /**
     * メモを設定する
     */
    public void setMemoCont(String memoCont) {
        this.memoCont = memoCont;
    }

    /**
     * メモを取得する
     */
    public String getMemoCont() {
        return memoCont;
    }

    /**
     * 担当者氏名を取得する
     *
     * @return resptyNm 担当者氏名
     */
    public String getResptyNm() {
        return this.resptyNm;
    }

    /**
     * 担当者氏名を設定する
     *
     * @param resptyNm 担当者氏名
     */
    public void setResptyNm(String resptyNm) {
        this.resptyNm = resptyNm;
    }

    /**
     * 習い事を取得する
     *
     * @return studyCont 習い事
     */
    public String getStudyCont() {
        return this.studyCont;
    }

    /**
     * 習い事を設定する
     *
     * @param studyCont 習い事
     */
    public void setStudyCont(String studyCont) {
        this.studyCont = studyCont;
    }

    /**
     * 得意科目区分を取得する
     *
     * @return goodSubjtDiv 得意科目区分
     */
    public String getGoodSubjtDiv() {
        return this.goodSubjtDiv;
    }

    /**
     * 得意科目区分を設定する
     *
     * @param goodSubjtDiv 得意科目区分
     */
    public void setGoodSubjtDiv(String goodSubjtDiv) {
        this.goodSubjtDiv = goodSubjtDiv;
    }

    /**
     * 不得意科目区分を取得する
     *
     * @return nogoodSubjtDiv 不得意科目区分
     */
    public String getNogoodSubjtDiv() {
        return this.nogoodSubjtDiv;
    }

    /**
     * 不得意科目区分を設定する
     *
     * @param nogoodSubjtDiv 不得意科目区分
     */
    public void setNogoodSubjtDiv(String nogoodSubjtDiv) {
        this.nogoodSubjtDiv = nogoodSubjtDiv;
    }

    /**
     * 希望職種を取得する
     *
     * @return hopeJobNm 希望職種
     */
    public String getHopeJobNm() {
        return this.hopeJobNm;
    }

    /**
     * 希望職種を設定する
     *
     * @param hopeJobNm 希望職種
     */
    public void setHopeJobNm(String hopeJobNm) {
        this.hopeJobNm = hopeJobNm;
    }

    /**
     * 希望大学を取得する
     *
     * @return hopeUnityNm 希望大学
     */
    public String getHopeUnityNm() {
        return this.hopeUnityNm;
    }

    /**
     * 希望大学を設定する
     *
     * @param hopeUnityNm 希望大学
     */
    public void setHopeUnityNm(String hopeUnityNm) {
        this.hopeUnityNm = hopeUnityNm;
    }

    /**
     * 希望学部学科を取得する
     *
     * @return hopeLearnSub 希望学部学科
     */
    public String getHopeLearnSub() {
        return this.hopeLearnSub;
    }

    /**
     * 希望学部学科を設定する
     *
     * @param hopeLearnSub 希望学部学科
     */
    public void setHopeLearnSub(String hopeLearnSub) {
        this.hopeLearnSub = hopeLearnSub;
    }

    /**
     * 特記事項を取得する
     *
     * @return specCont 特記事項
     */
    public String getSpecCont() {
        return this.specCont;
    }

    /**
     * 特記事項を設定する
     *
     * @param specCont 特記事項
     */
    public void setSpecCont(String specCont) {
        this.specCont = specCont;
    }

    /**
     * 会員コードを取得する
     *
     * @return memberCd 会員コード
     */
    public String getMemberCd() {
        return this.memberCd;
    }

    /**
     * 会員コードを設定する
     *
     * @param memberCd 会員コード
     */
    public void setMemberCd(String memberCd) {
        this.memberCd = memberCd;
    }

    /**
     * コースを取得する
     *
     * @return courseId コース
     */
    public String getCourseId() {
        return this.courseId;
    }

    /**
     * コースを設定する
     *
     * @param courseId コース
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * 入会年月日を取得する
     *
     * @return admissionDate 入会年月日
     */
    public Timestamp getAdmissionDate() {
        return this.admissionDate;
    }

    /**
     * 入会年月日を設定する
     *
     * @param admissionDate 入会年月日
     */
    public void setAdmissionDate(Timestamp admissionDate) {
        this.admissionDate = admissionDate;
    }
}
