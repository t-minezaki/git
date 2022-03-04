/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F00055GuardDto;
import jp.learningpark.modules.pop.dto.F00055MentorDto;
import jp.learningpark.modules.pop.dto.F00055StudentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>F00055 ユーザー選択画面（POP） Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/20 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00055Dao {

    /**
     * <p>学生情報を取得する</p>
     *
     * @param stuId     学生id
     * @param studentNm 学生名前
     * @param schyDiv   学年
     * @param orgId     組織ＩＤ
     * @param startRow  開始位置
     * @return　学生情報
     */
    List<F00055StudentDto> selectStudentInfo(@Param("stuId") String stuId, @Param("studentNm") String studentNm, @Param("schyDiv") String schyDiv, @Param("orgId") String orgId, @Param("startRow") Integer startRow);

    /**
     * <p>学生情報記録数を取得する</p>
     *
     * @param stuId     学生id
     * @param studentNm 学生名前
     * @param schyDiv   学年
     * @param orgId     組織ＩＤ
     * @return　学生情報記録数
     */
    Integer selectStudentInfoCount(@Param("stuId") String stuId, @Param("studentNm") String studentNm, @Param("schyDiv") String schyDiv, @Param("orgId") String orgId);

    /**
     * <p>メンター情報を取得する</p>
     *
     * @param mentorId メンターid
     * @param mentorNm メンター名前
     * @param orgId    組織ＩＤ
     * @param startRow 開始位置
     * @return　メンター情報
     */
    List<F00055MentorDto> selectMentorInfo(@Param("mentorId") String mentorId, @Param("mentorNm") String mentorNm, @Param("orgId") String orgId, @Param("startRow") Integer startRow);

    /**
     * <p>メンター情報記録数を取得する</p>
     *
     * @param mentorId メンターid
     * @param mentorNm メンター名前
     * @param orgId    組織ＩＤ
     * @return　メンター情報記録数
     */
    Integer selectMentorInfoCount(@Param("mentorId") String mentorId, @Param("mentorNm") String mentorNm, @Param("orgId") String orgId);

    /**
     * <p>保護者情報を取得する</p>
     *
     * @param guardId  保護者id
     * @param guardNm  保護者名前
     * @param mailad   メールアドレス
     * @param orgId    組織ＩＤ
     * @param startRow 開始位置
     * @return　保護者情報
     */
    List<F00055GuardDto> selectGuardInfo(@Param("guardId") String guardId, @Param("guardNm") String guardNm, @Param("mailad") String mailad, @Param("orgId") String orgId, @Param("startRow") Integer startRow);

    /**
     * <p>保護者情報記録数を取得する</p>
     *
     * @param guardId 保護者id
     * @param guardNm 保護者名前
     * @param mailad  メールアドレス
     * @param orgId   組織ＩＤ
     * @return　保護者情報記録数
     */
    Integer selectGuardInfoCount(@Param("guardId") String guardId, @Param("guardNm") String guardNm, @Param("mailad") String mailad, @Param("orgId") String orgId);
}
