package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.manager.dao.F21069Dao;
import jp.learningpark.modules.manager.dto.F21069Dto;
import jp.learningpark.modules.manager.dto.F21069DtoIn;
import jp.learningpark.modules.manager.service.F21069Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * 2020/05/25 ： NWT)hxl ： 新規作成
 * @date 2020/05/25 11:14
 */
@Service
public class F21069ServiceImpl implements F21069Service {
    /**
     * F21063 ッセージ新規画面 Dao
     */
    @Autowired
    F21069Dao f21069Dao;

    /**
     * メッセージマスター Dao
     */
    @Autowired
    private MstMessageDao mstMessageDao;

    /**
     * talkReadingStsService　dao
     */
    @Autowired
    private TalkReadingStsDao talkReadingStsDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * get admin list by admin id list
     *
     * @param adminIdList admin id list
     * @return
     */
    @Override
    public List<F21069DtoIn> selectStuByIdList(List<String> adminIdList,Integer page,Integer limit) {
        return f21069Dao.selectAdminByIdList(adminIdList,page,limit);
    }
    /**
     * get admin list by admin id list
     *
     * @param adminIdList admin id list
     * @return
     */
    @Override
    public List<F21069DtoIn> selectStuByIdListTotal(List<String> adminIdList) {
        return f21069Dao.selectStuByIdListTotal(adminIdList);
    }
    /**
     * 登録処理
     * <p>
     * 2019/06/24 hujiaxing: mod
     *
     * @param dto
     * @param MessageCont 　メッセージの内容
     * @param orgId       組織ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R doInsert(F21069Dto dto, String MessageCont, String orgId) {
        try {
            dto.setMessageTitle(URLDecoder.decode(dto.getMessageTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();

        //画面を元に、メッセージマスタへ登録する。
        MstMessageEntity mstMessageEntity = new MstMessageEntity();
        //組織ID
        mstMessageEntity.setOrgId(orgId);
        //面談記録ID
        mstMessageEntity.setTalkId(null);
        //メッセージタイトル
        mstMessageEntity.setMessageTitle(dto.getMessageTitle());
        //メッセージ内容
        mstMessageEntity.setMessageCont(MessageCont);
        //メッセージタップ区分
        mstMessageEntity.setMessageTypeDiv("2");
        //メッセージレベル区分
        mstMessageEntity.setMessageLevelDiv(dto.getMessageLevelDiv());
        //掲載予定開始日時
        mstMessageEntity.setPubPlanStartDt(dto.getPubPlanStartDt());
        //掲載予定終了日時
        mstMessageEntity.setPubPlanEndDt(dto.getPubPlanEndDt());
        //全体配信フラグ
        mstMessageEntity.setAllDeliverFlg(dto.getAllDeliverFlg());
        //添付ファイルパス
        mstMessageEntity.setAttachFilePath(dto.getAttachFilePath());
        //タイトル画像パス
        mstMessageEntity.setTitleImgPath(dto.getTitleImgPath());
        //作成日時
        mstMessageEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstMessageEntity.setCretUsrId(userId);
        //更新日時
        mstMessageEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstMessageEntity.setUpdUsrId(userId);
        //削除フラグ
        mstMessageEntity.setDelFlg(0);
        try {
            mstMessageDao.insert(mstMessageEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error();
        }

        for (int i = 0; i < dto.getAdminList().size(); i++) {
            TalkReadingStsEntity talkReadingStsEntity = new TalkReadingStsEntity();
            //組織ID
            talkReadingStsEntity.setOrgId(dto.getAdminList().get(i).getOrgId());
            //メッセージID
            talkReadingStsEntity.setMessageId(mstMessageEntity.getId());
            //配信先ID
            talkReadingStsEntity.setDeliverId(dto.getAdminList().get(i).getUsrId());
            //閲覧状況区分
            talkReadingStsEntity.setReadingStsDiv("0");
            //質問事項表示フラグ
            talkReadingStsEntity.setAskDispFlg(null);
            //面談事項表示フラグ
            talkReadingStsEntity.setTalkDispFlg(null);
            //作成日時
            talkReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザー
            talkReadingStsEntity.setCretUsrId(userId);
            //更新日時
            talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザー
            talkReadingStsEntity.setUpdUsrId(userId);
            //削除
            talkReadingStsEntity.setDelFlg(0);
            try {
                talkReadingStsDao.insert(talkReadingStsEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return R.error();
            }
        }
        return R.ok();
    }
}
