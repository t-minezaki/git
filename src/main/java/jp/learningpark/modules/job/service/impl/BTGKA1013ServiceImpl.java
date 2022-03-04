package jp.learningpark.modules.job.service.impl;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.job.dao.BTGKA1013Dao;
import jp.learningpark.modules.job.entity.BTGKA1013Dto;
import jp.learningpark.modules.job.service.BTGKA1013Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>デバイスTOKEN管理には有効期間超過デバイストーケンを無効化するため、定刻バッチを行う。</p>
 *
 * @author NWT)lgx
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/10/08 ： NWT)lgx ： 新規作成
 */
@Service
public class BTGKA1013ServiceImpl implements BTGKA1013Service {

    /**
     * btgka1013Dao
     */
    @Autowired
    private BTGKA1013Dao btgka1013Dao;

    @Autowired
    BTGKA1013ServiceImpl btgka1013Service;

    private static final Logger log = LoggerFactory.getLogger(BTGKA1013ServiceImpl.class);

    @Override
    public R updateDelFlgByDeviceId() {
        log.info("有効期限超過デバイストーケン無効化バッチ実行開始");
        //1 デバイスTOKEN管理から有効期間超過デバイスを取得する
        List<BTGKA1013Dto> usrIds = btgka1013Dao.getUsrIds();
        if (CollectionUtils.isNotEmpty(usrIds)){
            //2 取得したデータをもとに、デバイスTOKEN管理を更新する
            for (BTGKA1013Dto deviceId: usrIds) {
                try {
                    btgka1013Service.updateDelFlg(deviceId);
                }catch (Exception e){
                    return R.error("有効期間を超過するデバイストーケンの無効化処理が失敗しました。");
                }
            }
        }else{
            //取得できない場合
            //エラーメッセージ「有効期間を超過するデバイストーケンの無効化処理が失敗しました。」をログへ出力する。
            log.warn("有効期間超過デバイストーケンを取得できません。");
            log.info("有効期限超過デバイストーケン無効化バッチ正常終了");
            return R.ok(MessageUtils.getMessage("MSGCOMN0035", "有効期間超過デバイストーケン"));
        }
        log.info("有効期限超過デバイストーケン無効化バッチ正常終了");
        return R.ok();
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void updateDelFlg(BTGKA1013Dto deviceId) {
        //デバイスID
        Integer usrId = deviceId.getDeviceId();
        //更新日時
        Timestamp updDatime = deviceId.getUpdDatime();
        try {
            btgka1013Dao.updateDelFlgByDeviceId(usrId,updDatime);
        }catch (Exception ex){
            //更新処理でエラーが発生する場合
            log.error("有効期間を超過するデバイストーケンの無効化処理が失敗しました。");
            log.info("有効期限超過デバイストーケン無効化バッチ異常終了");
        }
    }
}
