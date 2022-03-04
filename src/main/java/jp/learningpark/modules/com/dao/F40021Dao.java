package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p></p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2021/6/7 : yang: 新規<br />
 * MANAMIRU1-686
 * @version 1.0
 */
@Mapper
public interface F40021Dao {
    List<MstUsrEntity> getUsrListByTchCd(@Param("tchCd") String tchCd);
}
