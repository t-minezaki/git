package jp.learningpark.modules.pop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.pop.dao.F21072Dao;
import jp.learningpark.modules.pop.dto.F21072Dto;
import jp.learningpark.modules.pop.service.F21072Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/03 ： NWT)hxl ： 新規作成
 * @date 2020/06/03 14:58
 */
@Service
public class F21072ServiceImpl implements F21072Service {
    /**
     * mstMessageDao
     */
    @Autowired
    MstMessageDao mstMessageDao;

    @Autowired
    F21072Dao f21072Dao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期化
     *
     * @param messageId セッションデータ．ＩＤ
     * @param orgId     セッションデータ．組織ＩＤ
     * @param pageSize  1ページのMAX件数30件
     * @param readFlg   既読未読フラグ
     * @param limit
     * @return
     */
    @Override
    public R init(Integer messageId, String orgId, Integer pageSize, String readFlg, Integer limit) {
        //引渡データ．IDを元に、下記条件、メッセージマスタを元に取得し、画面で表示される。
        MstMessageEntity mstMessageEntity = mstMessageDao.selectOne(new QueryWrapper<MstMessageEntity>().eq("id", messageId).eq("del_flg", 0));
        //message.titleをデコードする
        try {
            mstMessageEntity.setMessageTitle(URLDecoder.decode(mstMessageEntity.getMessageTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //生徒情報を取得する
        List<F21072Dto> f21072DtoList = f21072Dao.selectManagerAndMentorById(messageId, orgId, (pageSize - 1) * limit, readFlg, limit);
        //カウント数を取得する
        Integer count = f21072Dao.selectManagerAndMentorCount(messageId, orgId, readFlg);
        return R.ok().put("mstMessageEntity", mstMessageEntity).put("page", new PageUtils(f21072DtoList, count, limit, pageSize)).put("orgId", orgId);
    }
}
