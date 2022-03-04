/*
 * (C) 2019 LIGHTWORKS CORP.
 * システム名 : Web入会
 * 注意事項 :
 */
package jp.learningpark.framework.gakkenID.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 学研TID構造体
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2019/10/10
 */
public class GakkenTransactionID implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学研TIDプライマリキー
     */
    private String gtidpk;

    /**
     * 学研IDプライマリキー
     */
    private String gidpk;

    /**
     * 学研PIDプライマリキー
     */
    private String gpidpk;

    /**
     * メールアドレス1
     */
    private String mailaddress1;

    /**
     * メールアドレス2
     */
    private String mailaddress2;

    /**
     * メールアドレス3
     */
    private String mailaddress3;

    /**
     * 携帯メールアドレス
     */
    private String mobile;

    /**
     * 姓
     */
    private String lastname;

    /**
     * 名
     */
    private String firstname;

    /**
     * セイ
     */
    private String kanaLastname;

    /**
     * メイ
     */
    private String kanaFirstname;

    /**
     * 生年月日
     */
    private Date birth;

    /**
     * ニックネーム
     */
    private String nickname;

    /**
     * 性別（0:男性,1:女性）
     */
    private String sex;

    /**
     * 国
     */
    private String country;

    /**
     * 郵便番号
     */
    private String zip;

    /**
     * 電話番号
     */
    private String tel;

    /**
     * 電話番号2
     */
    private String tel2;

    /**
     * 都道府県コード
     */
    private String prefCd;

    /**
     * 住所1（都道府県）
     */
    private String address1;

    /**
     * 住所2（市区町村）
     */
    private String address2;

    /**
     * 住所3（番地）
     */
    private String address3;

    /**
     * 住所4（号室）
     */
    private String address4;

    /**
     * 住所5
     */
    private String address5;

    /**
     * 住所6
     */
    private String address6;

    /**
     * 予備項目_1
     */
    private String ext1;

    /**
     * 予備項目_2
     */
    private String ext2;

    /**
     * 予備項目_3
     */
    private String ext3;

    /**
     * サービスサイトコード
     */
    private String siteCd;

    /**
     * ショップコード
     */
    private String shopCd;

    /**
     * イベントキー1
     */
    private String eventKey1;

    /**
     * イベントキー2
     */
    private String eventKey2;

    /**
     * イベントキー3
     */
    private String eventKey3;

    /**
     * パートナーコー ド
     */
    private String partnerCd;

    /**
     * 状態
     */
    private String status;

    /**
     * 登録日
     */
    private Date registT;

    /**
     * 更新日
     */
    private Date updateTs;

    /**
     * 登録開始
     */
    private Date registTsFrom;

    /**
     * 登録終了
     */
    private Date registTsEnd;

    /**
     * 学研TIDプライマリキーを設定する
     */
    public void setGtidpk(String gtidpk) {
        this.gtidpk = gtidpk;
    }

    /**
     * 学研TIDプライマリキーを取得する
     */
    public String getGtidpk() {
        return this.gtidpk;
    }

    /**
     * 学研IDプライマリキーを設定する
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }

    /**
     * 学研IDプライマリキーを取得する
     */
    public String getGidpk() {
        return this.gidpk;
    }

    /**
     * 学研PIDプライマリキーを設定する
     */
    public void setGpidpk(String gpidpk) {
        this.gpidpk = gpidpk;
    }

    /**
     * 学研PIDプライマリキーを取得する
     */
    public String getGpidpk() {
        return this.gpidpk;
    }

    /**
     * メールアドレス1を設定する
     */
    public void setMailaddress1(String mailaddress1) {
        this.mailaddress1 = mailaddress1;
    }

    /**
     * メールアドレス1を取得する
     */
    public String getMailaddress1() {
        return this.mailaddress1;
    }

    /**
     * メールアドレス2を設定する
     */
    public void setMailaddress2(String mailaddress2) {
        this.mailaddress2 = mailaddress2;
    }

    /**
     * メールアドレス2を取得する
     */
    public String getMailaddress2() {
        return this.mailaddress2;
    }

    /**
     * メールアドレス3を設定する
     */
    public void setMailaddress3(String mailaddress3) {
        this.mailaddress3 = mailaddress3;
    }

    /**
     * メールアドレス3を取得する
     */
    public String getMailaddress3() {
        return this.mailaddress3;
    }

    /**
     * 携帯メールアドレスを設定する
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 携帯メールアドレスを取得する
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 姓を設定する
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * 姓を取得する
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * 名を設定する
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * 名を取得する
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * セイを設定する
     */
    public void setKanaLastname(String kanaLastname) {
        this.kanaLastname = kanaLastname;
    }

    /**
     * セイを取得する
     */
    public String getKanaLastname() {
        return this.kanaLastname;
    }

    /**
     * メイを設定する
     */
    public void setKanaFirstname(String kanaFirstname) {
        this.kanaFirstname = kanaFirstname;
    }

    /**
     * メイを取得する
     */
    public String getKanaFirstname() {
        return this.kanaFirstname;
    }

    /**
     * 生年月日を設定する
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 生年月日を取得する
     */
    public Date getBirth() {
        return this.birth;
    }

    /**
     * ニックネームを設定する
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * ニックネームを取得する
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * 性別（0:男性,1:女性）を設定する
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 性別（0:男性,1:女性）を取得する
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * 国を設定する
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 国を取得する
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * 郵便番号を設定する
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 郵便番号を取得する
     */
    public String getZip() {
        return this.zip;
    }

    /**
     * 電話番号を設定する
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 電話番号を取得する
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 都道府県コードを設定する
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * 都道府県コードを取得する
     */
    public String getPrefCd() {
        return this.prefCd;
    }

    /**
     * 住所1（都道府県）を設定する
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 住所1（都道府県）を取得する
     */
    public String getAddress1() {
        return this.address1;
    }

    /**
     * 住所2（市区町村）を設定する
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * 住所2（市区町村）を取得する
     */
    public String getAddress2() {
        return this.address2;
    }

    /**
     * 住所3（番地）を設定する
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    /**
     * 住所3（番地）を取得する
     */
    public String getAddress3() {
        return this.address3;
    }

    /**
     * 住所4（号室）を設定する
     */
    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    /**
     * 住所4（号室）を取得する
     */
    public String getAddress4() {
        return this.address4;
    }

    /**
     * 住所5を設定する
     */
    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    /**
     * 住所5を取得する
     */
    public String getAddress5() {
        return this.address5;
    }

    /**
     * 住所6を設定する
     */
    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    /**
     * 住所6を取得する
     */
    public String getAddress6() {
        return this.address6;
    }

    /**
     * サービスサイトコードを設定する
     */
    public void setSiteCd(String siteCd) {
        this.siteCd = siteCd;
    }

    /**
     * サービスサイトコードを取得する
     */
    public String getSiteCd() {
        return this.siteCd;
    }

    /**
     * ショップコードを設定する
     */
    public void setShopCd(String shopCd) {
        this.shopCd = shopCd;
    }

    /**
     * ショップコードを取得する
     */
    public String getShopCd() {
        return this.shopCd;
    }

    /**
     * イベントキー1を設定する
     */
    public void setEventKey1(String eventKey1) {
        this.eventKey1 = eventKey1;
    }

    /**
     * イベントキー1を取得する
     */
    public String getEventKey1() {
        return this.eventKey1;
    }

    /**
     * イベントキー2を設定する
     */
    public void setEventKey2(String eventKey2) {
        this.eventKey2 = eventKey2;
    }

    /**
     * イベントキー2を取得する
     */
    public String getEventKey2() {
        return this.eventKey2;
    }

    /**
     * イベントキー3を設定する
     */
    public void setEventKey3(String eventKey3) {
        this.eventKey3 = eventKey3;
    }

    /**
     * イベントキー3を取得する
     */
    public String getEventKey3() {
        return this.eventKey3;
    }

    /**
     * パートナーコー ドを設定する
     */
    public void setPartnerCd(String partnerCd) {
        this.partnerCd = partnerCd;
    }

    /**
     * パートナーコー ドを取得する
     */
    public String getPartnerCd() {
        return this.partnerCd;
    }

    /**
     * 状態を設定する
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 状態を取得する
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 登録日を設定する
     */
    public void setRegistT(Date registT) {
        this.registT = registT;
    }

    /**
     * 登録日を取得する
     */
    public Date getRegistT() {
        return this.registT;
    }

    /**
     * 更新日を設定する
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 更新日を取得する
     */
    public Date getUpdateTs() {
        return this.updateTs;
    }

    /**
     * 登録開始を設定する
     */
    public void setRegistTsFrom(Date registTsFrom) {
        this.registTsFrom = registTsFrom;
    }

    /**
     * 登録開始を取得する
     */
    public Date getRegistTsFrom() {
        return this.registTsFrom;
    }

    /**
     * 登録終了を設定する
     */
    public void setRegistTsEnd(Date registTsEnd) {
        this.registTsEnd = registTsEnd;
    }

    /**
     * 登録終了を取得する
     */
    public Date getRegistTsEnd() {
        return this.registTsEnd;
    }

    /**
     * 電話番号2を設定する
     *
     * @return tel2 電話番号2
     */
    public String getTel2() {
        return this.tel2;
    }

    /**
     * 電話番号2を取得する
     *
     * @param tel2 電話番号2
     */
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    /**
     * 予備項目_1を設定する
     *
     * @return ext1 予備項目_1
     */
    public String getExt1() {
        return this.ext1;
    }

    /**
     * 予備項目_1を取得する
     *
     * @param ext1 予備項目_1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    /**
     * 予備項目_2を設定する
     *
     * @return ext2 予備項目_2
     */
    public String getExt2() {
        return this.ext2;
    }

    /**
     * 予備項目_2を取得する
     *
     * @param ext2 予備項目_2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    /**
     * 予備項目_3を設定する
     *
     * @return ext3 予備項目_3
     */
    public String getExt3() {
        return this.ext3;
    }

    /**
     * 予備項目_3を取得する
     *
     * @param ext3 予備項目_3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
}