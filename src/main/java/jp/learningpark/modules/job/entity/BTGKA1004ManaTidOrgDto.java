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
public class BTGKA1004ManaTidOrgDto {
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
     * 組織コード
     */
    @NotNull(message = "組織コード")
    private String orgCd;

    protected static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static String validate(Integer rawNumber, BTGKA1004ManaTidOrgDto btgka1004ManaTidOrgDto, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(btgka1004ManaTidOrgDto, groups);
        // 2021/3/18 huangxinliang modify start
        StringBuilder msg = new StringBuilder();
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(MessageUtils.getMessage("MSGCOMB0026", Integer.toString(rawNumber), constraint.getMessage()));
            }
        }
        // 2020/12/9 huangxinliang modify start
        if (StringUtils.isEmpty(btgka1004ManaTidOrgDto.getManagerGidPk()) &&
                StringUtils.isEmpty(btgka1004ManaTidOrgDto.getMgrCd())){
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
     * 組織コードを取得する
     *
     * @return orgCd 組織コード
     */
    public String getOrgCd() {
        return this.orgCd;
    }

    /**
     * 組織コードを設定する
     *
     * @param orgCd 組織コード
     */
    public void setOrgCd(String orgCd) {
        this.orgCd = StringUtils.changeEmptyStringToNull(orgCd);
    }
}
