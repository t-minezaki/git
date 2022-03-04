/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstTextbEntity;
import jp.learningpark.modules.manager.dto.F20002Dto;

import java.util.List;
import java.util.Map;

/**
 * <p>F20002生徒基本情報画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/05 : gong: 新規<br />
 * @version 1.0
 */
public interface F20002Service {
    /**
     * <p>当生徒当学年の教科ごとの塾の教科書情報を取得</p>
     *
     * @param stuId 生徒Id
     * @param orgId 塾Id
     * @return
     */
    List<F20002Dto> getTextbDtoListOfSchByStuId(String stuId, String orgId);

    /**
     * <p>F20002 生徒情報を取得する(生徒id,生徒の名前，學年，所属校，塾名)</p>
     *
     * @param stuId 生徒id
     * @param orgId 塾Id
     * @return 生徒情報StuTextbDto
     */
    F20002Dto getStuInfoByStuId(String stuId, String orgId);

    /**
     * <p>1.2 塾学習期間IDの取得</p>
     *
     * @param orgId 塾Id
     * @return
     */
    MstCrmschLearnPrdEntity getCrmschLearnPrdId(String orgId);

    /**
     * <p>生徒選択した教科書リストを取得</p>
     * @param map
     * @return
     */
    List<MstTextbEntity> getTextbListOfStuByStuIdAndOrgIdAndSchyDiv(Map<String, Object> map);

    /**
     * 登録ボタンの処理
     *
     * @param bookDto
     * @return
     */
    /**
     * <p>登録ボタンの処理</p>
     *
     * @param bookDto
     * @return
     */
    R update(F20002Dto bookDto);
}
