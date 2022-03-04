package jp.learningpark.modules.job.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import com.google.common.io.Files;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.BookEndApi;
import jp.learningpark.framework.gakkenID.dto.BookEndReceiveDto;
import jp.learningpark.framework.gakkenID.dto.BookEndSendDto;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.BookendSendHstDao;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.BookendSendHstEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.job.service.BTGKA1010Service;
import jp.learningpark.modules.job.task.dao.BTGKA1010Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/22 ： NWT)hxl ： 新規作成
 * @date 2021/3/22 16:26
 */
@Service
public class BTGKA1010ServiceImpl implements BTGKA1010Service {

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * btgka1010Dao
     */
    @Autowired
    BTGKA1010Dao btgka1010Dao;

    /**
     * bookendSendHstDao
     */
    @Autowired
    BookendSendHstDao bookendSendHstDao;

    /**
     * btgka1010Service
     */
    @Autowired
    BTGKA1010ServiceImpl btgka1010Service;

    private static final Logger logger = LoggerFactory.getLogger(BTGKA1010ServiceImpl.class);

    CsvWriter csvWriter = null;
    //初回実施フラグ
    MstCodDEntity bookendBatchRunFlg = null;
    //本棚APIリクエストURL
    MstCodDEntity bookendRequestUrl = null;
    //本棚APIリクエストパス
    MstCodDEntity bookendApiPathKbn = null;
    //API をリクエストする用X-API-Key
    MstCodDEntity bookendApiKey = null;
    //グループコード
    MstCodDEntity bookGroupCd = null;
    //本棚で実行したAPI種別
//    MstCodDEntity bookApiFlg = null;
    //エラーファイル出力パス
    MstCodDEntity mstCodDOutputEntity = null;

