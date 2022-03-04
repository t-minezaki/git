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
public class BTGKA1004ManaOrgDto {
    /**
     * 組織コード
     */
    @NotNull(message = "組織コード")
    private String orgCd;
    /**
     * 組織名
     */
    @NotNull(message = "組織名")
    private String orgNm;
    /**
     * 階層コード
     */
    @NotNull(message = "階層コード")
    private Integer level;
    /**
     * 階層コード
     */
    private String upplevOrgId;
    /**
     * 所属区分
     */
    private String div;
    /**
     * Web申込利用フラグ
     */
    private String webUseFlg;

    protected static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static String validate(Integer rawNumber, BTGKA1004ManaOrgDto btgka1004ManaOrgDto, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(btgka1004ManaOrgDto, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(MessageUtils.getMessage("MSGCOMB0026", Integer.toString(rawNumber), constraint.getMessage()));
            }
            return msg.toString();
        } else {
            return null;
        }
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
        this.orgCd = StringUtils.changeEmptyStringToNull(orgCd);;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = StringUtils.changeEmptyStringToNull(orgNm);;
    }

    /**
     * 階層コードを取得する
     *
     * @return level 階層コード
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 階層コードを設定する
     *
     * @param level 階層コード
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 階層コードを取得する
     *
     * @return upplevOrgId 階層コード
     */
    public String getUpplevOrgId() {
        return this.upplevOrgId;
    }

    /**
     * 階層コードを設定する
     *
     * @param upplevOrgId 階層コード
     */
    public void setUpplevOrgId(String upplevOrgId) {
        this.upplevOrgId = upplevOrgId;
    }

    /**
     * 所属区分を取得する
     *
     * @return div 所属区分
     */
    public String getDiv() {
        return this.div;
    }

    /**
     * 所属区分を設定する
     *
     * @param div 所属区分
     */
    public void setDiv(String div) {
        this.div = div;
    }

    /**
     * Web申込利用フラグを取得する
     *
     * @return webUseFlg Web申込利用フラグ
     */
    public String getWebUseFlg() {
        return this.webUseFlg;
    }

    /**
     * Web申込利用フラグを設定する
     *
     * @param webUseFlg Web申込利用フラグ
     */
    public void setWebUseFlg(String webUseFlg) {
        this.webUseFlg = webUseFlg;
    }
}
