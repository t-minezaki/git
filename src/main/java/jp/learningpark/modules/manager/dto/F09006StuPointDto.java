package jp.learningpark.modules.manager.dto;

public class F09006StuPointDto {
    private String stuId;

    private Integer point;

    /**
     * ポイントに[multiple]の数値を掛けたもの
     * @param multiple 指定された値を乗算します
     */
    public void pointMultiply(int multiple){
        // 2020/12/22 huangxinliang modify start
        if (point == null){
            point = 0;
        }
        // 2020/12/22 huangxinliang modify end
        point *= multiple;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
