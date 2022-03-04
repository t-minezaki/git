package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F04011Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F04014_マナミルチャンネル参照画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/25 : yang: 新規<br />
 * @version 6.0
 */
@Mapper
public interface F04014Dao extends BaseMapper<F04011Dto> {
    /**
     *  全体の送信組織を抽出
     * @param noticeId お知らせID
     * @return
     */
    List<MstOrgEntity> getOrgList(@Param("noticeId")Integer noticeId);
}
