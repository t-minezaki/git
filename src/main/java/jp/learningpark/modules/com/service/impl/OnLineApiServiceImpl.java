package jp.learningpark.modules.com.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import com.google.common.io.Files;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.OnLineApiDao;
import jp.learningpark.modules.com.dao.OnLineApiTransactionBean;
import jp.learningpark.modules.com.dto.OnLineApiDto;
import jp.learningpark.modules.com.service.OnLineApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstUsrFirtPwDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstGrpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *  マナミルSAMLMAPPING API
 *
 * @author NWT)lyx
 */
@Service
public class OnLineApiServiceImpl implements OnLineApiService {
    /**
     * マスタ_明細Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * ユーザ初期パスワード管理　Dao
     */
    @Autowired
    MstUsrFirtPwDao mstUsrFirtPwDao;

    /**
     * MstGrpService
     */
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * マナミルSAMLMAPPING Dao
     */
    @Autowired
    private OnLineApiDao onLineApiDao;

    /**
     * 学研API呼出用クラス
     */
    @Autowired
    private GakkenIdAPI gakkenIdAPI;

    /**
     * OnLineApiTransactionBean
     */
    @Autowired
    OnLineApiTransactionBean onLineApiTransactionBean;

    /**
     * メール  Utils
     */
    @Autowired
    private MailUtils mailUtils;

    /**
     * TYPE:Contact
     */
    private static final String ATTRIBUTES_TYPE_CONTACT= "Contact";

    /**
     * TYPE:JukoJoho__c
     */
    private static final String ATTRIBUTES_TYPE_JUKOJOHO_C= "JukoJoho__c";

    /**
     * action:insert
     */
    private static final String ACTION_ADD= "insert";

    /**
     * action:update
     */
    private static final String ACTION_UP= "update";

    /**
     * action:update
     */
    private static final String SAML_OUT_FILE_NAME= "SamlDataSync";

    /**
     * msg:既に既存データが存在した、insertできない。
     */
    private static final String MSG_INSERT_DATAS= " 既に既存データが存在した、insertできない。";

    /**
     * msg:既存データが存在しない、updateできない
     */
    private static final String MSG_UPDATE_NODATA= " 既存データが存在しない、updateできない";

    /**
     * msg:" 既存生徒が存在しない、グループ導入できない"
     */
    private static final String MSG_STU_NODATA= " 既存生徒が存在しない、グループ導入できない";

    /**
     * msg:はNULL
     */
    private static final String MSG_DATA_CHECK_NULL= " はNULL";

    /**
     * msg:異常を処理する
     */
    private static final String MSG_CATCH_EXCEPTION= "異常を処理する";

    /**
     * ログ
     */
    private static final Logger log = LoggerFactory.getLogger(OnLineApiServiceImpl.class);

