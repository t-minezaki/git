/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;


import java.sql.Timestamp;

/**
 * <p>教科書デフォルトターム情報更新時間リスト</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/12/28 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F03001TmListDto {
    private Integer id;

    /**
     * 教科書デフォルトターム情報更新時間
     */
    private Timestamp tdtiUpdTm;

    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * tdtiUpdTmを取得する
     *
     * @return tdtiUpdTm tdtiUpdTm
     */
    public Timestamp getTdtiUpdTm() {
        return tdtiUpdTm;
    }

    /**
     * tdtiUpdTmを設定する
     *
     * @param tdtiUpdTm tdtiUpdTm
     */
    public void setTdtiUpdTm(Timestamp tdtiUpdTm) {
        this.tdtiUpdTm = tdtiUpdTm;
    }
}
