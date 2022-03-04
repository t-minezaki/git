/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.service.impl;

import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.dao.F40018Dao;
import jp.learningpark.modules.com.dto.F40018Dto;
import jp.learningpark.modules.com.service.F40018Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>ワンタイムパスワード入力画面</p>
 * <p>ServiceImpl</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/3 : xie: 新規<br />
 * @version 1.0
 */
@Service
public class F40018ServiceImpl implements F40018Service {

    /**
     * F40018Dao
     */
    @Autowired
    F40018Dao f40018Dao;


    @Override
    public F40018Dto authenticationPassword(String userId) {
        return f40018Dao.authenticationPassword( userId);
    }

    @Override
    public void update(String userId, String mailad, String roleDiv) {
        String afterUsrId = ShiroUtils.getUserEntity().getAfterUsrId();
        f40018Dao.update( userId, mailad, roleDiv, afterUsrId);
    }

}
