/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;/**
 * GakkenAppApplication
 *
 * @author lyh
 * @date 2020/04/27
 */

import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.student.dao.F11003Dao;
import jp.learningpark.modules.student.service.F11003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/04/27 : lyh: 新規<br />
 * @version 1.0
 */
@Service
public class F11003ServiceImpl implements F11003Service {

    @Autowired
    F11003Dao f11003Dao;

    /**
     *
     * @return
     */
    @Override
    public List<MstBlockEntity> init() {
        return f11003Dao.init();
    }
}