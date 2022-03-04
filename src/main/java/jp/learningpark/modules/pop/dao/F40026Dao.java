package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F40026Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>通知プッシュ失敗一覧画面（共通）</p >
 *
 * @author NWT : yyb <br />
 * 変更履歴 <br />
 * 2020/07/17 : yyb: 新規<br />
 * @version 7.1
 */
@Mapper
public interface F40026Dao {
  F40026Dto getInfo(@Param("msgId") Integer msgId, @Param("messageTypeDiv") String messageTypeDiv );
  List<F40026Dto> failedUserList(@Param("msgTypeDiv") String msgTypeDiv,@Param("msgId")Integer msgId,@Param("orgId")String orgId,@Param("limit")Integer limit,@Param("offset")Integer offset);
  void errDataUpdate(@Param("id") Integer id );

  /**
   *
   * @param errorIdList
   * @return
   * modify at 2021/09/17 for V9.02 UT-0029 by NWT yang
   */
  List<F40026Dto> selectDeliverInfo(@Param("errorIdList")List<Integer> errorIdList);
}
