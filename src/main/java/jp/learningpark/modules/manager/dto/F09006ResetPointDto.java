package jp.learningpark.modules.manager.dto;

import java.util.List;

/**
 * <p>F09006ResetPointDto</p>
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/16 : zmh: 新規<br />
 * @version 1.0
 */
public class F09006ResetPointDto {
    private List<String> stuIdList;

    public List<String> getStuIdList() {
        return stuIdList;
    }

    public void setStuIdList(List<String> stuIdList) {
        this.stuIdList = stuIdList;
    }
}