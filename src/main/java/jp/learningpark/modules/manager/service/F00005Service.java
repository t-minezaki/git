/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00005Dto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>F00004 利用者基本情報設定・修正 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
public interface F00005Service {
    /**
     * グループ情報のインポート
     *
     * @param file ファイル
     * @param type エラーとする or 上書きする
     * @return
     */
    R importFile(MultipartFile file, Integer type);

    /**
     * 生徒・グループ情報のインポート
     *
     * @param file ファイル
     * @param type エラーとする or 上書きする
     * @return
     */
    R importFileStuGrp(MultipartFile file, Integer type);

    /**
     * DBの生徒グループ管理、ユーザ基本マスタから⑧で選択した保存先に情報を出力ファイルに出力して保存する。
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00005Dto> getList(String orgId);

}
