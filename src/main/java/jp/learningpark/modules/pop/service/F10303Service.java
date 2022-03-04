/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.pop.dto.F10303Dto;

import java.util.List;

/**
 * <p>F10303 復習教科選択画面(POP)</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/11 : gong: 新規<br />
 * @version 1.0
 */
public interface F10303Service {
    /**
     * <p>F10303 当生徒の学校で学習するすべて教科を取得し</p>
     *
     * @param stuId 生徒Id
     * @param orgId 組織ID
     * @return list(教科div 教科名 教科img)
     */
    List<MstCodDEntity> getSubjtDivsByStuId(String stuId,String orgId);

    // 2020/11/12 zhangminghao modify start
    /**
     * デフォルト教科を取得
     *
     * @return 取得したデフォルトの教科情報
     */
    MstCodDEntity getDefaultSubject();
    // 2020/11/12 zhangminghao modify end

    /**
     * <p>登録ボタン押下</p>
     *
     * @param stuId 生徒Id
     * @param dto
     * @return
     */
    R submitFn(String stuId, F10303Dto dto);
}
