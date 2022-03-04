/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstOrgEntity;

/**
 * <p>F00003_組織設定・修正画面 Service/p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/24 : gong: 新規<br />
 * @version 1.0
 */
public interface F00003Service {
    /**
     * <p>登録</p>
     *
     * @param org
     * @param upLevel 前画面から受け取った階層
     * @return
     */
    R submitFn(MstOrgEntity org,Integer upLevel,String upTime);
}
