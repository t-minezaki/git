/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.pop.dao.F00055Dao;
import jp.learningpark.modules.pop.dto.F00055GuardDto;
import jp.learningpark.modules.pop.dto.F00055MentorDto;
import jp.learningpark.modules.pop.dto.F00055StudentDto;
import jp.learningpark.modules.pop.service.F00055Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F00055 ユーザー選択画面（POP） ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/20 : wen: 新規<br />
 * @version 1.0
 */
@Service
public class F00055ServiceImpl implements F00055Service {

    /**
     * F00055 ユーザー選択画面（POP） Dao
     */
    @Autowired
    F00055Dao f00055Dao;

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
    @Override
    public List<F00055StudentDto> getStudentInfo(String stuId, String studentNm, String schyDiv, String orgId, Integer startRow) {
        return f00055Dao.selectStudentInfo(stuId, studentNm, schyDiv, orgId, startRow);
    }

    /**
     * <p>学生情報記録数を取得する</p>
     *
     * @param stuId     学生id
     * @param studentNm 学生名前
     * @param schyDiv   学年
     * @param orgId     組織ＩＤ
     * @return　学生情報記録数
     */
    @Override
    public Integer getStudentInfoCount(String stuId, String studentNm, String schyDiv, String orgId) {
        return f00055Dao.selectStudentInfoCount(stuId, studentNm, schyDiv, orgId);
    }

    /**
     * <p>メンター情報を取得する</p>
     *
     * @param mentorId メンターid
     * @param mentorNm メンター名前
     * @param orgId    組織ＩＤ
     * @param startRow 開始位置
     * @return　メンター情報
     */
    @Override
    public List<F00055MentorDto> getMentorInfo(String mentorId, String mentorNm, String orgId, Integer startRow) {
        return f00055Dao.selectMentorInfo(mentorId, mentorNm, orgId, startRow);
    }

    /**
     * <p>メンター情報記録数を取得する</p>
     *
     * @param mentorId メンターid
     * @param mentorNm メンター名前
     * @param orgId    組織ＩＤ
     * @return　メンター情報記録数
     */
    @Override
    public Integer getMentorInfoCount(String mentorId, String mentorNm, String orgId) {
        return f00055Dao.selectMentorInfoCount(mentorId, mentorNm, orgId);
    }

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
    @Override
    public List<F00055GuardDto> getGuardInfo(String guardId, String guardNm, String mailad, String orgId, Integer startRow) {
        return f00055Dao.selectGuardInfo(guardId, guardNm, mailad, orgId, startRow);
    }

    /**
     * <p>保護者情報記録数を取得する</p>
     *
     * @param guardId 保護者id
     * @param guardNm 保護者名前
     * @param mailad  メールアドレス
     * @param orgId   組織ＩＤ
     * @return　保護者情報記録数
     */
    @Override
    public Integer getGuardInfoCount(String guardId, String guardNm, String mailad, String orgId) {
        return f00055Dao.selectGuardInfoCount(guardId, guardNm, mailad, orgId);
    }
}
