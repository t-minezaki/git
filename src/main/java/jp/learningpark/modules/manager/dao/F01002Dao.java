package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>F01002_塾時期新規・変更画面 Dao</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/09 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F01002Dao {
    /**
     * 塾学習期間マスタ
     * @param orgId 組織ID
     * @param id    ID
     * @return      学年区分情報
     */
    MstCrmschLearnPrdEntity selectByIdOrgId(@Param("orgId") String orgId, @Param("id") Integer id);

    /**
     * 学年区分リストボックスを表示するため、コードマスタを元に、学年区分を取得し、画面で表示する。
     * @return  塾学習期間一覧情報
     */
    List<MstCodDEntity> selectCodeValue();

    /**
     * 引渡データ．IDをキーとして、塾学習期間マスタを更新する。
     * @param orgId     組織ID
     * @param id        ID
     * @param schyDiv   学年区分
     * @return          更新情報
     */
    Integer updateCrm(@Param("orgId") String orgId, @Param("id") Integer id, @Param("schyDiv") String schyDiv);

    /**
     * 塾学習期間データが生徒教科書選択管理マスタに存在するかどうかを判定する。
     * @param crmId 塾学習期
     * @return      生徒教科書選択管理情報
     */
    Integer selectOneByCrmId(Integer crmId);

    /**
     * 対応する学習時期マスタから、データを物理削除する。
     * @param crmId 塾学習期
     * @return      削除情報
     */
    Integer deleteByCrmId(Integer crmId);
}
