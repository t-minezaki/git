/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F10303 復習教科選択画面(POP)</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/19 : yangfan: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F10303Dao {
    /**
     * <p>F10303 当生徒の学校で学習するすべて教科を取得し</p>
     *
     * @param stuId 生徒Id
     * @param orgId 組織ID
     * @return list(教科div 教科名 教科img)
     */
    List<MstCodDEntity> selectSubjtDivsByStuId(@Param("stuId") String stuId,@Param("orgId") String orgId);

    // 2020/11/12 zhangminghao modify start
    /**
     * デフォルト教科を取得
     *
     * @param defaultSubject 教科のデフォルト名
     * @return 取得したデフォルトの教科情報
     */
    MstCodDEntity getDefaultSubject(String defaultSubject);
    // 2020/11/12 zhangminghao modify end
}
