/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dto;

import java.util.Objects;

/**
 * <p>組織検索追加画面 Dto</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/10/14: zmh: 新規<br />
 * @version 1.0
 */
public class F00046Dto implements Comparable<F00046Dto> {
    private String orgId;
    private String orgNm;
    private Integer level;
    private String upplevOrgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUpplevOrgId() {
        return upplevOrgId;
    }

    public void setUpplevOrgId(String upplevOrgId) {
        this.upplevOrgId = upplevOrgId;
    }

    @Override
    public String toString() {
        return "F00046Dto{" +
                "orgId='" + orgId + '\'' +
                ", orgNm='" + orgNm + '\'' +
                ", level=" + level +
                ", upplevOrgId='" + upplevOrgId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        F00046Dto f00046Dto = (F00046Dto) o;
        return Objects.equals(orgId, f00046Dto.orgId) &&
                Objects.equals(orgNm, f00046Dto.orgNm) &&
                Objects.equals(level, f00046Dto.level) &&
                Objects.equals(upplevOrgId, f00046Dto.upplevOrgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, orgNm, level, upplevOrgId);
    }

    @Override
    public int compareTo(F00046Dto target) {
        if(Objects.equals(level, target.getLevel())){
            return 0;
        }

        return level > target.getLevel() ? -1 : 1;
    }
}