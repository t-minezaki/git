package jp.learningpark.modules.manager.dto;

import java.util.List;

/**
 * <p>ポイント設定確認画面 情報更新処理 Dto</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
public class F09011SaveDto {

    /**
     * 画面生徒ID
     */
    private List<String> stuIdList;

    /**
     * もう存在している学生ポイント情報の更新時刻
     * その後の提出時に排他ロックの判断に使用されます
     * 同時に、学生ポイント情報がデータベースにすでに存在するかどうかを判断するために使用できます
     */
    private List<F09011StuPointDto> oldStuPoint;

    /**
     * 付与ポイント
     */
    private Integer point;

    public List<String> getStuIdList() {
        return stuIdList;
    }

    public void setStuIdList(List<String> stuIdList) {
        this.stuIdList = stuIdList;
    }

    public List<F09011StuPointDto> getOldStuPoint() {
        return oldStuPoint;
    }

    public void setOldStuPoint(List<F09011StuPointDto> oldStuPoint) {
        this.oldStuPoint = oldStuPoint;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "F09011SaveDto{" +
                "stuIdList=" + stuIdList +
                ", point=" + point +
                '}';
    }
}