    /**
     * ブランド取得API
     * @param paramsMap
     * @param serialNumber
     * @param timestamp
     * @return
     */
    @Override
    public ResponseEntity<Object> handleData(JSONArray paramsMap, String serialNumber, String timestamp, String data) {
        //システム時刻
        String times = DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS);
        log.info("START SamlDataSync");
        boolean handleFag = true;
        boolean handlePoint = true;
        try {
            // 1.1　チェックエラーデータを出力するために、下記条件でコードマスタ_明細からエラー出力ファイルのパスを取得する。
            MstCodDEntity mstCodDOutputJsonEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "SAML_JSON_FILE_PATH")
                    .eq("del_flg", 0)
                    .eq("cod_cd", "1")
                    .last("limit 1"));
            // 1.1.1 ファイルパスを取得できない場合、1.3を実行する
            // 1.3　上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
            if (mstCodDOutputJsonEntity == null) {
                log.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
                //JSONファイル保存パス取得失敗
                //203--> HTTP/1.1 500 Internal Server Error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error().put("msg", "JSONファイル保存パス取得失敗、処理を中断する").put("code", "203").put("HTTP/1.1", "500 Internal Server Error"));
            }
            // 1.1.2 ファイルパスを取得できる場合、もらったのJSONファイル保存する。ファイル名「SerialNumber_YYYYMMDDhhmmssmi.json」
            String outPutJsonFilePath = mstCodDOutputJsonEntity.getCodValue() + '/' + DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYYMMDD);
            // ファイル名「SerialNumber_YYYYMMDDhhmmssmi.json」
            String jsonFilePath =  serialNumber + "_" + times + GakkenConstant.JSON_FILE_SUFFIX;
            // JSONファイル保存する
            uploadJsonFile(jsonFilePath, data, outPutJsonFilePath);

            // 1.2　チェックエラーデータを出力するために、下記条件でコードマスタ_明細からエラー出力ファイルのパスを取得する。
            MstCodDEntity mstCodDOutputEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "SAML_OUT_FILE_PATH")
                    .eq("del_flg", 0)
                    .eq("cod_cd", "1")
                    .last("limit 1"));
            // 1.3　上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
            if (mstCodDOutputEntity == null) {
                log.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
                //エラーファイルパス取得失敗
                //203--> HTTP/1.1 500 Internal Server Error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error().put("msg", "エラーファイルパス取得失敗、処理を中断する").put("code", "203").put("HTTP/1.1", "500 Internal Server Error"));
            }
            String outPutCsvFilePath = mstCodDOutputEntity.getCodValue() + '/' + DateUtils.format(DateUtils.getSysDate(), Constant.DATE_FORMAT_YYYYMMDD);
            // 1.4　性別区分を変換するために、下記条件でコードマスタ_明細から両方の性別区分対応関係データを取得する。
            List<MstCodDEntity> genderDivList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "GENDR_DIV")
                    .eq("del_flg", 0));
            // 1.5　学年区分を変換するために、下記条件でコードマスタ_明細から両方の学年区分対応関係データを取得する。
            List<MstCodDEntity> schyDivList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "SCHY_DIV")
                    .eq("del_flg", 0));
            // 1.6　続柄区分を変換するために、下記条件でコードマスタ_明細から両方の続柄区分対応関係データを取得する。
            List<MstCodDEntity> reltnspList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "RELTNSP_DIV")
                    .eq("del_flg", 0));
            // 1.7　曜日区分を変換するために、下記条件でコードマスタ_明細から両方の曜日区分対応関係データを取得する。
            List<MstCodDEntity> dayweekDivList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "DAYWEEK_DIV")
                    .eq("del_flg", 0));

            //エラー CSVファイル物理名
            String errFilePath = GakkenConstant.ERR_PREFIX + SAML_OUT_FILE_NAME + "_" + times + GakkenConstant.CSV_FILE_SUFFIX;
            CsvWriter csvWriter = null;
            File outputFile = null;
            long lenth = 0L;
            try {
                String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), errFilePath);
                outputFile = new File(tempPath);
                if (!outputFile.exists()) {
                    outputFile.getParentFile().mkdirs();
                    outputFile.createNewFile();
                }
                // CSV書き込みオブジェクトを作成する
                try {
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                    bufferedOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
                    csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
                }catch (Exception e){
                    throw new RRException("ファイル書き込みエラー");
                }
                csvWriter.setTextQualifier('"');
                csvWriter.setForceQualifier(true);
                String headersStr = "エラーメッセージ";
                // ヘッダーを書き込む
                csvWriter.writeRecord(headersStr.split(",", -1));
                csvWriter.flush();
                lenth = outputFile.length();
            }catch (IOException e){
                e.printStackTrace();
            }
            List<OnLineApiDto> recordsList = paramsMap.toJavaList(OnLineApiDto.class);
            for(OnLineApiDto dto : recordsList){
                String msg = null;
                do{
                    try{
                        // Contact.SeitoBango2__cを基づいて、データの既存チェックを行う
                        MstStuEntity stu = onLineApiDao.selectGurdId(dto.SeitoBango2__c);
                        // attributesのtypeが「Contact」データの導入処理を行う。
                        if(ATTRIBUTES_TYPE_CONTACT.equals(dto.attributes.type) ){
                            //参照シート「JSON例」の必須項目欄＝〇　かつ、Jsonファイルの値はNULLで設定された場合、当該レコードは必須チェックエラーのため、2.1.3の処理へ。
                            msg = checkContactNullData(dto);
                            if(!StringUtils.isEmpty(msg)){
                                msg = msg + MSG_DATA_CHECK_NULL;
                                break;
                            }
                            // actionがinsert と　DBに当該レコード既存しないの場合,新規操作を行う。
                            if(ACTION_ADD.equals(dto.attributes.action) && stu == null){
                                msg = onLineApiTransactionBean.contactInsert(dto, genderDivList, reltnspList, schyDivList);
                            }
                            // actionがinsert と　DBに当該レコード既存の場合、エラーのため、2.1.3の処理へ。
                            else if(ACTION_ADD.equals(dto.attributes.action) && stu != null){
                                msg = MSG_INSERT_DATAS;
                            }
                            // actionがupdateの場合 DBに当該レコード既存の場合,更新操作を行う。
                            else if(ACTION_UP.equals(dto.attributes.action) && stu != null){
                                msg = onLineApiTransactionBean.contactUpdate(dto, genderDivList, reltnspList, schyDivList, stu);
                            }
                            //actionがupdateの場合 DBに当該レコード既存しないの場合、新規操作を行う。
                            else if(ACTION_UP.equals(dto.attributes.action) && stu == null){
                                msg = onLineApiTransactionBean.contactInsert(dto, genderDivList, reltnspList, schyDivList);
                            }
                        }
                        // attributesのtypeが「JukoJoho__c」
                        else if(ATTRIBUTES_TYPE_JUKOJOHO_C.equals(dto.attributes.type)){
                            // 生徒既存しない場合、エラーと判定し、エラーメッセージ連れて3.1.4の処理へ。
                            if(stu == null){
                                msg = MSG_STU_NODATA;
                                break;
                            }
                            // 参照シート「JSON例」の必須項目欄＝〇　かつ、Jsonファイルの値はNULLで設定された場合、当該レコードは必須チェックエラーのため、3.1.4の処理へ。
                            msg = checkJukoJohoNullData(dto);
                            if(!StringUtils.isEmpty(msg)){
                                msg =  msg + MSG_DATA_CHECK_NULL;
                                break;
                            }
                            //グループデータ取得
                            List<MstGrpEntity> mstGrpList = mstGrpService.list(new QueryWrapper<MstGrpEntity>()
                                    .eq("grp_cod",dto.KyoshitsuCourceMaster__c)
                                    .eq("del_flg",0));
                            // グループ既存しない場合、グループデータを作成する。
                            if(ACTION_ADD.equals(dto.attributes.action) && mstGrpList.size() == 0){
                                //グループマスタ登録
                                msg =onLineApiTransactionBean.mstGrpSave(dto, dayweekDivList, stu.getStuId());
                            }
                            // グループ既存の場合、action=insertと合致しないため、生徒グループ管理に生徒とグループ関連関係設定します。。
                            else if(ACTION_ADD.equals(dto.attributes.action) && mstGrpList.size() > 0){
                                msg = onLineApiTransactionBean.jukoJohoStuGrpSave(stu.getStuId(), mstGrpList.get(0).getGrpId(), dto.KyoshitsuCourceMaster__c);
                            }
                            // グループ既存の場合、グループデータを更新する
                            else if(ACTION_UP.equals(dto.attributes.action) && mstGrpList.size() > 0){
                                msg = onLineApiTransactionBean.jukoJohoUpdate(dto, dayweekDivList, mstGrpList, stu.getStuId());
                            }
                            //①でグループ既存しないの場合、action=updateと合致しないため、新規操作を行う、グループデータを作成する。
                            else if(ACTION_UP.equals(dto.attributes.action) && mstGrpList.size() == 0){
                                //グループマスタ登録
                                msg =onLineApiTransactionBean.mstGrpSave(dto, dayweekDivList, stu.getStuId());
                            }
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        msg =  MSG_CATCH_EXCEPTION;
                    }
                }while(false);
                if(msg!=null){
                    msg = "レコードID：" + dto.id + " " + msg;
                    outputErrorMessage(csvWriter, MessageUtils.getMessage("MSGCOMN0190", serialNumber,timestamp,msg));
                    handleFag = false;
                }
            }
            //メッセージをログに出力する。
            uploadErrorMessageFile(errFilePath, csvWriter, outputFile, lenth, outPutCsvFilePath);
            if (csvWriter != null) {
                csvWriter.close();
            }
            // 一部データ連携失敗の場合、メールサーバーを基づいて、【エラー】メール通知送信を行う。
            if(!handleFag){
                handlePoint = false;
                postEmail(errFilePath, serialNumber, timestamp, outPutCsvFilePath);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            if(handlePoint){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error().put("msg", e.getMessage()).put("code", "203").put("HTTP/1.1", "500 Internal Server Error"));
            }else {
                // 配信失敗の場合
                return ResponseEntity.ok().body(R.ok().put("msg", e.getMessage()).put("code", "200").put("HTTP/1.1", "200"));
            }

        }
        log.info("END SamlDataSync");
        return ResponseEntity.ok().body(R.ok().put("msg", "成功").put("code", "200").put("HTTP/1.1", "200"));
    }


    /**
     * JSONファイル保存する
     * @param outputFilename
     * @param json
     */
    private void uploadJsonFile(String outputFilename, String json, String outPutJsonFilePath) {
        BufferedWriter writer = null;
        File outFile = new File(outPutJsonFilePath + File.separator + outputFilename);
        try {
            if(!outFile.exists()){
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile,false)));
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RRException("ファイル書き込みエラー");
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 【エラー】メール通知送信を行う。
     * @return
     */
    @Override
    public void postEmail(String errFilePath,String serialNumber,String timestamp, String outPutCsvFilePath) {
        try {
            // エラーメール通知のメール本文を取得するため、コードマスタより取得する。
            // ・コードマスタ．コード値:メール本文
            //・	コードマスタ．コード値２:メール件名
            //・	コードマスタ．コード値３:宛先メールアドレス
            MstCodDEntity  samlMail= mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>()
                    .eq("cod_key", "MAIL_MSG_SAML")
                    .eq("del_flg", 0)
                    .eq("cod_cd", "1")
                    .last("limit 1"));
//            StringBuffer sb = new StringBuffer();
//            sb.append(samlMail.getCodValue2() + "<br>")
//                    .append("<p style='text-align:right'>マナミル " + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS) + "</p>");
////            mailUtils.sendMail(email, title, sb.toString());
            List<String> filePath = new ArrayList<String>();
            filePath.add(outPutCsvFilePath + File.separator + errFilePath);
            mailUtils.sendMailAttach(samlMail.getCodValue3(), samlMail.getCodValue2(), samlMail.getCodValue() , filePath);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RRException(MessageUtils.getMessage("MSGCOMN0094"));
        }
    }

    /**
     * getGidKp
     * @param gid
     * @return
     * @throws Exception
     */
    @Override
    public String getGidKp(String gid) throws Exception{
        String gidKp = null;
        V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
        V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
        gakkenID.setGid(gid);
        // GIDPK
        gakkenIdSelect.setGidpk(true);
        // GID
        gakkenIdSelect.setGid(true);
        V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs resultGakkenIDs =  gakkenIdAPI.getGakkenIDSearch(gakkenID,gakkenIdSelect);
        if (resultGakkenIDs != null && resultGakkenIDs.getRecordCounts() >0) {
            V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDs = resultGakkenIDs.getGakkenID();
            gidKp = gakkenIDs[0].getGidpk();
        }
        return gidKp;
    }

    /**
     * エラーメッセージを出力
     * @param csvWriter
     * @param errorMessage
     * @throws IOException
     */
    private void outputErrorMessage(CsvWriter csvWriter, String errorMessage) throws IOException {
        log.error(errorMessage);
        String[] strings  = {errorMessage};
        csvWriter.writeRecord(strings);
        csvWriter.flush();
    }


    /**
     * エラー情報ファイルをアップロードする
     * @param outputFilename
     * @param csvWriter
     * @param outputFile
     * @param headerLength
     */
    private void uploadErrorMessageFile(String outputFilename, CsvWriter csvWriter, File outputFile, long headerLength, String outPutCsvFilePath) {
        if (csvWriter != null) {
            csvWriter.close();
            if (outputFile.length() > headerLength) {
                File outFile = new File(outPutCsvFilePath + File.separator + outputFilename);
                try {
                    if (!outFile.exists()) {
                        outFile.getParentFile().mkdirs();
                        outFile.createNewFile();
                    }
                    Files.copy(outputFile, outFile);
                } catch (IOException e) {
                    throw new RRException(e.getMessage());
                }
            }
            outputFile.delete();
        }
    }

    /**
     *　ContactCheck
     * @param dto
     * @return
     */
    private String checkContactNullData(OnLineApiDto dto){
//        if(StringUtils.isEmpty(dto.HogoshaMeiKana__c)){
//            return  "HogoshaMeiKana__c";
//        }
        if(StringUtils.isEmpty(dto.HogoshaMei__c)){
            return "HogoshaMei__c";
        }
//        if(StringUtils.isEmpty(dto.HogoshaSeiKana__c)){
//            return "HogoshaSeiKana__c";
//        }
        if(StringUtils.isEmpty(dto.HogoshaSei__c)){
            return "HogoshaSei__c";
        }
        if(StringUtils.isEmpty(dto.SeitoBango2__c)){
            return "SeitoBango2__c";
        }
        if(StringUtils.isEmpty(dto.KazokuCode__c)){
            return "KazokuCode__c";
        }
        return null;
    }

    /**
     * JukoJoho_C Check
     * @param dto
     * @return
     */
    private String checkJukoJohoNullData(OnLineApiDto dto){
        if(StringUtils.isEmpty(dto.KyoshitsuCourceMaster__c)){
            return "KozaCource__c";
        }
        if(StringUtils.isEmpty(dto.KyoshitsuCourceMaster__r)){
            return "KyoshitsuCourceMaster__r";
        }
        return null;
    }

}