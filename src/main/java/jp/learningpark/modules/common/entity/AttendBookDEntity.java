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
 * 出席簿明細
 *
 * @author NWT
 */
@TableName("attend_book_d")
public class AttendBookDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 出席簿ヘーダのID
     */
    @NotNull
    private Integer attendBookId;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 出欠ステータス区分
     */
    private String absStsDiv;

    /**
     * 宿題区分
     */
    private String homeWkDiv;

    /**
     * 小テスト点数
     */
    private Integer testPoints;

    /**
     * ケア
     */
    private String careDiv;

    /**
     * add at 2021/08/12 for V9.02 by NWT wen
     * 小テスト合否区分
     */
    private String testPassKbn;

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
     * 予備１
     */
    private String yobi1;

    /**
     * 予備２
     */
    private String yobi2;

    /**
     * 予備３
     */
    private String yobi3;

    /**
     * 予備４
     */
    private String yobi4;

    /**
     * 予備５
     */
    private String yobi5;

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
     * 出席簿ヘーダのIDを設定する
     */
    public void setAttendBookId(Integer attendBookId) {
        this.attendBookId = attendBookId;
    }

    /**
     * 出席簿ヘーダのIDを取得する
     */
    public Integer getAttendBookId() {
        return attendBookId;
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
     * 教科区分を設定する
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 教科区分を取得する
     */
    public String getSubjtDiv() {
        return subjtDiv;
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
     * 宿題区分を設定する
     */
    public void setHomeWkDiv(String homeWkDiv) {
        this.homeWkDiv = homeWkDiv;
    }

    /**
     * 宿題区分を取得する
     */
    public String getHomeWkDiv() {
        return homeWkDiv;
    }

    /**
     * 小テスト点数を設定する
     */
    public void setTestPoints(Integer testPoints) {
        this.testPoints = testPoints;
    }

    /**
     * 小テスト点数を取得する
     */
    public Integer getTestPoints() {
        return testPoints;
    }

    /**
     * ケアを設定する
     */
    public void setCareDiv(String careDiv) {
        this.careDiv = careDiv;
    }

    /**
     * ケアを取得する
     */
    public String getCareDiv() {
        return careDiv;
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
     * 予備１を取得する
     *
     * @return yobi1 予備１
     */
    public String getYobi1() {
        return this.yobi1;
    }

    /**
     * 予備１を設定する
     *
     * @param yobi1 予備１
     */
    public void setYobi1(String yobi1) {
        this.yobi1 = yobi1;
    }

    /**
     * 予備２を取得する
     *
     * @return yobi2 予備２
     */
    public String getYobi2() {
        return this.yobi2;
    }

    /**
     * 予備２を設定する
     *
     * @param yobi2 予備２
     */
    public void setYobi2(String yobi2) {
        this.yobi2 = yobi2;
    }

    /**
     * 予備３を取得する
     *
     * @return yobi3 予備３
     */
    public String getYobi3() {
        return this.yobi3;
    }

    /**
     * 予備３を設定する
     *
     * @param yobi3 予備３
     */
    public void setYobi3(String yobi3) {
        this.yobi3 = yobi3;
    }

    /**
     * 予備４を取得する
     *
     * @return yobi4 予備４
     */
    public String getYobi4() {
        return this.yobi4;
    }

    /**
     * 予備４を設定する
     *
     * @param yobi4 予備４
     */
    public void setYobi4(String yobi4) {
        this.yobi4 = yobi4;
    }

    /**
     * 予備５を取得する
     *
     * @return yobi5 予備５
     */
    public String getYobi5() {
        return this.yobi5;
    }

    /**
     * 予備５を設定する
     *
     * @param yobi5 予備５
     */
    public void setYobi5(String yobi5) {
        this.yobi5 = yobi5;
    }

    /**
     * 小テスト合否区分を取得する
     * <p>
     * add at 2021/08/12 for V9.02 by NWT wen
     *
     * @return testPassKbn 小テスト合否区分
     */
    public String getTestPassKbn() {
        return this.testPassKbn;
    }

    /**
     * 小テスト合否区分を設定する
     * <p>
     * add at 2021/08/12 for V9.02 by NWT wen
     *
     * @param testPassKbn 小テスト合否区分
     */
    public void setTestPassKbn(String testPassKbn) {
        this.testPassKbn = testPassKbn;
    }
}
