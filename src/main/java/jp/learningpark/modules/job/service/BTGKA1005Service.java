/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.service;

/**
 * <p>BTGKA1005_その他の連携ファイル導入</p >
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
public interface BTGKA1005Service {
    void prepare();

    void courseData();

    void courseDiv();

    void releaseResource();
}
