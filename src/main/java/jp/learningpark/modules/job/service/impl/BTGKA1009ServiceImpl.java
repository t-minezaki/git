/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
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
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.job.dao.BTGKA1009Dao;
import jp.learningpark.modules.job.service.BTGKA1009Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 本棚入会日次バッチ
 *
 * @author NWT : cuikl <br />
 * 変更履歴 <br />
 * 2021/3/22 : 新規<br />
 * @version 1.0
 */
@Service
public class BTGKA1009ServiceImpl implements BTGKA1009Service {

    /**
     * マスタ_明細Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * 本棚入会日次バッチDao
     */
    @Autowired
    BTGKA1009Dao btgka1009Dao;

    /**
     * 本棚連携履歴Dao
     */
    @Autowired
    BookendSendHstDao bookendSendHstDao;

    private static final Logger log = LoggerFactory.getLogger(BTGKA1009ServiceImpl.class);

    /**
     * 出力ファイルのパス
     */
    private static final AtomicReference<MstCodDEntity> OUTPUT_CSV_FILE_PATH = new AtomicReference<>();

    /**
     * 本棚入会日次バッチerviceImpl
     */
    @Autowired
    BTGKA1009ServiceImpl btgka1009Service;

    /**
     * 本棚へ連携処理
     * @return
     */
    @Override
    public R dayRegistbulk(){
        // 本棚APIリクエストURLを取得
        HashMap<String,String> param = new HashMap<>(6);
        MstCodDEntity bookendRequestUrl = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_REQUEST_URL").eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()).last("limit 1"));
        if (bookendRequestUrl == null || StringUtils.isEmpty(bookendRequestUrl.getCodValue()) || StringUtils.isEmpty(bookendRequestUrl.getCodValue2())){
            // データ取得できない
            log.error("本棚APIリクエストURLが空白です。");
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }
        param.put("url",bookendRequestUrl.getCodValue());
        // 本棚APIリクエストパスを取得
        MstCodDEntity bookendApiPathKbn = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_API_PATH_KBN").eq("cod_cd","3").eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()).last("limit 1"));
        if (bookendApiPathKbn == null || StringUtils.isEmpty(bookendApiPathKbn.getCodCd()) || StringUtils.isEmpty(bookendApiPathKbn.getCodValue())){
            // データ取得できない
            log.error("本棚APIリクエストパスが空白です。");
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }
        param.put("path",bookendApiPathKbn.getCodValue());
        // API をリクエストする用X-API-Keyを取得
        MstCodDEntity bookendApiKey = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOKEND_API_KEY").eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()).last("limit 1"));
        if (bookendApiKey == null || StringUtils.isEmpty(bookendApiKey.getCodValue())){
            // データ取得できない
            log.error("API をリクエストする用X-API-Keyが空白です。");
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }
        param.put("key",bookendApiKey.getCodValue());
        // コード明細からグループコードを取得
        MstCodDEntity bookGroupCd = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOK_GROUP_CD").eq("cod_cd",GakkenConstant.GROUP_ID).eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()).last("limit 1"));
        if (bookGroupCd == null || StringUtils.isEmpty(bookGroupCd.getCodCd())){
            // データ取得できない
            log.error("グループコードが空白です。");
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }
        param.put("groupCd",bookGroupCd.getCodCd());
        // コード明細から本棚で実行したAPI種別を取得
        MstCodDEntity bookApiFlg = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "BOOK_API_FLG").eq("cod_cd","1").eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()).last("limit 1"));
        if (bookApiFlg == null || StringUtils.isEmpty(bookApiFlg.getCodCd()) || StringUtils.isEmpty(bookApiFlg.getCodValue())){
            // データ取得できない
            log.error("本棚で実行したAPI種別が空白です。");
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }
        param.put("apiFlg",bookApiFlg.getCodCd());
        // コードマスタ明細からエラーファイル出力パスを取得
        MstCodDEntity outFilePath = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_OUT_FILE_PATH").eq("cod_cd", "1").eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()).last("limit 1"));
        if (outFilePath == null || StringUtils.isEmpty(outFilePath.getCodCd())){
            // データ取得できない
            log.error("エラーファイル出力パスが空白です。");
            return R.error("本棚API利用必要なパラメータが取得できません。");
        }
        param.put("outFilePath",outFilePath.getCodValue());
        // 本棚連携履歴を取得
        List<BookendSendHstEntity> selectBookSendHst = btgka1009Dao.selectBookSendHst();
        List<String> gidpkList = new ArrayList<>();
        for (BookendSendHstEntity bookendSendHstEntity:selectBookSendHst){
            // 入会中情報リストに設定
            gidpkList.add(bookendSendHstEntity.getGidpk());
        }
        if (gidpkList.size() >0){
            // ユーザ基本マスタには入会中のデータを更新
            btgka1009Dao.updateBookSended(gidpkList);
        }
        // 生徒の本棚連携対象を取得
        List<MstUsrEntity> usrList = btgka1009Dao.selectUsrList(gidpkList);
        if (usrList == null || usrList.size() < 1){
            log.info("生徒の本棚連携対象を取得なし");
            return R.ok();
        }
        long start = System.currentTimeMillis();
        log.info("START 入会会員を本棚側へ連携処理:{}", start);
        try {
            //システム時刻
            String today = DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYYMMDD);
            MstCodDEntity mstCodDOutputEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_OUT_FILE_PATH").eq("del_flg", 0).eq("cod_cd", "1").last("limit 1"));
            //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
            if (mstCodDOutputEntity == null) {
                log.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
                return R.error("ファイルパスを取得できない");
            }
            mstCodDOutputEntity.setCodValue(mstCodDOutputEntity.getCodValue() + '/' + today);
            OUTPUT_CSV_FILE_PATH.set(mstCodDOutputEntity);
            String outFileName = GakkenConstant.ERR_PREFIX + GakkenConstant.BTGKA1009_CSV_FILE_NAME + today +GakkenConstant.CSV_FILE_SUFFIX;
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outFileName);
            File outputFile = new File(tempPath);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }
            CsvWriter csvWriter;
            try{
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                bufferedOutputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
                csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
            }catch (Exception e){
                log.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }

            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            String[] header = {"学研IDプライマリーキー", "ロール区分", "グループコード", "結果"};
            csvWriter.writeRecord(header);
            csvWriter.flush();
            long headerLength = outputFile.length();
            if (usrList.size()>1000){
                // 件数 > 1000件
                List<MstUsrEntity> newEntity;
                int count = usrList.size()/1000;
                for (int i=1;i<=count;i++){
                    newEntity = usrList.subList((i-1)*1000,i*1000);
                    doUpdate(newEntity, param, csvWriter);
                }
                int num = usrList.size()%1000;
                if (num > 0){
                    newEntity = usrList.subList(count*1000,count*1000+num);
                    doUpdate(newEntity, param, csvWriter);
                }
            }else {
                // 件数 <= 1000件
                doUpdate(usrList, param, csvWriter);
            }
            csvWriter.close();
            if (outputFile.length() > headerLength) {
                File outFile = new File(OUTPUT_CSV_FILE_PATH.get().getCodValue() + File.separator + outFileName);
                try {
                    if (!outFile.exists()) {
                        outFile.getParentFile().mkdirs();
                        outFile.createNewFile();
                    }
                    Files.copy(outputFile, outFile);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RRException(e.getMessage());
                }
            }
            outputFile.delete();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            long end = System.currentTimeMillis();
            log.info("END   入会会員を本棚側へ連携処理:{}", end);
            log.info("入会会員を本棚側へ連携処理時間: {}ms", end - start);
        }
        return R.ok();
    }

    /**
     * API処理
     * @param list
     * @param param
     */
    public void doUpdate(List<MstUsrEntity> list, HashMap<String,String> param,CsvWriter csvWriter){
        List<BookEndSendDto> sendList = new ArrayList<>();
        for (MstUsrEntity mstUsrEntity:list){
            BookEndSendDto sendDto = new BookEndSendDto();
            sendDto.setServiced(GakkenConstant.SERVICE_CD);
            sendDto.setGidpk(mstUsrEntity.getGidpk());
            sendDto.setGroupid(GakkenConstant.GROUP_ID);
            sendList.add(sendDto);
        }
        // 入会会員状態を本棚側への連携処理
        String responseString = BookEndApi.active(sendList, param.get("key"), param.get("url"), param.get("path"));
        if (StringUtils.isEmpty(responseString)){
            // ポータルbookendバルク登録API｛/registbulk｝コールNG
            log.error("本棚APIリクエスト中にエラー発生しました。");
        }
        // ポータルbookendバルク登録API｛/registbulk｝コール成功
        List<BookEndReceiveDto> resultList = JSONObject.parseArray(responseString, BookEndReceiveDto.class);
        HashMap<String,String> roleDivMap = new HashMap<>(1000);
        for (MstUsrEntity entity:list){
            roleDivMap.put(entity.getGidpk(), entity.getRoleDiv());
        }

        for (BookEndReceiveDto dto : resultList){
            if ("true".equals(dto.getResult())&&!StringUtils.isEmpty(dto.getGidpk())){
                // ユーザ基本マスタを更新
                try{
                    btgka1009Service.updateData(dto, roleDivMap, param);
                }catch (Exception e){
                    log.error(e.getMessage());
                    log.error("ユーザ基本マスタ更新は失敗している");
                }
            }else {
                try{
                    String[] strings = {dto.getGidpk(), roleDivMap.get(dto.getGidpk()), dto.getGroupid(), dto.getResult()};
                    csvWriter.writeRecord(strings);
                    csvWriter.flush();
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateData (BookEndReceiveDto dto, HashMap<String,String> roleDivMap, HashMap<String,String> param){
        btgka1009Dao.bookSend(GakkenConstant.SYS_BATCH, dto.getGidpk());
        BookendSendHstEntity bookendSendHstEntity= new BookendSendHstEntity();
        // 学研IDプライマリキー
        bookendSendHstEntity.setGidpk(dto.getGidpk());
        // ロール区分
        bookendSendHstEntity.setRoleDiv(roleDivMap.get(dto.getGidpk()));
        // グループコード
        bookendSendHstEntity.setBookGroupCd(param.get("groupCd"));
        // 登録退会フラグ
        bookendSendHstEntity.setBookApiFlg(param.get("apiFlg"));
        // 連携日
        bookendSendHstEntity.setSendDatime(DateUtils.getSysTimestamp());
        // 作成日時
        bookendSendHstEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        bookendSendHstEntity.setCretUsrId(GakkenConstant.SYS_BATCH);
        // 更新日時
        bookendSendHstEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        bookendSendHstEntity.setUpdUsrId(GakkenConstant.SYS_BATCH);
        // 削除フラグ
        bookendSendHstEntity.setDelFlg(GakkenConstant.DEL_FLG.NO.getValue());
        // 本棚連携履歴登録
        bookendSendHstDao.insert(bookendSendHstEntity);
    }

    /**
     * 本棚へ連携処理
     * @return
     */
    @Override
    public R test(){
        // 本棚APIリクエストURLを取得
        HashMap<String,String> param = new HashMap<>(6);
        param.put("url","https://begroup.gakken.jp");
        param.put("path","/registbulk");
        param.put("key","7NA2mxYA6Bw9VrC9CLyrp6DX1pyHW1fPyNed8yZrwD24Gn1K9bLSm112gB9Uye4Z");
        param.put("groupCd","ayIPNF3KHB1OUKqv");
        param.put("apiFlg","1");
        param.put("outFilePath","/root/Test Data");
        // 本棚連携履歴を取得
        List<BookendSendHstEntity> selectBookSendHst = btgka1009Dao.selectBookSendHst();
        List<String> gidpkList = new ArrayList<>();
        for (BookendSendHstEntity bookendSendHstEntity:selectBookSendHst){
            // 入会中情報リストに設定
            gidpkList.add(bookendSendHstEntity.getGidpk());
        }
        if (gidpkList.size() >0){
            // ユーザ基本マスタには入会中のデータを更新
            btgka1009Dao.updateBookSended(gidpkList);
        }
        // 生徒の本棚連携対象を取得
        List<MstUsrEntity> usrList = new ArrayList<>();
        MstUsrEntity studentEntity = new MstUsrEntity();
        studentEntity.setRoleDiv("4");
        studentEntity.setGidpk("530427d0c5e543f8906f59245a9a0ed8");
        studentEntity.setDelFlg(0);
        usrList.add(studentEntity);
        MstUsrEntity guardEntity = new MstUsrEntity();
        guardEntity.setRoleDiv("3");
        guardEntity.setGidpk("85d54261091946559894b6e0003bfda4");
        guardEntity.setDelFlg(0);
        usrList.add(guardEntity);
        long start = System.currentTimeMillis();
        log.info("START 入会会員を本棚側へ連携処理:{}", start);
        boolean result = true;
        try {
            if (usrList.size()>1000){
                // 件数 > 1000件
                List<MstUsrEntity> newEntity;
                int count = usrList.size()/1000;
                for (int i=1;i<=count;i++){
                    newEntity = usrList.subList((i-1)*1000,i*1000);
                    //result = doUpdate(newEntity, param);
                    if (result==false){
                        return R.error();
                    }
                }
                int num = usrList.size()%1000;
                if (num > 0){
                    newEntity = usrList.subList(count*1000,count*1000+num);
                    //result = doUpdate(newEntity, param);
                }
            }else {
                // 件数 <= 1000件
                //result = doUpdate(usrList, param);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            long end = System.currentTimeMillis();
            log.info("END   入会会員を本棚側へ連携処理:{}", end);
            log.info("入会会員を本棚側へ連携処理時間: {}ms", end - start);
            if (!result){
                return R.error("呼び出しに失敗しました。");
            }
        }
        return R.ok();
    }
}
