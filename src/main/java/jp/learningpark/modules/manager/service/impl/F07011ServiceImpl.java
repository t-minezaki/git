/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F07011Dao;
import jp.learningpark.modules.manager.dto.F07011Dto;
import jp.learningpark.modules.manager.service.F07011Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F07011_成績教科メンテナンス一覧画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/23 : yang: 新規<br />
 * @version 1.0
 */
@Service
public class F07011ServiceImpl implements F07011Service {
    /**
     * F07011Dao
     */
    @Autowired
    private  F07011Dao f07011Dao;

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
    @Override
    public List<F07011Dto> getMstcodList(String schy,String codCd,String subName,Integer limit,Integer page) {
        return f07011Dao.mstcodList(schy,codCd,subName,limit,page);
    }

    /**
     *<p>コードマスタ情報記録数取得</p>
     *
     * @param schy 学年
     * @param codCd 教科CD
     * @param subName 教科名
     * @return
     */
    @Override
    public Integer getMstcodCount(String schy,String codCd,String subName) {
        return f07011Dao.mstcodCount(schy,codCd,subName);
    }

}
