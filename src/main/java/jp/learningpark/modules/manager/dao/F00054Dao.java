/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F00054Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * F00054_生徒グループ関係設定修正画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/19 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00054Dao {

    /**
     * 生徒と生徒グループ管理情報を表示するため
     * @param  orgId セッションデータ．組織ＩＤ
     * @return
     */
    List<F00054Dto> getStuInformation(@Param("orgId")String orgId,@Param("params") Map<String,String> params);
    /**
     * グループID、グループ名情報取得
     * @param  orgId セッションデータ．組織ＩＤ
     * @return
     */
    List<F00054Dto> getGroupList(@Param("orgId")String orgId);

    /**
     * 追加グループ情報を表示するため
     * @param  orgId セッションデータ．組織ＩＤ
     * @returns
     */
    List<F00054Dto> getStuList(@Param("orgId")String orgId);

    List<F00054Dto> getGrpStuInfo(@Param("orgId")String orgId,@Param("groupId")Integer groupId);

}
