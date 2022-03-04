/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.manager.dto.F00041Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>ユーザー基本情報一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/12 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00041Dao {
    /**
     * 初期ユーザ―一覧を取得
     *
     * @param orgId 組織ID
     * @param limit
     * @param page
     * @return
     */
    List<F00041Dto> selectMgrList(@Param("orgId") String orgId, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * 初期ユーザ―カウントを取得
     *
     * @param orgId 組織ID
     * @return
     */
    Integer selectMgrCount(@Param("orgId") String orgId);

    /**
     * ユーザ―一覧を取得
     *
     * @param usrRole ユーザ権限区分
     * @param name 姓名
     * @param knName カナ姓名
     * @param orgIdList 組織IDリスト
     * @param afterUsrId 変更後ユーザID
     * @param usrSts ユーザステータス
     * @param orgId 組織ID
     * @param schy 学年
     * @param limit
     * @param page
     * @return
     */
    List<F00041Dto> selectUserList(
            @Param("usrRole") String usrRole,
            @Param("name") String name,
            @Param("knName") String knName,
            @Param("schy") String schy,
            @Param("orgIdList") List<String> orgIdList,
            @Param("orgId") String orgId,
            @Param("afterUsrId") String afterUsrId, @Param("usrSts") String usrSts, @Param("limit") Integer limit, @Param("page") Integer page);

    /**
     * ユーザ―カウントを取得
     *
     * @param usrRole ユーザ権限区分
     * @param name 姓名
     * @param knName カナ姓名
     * @param orgIdList 組織IDリスト
     * @param orgId 組織ID
     * @param schy 学年
     * @param afterUsrId 変更後ユーザID
     * @param usrSts ユーザステータス
     * @return
     */
    Integer selectUserCount(
            @Param("usrRole") String usrRole,
            @Param("name") String name,
            @Param("knName") String knName,
            @Param("schy") String schy,
            @Param("orgIdList") List<String> orgIdList, @Param("orgId") String orgId, @Param("afterUsrId") String afterUsrId, @Param("usrSts") String usrSts);

    /**
     * @param stuEntities 修正が必要な学生リスト
     * @return
     */
    int updateStudent(@Param("stuEntities") List<MstStuEntity> stuEntities);
}
