package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21003Dto;

import java.util.List;

/**
 * <p>F21003_遅刻・欠席一覧画面（対応ステータス別） Service</p >
 *
 * @author NWT : LiYuHuan <br />
 * @version 5.0
 */
public interface F21003Service {
    List<F21003Dto> select(String userId, String orgId, String corrspdSts, String tgtYmd,String roleDiv,Integer limit, Integer offset);
    Integer count(String userId, String orgId, String corrspdSts, String tgtYmd,String roleDiv);
}
