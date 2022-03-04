package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstEventEntity;

/**
 * <p>F08004</p >
 *
 * @author NWT : zhaoxiaoqin <br />
 * 2019/09/23 : zhaoxiaoqin: 新規<br />
 * @version 1.0
 */
public class F08004Dto extends MstEventEntity {

    // テンプレートタイトル
    private String tmplateTitle;

    // 対象
    private String object;

    /**
     * 画面．テンプレートタイトル
     *
     * @return tmplateTitle 画面．テンプレートタイトル
     */
    public String getTmplateTitle() {
        return tmplateTitle;
    }

    /**
     * 画面．テンプレートタイトル
     *
     * @param tmplateTitle 画面．テンプレートタイトル
     */
    public void setTmplateTitle(String tmplateTitle) {
        this.tmplateTitle = tmplateTitle;
    }

    /**
     * 画面．対象
     *
     * @return object 画面．対象
     */
    public String getObject() {
        return object;
    }

    /**
     * 画面．対象
     *
     * @param object 画面．対象
     */
    public void setObject(String object) {
        this.object = object;
    }
}
