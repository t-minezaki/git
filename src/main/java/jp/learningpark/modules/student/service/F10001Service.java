/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.student.dto.F10001Dto;

import java.util.List;

/**
 * <p>F10001生徒基本情報登録画面 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/16 : gong: 新規<br />
 * @version 1.0
 */
public interface F10001Service {
    /**
     * <p>更新</p>
     *
     * @param dto ページ情報
     * @return
     */
    R update(F10001Dto dto);

    /**
     * 生徒タイムプラン
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return StuMstDto(写真 　 生徒姓名 　 生徒ID 　 生年月日 　 性別 　 学校学年 　 塾)
     */
    F10001Dto getByStuId(String stuId, String orgId);

    /**
     * <p>生徒が選択した教科書情報(stu_textb_choc_mst)</p>
     *
     * @param stuId 生徒id
     * @return 教科書Idリスト
     */
    List<String> getTextbChocList(String stuId);
}
