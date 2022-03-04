/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F09022Dao;
import jp.learningpark.modules.manager.dto.F09022Dto;
import jp.learningpark.modules.manager.service.F09022Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>スマホ_一斉お知らせ配信状況一覧画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/27 : lyh: 新規<br />
 * @version 6.0
 */
@Service
public class F09022ServiceImpl implements F09022Service {
    @Autowired
    F09022Dao f09022Dao;
    //初期表示
    public List<F09022Dto> init(String orgId,Integer noticeId,String flg){
        return f09022Dao.init(orgId, noticeId, flg);
    }
}