package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09011StuDto;
import jp.learningpark.modules.manager.dto.F09011StuPointDto;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>ポイント設定確認画面Dao</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F09011Dao {

    /**
     * ユーザーの役割と学生IDに基づいて対応する学生情報を返します
     *
     * @param user   現在ログインしているユーザー
     * @param stuIds 照会する学生IDコレクション
     * @param limit  ページングサイズ
     * @param page   ページ番号
     * @return 学生情報を返します
     */
    List<F09011StuDto> selectStuInfoListByStuId(@Param("user") SysUserEntity user,
                                                @Param("stuIds") List<String> stuIds,
                                                @Param("limit") Integer limit,
                                                @Param("page") Integer page);

    /**
     * ユーザーの役割と学生IDに応じて、対応する数の学生情報を返します
     *
     * @param user   現在ログインしているユーザー
     * @param stuIds 照会する学生IDコレクション
     * @return 対応する数の学生を返します
     */
    Integer selectStuInfoListByStuIdCount(@Param("user") SysUserEntity user,
                                          @Param("stuIds") List<String> stuIds);

    /**
     * 学生ポイント情報の更新時刻を取得する
     *
     * @param stuIds 照会する学生IDコレクション
     * @return 学生ポイントデータ更新時間
     */
    List<F09011StuPointDto> selectStuUpdateTime(@Param("stuIds") List<String> stuIds);
}
