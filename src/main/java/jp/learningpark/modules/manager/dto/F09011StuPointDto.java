package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.StuPointEntity;

/**
 * <p>ポイント設定確認画面 生徒 Dto</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
public class F09011StuPointDto extends StuPointEntity {

    /**
     * 排他
     */
    private String updDatimeFormat;

    public String getUpdDatimeFormat() {
        return updDatimeFormat;
    }

    public void setUpdDatimeFormat(String updDatimeFormat) {
        this.updDatimeFormat = updDatimeFormat;
    }
}
