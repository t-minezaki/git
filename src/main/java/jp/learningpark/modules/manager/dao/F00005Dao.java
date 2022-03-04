/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F00005Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>F00005_生徒グループ管理画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/20 : gong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00005Dao {
    /**
     * DBの生徒グループ管理、ユーザ基本マスタから⑧で選択した保存先に情報を出力ファイルに出力して保存する。
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00005Dto> selectList(String orgId);
}
