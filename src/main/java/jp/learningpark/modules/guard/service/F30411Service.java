package jp.learningpark.modules.guard.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.guard.dto.F30411Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * F30411Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/18 ： NWT)hxl ： 新規作成
 * @date 2019/11/18 9:57
 */
public interface F30411Service {
    /**
     * 遅れる理由をすべて入手する
     * @return
     */
    List<F30411Dto> getReasons();

    /**
     * 休暇タイプを取得する
     * @return
     */
    List<F30411Dto> getContents();

    /**
     * ボリュームusr_idを取得する
     */
    List<String> getUsrIds(@Param("stuId")String stuId, @Param("orgId")String orgId);

    R submit(String type, Integer lateTime, String lateDay, String reason, String remark);
    R sendMessage(String stuId, MstNoticeEntity mstNoticeEntity);
}