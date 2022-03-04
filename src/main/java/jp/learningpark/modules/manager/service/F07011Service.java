/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;


import jp.learningpark.modules.manager.dto.F07011Dto;

import java.util.List;

/**
 * <p>F07011_成績教科メンテナンス一覧画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/23 : yang: 新規<br />
 * @version 1.0
 */
public interface F07011Service {
    /**
     *<p>コードマスタ情報取得</p>
     *
     * @param schy 学年
     * @param codCd 教科CD
     * @param subName 教科名
     * @param limit 件数
     * @param page ページ数
     * @return
     */
    List<F07011Dto> getMstcodList(String schy,String codCd,String subName,Integer limit,Integer page);

    /**
     *<p>コードマスタ情報記録数取得</p>
     *
     * @param schy 学年
     * @param codCd 教科CD
     * @param subName 教科名
     * @return
     */
    Integer getMstcodCount(String schy,String codCd,String subName);
}
