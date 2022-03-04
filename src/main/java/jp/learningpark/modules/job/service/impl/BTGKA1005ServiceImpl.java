/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.io.Files;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstCourseDivDao;
import jp.learningpark.modules.common.dao.MstInCourseDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstCourseDivEntity;
import jp.learningpark.modules.common.entity.MstInCourseEntity;
import jp.learningpark.modules.job.service.BTGKA1005Service;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <p>BTGKA1005_その他の連携ファイル導入</p>
 * <p></p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * 2020/11/23 ： NWT)hxl ： 変更
 * @version 1.0
 */
@Service("BTGKA1005Service")
public class BTGKA1005ServiceImpl implements BTGKA1005Service {

    @Autowired
    MstCodDDao mstCodDDao;

    @Autowired
    MstInCourseDao mstInCourseDao;

    @Autowired
    MstCourseDivDao mstCourseDivDao;

    SftpClient sftpClient1 = null;
    SftpClient sftpClient2 = null;

    MstCodDEntity webInEntity = null;
    MstCodDEntity webOutEntity = null;
    Map<String, File> fileMap = null;

    private static final Logger log = LoggerFactory.getLogger(BTGKA1005ServiceImpl.class);
    //書類の日付
    private String yyyymmdd = null;

    @Override
    public void prepare() {
        //バッチ前処理
        //書類の日付
        yyyymmdd = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD);
        //導入ファイルを読込むために、下記条件でコードマスタ_明細から入力ファイルのパスを取得する。
        webInEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_IN_FILE_PATH").eq("del_flg", 0).eq("cod_cd","1"));
        //チェックエラーデータを出力するために、下記条件でコードマスタ_明細からエラー出力ファイルのパスを取得する。
        webOutEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_OUT_FILE_PATH").eq("del_flg", 0).eq("cod_cd","1"));
        if (webInEntity == null || webOutEntity == null){
            //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
            log.error( MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
            throw new RRException(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
        }
        if (!StringUtils.isEmpty(webInEntity.getCodValue())) {
            try {
                sftpClient1 = new SftpClient(webInEntity.getCodValue2().trim(), webInEntity.getCodValue4().trim()
                        , webInEntity.getCodValue5().trim(), Integer.parseInt(webInEntity.getCodValue3().trim()));
                sftpClient2 = new SftpClient(webOutEntity.getCodValue2().trim(), webOutEntity.getCodValue4().trim()
                        , webOutEntity.getCodValue5().trim(), Integer.parseInt(webOutEntity.getCodValue3().trim()));
            } catch (Exception e) {
                log.error("ファイルサーバー接続エラー");
                throw new RRException("ファイルサーバー接続エラー");
            }
            if (sftpClient1 == null || sftpClient2 == null) {
                log.error("ファイルサーバー接続エラー");
                throw new RRException("ファイルサーバー接続エラー");
            }
            fileMap = sftpClient1.readAllFromFolder(webInEntity.getCodValue() + "/" + yyyymmdd);
        }else {
            fileMap = FileUtils.getFilesDatas(webInEntity.getCodValue() + "/" + yyyymmdd);
        }
    }

    @Override
    public void courseData() {
        try {
            //SNK_coursedata_yyyymmdd.csv
            File snkCourseDataInFile = fileMap.get("SNK_coursedata_" + yyyymmdd + ".csv");
            //ERR_SNK_coursedata_yyyymmdd.csv
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), "ERR_SNK_coursedata_" + yyyymmdd + ".csv");
            File snkCourseDataOutFile = new File(tempPath);
            if (!snkCourseDataOutFile.exists()) {
                try{
                    snkCourseDataOutFile.getParentFile().mkdirs();
                    snkCourseDataOutFile.createNewFile();
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
            //入会コース情報ファイルの導入処理を行う。（全量導入、ファイル数：2）其1
            boolean first = courseDataImport(snkCourseDataInFile, snkCourseDataOutFile, 4, true);
            //DF_coursedata_enrl_yyyymmdd.csv
            File dfCourseDataEnrlInFile = fileMap.get("DF_coursedata_enrl_" + yyyymmdd + ".csv");
            //ERR_DF_coursedata_enrl_yyyymmdd.csv
            tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), "ERR_DF_coursedata_enrl_" + yyyymmdd + ".csv");
            File dfCourseDataEnrlOutFile = new File(tempPath);
            if (!dfCourseDataEnrlOutFile.exists()) {
                dfCourseDataEnrlOutFile.getParentFile().mkdirs();
                dfCourseDataEnrlOutFile.createNewFile();
            }
            ////入会コース情報ファイルの導入処理を行う。（全量導入、ファイル数：2）其2
            courseDataImport(dfCourseDataEnrlInFile, dfCourseDataEnrlOutFile, 2, first);
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void courseDiv() {
        //SNK_course_div_yyyymmdd.csv
        File snkCourseDivInFile = fileMap.get("SNK_course_div_" + yyyymmdd + ".csv");
        //ERR_SNK_course_div_yyyymmdd.csv
        File snkCourseDivOutFile = new File("ERR_SNK_course_div_" + yyyymmdd + ".csv");
        //コース区分情報ファイルの導入処理を行う。（全量導入、ファイル数：１）
        courseDivImport( snkCourseDivInFile, snkCourseDivOutFile, 2, true);
    }

    private boolean courseDataImport(File importFile, File exportFile,Integer column, boolean first){
        //ファイルが空フラグかどうか
        boolean flag = false;
        //行数
        Integer row = 1;
        //读
        CsvReader csvReader = null;
        //写
        CsvWriter csvWriter = null;
        //エラーメッセージ
        StringBuilder errMessage = new StringBuilder();
        //ログインユーザＩＤ
        String loginId = "system";
        if ( first ) {
            first = false;
            //入会コース管理データを削除する。※論理削除
            MstInCourseEntity mstInCourse = new MstInCourseEntity();
            mstInCourse.setDelFlg(1);
            mstInCourseDao.update(mstInCourse, new QueryWrapper<>());
        }
        //文書存在性検査を導入する
        if (importFile != null && importFile.exists()){
            long headerLength = 0;
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(importFile))),',', StandardCharsets.UTF_8);
                try {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(exportFile));
                    bufferedOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
                    csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new RRException("ファイル書き込みエラー");
                }
                //2020/11/18 9.0 huangxinliang modify end
                csvWriter.setTextQualifier('"');
                csvWriter.setForceQualifier(true);
                //読むheader
                csvReader.readHeaders();
                //书header
                String headersStr = csvReader.getRawRecord() + ",\tエラーメッセージ\t";
                csvWriter.writeRecord(headersStr.replaceAll("\"","").split(",", -1));
                csvWriter.flush();
                headerLength = exportFile.length();
                //コンテンツを読む
                while (csvReader.readRecord()){
                    flag = true;
                    row++;
                    MstInCourseEntity mstInCourseEntity = new MstInCourseEntity();
                    //空
                    errMessage.setLength(0);

                    for (int i = 0; i < column; i++){
                        switch (i){
                            case 0 :
                                if (StringUtils.isEmpty(csvReader.get(0))){
                                    errMessage.append( MessageUtils.getMessage("MSGCOMB0026",row.toString(), "コースコード"));
                                }else {
                                    //コースID  入会コース情報．コースID
                                    mstInCourseEntity.setCourseId(csvReader.get(0));
                                }
                                break;
                            case 1 :
                                if (StringUtils.isEmpty(csvReader.get(1))){
                                    errMessage.append( MessageUtils.getMessage("MSGCOMB0026",row.toString(), "コース名"));
                                }else {
                                    //コース名  入会コース情報．コース名
                                    mstInCourseEntity.setCourseNm(csvReader.get(1));
                                }
                                break;
                            case 2 :
                                if (StringUtils.isEmpty(csvReader.get(2))){
                                    errMessage.append( MessageUtils.getMessage("MSGCOMB0026",row.toString(), "コース区分"));
                                }else {
                                    //コース区分  入会コース情報．コース区分
                                    mstInCourseEntity.setCourseDiv(csvReader.get(2));
                                }
                                break;
                            case 3 :
                                if (StringUtils.isEmpty(csvReader.get(3))){
                                    errMessage.append( MessageUtils.getMessage("MSGCOMB0026",row.toString(), "非表示フラグ"));
                                }else {
                                    //非表示フラグ  入会コース情報．非表示フラグ
                                    mstInCourseEntity.setDisplayFlg(csvReader.get(3));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    if(errMessage.length()>0 && !"null".equals(errMessage.toString()) && !"".equals(errMessage.toString())){
                        //データを書き込む
                        csvWriter.writeRecord((csvReader.getRawRecord().replaceAll("\"","") + errMessage.toString()).split(",", -1));
                        csvWriter.flush();
                    }else {
                        //ファイルデータより下記テーブルのデータを作成する。※入会コース管理
                        //入会コース管理のデータ存在チェック
                        MstInCourseEntity inCourseEntityFlg = mstInCourseDao.selectOne(new QueryWrapper<MstInCourseEntity>().select("course_id").eq("course_id", mstInCourseEntity.getCourseId()));
                        //更新日時  システム日時
                        mstInCourseEntity.setUpdDatime( DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ  ログインユーザＩＤ
                        mstInCourseEntity.setUpdUsrId(loginId);
                        //削除フラグ  「0：有効」
                        mstInCourseEntity.setDelFlg(0);
                        if (inCourseEntityFlg  == null){
                            if (column == 2){
                                mstInCourseEntity.setCourseDiv(" ");
                            }
                            mstInCourseEntity.setCretDatime(DateUtils.getSysTimestamp());
                            mstInCourseEntity.setCretUsrId(loginId);
                            //上記取得できない場合、新規作成を行う。
                            try {
                                mstInCourseDao.insert(mstInCourseEntity);
                            } catch (Exception e){
                                log.error(e.getMessage());
                                log.error("入会コース管理登録に失敗する");
                                String rawData = csvReader.getRawRecord() + ",\"入会コース管理登録に失敗する\"";
                                csvWriter.writeRecord(rawData.replaceAll("\"","").split(",", -1));
                                csvWriter.flush();
                            }
                        }
                        if(inCourseEntityFlg != null ){
                            //上記取得できる場合、既存データを更新する。
                            try {
                                mstInCourseDao.update(mstInCourseEntity, new QueryWrapper<MstInCourseEntity>().eq("course_id", mstInCourseEntity.getCourseId()));
                            } catch (Exception e){
                                log.error(e.getMessage());
                                log.error("入会コース管理更新に失敗する");
                                String rawData = csvReader.getRawRecord() + ",\"入会コース管理更新に失敗する\"";
                                csvWriter.writeRecord(rawData.replaceAll("\"","").split(",", -1));
                                csvWriter.flush();
                            }
                        }
                    }
                }
                if (!flag) {
                    //入力ファイルの明細データが存在しない場合、下記メッセージをログに出力する。
                    log.error( MessageUtils.getMessage("MSGCOMN0017", importFile.getName() + "の明細"));
                }
            } catch (IOException e){
                log.error(e.getMessage());
            } finally {
                if (csvReader != null) {
                    csvReader.close();
                }
                if (csvWriter != null) {
                    csvWriter.close();
                }
                if (exportFile.length() > headerLength) {
                    if (!StringUtils.isEmpty(webOutEntity.getCodValue2())) {
                        sftpClient2.upLoadFile(exportFile, webOutEntity.getCodValue() + "/" + yyyymmdd);
                    } else {
                        File outFile = new File(webOutEntity.getCodValue() + "/" + yyyymmdd + File.separator + exportFile.getName());
                        try {
                            if (!outFile.exists()) {
                                outFile.getParentFile().mkdirs();
                                outFile.createNewFile();
                            }
                            Files.copy(exportFile, outFile);
                        } catch (IOException e) {
                            throw new RRException(e.getMessage());
                        }
                    }
                }
                exportFile.delete();
            }
        } else {
            log.error( MessageUtils.getMessage("MSGCOMB0025", "入会コース情報"));
        }
        return first;
    }

    private void courseDivImport(File importFile, File exportFile ,Integer column, boolean first){
        boolean flag = false;
        Integer row = 1;
        CsvReader csvReader = null;
        //写
        CsvWriter csvWriter = null;
        StringBuilder errMessage = new StringBuilder();
        //ログインユーザＩＤ
        String loginId = "system";

        //文書存在性検査を導入する
        if (importFile != null && importFile.exists()){
            long headerLength = 0;
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(importFile))),',', StandardCharsets.UTF_8);
                try {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(exportFile));
                    bufferedOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
                    csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new RRException("ファイル書き込みエラー");
                }
                //2020/11/18 9.0 huangxinliang modify end
                csvWriter.setTextQualifier('"');
                csvWriter.setForceQualifier(true);
                //読むheader
                csvReader.readHeaders();
                //书header
                String headersStr = csvReader.getRawRecord() + ",\"エラーメッセージ\"";
                csvWriter.writeRecord(headersStr.replaceAll("\"","").split(",", -1));
                csvWriter.flush();

                headerLength = exportFile.length();

                //コンテンツを読む
                while (csvReader.readRecord()){
                    if ( first ) {
                        first = false;
                        //コース区分管理データを削除する。※論理削除
                        MstCourseDivEntity mstCourseDiv = new MstCourseDivEntity();
                        mstCourseDiv.setDelFlg(1);
                        mstCourseDivDao.update(mstCourseDiv, new QueryWrapper<>());
                    }
                    flag = true;
                    row++;
                    MstCourseDivEntity mstCourseDivEntity = new MstCourseDivEntity();
                    //空
                    errMessage.setLength(0);
                    for (int i = 0; i < column; i++){
                        switch (i){
                            case 0 :
                                if (StringUtils.isEmpty(csvReader.get(0))){
                                    errMessage.append( MessageUtils.getMessage("MSGCOMB0026",row.toString(), "コース区分"));
                                }else {
                                    //コース区分情報．コース区分
                                    mstCourseDivEntity.setCourseDiv(csvReader.get(0));
                                }
                                break;
                            case 1 :
                                if (StringUtils.isEmpty(csvReader.get(1))){
                                    //コース区分情報．コース区分名
                                    errMessage.append( MessageUtils.getMessage("MSGCOMB0026",row.toString(), "コース区分名"));
                                }else {
                                    mstCourseDivEntity.setCourseDivNm(csvReader.get(1));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    if(errMessage.length()>0 && !"null".equals(errMessage.toString()) && !"".equals(errMessage.toString())){
                        //データを書き込む
                        csvWriter.writeRecord((csvReader.getRawRecord().replaceAll("\"","") + errMessage.toString()).split(",", -1));
                        csvWriter.flush();
                    }else {
                        //コース区分管理のデータ存在チェック
                        MstCourseDivEntity courseDivEntityFlg = mstCourseDivDao.selectOne(new QueryWrapper<MstCourseDivEntity>().select("course_div").eq("course_div", mstCourseDivEntity.getCourseDiv()));
                        //更新日時  システム日時
                        mstCourseDivEntity.setUpdDatime( DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ  ログインユーザＩＤ
                        mstCourseDivEntity.setUpdUsrId(loginId);
                        //削除フラグ  「0：有効」
                        mstCourseDivEntity.setDelFlg(0);

                        if (courseDivEntityFlg == null) {
                            //作成日時  システム日時
                            mstCourseDivEntity.setCretDatime( DateUtils.getSysTimestamp());
                            //作成ユーザＩＤ  ログインユーザＩＤ
                            mstCourseDivEntity.setCretUsrId(loginId);
                            //上記取得できない場合、新規作成を行う。
                            try {
                                mstCourseDivDao.insert(mstCourseDivEntity);
                            } catch (Exception e){
                                log.error(e.getMessage());
                                log.error("コース区分管理登録に失敗する");
                                String rawData = csvReader.getRawRecord() + ",\"コース区分管理登録に失敗する\"";
                                csvWriter.writeRecord(rawData.replaceAll("\"","").split(",", -1));
                                csvWriter.flush();
                            }
                        } else {
                            //上記取得できる場合、既存データを更新する。
                            try {
                                mstCourseDivDao.update(mstCourseDivEntity, new QueryWrapper<MstCourseDivEntity>().eq("course_div", mstCourseDivEntity.getCourseDiv()));
                            } catch (Exception e){
                                log.error(e.getMessage());
                                log.error("コース区分管理更新に失敗する");
                                String rawData = csvReader.getRawRecord() + ",\"コース区分管理更新に失敗する\"";
                                csvWriter.writeRecord(rawData.replaceAll("\"","").split(",", -1));
                                csvWriter.flush();
                            }
                        }
                    }
                }
                if (!flag) {
                    log.error( MessageUtils.getMessage("MSGCOMN0017",importFile.getName() + "の明細") );
                }
            } catch (IOException e){
                log.error(e.getMessage());
            } finally {
                if (csvReader != null) {
                    csvReader.close();
                }
                if (csvWriter != null) {
                    csvWriter.close();
                }
                if (exportFile.length() > headerLength) {
                    if (!StringUtils.isEmpty(webOutEntity.getCodValue2())) {
                        sftpClient2.upLoadFile(exportFile, webOutEntity.getCodValue() + "/" + yyyymmdd);
                    } else {
                        File outFile = new File(webOutEntity.getCodValue() + "/" + yyyymmdd + File.separator + exportFile.getName());
                        try {
                            if (!outFile.exists()) {
                                outFile.getParentFile().mkdirs();
                                outFile.createNewFile();
                            }
                            Files.copy(exportFile, outFile);
                        } catch (IOException e) {
                            throw new RRException(e.getMessage());
                        }
                    }
                }
                exportFile.delete();
            }
        } else {
            //指定パスにはファイルが存在しない場合、下記メッセージをログに出力する。
            log.error( MessageUtils.getMessage("MSGCOMB0025", "コース区分情報"));
        }
    }

    @Override
    public void releaseResource(){
        yyyymmdd = null;
        webInEntity = null;
        webOutEntity = null;
        sftpClient1 = null;
        sftpClient2 = null;
        fileMap = null;
    }
}
