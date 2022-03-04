package jp.learningpark.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstDeviceTokenDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.job.service.BTGKA1012Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p></p >
 *
 * @author NWT : wq <br />
 * <p>
 * 2021/10/9
 * @version 1.0
 */
@Service
public class BTGKA1012ServiceImpl implements BTGKA1012Service {

    /**
     * マスタコード　Dao
     */
    final MstCodDDao mstCodDDao;

    /**
     * デバイスTOKEN管理　Dao
     */
    final MstDeviceTokenDao deviceTokenDao;

    private static Logger logger = LoggerFactory.getLogger(BTGKA1012ServiceImpl.class);

    public BTGKA1012ServiceImpl(MstCodDDao mstCodDDao, MstDeviceTokenDao deviceTokenDao) {
        this.mstCodDDao = mstCodDDao;
        this.deviceTokenDao = deviceTokenDao;
    }

    /**
     * 1.データベースを接続
     * 2.操作データテーブル
     */
    @Override
    public void execute() {
        logger.info("デバイストーケン無効化バッチ，実行開始：【{}】", DateUtils.getSysTimestamp());
        // コードマスタ明細から通知サーバー側DBへ繋がる接続文字列を取得する。
        MstCodDEntity connInfo = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2", "cod_value_3", "cod_value_4", "cod_value_5").eq("cod_key",
                        "APP_DATABASE_CONN").eq("del_flg", 0));
        if (connInfo == null) {
            logger.warn("通知サーバー側DB接続文字列が取得できません。");
            logger.info("デバイストーケン無効化バッチ，実行終了：【{}】", DateUtils.getSysTimestamp());
            return;
        }

        //コードマスタ明細からデバイストーケン無効化バッチ実行期間を取得する。
        MstCodDEntity executeDays = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "DEVICE_INVALID_TIME").eq("del_flg", 0));
        if (executeDays == null) {
            logger.warn("無効化バッチ実行期間を取得できません。");
            logger.info("デバイストーケン無効化バッチ，実行終了：【{}】", DateUtils.getSysTimestamp());
            return;
        }

        //コードマスタ明細からデバイストーケン無効化対象の更新遅延時間を取得する。
        MstCodDEntity delayDays = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "UPDATE_LATER_TIME").eq("del_flg", 0));
        if (delayDays == null) {
            logger.warn("更新遅延時間を取得できません。");
            logger.info("デバイストーケン無効化バッチ，実行終了：【{}】", DateUtils.getSysTimestamp());
            return;
        }

        //コードマスタ明細からデバイストーケン無効化対象の毎回最大提出記録数を取得する。
        MstCodDEntity commitRecords = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "COMMIT_DATA_CNT").eq("del_flg", 0));
        int records = 0;
        if (commitRecords == null || StringUtils.isBlank(commitRecords.getCodValue())) {
            //　データを取得できない場合、
            records = 100;
        } else {
            records = Integer.parseInt(commitRecords.getCodValue());
        }

        //実行期間
        int executableDays = Integer.parseInt(executeDays.getCodValue());
        String nowDateStr = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        Date nowDate = DateUtils.parse(nowDateStr, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        Date executeRange = DateUtils.addMinutes(nowDate, -executableDays);

        //更新遅延時間
        int delayMinutes = Integer.parseInt(delayDays.getCodValue());
        String nowTimeStr = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        Date nowTime = DateUtils.parse(nowTimeStr, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
        Date delayRange = DateUtils.addMinutes(nowTime, -delayMinutes);
        Timestamp delayTime = new Timestamp(delayRange.getTime());
        int loop = 0;

        //マナミルの無効化対象データを取得する。
        List<MstDeviceTokenEntity> deviceTokenList = deviceTokenDao.selectList(
                new QueryWrapper<MstDeviceTokenEntity>().select("usr_id", "device_id").ge("upd_datime", executeRange).eq("del_flg", 1));
        if (deviceTokenList.isEmpty()) {
            logger.warn("無効化対象データがありません。");
            logger.info("デバイストーケン無効化バッチ，実行終了：【{}】", DateUtils.getSysTimestamp());
            return;
        }

        //Connection対象を宣言する
        Connection con = null;
        //ドライバ名
        String driver = "org.postgresql.Driver";
        //URLアクセスするデータベースを指し示す
        String url = "jdbc:postgresql://" + connInfo.getCodValue() + ":" + connInfo.getCodValue2() + "/" + connInfo.getCodValue3() + "?useUnicode=true&characterEncoding" + "=UTF-8";
        //postgres構成時のユーザー名
        String user = connInfo.getCodValue4();
        //postgres構成時のパスワード
        String password = connInfo.getCodValue5();
        Statement statement = null;
        try {
            //ドライバーをロードする
            Class.forName(driver);
            logger.info("データベース駆動ロード成功。");
        } catch (ClassNotFoundException e) {
            //データベース駆動系例外処理
            logger.error("データベース駆動ロード失敗。駆動名：{}", driver);
            throw new RRException("データベース駆動が見つからない。");
        }
        try {
            //1.getConnection()メソッド、Postgresデータベースに接続! !
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                logger.info("データベース接続成功。");
            }
        } catch (SQLException e) {
            logger.error("データベース接続失敗。接続経路、ユーザー名またはパスワードが正しいか確認してください。");
            throw new RRException("データベース接続時に異常発生。");
        }
        int deviceId = 0;
        try {
            con.setAutoCommit(false);
            //2.SQL文を実行するstatement系オブジェクトを作成! !
            statement = con.createStatement();
            //実行するSQL文
            String sql = "";
            for (int i = 0; i < deviceTokenList.size(); i++) {
                MstDeviceTokenEntity device = deviceTokenList.get(i);
                deviceId = device.getDeviceId();
                sql = "update t_device set update_user_id = 'manamiru', update_date = '" +  DateUtils.getSysTimestamp() + "', del_flg = '1' " + "where id = " + device.getDeviceId() + " and update_date <= '" + delayTime + "' and del_flg = '0'";
                statement.addBatch(sql);
                loop++;
                // 百のデータごとに一回実行します。
                if (loop == records || i == deviceTokenList.size() - 1) {
                    statement.executeBatch();
                    con.commit();
                    loop = 0;
                }
            }
        } catch (SQLException e) {
            logger.error("デバイスID：" + deviceId + "の更新が失敗しました。");
            try {
                con.rollback();
            } catch (SQLException e2) {
                logger.error("トランザクションロールバック失敗。");
            }
            throw new RRException("データ更新の失敗。");
        } finally {
            //資源を放出する。
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                logger.error("データベース接続のクローズに失敗しました。");
                throw new RRException("データ更新の失敗。");
            }
        }
        logger.info("デバイストーケン無効化バッチ，実行終了：【{}】", DateUtils.getSysTimestamp());
    }
}
