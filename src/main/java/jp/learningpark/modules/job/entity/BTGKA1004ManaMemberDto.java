package jp.learningpark.modules.job.entity;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/9/7 ： NWT)hxl ： 新規作成
 * @date 2020/9/7 14:46
 */
public class BTGKA1004ManaMemberDto {
    /**
     * 教室コード
     */
    @NotNull(message = "教室コード")
    private String orgId;

    /**
     * 会員コード
     */
    @NotNull(message = "会員コード")
    private String memberCd;

    /**
     * 保護者名(漢字)_姓
     */
//    @NotNull(message = "保護者名(漢字)_姓")
    private String guardFlnmNm;

    /**
     * 保護者名(漢字)_名
     */
//    @NotNull(message = "保護者名(漢字)_名")
    private String guardFlnmLnm;

    /**
     * 保護者名(カナ)_姓
     */
    private String guardFlnmKnNm;

    /**
     * 保護者名(カナ)_名
     */
    private String guardFlnmKnLnm;

    /**
     * 生徒GIDPK
     */
    @NotNull(message = "生徒GIDPK")
    private String stuGidpk;

    /**
     * 保護者GIDPK
     */
    @NotNull(message = "保護者GIDPK")
    private String guardGidpk;

    protected static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static String validate(Integer rawNumber, BTGKA1004ManaMemberDto btgka1004MemberDto, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(btgka1004MemberDto, groups);
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
     * 教室コードを取得する
     *
     * @return orgId 教室コード
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 教室コードを設定する
     *
     * @param orgId 教室コード
     */
    public void setOrgId(String orgId) {
        this.orgId = StringUtils.changeEmptyStringToNull(orgId);
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
        this.memberCd = StringUtils.changeEmptyStringToNull(memberCd);
    }


    /**
     * 保護者名(漢字)_姓を取得する
     *
     * @return guardFlnmNm 保護者名(漢字)_姓
     */
    public String getGuardFlnmNm() {
        return this.guardFlnmNm;
    }

    /**
     * 保護者名(漢字)_姓を設定する
     *
     * @param guardFlnmNm 保護者名(漢字)_姓
     */
    public void setGuardFlnmNm(String guardFlnmNm) {
        this.guardFlnmNm = StringUtils.changeEmptyStringToNull(guardFlnmNm);
    }

    /**
     * 保護者名(漢字)_名を取得する
     *
     * @return guardFlnmLnm 保護者名(漢字)_名
     */
    public String getGuardFlnmLnm() {
        return this.guardFlnmLnm;
    }

    /**
     * 保護者名(漢字)_名を設定する
     *
     * @param guardFlnmLnm 保護者名(漢字)_名
     */
    public void setGuardFlnmLnm(String guardFlnmLnm) {
        this.guardFlnmLnm = StringUtils.changeEmptyStringToNull(guardFlnmLnm);
    }

    /**
     * 保護者名(カナ)_姓を取得する
     *
     * @return guardFlnmKnNm 保護者名(カナ)_姓
     */
    public String getGuardFlnmKnNm() {
        return this.guardFlnmKnNm;
    }

    /**
     * 保護者名(カナ)_姓を設定する
     *
     * @param guardFlnmKnNm 保護者名(カナ)_姓
     */
    public void setGuardFlnmKnNm(String guardFlnmKnNm) {
        this.guardFlnmKnNm = StringUtils.changeEmptyStringToNull(guardFlnmKnNm);
    }

    /**
     * 保護者名(カナ)_名を取得する
     *
     * @return guardFlnmKnLnm 保護者名(カナ)_名
     */
    public String getGuardFlnmKnLnm() {
        return this.guardFlnmKnLnm;
    }

    /**
     * 保護者名(カナ)_名を設定する
     *
     * @param guardFlnmKnLnm 保護者名(カナ)_名
     */
    public void setGuardFlnmKnLnm(String guardFlnmKnLnm) {
        this.guardFlnmKnLnm = StringUtils.changeEmptyStringToNull(guardFlnmKnLnm);
    }

    /**
     * 生徒GIDPKを取得する
     *
     * @return stuGidpk 生徒GIDPK
     */
    public String getStuGidpk() {
        return this.stuGidpk;
    }

    /**
     * 生徒GIDPKを設定する
     *
     * @param stuGidpk 生徒GIDPK
     */
    public void setStuGidpk(String stuGidpk) {
        this.stuGidpk = StringUtils.changeEmptyStringToNull(stuGidpk);
    }

    /**
     * 保護者GIDPKを取得する
     *
     * @return guardGidpk 保護者GIDPK
     */
    public String getGuardGidpk() {
        return this.guardGidpk;
    }

    /**
     * 保護者GIDPKを設定する
     *
     * @param guardGidpk 保護者GIDPK
     */
    public void setGuardGidpk(String guardGidpk) {
        this.guardGidpk = StringUtils.changeEmptyStringToNull(guardGidpk);
    }
}
