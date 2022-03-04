package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.manager.dto.F21069Dto;
import jp.learningpark.modules.manager.service.F21070Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
 * @date 2020/05/25 18:27
 */
@Service
public class F21070ServiceImpl implements F21070Service {
    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;
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

    /**
     * commonDao
     */
    @Autowired
    private CommonDao commonDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     *
     * @param messageId メッセージID
     * @return
     */
    @Override
    public R init(Integer messageId) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        //メッセージを初期表示するため、お知らせマスタから最新の知らせ情報を取得し、画面で表示される。
        MstMessageEntity mstMessageEntity = mstMessageDao.selectById(messageId);
        //配信先リストの取得Id
        List<TalkReadingStsEntity> talkReadingStsEntityList = talkReadingStsDao.selectList(new QueryWrapper<TalkReadingStsEntity>()
                .select("deliver_id").eq("message_id", messageId)
                .eq("del_flg", 0));
        //先生IDリスト
        List<String> adminIdList = new ArrayList<>();
        //get mentor or admin id list
        for (TalkReadingStsEntity talkReadingStsEntity : talkReadingStsEntityList) {
            adminIdList.add(talkReadingStsEntity.getDeliverId());
        }
        F21069Dto f21069Dto = new F21069Dto();
        f21069Dto.setMessageCont(mstMessageEntity.getMessageCont());
        f21069Dto.setMessageTitle(mstMessageEntity.getMessageTitle());
        f21069Dto.setMessageLevelDiv(mstMessageEntity.getMessageLevelDiv());
        f21069Dto.setAttachFilePath(mstMessageEntity.getAttachFilePath());
        f21069Dto.setUpdDatime(mstMessageEntity.getUpdDatime());
        f21069Dto.setAllDeliverFlg(mstMessageEntity.getAllDeliverFlg());
        //掲載予定開始日時
        f21069Dto.setPubPlanStartDtStr(DateUtils.format(mstMessageEntity.getPubPlanStartDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        //掲載予定終了日時
        f21069Dto.setPubPlanEndDtStr(DateUtils.format(mstMessageEntity.getPubPlanEndDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));
        return R.ok().put("message", f21069Dto).put("adminIdList", adminIdList).put("roleDiv",roleDiv).put("org", org);
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
        MstMessageEntity dbMstMessageEntity = mstMessageDao.selectById(dto.getId());
        String dbUpdateTime = DateUtils.format(dbMstMessageEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        String pageUpdateTime = DateUtils.format(dto.getUpdDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        if (pageUpdateTime.compareTo(dbUpdateTime) != 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        try {
            dto.setMessageTitle(URLDecoder.decode(dto.getMessageTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();

        //修正した配信先ユーザー情報を反映するため、
        //メッセージタイトル
        dbMstMessageEntity.setMessageTitle(dto.getMessageTitle());
        //メッセージ内容
        dbMstMessageEntity.setMessageCont(MessageCont);
        //メッセージレベル区分
        dbMstMessageEntity.setMessageLevelDiv(dto.getMessageLevelDiv());
        //掲載予定開始日時
        dbMstMessageEntity.setPubPlanStartDt(dto.getPubPlanStartDt());
        //掲載予定終了日時
        dbMstMessageEntity.setPubPlanEndDt(dto.getPubPlanEndDt());
        //全体配信フラグ
        dbMstMessageEntity.setAllDeliverFlg(dto.getAllDeliverFlg());
        //添付ファイルパス
        dbMstMessageEntity.setAttachFilePath(dto.getAttachFilePath());
        //タイトル画像パス
        dbMstMessageEntity.setTitleImgPath(dto.getTitleImgPath());
        //更新日時
        dbMstMessageEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        dbMstMessageEntity.setUpdUsrId(userId);
        try {
            mstMessageDao.updateById(dbMstMessageEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error();
        }

        talkReadingStsDao.delete(new QueryWrapper<TalkReadingStsEntity>().eq("message_id", dto.getId()));
        for (int i = 0; i < dto.getAdminList().size(); i++) {
            TalkReadingStsEntity talkReadingStsEntity = new TalkReadingStsEntity();
            //組織ID
            talkReadingStsEntity.setOrgId(dto.getAdminList().get(i).getOrgId());
            //メッセージID
            talkReadingStsEntity.setMessageId(dto.getId());
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
