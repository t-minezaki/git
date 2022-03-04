/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.entity;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p>作用描述</p>
 * <p>详细描述</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/9/16 : xie: 新規<br />
 * @version 1.0
 */
public class BTGKA1004ManaTeacherDto {
    /**
     * 指導者GIDPK
     */
//    @NotNull(message = "指導者GIDPK")
    private String managerGidPk;

    /**
     * 指導者管理コード
     */
//    @NotNull(message = "指導者管理コード")
    private String mgrCd;

    /**
     * 指導者姓
     */
    @NotNull(message = "指導者姓")
    private String mgrFlnmNm;

    /**
     * 指導者名
     */
//    @NotNull(message = "指導者名")
    private String mgrFlnmLnm;

    /**
     * 指導者姓フリガナ
     */
//    @NotNull(message = "指導者姓フリガナ")
    private String mgrFlnmKnNm;

    /**
     * 指導者名フリガナ
     */
//    @NotNull(message = "指導者名フリガナ")
    private String mgrFlnmKnLnm;

    /**
     * メールアドレス
     */
    private String mailad;
    /**
     * 電話番号
     */
    private String telnum;

    protected static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static String validate(Integer rawNumber, BTGKA1004ManaTeacherDto btgka1004ManaTeacherDto, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(btgka1004ManaTeacherDto, groups);
        // 2021/3/18 huangxinliang modify start
        StringBuilder msg = new StringBuilder();
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(MessageUtils.getMessage("MSGCOMB0026", Integer.toString(rawNumber), constraint.getMessage()));
            }
        }
        // 2020/12/9 huangxinliang modify start
        if (StringUtils.isEmpty(btgka1004ManaTeacherDto.getManagerGidPk()) &&
                StringUtils.isEmpty(btgka1004ManaTeacherDto.getMgrCd())){
            msg.append(MessageUtils.getMessage("MSGCOMB0026", Integer.toString(rawNumber), "指導者GIDPK　または　指導者コード"));
        }
        // 2020/12/9 huangxinliang modify end
        return msg.length() == 0 ? null : msg.toString();
        // 2021/3/18 huangxinliang modify end
    }

    /**
     * 指導者GIDPKを取得する
     *
     * @return managerGidPk 指導者GIDPK
     */
    public String getManagerGidPk() {
        return this.managerGidPk;
    }

    /**
     * 指導者GIDPKを設定する
     *
     * @param managerGidPk 指導者GIDPK
     */
    public void setManagerGidPk(String managerGidPk) {
        this.managerGidPk = StringUtils.changeEmptyStringToNull(managerGidPk);
    }

    /**
     * 指導者管理コードを取得する
     *
     * @return mgrCd 指導者管理コード
     */
    public String getMgrCd() {
        return this.mgrCd;
    }

    /**
     * 指導者管理コードを設定する
     *
     * @param mgrCd 指導者管理コード
     */
    public void setMgrCd(String mgrCd) {
        this.mgrCd = StringUtils.changeEmptyStringToNull(mgrCd);
    }

    /**
     * 指導者姓を取得する
     *
     * @return mgrFlnmNm 指導者姓
     */
    public String getMgrFlnmNm() {
        return this.mgrFlnmNm;
    }

    /**
     * 指導者姓を設定する
     *
     * @param mgrFlnmNm 指導者姓
     */
    public void setMgrFlnmNm(String mgrFlnmNm) {
        this.mgrFlnmNm = StringUtils.changeEmptyStringToNull(mgrFlnmNm);
    }

    /**
     * 指導者名を取得する
     *
     * @return mgrFlnmLnm 指導者名
     */
    public String getMgrFlnmLnm() {
        return this.mgrFlnmLnm;
    }

    /**
     * 指導者名を設定する
     *
     * @param mgrFlnmLnm 指導者名
     */
    public void setMgrFlnmLnm(String mgrFlnmLnm) {
        this.mgrFlnmLnm = StringUtils.changeEmptyStringToNull(mgrFlnmLnm);
    }

    /**
     * 指導者姓フリガナを取得する
     *
     * @return mgrFlnmKnNm 指導者姓フリガナ
     */
    public String getMgrFlnmKnNm() {
        return this.mgrFlnmKnNm;
    }

    /**
     * 指導者姓フリガナを設定する
     *
     * @param mgrFlnmKnNm 指導者姓フリガナ
     */
    public void setMgrFlnmKnNm(String mgrFlnmKnNm) {
        this.mgrFlnmKnNm = StringUtils.changeEmptyStringToNull(mgrFlnmKnNm);
    }

    /**
     * 指導者フリガナを取得する
     *
     * @return mgrFlnmKnLnm 指導者フリガナ
     */
    public String getMgrFlnmKnLnm() {
        return this.mgrFlnmKnLnm;
    }

    /**
     * 指導者フリガナを設定する
     *
     * @param mgrFlnmKnLnm 指導者フリガナ
     */
    public void setMgrFlnmKnLnm(String mgrFlnmKnLnm) {
        this.mgrFlnmKnLnm = StringUtils.changeEmptyStringToNull(mgrFlnmKnLnm);
    }

    /**
     * メールアドレスを取得する
     *
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return this.mailad;
    }

    /**
     * メールアドレスを設定する
     *
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = StringUtils.changeEmptyStringToNull(mailad);
    }

    /**
     * 電話番号を取得する
     *
     * @return telnum 電話番号
     */
    public String getTelnum() {
        return this.telnum;
    }

    /**
     * 電話番号を設定する
     *
     * @param telnum 電話番号
     */
    public void setTelnum(String telnum) {
        this.telnum = StringUtils.changeEmptyStringToNull(telnum);
    }
}