    @Override
    public R monthExit() {
        long start = System.currentTimeMillis();
        logger.info("START 本棚退会月次バッチ:{}", start);
        boolean dataMissFlag = false;
        //1.コードマスタ明細から本棚APIリクエスト用の各パラメータを取得する。
        do {
            //1.1　月次バッチ初回実施フラグを取得する。
            bookendBatchRunFlg = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_BATCH_RUN_FLG").eq("del_flg", 0));
            if (bookendBatchRunFlg == null){
                dataMissFlag = true;
                break;
            }
            //1.2　本棚APIリクエストURLを取得する。
            bookendRequestUrl = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_REQUEST_URL").eq("del_flg", 0));
            if (bookendRequestUrl == null){
                dataMissFlag = true;
                break;
            }
            //1.3　本棚APIリクエストパスを取得する。
            bookendApiPathKbn = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_API_PATH_KBN").eq("cod_cd", "4").eq("del_flg", 0));
            if (bookendApiPathKbn == null){
                dataMissFlag = true;
                break;
            }
            //1.4　API をリクエストする用X-API-Keyを取得する。
            bookendApiKey = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_API_KEY").eq("del_flg", 0));
            if (bookendApiKey == null){
                dataMissFlag = true;
                break;
            }
            //1.5　コードマスタ明細からグループコードを取得する。
            bookGroupCd = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOK_GROUP_CD").eq("cod_cd",GakkenConstant.GROUP_ID).eq("del_flg", 0));
            if (bookGroupCd == null){
                dataMissFlag = true;
                break;
            }
            //1.6　コードマスタ明細から本棚で実行したAPI種別を取得する。
//            bookApiFlg = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOK_API_FLG").eq("del_flg", 0));
//            if (bookApiFlg == null){
//                dataMissFlag = true;
//                break;
//            }
            //1.7　コードマスタ明細からエラーファイル出力パスを取得する。
            mstCodDOutputEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_OUT_FILE_PATH").eq("del_flg", 0).eq("cod_cd", "1").last("limit 1"));
            if (mstCodDOutputEntity == null){
                dataMissFlag = true;
                break;
            }
        }while (false);
        if (dataMissFlag){
            //1.5　上記1.1～1.4の処理中、いずれか処理でデータ取得できない場合、
            //メッセージ「本棚API利用必要なパラメータが取得できません。」をログファイルへ出力する。
            logger.error("本棚API利用必要なパラメータが取得できません。");
            //処理を中断する。
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }

        //2.退会前処理
        //2.1　直近退会データを取得するため、下記条件で本棚連携履歴を取得する。
        //2.2　取得した退会中情報リストをもとに、ユーザ基本マスタには退会中のデータを更新する。
        List<String> exitedGidpkList = btgka1010Dao.selectExitedGidpkList();

        Timestamp sysTimestamp = DateUtils.getSysTimestamp();

        //エラーCSVファイル物理名
        String errFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.BTGKA1010_PREFIX + DateUtils.format(sysTimestamp, GakkenConstant.DATE_FORMAT_YYYYMMDD) + GakkenConstant.CSV_FILE_SUFFIX;
        File outputFile = null;
        long headerLength = 0;

        try {
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), errFilePath);
            outputFile = new File(tempPath);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }
            // CSV書き込みオブジェクトを作成する
            try {
                //2020/11/18 9.0 huangxinliang modify start
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                bufferedOutputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
                csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
                //2020/11/18 9.0 huangxinliang modify end
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }

            csvWriter.setTextQualifier('"');

            csvWriter.setForceQualifier(true);

            String[] csvContent = {"GIDPK", "ROLE"};
            // リーディングヘッド
            csvWriter.writeRecord(csvContent);

            csvWriter.flush();

            headerLength = outputFile.length();

            //バッチ実施日時の月－1
            Date preMonth = DateUtils.addMonths(sysTimestamp, -1);
            //ブランドコード
            String brandCd = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ORG_ID_ADD").eq("cod_cd", "1")).getCodValue();
            //会員退会状態を本棚側への連携処理
            csvContent[1] = GakkenConstant.STUDENT_ROLE_DIV;
            doExit(sysTimestamp, csvContent, brandCd, GakkenConstant.STUDENT_ROLE_DIV, preMonth, exitedGidpkList, bookendBatchRunFlg.getCodValue());
            //保護者退会状態を本棚側への連携処理
            csvContent[1] = GakkenConstant.GUARD_ROLE_DIV;
            doExit(sysTimestamp, csvContent, brandCd, GakkenConstant.GUARD_ROLE_DIV, preMonth, exitedGidpkList, bookendBatchRunFlg.getCodValue());
        }catch (IOException e){
            logger.error(e.getMessage());
        }finally {
            if (csvWriter != null){
                csvWriter.close();
                if (outputFile.length() > headerLength) {
                    File outFile = new File(mstCodDOutputEntity.getCodValue() + File.separator + DateUtils.format(sysTimestamp, GakkenConstant.DATE_FORMAT_YYYYMMDD) + File.separator + errFilePath);
                    try {
                        if (!outFile.exists()) {
                            outFile.getParentFile().mkdirs();
                            outFile.createNewFile();
                        }
                        Files.copy(outputFile, outFile);
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                        throw new RRException(e.getMessage());
                    }
                }
                outputFile.delete();
            }
        }

        btgka1010Dao.updateMstCodD(sysTimestamp);

        long end = System.currentTimeMillis();
        logger.info("END   本棚退会月次バッチ:{}", end);
        logger.info("本棚退会月次バッチ処理時間: {}ms", end - start);
        return R.ok();
    }

    /**
     * 会員/保護者退会状態を本棚側への連携処理
     * @param sysTimestamp
     * @param csvContent
     * @param brandCd
     * @param roleDiv
     * @param preMonth
     * @throws IOException
     */
    private void doExit(Timestamp sysTimestamp, String[] csvContent, String brandCd, String roleDiv, Date preMonth, List<String> exitedGidpkList, String bookendBatchRunFlg) throws IOException {
        List<String> gidpkAndDelFlagList = btgka1010Dao.selectGidpkAndDelFlgList(brandCd, GakkenConstant.GID_FLG.YES.getValue(), exitedGidpkList,
                bookendBatchRunFlg, DateUtils.format(preMonth, GakkenConstant.DATE_FORMAT_YYYY_MM_BARS), roleDiv);
        //削除されたのGidpkコレクション
        Set<String> delGidpkSet = new HashSet<>();
        //削除されていないのGidpkコレクション
        Set<String> notDelGidpkSet = new HashSet<>();
        for (String s : gidpkAndDelFlagList) {
            String[] gidpkAndDelFlg = s.split(",");
            if (StringUtils.isEmpty(gidpkAndDelFlg[0])){
                continue;
            }
            if (StringUtils.equals(GakkenConstant.DEL_FLG.YES.getValue() + "", gidpkAndDelFlg[1])){
                delGidpkSet.add(gidpkAndDelFlg[0]);
            }else {
                notDelGidpkSet.add(gidpkAndDelFlg[0]);
            }
        }
        //同一GIDPKのすべての削除フラグ＝「1：無効」のGIDPKリスト
        delGidpkSet.removeAll(notDelGidpkSet);
        List<List<BookEndSendDto>> sendListOfDtoList = new ArrayList<>();
        List<String> delGidpkList = new ArrayList<>(delGidpkSet);
        for (int i = 0; i < delGidpkList.size(); i++) {
            if (i % 1000 == 0){
                sendListOfDtoList.add(new ArrayList<>());
            }
            int index = (int) Math.ceil((i + 1) / 1000.0) - 1;
            BookEndSendDto bookEndSendDto = new BookEndSendDto();
            //サービスコード
            bookEndSendDto.setServiced(GakkenConstant.SERVICE_CD);
            //学研IDプライマリーキー
            bookEndSendDto.setGidpk(delGidpkList.get(i));
            //グループID
            bookEndSendDto.setGroupid(GakkenConstant.GROUP_ID);
            //退会日
            bookEndSendDto.setWithdrawaldate(sysTimestamp);
            sendListOfDtoList.get(index).add(bookEndSendDto);
        }

        //取得したGIDPKリストを基に、本棚APIコールして、退会会員/保護者を本棚側へ連携する。　　※複数件あり
        for (List<BookEndSendDto> bookEndSendDtos : sendListOfDtoList) {
            int retryTimes = 1;
            String result = null;
            //2.3.3　ポータルbookendバルク退会API｛/withdrawalbulk｝コールNGの場合、
            while (result == null && retryTimes <= 3){
                logger.info(bookendApiPathKbn.getCodValue() + " called at " + retryTimes + " times");
                result = BookEndApi.active(bookEndSendDtos, bookendApiKey.getCodValue(), bookendRequestUrl.getCodValue(), bookendApiPathKbn.getCodValue());
                retryTimes ++;
            }
            if (result == null){
                for (BookEndSendDto bookEndSendDto : bookEndSendDtos) {
                    csvContent[0] = bookEndSendDto.getGidpk();
                    csvWriter.writeRecord(csvContent);
                    csvWriter.flush();
                }
            }else {
                List<BookEndReceiveDto> list = JSONObject.parseArray(result, BookEndReceiveDto.class);
                for (BookEndReceiveDto bookEndReceiveDto : list) {
                    if (StringUtils.equals(bookEndReceiveDto.getResult(), "true")){
                        btgka1010Service.exitSuccess(bookEndReceiveDto.getGidpk(), sysTimestamp, roleDiv);
                    }else {
                        csvContent[0] = bookEndReceiveDto.getGidpk();
                        csvWriter.writeRecord(csvContent);
                        csvWriter.flush();
                    }
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    void exitSuccess(String gidpk, Date updTime, String roleDiv){
        btgka1010Dao.updateStudent(gidpk, updTime);
        BookendSendHstEntity bookendSendHstEntity = new BookendSendHstEntity();
        //学研IDプライマリキー
        bookendSendHstEntity.setGidpk(gidpk);
        //ロール区分
        bookendSendHstEntity.setRoleDiv(roleDiv);
        //グループコード
        bookendSendHstEntity.setBookGroupCd(bookGroupCd.getCodCd());
        //登録退会フラグ
        bookendSendHstEntity.setBookApiFlg("2");
        //連携日
        bookendSendHstEntity.setSendDatime(DateUtils.toTimestamp(updTime));
        //作成日時
        bookendSendHstEntity.setCretDatime(DateUtils.toTimestamp(updTime));
        //作成ユーザＩＤ
        bookendSendHstEntity.setCretUsrId("SYSBatch");
        //更新日時
        bookendSendHstEntity.setUpdDatime(DateUtils.toTimestamp(updTime));
        //更新ユーザＩＤ
        bookendSendHstEntity.setUpdUsrId("SYSBatch");
        //削除フラグ
        bookendSendHstEntity.setDelFlg(0);
        bookendSendHstDao.insert(bookendSendHstEntity);
    }
}
