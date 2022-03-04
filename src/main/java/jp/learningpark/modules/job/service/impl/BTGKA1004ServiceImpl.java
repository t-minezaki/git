package jp.learningpark.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.io.Files;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstOrgDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.job.dao.BTGKA1004TransactionBean;
import jp.learningpark.modules.job.entity.*;
import jp.learningpark.modules.job.service.BTGKA1004Service;
import jp.learningpark.modules.job.task.dao.BTGKA1004Dao;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/8/26 ： NWT)hxl ： 新規作成
 * 2020/11/23 ： NWT)hxl ： 変更
 * @date 2020/8/26 12:18
 */
@Service
public class BTGKA1004ServiceImpl implements BTGKA1004Service {

    /**
     * マスタ_明細Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * btgka1004TransantionBean
     */
    @Autowired
    BTGKA1004TransactionBean btgka1004TransactionBean;

    /**
     * btgka1004Dao
     */
    @Autowired
    private BTGKA1004Dao btgka1004Dao;

    /**
     * mstOrgDao
     */
    @Autowired
    private MstOrgDao mstOrgDao;

    /**
     * mstUsrDao
     */
    @Autowired
    private MstUsrDao mstUsrDao;


    /**
     * 入力ファイルのパス
     */
    private static final AtomicReference<MstCodDEntity> IMPORT_CSV_FILE_PATH = new AtomicReference<>();

    /**
     * 出力ファイルのパス
     */
    private static final AtomicReference<MstCodDEntity> OUTPUT_CSV_FILE_PATH = new AtomicReference<>();

    /**
     * 組織コード前付き文字
     */
    private static final AtomicReference<MstCodDEntity> orgPrefix = new AtomicReference<>();

    /**
     * 上位組織導入可否フラグ
     */
    private static final AtomicReference<String> webUporgInFlgReference = new AtomicReference<>();

    /**
     * ファイル導入実施要否フラグ
     */
    private static final AtomicReference<MstCodDEntity> WEB_FILE_IN_FLG_REFERENCE = new AtomicReference<>();

    private static final String IMPORT_FLAG = "1";
    /**
     * Web申込教室階層
     */
    private static AtomicInteger classRoomLevel = new AtomicInteger();

    Map<String, BTGKA1004MemberDto> mapOfMemberDto = new HashMap<>();

    SftpClient sftpClient1 = null;
    SftpClient sftpClient2 = null;

    private static final Logger log = LoggerFactory.getLogger(BTGKA1004ServiceImpl.class);

    private static final String ORG_UPDATE_FLAG = "1";

    Map<String, File> fileMap = null;

    @Override
    public R importDateFromCsvFile() {
        //システム時刻
        String yesterday = DateUtils.format(DateUtils.addDays(DateUtils.getSysDate(), -1), Constant.DATE_FORMAT_YYYYMMDD);
        //1.1　導入ファイルを読込むために、下記条件でコードマスタ_明細から入力ファイルのパスを取得する。
        do {
            MstCodDEntity mstCodDImportEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_IN_FILE_PATH").eq("del_flg", 0).eq("cod_cd", "1").last("limit 1"));
            //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
            if (mstCodDImportEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
            }
            mstCodDImportEntity.setCodValue(mstCodDImportEntity.getCodValue() + '/' + yesterday);
            IMPORT_CSV_FILE_PATH.set(mstCodDImportEntity);
            if (!StringUtils.isEmpty(IMPORT_CSV_FILE_PATH.get().getCodValue2())) {
                try {
                    sftpClient1 = new SftpClient(IMPORT_CSV_FILE_PATH.get().getCodValue2().trim(), IMPORT_CSV_FILE_PATH.get().getCodValue4().trim(), IMPORT_CSV_FILE_PATH.get().getCodValue5().trim(), Integer.parseInt(IMPORT_CSV_FILE_PATH.get().getCodValue3().trim()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return R.error("ファイルサーバー接続エラー");
                }
                if (sftpClient1 == null) {
                    return R.error("ファイルサーバー接続エラー");
                }
                fileMap = sftpClient1.readAllFromFolder(IMPORT_CSV_FILE_PATH.get().getCodValue());
                if (fileMap.isEmpty()) {
                    return R.error("フォルダが空です");
                }
            } else {
                fileMap = FileUtils.getFilesDatas(IMPORT_CSV_FILE_PATH.get().getCodValue());
            }
        } while (false);

        //1.2　チェックエラーデータを出力するために、下記条件でコードマスタ_明細からエラー出力ファイルのパスを取得する。
        do {
            MstCodDEntity mstCodDOutputEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_OUT_FILE_PATH").eq("del_flg", 0).eq("cod_cd", "1").last("limit 1"));
            //上記のファイルパスを取得できない場合、下記メッセージをログに出力する。
            if (mstCodDOutputEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
            }
            mstCodDOutputEntity.setCodValue(mstCodDOutputEntity.getCodValue() + '/' + yesterday);
            OUTPUT_CSV_FILE_PATH.set(mstCodDOutputEntity);
            if (!StringUtils.isEmpty(OUTPUT_CSV_FILE_PATH.get().getCodValue2())) {
                try {
                    sftpClient2 = new SftpClient(OUTPUT_CSV_FILE_PATH.get().getCodValue2().trim(), OUTPUT_CSV_FILE_PATH.get().getCodValue4().trim(), OUTPUT_CSV_FILE_PATH.get().getCodValue5().trim(), Integer.parseInt(OUTPUT_CSV_FILE_PATH.get().getCodValue3().trim()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return R.error("ファイルサーバー接続エラー");
                }
                if (sftpClient2 == null) {
                    return R.error("ファイルサーバー接続エラー");
                }
            }
        } while (false);

        //1.6　組織コード前付き文字を取得するために、下記条件でコードマスタ_明細から付き文字を取得する。
        do {
            MstCodDEntity mstCodDOrgPrefixEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ORG_ID_ADD").eq("del_flg", 0));
            if (mstCodDOrgPrefixEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0035", "組織コードの前に文字をつける"));
            }
            orgPrefix.set(mstCodDOrgPrefixEntity);
        } while (false);

        //1.7　Web申込教室階層を取得するために、下記条件でコードマスタ_明細から設定値を取得する。
        do {
            MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_ORG_LEVEL").eq("del_flg", 0));
            if (mstCodDEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0035", "Web申込教室階層"));
            }
            classRoomLevel.set(Integer.parseInt(mstCodDEntity.getCodValue()));
        } while (false);

        //1.8　上位組織導入可否フラグを取得するために、下記条件でコードマスタ_明細から設置値を取得する。
        do {
            MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_UPORG_IN_FLG").eq("del_flg", 0));
            if (mstCodDEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0035", "上位組織導入可否フラグ"));
            }
            webUporgInFlgReference.set(StringUtils.trim(mstCodDEntity.getCodValue()));
        } while (false);

        //1.9　ファイル導入実施要否フラグを取得するために、下記条件でコードマスタ_明細から設置値を取得する。
        do{
            MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "WEB_FILE_IN_FLG").eq("del_flg", 0));
            if (mstCodDEntity == null){
                //①　取得できない場合、ディフォルト値は各フラグ＝「1：要」を利用する。
                mstCodDEntity = new MstCodDEntity();
                mstCodDEntity.setCodValue(IMPORT_FLAG);
                mstCodDEntity.setCodValue2(IMPORT_FLAG);
                mstCodDEntity.setCodValue3(IMPORT_FLAG);
                mstCodDEntity.setCodValue4(IMPORT_FLAG);
            }
            WEB_FILE_IN_FLG_REFERENCE.set(mstCodDEntity);
        }while (false);

        //取得した会員情報導入要否フラグ＝「1：要」の場合、
        //2. 会員情報ファイルの導入処理を行う。（差分導入、ファイル数：２）
        if (StringUtils.equals(IMPORT_FLAG, WEB_FILE_IN_FLG_REFERENCE.get().getCodValue())) {
            do {
                //会員情報SNK CSVファイル
                String snkMemberFilePath = GakkenConstant.SNK_MEMBER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //会員情報差分 CSVファイル
                String dfMemberFilePath = GakkenConstant.DF_MEMBER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //会員情報補足ファイル
                String manaMemberFilePath = GakkenConstant.MANA_MEMBER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //エラー会員情報SNK CSVファイル物理名
                String errSnkMemberFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.SNK_MEMBER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //エラー会員情報差分 CSVファイル物理名
                String errDfMemberFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.DF_MEMBER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //エラー会員情報補足ファイル
                String errManaMemberFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.MANA_MEMBER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                mapOfMemberDto.clear();
                try {
                    //会員情報補足ファイルの導入処理
                    importMemberFillCsvFile(manaMemberFilePath, errManaMemberFilePath);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    break;
                }
                //snk会員情報ファイルの導入処理
                importMemberCsvFile(snkMemberFilePath, errSnkMemberFilePath);
                //df会員情報ファイルの導入処理
                importMemberCsvFile(dfMemberFilePath, errDfMemberFilePath);
            } while (false);
        }

        //取得した組織情報導入要否フラグ＝「1：要」の場合、
        //3. 組織情報ファイルの導入処理を行う。（全量導入、ファイル数：１）
        if (StringUtils.equals(IMPORT_FLAG, WEB_FILE_IN_FLG_REFERENCE.get().getCodValue2())) {
            do {
                //組織情報SNK CSVファイル
                String manaOrgFilePath = GakkenConstant.MANA_ORG_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //エラー組織情報SNK CSVファイル物理名
                String errManaOrgFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.MANA_ORG_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //組織情報ファイルの導入処理を行う。
                importManaOrgCsvFile(manaOrgFilePath, errManaOrgFilePath, true);
            } while (false);
        }

        //取得した指導者情報導入要否フラグ＝「1：要」の場合、
        //4. 指導者情報ファイルの導入処理を行う。（全量導入、ファイル数：１）
        if (StringUtils.equals(IMPORT_FLAG, WEB_FILE_IN_FLG_REFERENCE.get().getCodValue3())) {
            do {
                //指導者情報 CSVファイル
                String manaTeacherFilePath = GakkenConstant.MANA_TEACHER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //指導者情報 CSVファイル物理名
                String errManaTeacherFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.MANA_TEACHER_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //指導者情報ファイルの導入処理
                importTeacherCsvFile(manaTeacherFilePath, errManaTeacherFilePath, true);
            } while (false);
        }

        //取得した指導者教室所属情報導入要否フラグ＝「1：要」の場合、
        //5. 指導者_教室所属情報ファイルの導入処理を行う。（全量導入、ファイル数：１）
        if (StringUtils.equals(IMPORT_FLAG, WEB_FILE_IN_FLG_REFERENCE.get().getCodValue4())) {
            do {
                //指導者_教室所属情報 CSVファイル
                String manaTidOrgFilePath = GakkenConstant.MANA_TID_ORG_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //指導者_教室所属情報 CSVファイル物理名
                String errManaTidOrgFileFilePath = GakkenConstant.ERR_PREFIX + GakkenConstant.MANA_TID_ORG_PREFIX + yesterday + GakkenConstant.CSV_FILE_SUFFIX;
                //指導者_教室所属情報ファイルの導入処理
                importManaTidOrgCsvFile(manaTidOrgFilePath, errManaTidOrgFileFilePath, true);
            } while (false);
        }

        //6. ユーザ基本マスタの所属組織フラグを更新する。
//        do {
//            List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("system_kbn", "2").eq("del_flg", 0));
//            Map<String, Map<String, MstUsrEntity>> mapOfGidpkAndOrgIdUserMap = new HashMap<>(8);
//            Map<String, List<String>> mapOfGidpkAndOrgIdList = new HashMap<>(8);
//            for (MstUsrEntity mstUsrEntity : mstUsrEntityList) {
//                Map<String, MstUsrEntity> mapOfOrgIdAndUser;
//                List<String> listOfOrgId;
//                String key = mstUsrEntity.getGidpk();
//                String orgId = mstUsrEntity.getOrgId();
//                if (!mapOfGidpkAndOrgIdUserMap.containsKey(key)) {
//                    mapOfOrgIdAndUser = new HashMap<>(8);
//                    mapOfGidpkAndOrgIdUserMap.put(key, mapOfOrgIdAndUser);
//                } else {
//                    mapOfOrgIdAndUser = mapOfGidpkAndOrgIdUserMap.get(key);
//                }
//                mapOfOrgIdAndUser.put(orgId, mstUsrEntity);
//                if (!mapOfGidpkAndOrgIdList.containsKey(key)) {
//                    listOfOrgId = new ArrayList<>();
//                    mapOfGidpkAndOrgIdList.put(key, listOfOrgId);
//                } else {
//                    listOfOrgId = mapOfGidpkAndOrgIdList.get(key);
//                }
//                listOfOrgId.add(orgId);
//            }
//            for (Map.Entry<String, Map<String, MstUsrEntity>> entry : mapOfGidpkAndOrgIdUserMap.entrySet()) {
//                String gidpk = entry.getKey();
//                Map<String, MstUsrEntity> mapOfOrgIdAndUser = entry.getValue();
//                List<String> listOfOrgIdList = mapOfGidpkAndOrgIdList.get(gidpk);
//                for (Map.Entry<String, MstUsrEntity> entry1 : mapOfOrgIdAndUser.entrySet()) {
//                    String orgId = entry1.getKey();
//                    MstUsrEntity userEntity = entry1.getValue();
//                    Integer count = btgka1004Dao.selectThisAndUpOrgIdListFromOrgIdList(orgId, listOfOrgIdList, orgPrefix.get().getCodValue());
//                    userEntity.setOwnerOrgFlg(count > 1 ? "0" : "1");
//                    try {
//                        mstUsrDao.updateById(userEntity);
//                    } catch (Exception e) {
//                        log.error(e.getMessage());
//                    }
//                }
//            }
//        } while (false);

        //７.　上記全部処理が正常に終了する際に、下記組織更新処理を行う。
        do {
            //7.1　下記条件でコードマスタ明細から組織マスタ更新可否のキー値を取得する。
            MstCodDEntity mstOrgUpdFlg = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "MST_ORG_UPD_FLG").eq("del_flg", 0));
            if (mstOrgUpdFlg == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0035", "Web申込教室階層"));
            }
            if (StringUtils.equals(ORG_UPDATE_FLAG, mstOrgUpdFlg.getCodValue())) {
                MstOrgEntity mstOrgEntity = new MstOrgEntity();
                mstOrgEntity.setUpdDatime(DateUtils.getSysTimestamp());
                mstOrgEntity.setUpdUsrId("system");
                mstOrgEntity.setDelFlg(1);
                mstOrgDao.update(mstOrgEntity, new UpdateWrapper<MstOrgEntity>().
                        eq("brand_cd", orgPrefix.get().getCodValue()).eq("del_flg", 0).
                        ge("level", 2));
            }
        }while (false);

        //8.　導入した指導者情報に対して、教室管理者ではない指導者を削除する。
//        do {
            //管理者基本マスタを削除する。　　※物理削除
//            btgka1004Dao.deleteTeacherPhysical();
            //ユーザーキャラタを削除する。　　※物理削除
//            btgka1004Dao.deleteTeacherSysUserRolePhysical();
            //ユーザ基本マスタを削除する。　　※物理削除
//            btgka1004Dao.deleteTeacherUserPhysical();
//        }while (false);

        fileMap = null;
        if (sftpClient1 != null) {
            sftpClient1.disConnect();
        }
        if (sftpClient2 != null) {
            sftpClient2.disConnect();
        }
        return R.ok();
    }

    /**
     * 会員情報補足ファイルの導入処理
     *
     * @param importFilename
     */
    private void importMemberFillCsvFile(String importFilename, String outputFilename) {
        long start = System.currentTimeMillis();
        log.info("START 会員情報補足ファイルの導入処理:{}", start);
        File file = fileMap.get(importFilename);
        if (file == null || !file.exists()) {
            //2.0.1　指定パスには会員情報補足ファイルが存在しない場合、下記メッセージをログに出力する。
            log.error(MessageUtils.getMessage("MSGCOMB0025", "会員情報補足"));
            throw new RRException(MessageUtils.getMessage("MSGCOMB0025", "会員情報補足"));
        }

        CsvReader csvReader = null;
        CsvWriter csvWriter = null;
        File outputFile = null;

        long headerLength = 0;
        try {
            // CSV読み取りオブジェクトを作成する
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(file))), ',', StandardCharsets.UTF_8);
                //2020/11/18 9.0 huangxinliang modify end
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RRException("ファイル読み取りエラー");
            }
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
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
                log.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // リーディングヘッド
            csvReader.readHeaders();
            String headersStr = csvReader.getRawRecord() + ",\tエラーメッセージ\t";
            // ヘッダーを書き込む
            csvWriter.writeRecord(headersStr.replaceAll("\"", "").split(",", -1));
            csvWriter.flush();

            headerLength = outputFile.length();

            // 空のデータフラグ
            boolean emptyData = true;
            int rawNumber = 1;

            while (csvReader.readRecord()) {
                emptyData = false;
                rawNumber++;
                BTGKA1004ManaMemberDto btgka1004ManaMemberDto = new BTGKA1004ManaMemberDto();
                btgka1004ManaMemberDto.setOrgId(csvReader.get("教室コード"));
                btgka1004ManaMemberDto.setMemberCd(csvReader.get("会員コード"));
                btgka1004ManaMemberDto.setGuardFlnmNm(csvReader.get("保護者姓（漢字）"));
                btgka1004ManaMemberDto.setGuardFlnmLnm(csvReader.get("保護者名（漢字）"));
                btgka1004ManaMemberDto.setGuardFlnmKnNm(csvReader.get("保護者姓（カナ）"));
                btgka1004ManaMemberDto.setGuardFlnmKnLnm(csvReader.get("保護者名（カナ）"));
                btgka1004ManaMemberDto.setStuGidpk(csvReader.get("生徒GIDPK"));
                btgka1004ManaMemberDto.setGuardGidpk(csvReader.get("保護者GIDPK"));
                //2.2.1　フィルデータの必須項目のチェック処理を行う。
                String errorMessage = BTGKA1004ManaMemberDto.validate(rawNumber, btgka1004ManaMemberDto);
                if (!StringUtils.isEmpty(errorMessage)) {
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                //2.2.1.2　チェック正常の場合、処理を継続する。
                try {
                    // 2.1　会員情報補足リストを作成するために、会員情報補足データ件数をもとに、下記処理を繰り返す。
                    BTGKA1004MemberDto btgka1004MemberDto = new BTGKA1004MemberDto();
                    // ファイルの各項目を会員情報補足リストに設定する。
                    btgka1004MemberDto.setOrgId(btgka1004ManaMemberDto.getOrgId());
                    btgka1004MemberDto.setMemberCd(btgka1004ManaMemberDto.getMemberCd());
                    btgka1004MemberDto.setGuardFlnmNm(btgka1004ManaMemberDto.getGuardFlnmNm());
                    btgka1004MemberDto.setGuardFlnmLnm(btgka1004ManaMemberDto.getGuardFlnmLnm());
                    btgka1004MemberDto.setGuardFlnmKnNm(btgka1004ManaMemberDto.getGuardFlnmKnNm());
                    btgka1004MemberDto.setGuardFlnmKnLnm(btgka1004ManaMemberDto.getGuardFlnmKnLnm());
                    btgka1004MemberDto.setStuGidpk(btgka1004ManaMemberDto.getStuGidpk());
                    btgka1004MemberDto.setGuardGidpk(btgka1004ManaMemberDto.getGuardGidpk());
                    //key:orgId-memberCd
                    mapOfMemberDto.put(btgka1004MemberDto.getOrgId() + "-" + btgka1004MemberDto.getMemberCd(), btgka1004MemberDto);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("method saveOrUpdateMemberData process failed with raw number :" + rawNumber);
                }
            }
            if (emptyData) {
                //2.0.2　会員情報補足ファイルの明細データが存在しない場合、下記メッセージをログに出力する。
                log.error(MessageUtils.getMessage("MSGCOMN0017", "会員情報補足ファイルの明細"));
                throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "会員情報補足ファイルの明細"));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
            uploadErrorMessageFile(outputFilename, csvWriter, outputFile, headerLength);
            long end = System.currentTimeMillis();
            log.info("END   会員情報補足ファイルの導入処理:{}", end);
            log.info("会員情報補足ファイルの導入処理時間: {}ms", end - start);
        }
    }

    /**
     * 会員情報ファイルの導入処理
     *
     * @param importFilename
     * @param outputFilename
     */
    private void importMemberCsvFile(String importFilename, String outputFilename) {
        long start = System.currentTimeMillis();
        log.info("START 会員情報ファイルの導入処理:{}", start);
        File file = fileMap.get(importFilename);
        if (file == null || !file.exists()) {
            //2.0.1　指定パスには会員情報補足ファイルが存在しない場合、下記メッセージをログに出力する。
            log.error(MessageUtils.getMessage("MSGCOMB0025", "会員情報補足"));
            return;
        }

        CsvReader csvReader = null;
        CsvWriter csvWriter = null;
        File outputFile = null;

        long headerLength = 0;

        try {
            // CSV読み取りオブジェクトを作成する
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(file))), ',', StandardCharsets.UTF_8);
                //2020/11/18 9.0 huangxinliang modify end
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RRException("ファイル読み取りエラー");
            }
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
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
                log.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // リーディングヘッド
            csvReader.readHeaders();
            String headersStr = csvReader.getRawRecord() + ",\tエラーメッセージ\t";
            // ヘッダーを書き込む
            csvWriter.writeRecord(headersStr.replaceAll("\"", "").split(",", -1));
            csvWriter.flush();

            headerLength = outputFile.length();

            // 空のデータフラグ
            boolean emptyData = true;

            boolean dfFlag = importFilename.contains(GakkenConstant.DF_MEMBER_PREFIX);
            int rawNumber = 1;

            while (csvReader.readRecord()) {
                emptyData = false;
                rawNumber++;
                BTGKA1004MemberCheckDto btgka1004MemberDto = new BTGKA1004MemberCheckDto();
                btgka1004MemberDto.setOrgId(csvReader.get("教室コード"));
                btgka1004MemberDto.setMemberCd(csvReader.get("会員コード"));
                btgka1004MemberDto.setStuFlnmNm(csvReader.get("会員姓"));
                btgka1004MemberDto.setStuFlnmLnm(csvReader.get("会員名"));
                btgka1004MemberDto.setStuFlnmKnNm(csvReader.get("会員姓（カナ）"));
                btgka1004MemberDto.setStuFlnmKnLnm(csvReader.get("会員名（カナ）"));
                btgka1004MemberDto.setSchyDiv(csvReader.get("学年"));
                btgka1004MemberDto.setBirthd(DateUtils.parse(csvReader.get("生年月日"), Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
                btgka1004MemberDto.setGendrDiv(csvReader.get("性別"));
                btgka1004MemberDto.setGuardNm(csvReader.get("保護者名（漢字）"));
                btgka1004MemberDto.setGuardKnNm(csvReader.get("保護者名（カナ）"));
                btgka1004MemberDto.setPostCd(csvReader.get("郵便番号"));
                btgka1004MemberDto.setPrefecturesCd(csvReader.get("住所（都道府県）"));
                btgka1004MemberDto.setCity(csvReader.get("住所（市区町村）"));
                btgka1004MemberDto.setAddress(csvReader.get("住所（番地以降）"));
                btgka1004MemberDto.setTelnum(csvReader.get("電話番号"));
                btgka1004MemberDto.setUrgTelnum(csvReader.get("緊急電話番号"));
                btgka1004MemberDto.setMemberStatus(csvReader.get("会員状況"));
                btgka1004MemberDto.setSch(csvReader.get("学校"));
                btgka1004MemberDto.setMailad(csvReader.get("メールアドレス"));
                btgka1004MemberDto.setTeacherNm(csvReader.get("指導者名"));
                btgka1004MemberDto.setCourse(csvReader.get("コース"));
                btgka1004MemberDto.setLoginDate(DateUtils.parse(csvReader.get("登録年月日"), Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
                btgka1004MemberDto.setMoveDate(DateUtils.parse(csvReader.get("移動年月日"), Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
                btgka1004MemberDto.setRegisterDate(DateUtils.parse(csvReader.get("入会年月日"), Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
                btgka1004MemberDto.setRecoverDate(DateUtils.parse(csvReader.get("復会年月日"), Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
                btgka1004MemberDto.setReplyCancelFlag(csvReader.get("申請取消フラグ"));
                btgka1004MemberDto.setReplyContentDiv(csvReader.get("申込内容区分"));
                //2.2.1　フィルデータの必須項目のチェック処理を行う。
                String errorMessage = BTGKA1004MemberCheckDto.validate(dfFlag, rawNumber, btgka1004MemberDto);
//                if (!StringUtils.isEmail(btgka1004MemberDto.getMailad())) {
//                    errorMessage = errorMessage + "メールアドレスのフォーマットが間違っています。";
//                }
                if (!StringUtils.isEmpty(errorMessage)) {
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                String key = btgka1004MemberDto.getOrgId() + "-" + btgka1004MemberDto.getMemberCd();
                if (mapOfMemberDto.containsKey(key)) {
                    BTGKA1004MemberDto btgka1004MemberDto1 = mapOfMemberDto.get(key);
                    btgka1004MemberDto1.setStuFlnmNm(btgka1004MemberDto.getStuFlnmNm());
                    btgka1004MemberDto1.setStuFlnmLnm(btgka1004MemberDto.getStuFlnmLnm());
                    btgka1004MemberDto1.setStuFlnmKnNm(btgka1004MemberDto.getStuFlnmKnNm());
                    btgka1004MemberDto1.setStuFlnmKnLnm(btgka1004MemberDto.getStuFlnmKnLnm());
                    btgka1004MemberDto1.setSchyDiv(btgka1004MemberDto.getSchyDiv());
                    btgka1004MemberDto1.setBirthd(btgka1004MemberDto.getBirthd());
                    btgka1004MemberDto1.setGendrDiv(btgka1004MemberDto.getGendrDiv());
                    btgka1004MemberDto1.setGuardNm(btgka1004MemberDto.getGuardNm());
                    btgka1004MemberDto1.setGuardKnNm(btgka1004MemberDto.getGuardKnNm());
                    btgka1004MemberDto1.setPostCd(btgka1004MemberDto.getPostCd());
                    btgka1004MemberDto1.setPrefecturesCd(btgka1004MemberDto.getPrefecturesCd());
                    btgka1004MemberDto1.setCity(btgka1004MemberDto.getCity());
                    btgka1004MemberDto1.setAddress(btgka1004MemberDto.getAddress());
                    btgka1004MemberDto1.setTelnum(btgka1004MemberDto.getTelnum());
                    btgka1004MemberDto1.setUrgTelnum(btgka1004MemberDto.getUrgTelnum());
                    btgka1004MemberDto1.setMemberStatus(btgka1004MemberDto.getMemberStatus());
                    btgka1004MemberDto1.setSch(btgka1004MemberDto.getSch());
                    btgka1004MemberDto1.setMailad(btgka1004MemberDto.getMailad());
                    btgka1004MemberDto1.setTeacherNm(btgka1004MemberDto.getTeacherNm());
                    btgka1004MemberDto1.setCourse(btgka1004MemberDto.getCourse());
                    btgka1004MemberDto1.setLoginDate(btgka1004MemberDto.getLoginDate());
                    btgka1004MemberDto1.setMoveDate(btgka1004MemberDto.getMoveDate());
                    btgka1004MemberDto1.setRegisterDate(btgka1004MemberDto.getRegisterDate());
                    btgka1004MemberDto1.setRecoverDate(btgka1004MemberDto.getRecoverDate());
                    btgka1004MemberDto1.setReplyCancelFlag(btgka1004MemberDto.getReplyCancelFlag());
                    btgka1004MemberDto1.setReplyContentDiv(btgka1004MemberDto.getReplyContentDiv());
                    //2.2.1.2　チェック正常の場合、処理を継続する。
                    try {
                        btgka1004TransactionBean.setBtgka1004MemberDto(btgka1004MemberDto1);
                        btgka1004TransactionBean.saveOrUpdateMemberData(orgPrefix.get().getCodValue());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        outputErrorMessage(csvReader, csvWriter, "method saveOrUpdateMemberData process failed with raw number :" + rawNumber, e.getMessage());
                    }
                } else {
                    // 2.2.2.0 > 2）　取得できない場合、下記エラーメッセージを編集する。
                    outputErrorMessage(csvReader, csvWriter, MessageUtils.getMessage("MSGCOMN0163", Integer.toString(rawNumber), "教室コード又は会員コード", "会員情報補足ファイル"), MessageUtils.getMessage("MSGCOMN0163", Integer.toString(rawNumber), "教室コード又は会員コード", "会員情報補足ファイル"));
                }
            }
            if (emptyData) {
                //入力ファイルの明細データが存在しない場合、下記メッセージをログに出力する。
                log.error(MessageUtils.getMessage("MSGCOMN0017", importFilename + "の明細"));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
            uploadErrorMessageFile(outputFilename, csvWriter, outputFile, headerLength);
            long end = System.currentTimeMillis();
            log.info("END   会員情報ファイルの導入処理:{}", end);
            log.info("会員情報ファイルの導入処理時間: {}ms", end - start);
        }
    }

    /**
     * 指導者情報ファイルの導入処理
     *
     * @param importFilename
     * @param outputFilename
     */
    private void importTeacherCsvFile(String importFilename, String outputFilename, Boolean first) {
        long start = System.currentTimeMillis();
        log.info("START 指導者情報ファイルの導入処理:{}", start);
        File file = fileMap.get(importFilename);
        if (file == null || !file.exists()) {
            //指定パスには会員情報補足ファイルが存在しない場合、下記メッセージをログに出力する。
            log.error(MessageUtils.getMessage("MSGCOMB0025", "指導者情報"));
            return;
        }
        CsvReader csvReader = null;
        CsvWriter csvWriter = null;
        File outputFile = null;
        // 空のデータフラグ
        boolean emptyData = true;
        //ログインユーザＩＤ
        String loginId = "system";

        long headerLength = 0;

        try {
            // CSV読み取りオブジェクトを作成する
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(file))), ',', StandardCharsets.UTF_8);
                //2020/11/18 9.0 huangxinliang modify end
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RRException("ファイル読み取りエラー");
            }
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
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
                log.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // リーディングヘッド
            csvReader.readHeaders();
            String headersStr = csvReader.getRawRecord() + ",\tエラーメッセージ\t";
            // ヘッダーを書き込む
            csvWriter.writeRecord(headersStr.replaceAll("\"", "").split(",", -1));
            csvWriter.flush();

            headerLength = outputFile.length();

            int rawNumber = 1;
            while (csvReader.readRecord()) {

                emptyData = false;
                rawNumber++;
                BTGKA1004ManaTeacherDto btgka1004ManaTeacherDto = new BTGKA1004ManaTeacherDto();
                btgka1004ManaTeacherDto.setManagerGidPk(csvReader.get("指導者GIDPK"));
                btgka1004ManaTeacherDto.setMgrCd(csvReader.get("指導者管理コード"));
                btgka1004ManaTeacherDto.setMgrFlnmNm(csvReader.get("指導者姓"));
                btgka1004ManaTeacherDto.setMgrFlnmLnm(csvReader.get("指導者名"));
                btgka1004ManaTeacherDto.setMgrFlnmKnNm(csvReader.get("指導者姓フリガナ"));
                btgka1004ManaTeacherDto.setMgrFlnmKnLnm(csvReader.get("指導者名フリガナ"));
                btgka1004ManaTeacherDto.setMailad(csvReader.get("メールアドレス"));
                btgka1004ManaTeacherDto.setTelnum(csvReader.get("電話番号"));
                //4.2.1　フィルデータの必須項目のチェック処理を行う。
                String errorMessage = BTGKA1004ManaTeacherDto.validate(rawNumber, btgka1004ManaTeacherDto);
                if (!StringUtils.isEmpty(errorMessage)) {
                    //チェックエラーの場合、
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                try {
                    btgka1004TransactionBean.saveOrUpdateManaTeacherData(btgka1004ManaTeacherDto, loginId);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    outputErrorMessage(csvReader, csvWriter, "method saveOrUpdateManaTeacherData process failed with raw number :" + rawNumber, e.getMessage());
                }
            }
            if (emptyData) {
                //入力ファイルの明細データが存在しない場合、下記メッセージをログに出力する。
                log.error(MessageUtils.getMessage("MSGCOMN0017", importFilename + "の明細"));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            uploadErrorMessageFile(outputFilename, csvWriter, outputFile, headerLength);
            if (csvReader != null) {
                csvReader.close();
            }
            long end = System.currentTimeMillis();
            log.info("END   指導者情報ファイルの導入処理:{}", end);
            log.info("指導者情報ファイルの導入処理時間: {}ms", end - start);
        }
    }

    /**
     * 指導者_教室所属情報ファイルの導入処理
     *
     * @param importFilename
     * @param outputFilename
     */
    private void importManaTidOrgCsvFile(String importFilename, String outputFilename, Boolean first) {
        long start = System.currentTimeMillis();
        log.info("START 指導者_教室所属情報ファイルの導入処理:{}", start);
        File file = fileMap.get(importFilename);
        if (file == null || !file.exists()) {
            //指定パスには会員情報補足ファイルが存在しない場合、下記メッセージをログに出力する。
            log.error(MessageUtils.getMessage("MSGCOMB0025", "指導者_教室所属情報"));
            return;
        }
        CsvReader csvReader = null;
        CsvWriter csvWriter = null;
        File outputFile = null;
        // 空のデータフラグ
        boolean emptyData = true;
        //ログインユーザＩＤ
        String loginId = "system";

        long headerLength = 0;

        try {
            // CSV読み取りオブジェクトを作成する
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(file))), ',', StandardCharsets.UTF_8);
                //2020/11/18 9.0 huangxinliang modify end
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RRException("ファイル読み取りエラー");
            }
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
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
                log.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // リーディングヘッド
            csvReader.readHeaders();

            String headersStr = csvReader.getRawRecord() + ",\tエラーメッセージ\t";
            // ヘッダーを書き込む
            csvWriter.writeRecord(headersStr.replaceAll("\"", "").split(",", -1));

            csvWriter.flush();

            headerLength = outputFile.length();

            int rawNumber = 1;

            List<BTGKA1004UsrDto> mstUsrEntities = btgka1004Dao.selectAllTeacher();
            Map<String, Map<String, BTGKA1004UsrDto>> mapOfGidkpAndOrgId2Usr = new HashMap<>(8);
            Map<String, Map<String, BTGKA1004UsrDto>> mapOfTchCdAndOrgId2Usr = new HashMap<>(8);
            mstUsrEntities.forEach(mstUsrEntity -> {
                if (StringUtils.isEmpty(mstUsrEntity.getGidpk()) && StringUtils.isEmpty(mstUsrEntity.getTchCd())) {
                    return;
                }
                if (StringUtils.isEmpty(mstUsrEntity.getTchCd())) {
                    if (mapOfGidkpAndOrgId2Usr.containsKey(mstUsrEntity.getGidpk())) {
                        Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr = mapOfGidkpAndOrgId2Usr.get(mstUsrEntity.getGidpk());
                        String orgId = StringUtils.isEmpty(mstUsrEntity.getOrgId()) ? null : mstUsrEntity.getOrgId();
                        if (mapOfOrgId2Usr.containsKey(orgId)) {
                            log.error("同じメンターに対して同じ組織の2つのデータが存在します。");
                        } else {
                            mapOfOrgId2Usr.put(orgId, mstUsrEntity);
                        }
                    } else {
                        Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr = new HashMap<>(8);
                        String orgId = StringUtils.isEmpty(mstUsrEntity.getOrgId()) ? null : mstUsrEntity.getOrgId();
                        mapOfOrgId2Usr.put(orgId, mstUsrEntity);
                        mapOfGidkpAndOrgId2Usr.put(mstUsrEntity.getGidpk(), mapOfOrgId2Usr);
                    }
                } else {
                    if (mapOfTchCdAndOrgId2Usr.containsKey(mstUsrEntity.getTchCd())) {
                        Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr = mapOfTchCdAndOrgId2Usr.get(mstUsrEntity.getTchCd());
                        String orgId = StringUtils.isEmpty(mstUsrEntity.getOrgId()) ? null : mstUsrEntity.getOrgId();
                        if (mapOfOrgId2Usr.containsKey(orgId)) {
                            log.error("同じメンターに対して同じ組織の2つのデータが存在します。");
                        } else {
                            mapOfOrgId2Usr.put(orgId, mstUsrEntity);
                        }
                    } else {
                        Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr = new HashMap<>(8);
                        String orgId = StringUtils.isEmpty(mstUsrEntity.getOrgId()) ? null : mstUsrEntity.getOrgId();
                        mapOfOrgId2Usr.put(orgId, mstUsrEntity);
                        mapOfTchCdAndOrgId2Usr.put(mstUsrEntity.getTchCd(), mapOfOrgId2Usr);
                    }
                }
            });

            while (csvReader.readRecord()) {
                emptyData = false;
                try {
                    if (first) {
                        first = false;
                        //管理者基本マスタデータを削除する。※論理削除
                        btgka1004Dao.deleteTeacherLogic();
                        //ユーザ基本マスタデータを削除する。※論理削除
                        btgka1004Dao.deleteTeacherUsrLogic();
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("論理削除が失敗しました。");
                }
                rawNumber++;
                BTGKA1004ManaTidOrgDto btgka1004ManaTidOrgDto = new BTGKA1004ManaTidOrgDto();
                btgka1004ManaTidOrgDto.setManagerGidPk(csvReader.get("指導者GIDPK"));
                btgka1004ManaTidOrgDto.setMgrCd(csvReader.get("指導者管理コード"));
                btgka1004ManaTidOrgDto.setOrgCd(csvReader.get("組織コード"));
                //5.2.1　フィルデータの必須項目のチェック処理を行う。
                String errorMessage = BTGKA1004ManaTidOrgDto.validate(rawNumber, btgka1004ManaTidOrgDto);
                if (!StringUtils.isEmpty(errorMessage)) {
                    //チェックエラーの場合、
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                //指導者_教室情報．組織IDをもとに、下記条件で組織マスタから組織情報を取得する。
                Integer level = btgka1004Dao.selectOrgLevelByOrgId(orgPrefix.get().getCodValue() + btgka1004ManaTidOrgDto.getOrgCd());
                if (level == null){
                    //取得できない場合、下記メッセージをエラーファイルに出力する。
                    //｛MSGCOMB0005：｛{0}の{1}が存在しません。｝,レコードの行番号,'組織'｝
                    errorMessage = MessageUtils.getMessage("MSGCOMB0005", rawNumber + "行目", "組織");
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //下記処理をスキップして、次データを読込む。
                    continue;
                }
                // 2021/1/6 huangxinliang modify start
                if (level != (classRoomLevel.get() + 2)){
                // 2021/1/6 huangxinliang modify end
                    //取得した当教室階層　≠　取得したコードマスタ明細．コード値の場合、下記エラーデータをエラーファイルに出力する。
                    //｛MSGCOMB0027：｛{0}行目の{1}は｛2｝ではありません。｝,レコードの行番号,'組織','教室'｝
                    errorMessage = MessageUtils.getMessage("MSGCOMB0027", Integer.toString(rawNumber), "組織", "教室");
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //下記処理をスキップして、次データを読込む。
                    continue;
                }
                if (StringUtils.isEmpty(btgka1004ManaTidOrgDto.getMgrCd())) {
                    //指導者_教室所属情報．指導者GIDPKある場合、ファイルデータより下記テーブルのデータを作成する。
                    if (mapOfGidkpAndOrgId2Usr.containsKey(btgka1004ManaTidOrgDto.getManagerGidPk())) {
                        //gidpkが存在します
                        Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr = mapOfGidkpAndOrgId2Usr.get(btgka1004ManaTidOrgDto.getManagerGidPk());
                        handleTidOrgData(csvReader, csvWriter, loginId, rawNumber, btgka1004ManaTidOrgDto, mapOfOrgId2Usr);
                    } else {
                        //gidpkは存在しません
                        errorMessage = MessageUtils.getMessage("MSGCOMB0024", Integer.toString(rawNumber), "指導者GIDPK", "管理者基本マスタ");
                        outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    }
                } else {
                    //指導者_教室所属情報．指導者管理コードある場合、ファイルデータより下記テーブルのデータを作成する。
                    if (mapOfTchCdAndOrgId2Usr.containsKey(btgka1004ManaTidOrgDto.getMgrCd())) {
                        //gidpkが存在します
                        Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr = mapOfTchCdAndOrgId2Usr.get(btgka1004ManaTidOrgDto.getMgrCd());
                        handleTidOrgData(csvReader, csvWriter, loginId, rawNumber, btgka1004ManaTidOrgDto, mapOfOrgId2Usr);
                    } else {
                        //gidpkは存在しません
                        errorMessage = MessageUtils.getMessage("MSGCOMB0024", Integer.toString(rawNumber), "指導者管理コード", "管理者基本マスタ");
                        outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            uploadErrorMessageFile(outputFilename, csvWriter, outputFile, headerLength);
            if (csvReader != null) {
                csvReader.close();
            }
            if (emptyData) {
                //入力ファイルの明細データが存在しない場合、下記メッセージをログに出力する。
                log.error(MessageUtils.getMessage("MSGCOMN0017", importFilename + "の明細"));
            }
            long end = System.currentTimeMillis();
            log.info("END   指導者_教室所属情報ファイルの導入処理:{}", end);
            log.info("指導者_教室所属情報ファイルの導入処理時間: {}ms", end - start);
        }

    }

    /**
     * 組織情報ファイルの導入処理を行う。（全量導入、ファイル数：１）
     *
     * @param importFilename
     * @param outputFilename
     */
    private void importManaOrgCsvFile(String importFilename, String outputFilename, Boolean first) {
        long start = System.currentTimeMillis();
        log.info("START 組織情報ファイルの導入処理:{}", start);
        //ログインユーザＩＤ
        String loginId = "system";
        //3.1.3　Web入会から導入するブランドの初期管理者を作成する。
        MstOrgEntity brandOrgEntity = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().eq("system_kbn", "2").eq("del_flg", 0).
                eq("org_id", orgPrefix.get().getCodValue()));
        if (brandOrgEntity == null) {
            //②　取得できない場合、組織マスタなどにブランドデータを登録する。
            btgka1004TransactionBean.saveBrandData(loginId, orgPrefix.get().getCodValue(), orgPrefix.get().getCodValue3());
        }else {
            if (brandOrgEntity.getLevel() != 1){
                brandOrgEntity.setLevel(1);
                mstOrgDao.updateById(brandOrgEntity);
            }
        }
        File file = fileMap.get(importFilename);
        if (file == null || !file.exists()) {
            //指定パスには会員情報補足ファイルが存在しない場合、下記メッセージをログに出力する。
            log.error(MessageUtils.getMessage("MSGCOMB0025", "組織情報"));
            return;
        }
        CsvReader csvReader = null;
        CsvWriter csvWriter = null;
        File outputFile = null;
        // 空のデータフラグ
        boolean emptyData = true;

        long headerLength = 0;

        try {
            // CSV読み取りオブジェクトを作成する
            try {
                //2020/11/18 9.0 huangxinliang modify start
                csvReader = new CsvReader(new BufferedInputStream(new BOMInputStream(new FileInputStream(file))), ',', StandardCharsets.UTF_8);
                //2020/11/18 9.0 huangxinliang modify end
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RRException("ファイル読み取りエラー");
            }
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
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
                log.error(e.getMessage());
                throw new RRException("ファイル書き込みエラー");
            }
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // リーディングヘッド
            csvReader.readHeaders();

            String headersStr = csvReader.getRawRecord() + ",\tエラーメッセージ\t";
            // ヘッダーを書き込む
            csvWriter.writeRecord(headersStr.replaceAll("\"", "").split(",", -1));

            csvWriter.flush();

            headerLength = outputFile.length();

            int rawNumber = 1;

            while (csvReader.readRecord()) {
                emptyData = false;
                if (first) {
                    first = false;
                    // 2020/12/25 huangxinliang modify start
                    Integer maxLevel = btgka1004Dao.getMaxLevel();
                    // 2020/12/25 huangxinliang modify end
                    //旧FCと直営組織を削除する。　※物理削除（教室の直近上１層）
                    try {
                        // 2020/12/25 huangxinliang modify start
                        mstOrgDao.delete(new QueryWrapper<MstOrgEntity>().eq("level", maxLevel - 1).eq("system_kbn", "2"));
                        // 2020/12/25 huangxinliang modify end
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new RRException("物理は組織の主データを削除することに失敗する。");
                    }
                    //組織マスタデータを削除する。　※論理削除
                    MstOrgEntity mstOrgEntity1 = new MstOrgEntity();
                    mstOrgEntity1.setDelFlg(1);

                    UpdateWrapper<MstOrgEntity> updateWrapper = new UpdateWrapper<MstOrgEntity>().eq("system_kbn", "2").eq("del_flg", 0).
                            ne("org_id", orgPrefix.get().getCodValue());
                    if (StringUtils.equals("0", webUporgInFlgReference.get())) {
                        // 2020/12/25 huangxinliang modify start
                        updateWrapper.eq("level", maxLevel);
                        // 2020/12/25 huangxinliang modify end
                    }
                    try {
                        mstOrgDao.update(mstOrgEntity1, updateWrapper);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new RRException("論理は組織の主データを削除することに失敗する。");
                    }
                }
                rawNumber++;
                BTGKA1004ManaOrgDto btgka1004ManaOrgDto = new BTGKA1004ManaOrgDto();
                btgka1004ManaOrgDto.setOrgCd(csvReader.get("組織コード"));
                btgka1004ManaOrgDto.setOrgNm(csvReader.get("組織名"));
                try {
                    btgka1004ManaOrgDto.setLevel(StringUtils.isEmpty(csvReader.get("階層コード")) ? null : Integer.parseInt(csvReader.get("階層コード")));
                }catch (NumberFormatException e){
                    //チェックエラーの場合、
                    outputErrorMessage(csvReader, csvWriter, e.getMessage(), e.getMessage());
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                btgka1004ManaOrgDto.setUpplevOrgId(csvReader.get("上位所属組織コード"));
                btgka1004ManaOrgDto.setDiv(csvReader.get("所属区分"));
                btgka1004ManaOrgDto.setWebUseFlg(csvReader.get("Web申込利用フラグ"));

                //3.2.1　フィルデータの必須項目のチェック処理を行う。
                String errorMessage = BTGKA1004ManaOrgDto.validate(rawNumber, btgka1004ManaOrgDto);

                if (!StringUtils.isEmpty(errorMessage)) {
                    //チェックエラーの場合、
                    outputErrorMessage(csvReader, csvWriter, errorMessage, errorMessage);
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                try {
                    // 2020/12/25 huangxinliang modify start
                    //組織情報．階層コード＝0の場合、
                    if (btgka1004ManaOrgDto.getLevel() == 0){
                        //当データをスキップして、次データを読込む
                        continue;
                    }
                    // 2020/12/25 huangxinliang modify end
                    //取得した上位組織導入可否フラグ＝「1：可」の場合 || 組織情報．階層コード　＝　取得したWeb申込教室階層コードの場合
                    if (StringUtils.equals("1", webUporgInFlgReference.get()) || btgka1004ManaOrgDto.getLevel().equals(classRoomLevel.get())){
                        //3.2.2　ファイルデータより下記テーブルのデータを作成する。
                        MstOrgEntity mstOrgEntity2 = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().eq("system_kbn", "2").eq("del_flg", 1).
                                eq("org_id", orgPrefix.get().getCodValue() + btgka1004ManaOrgDto.getOrgCd()));
                        btgka1004TransactionBean.saveOrUpadteOrgData(btgka1004ManaOrgDto, mstOrgEntity2, loginId, orgPrefix.get().getCodValue(),
                                classRoomLevel.get() <= btgka1004ManaOrgDto.getLevel());
                    }
                    //組織情報．階層コード　＝　取得したWeb申込教室階層コード　-　1の場合、教室直近上層のFCと直営組織を作成する。
                    if (btgka1004ManaOrgDto.getLevel() == classRoomLevel.get() - 1){
                        btgka1004TransactionBean.saveFcOrUpOrgData(btgka1004ManaOrgDto, loginId, orgPrefix.get().getCodValue());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    // 2020/12/28 huangxinliang modify start
                    outputErrorMessage(csvReader, csvWriter, e.getMessage(), "組織マスター登録または更新失敗");
                    // 2020/12/28 huangxinliang modify end
                }
            }
            MstOrgEntity mstOrgEntityForUpdate = new MstOrgEntity();
            mstOrgEntityForUpdate.setMgrFlg("1");
            try {
                mstOrgDao.update(mstOrgEntityForUpdate, new QueryWrapper<MstOrgEntity>().le("level", classRoomLevel.get() + 2).eq("system_kbn", "2").eq("del_flg", 0));
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RRException("組織マスターマークの更新に失敗");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            uploadErrorMessageFile(outputFilename, csvWriter, outputFile, headerLength);
            if (csvReader != null) {
                csvReader.close();
            }
            if (emptyData) {
                //入力ファイルの明細データが存在しない場合、下記メッセージをログに出力する。
                log.error(MessageUtils.getMessage("MSGCOMN0017", importFilename + "の明細"));
            }
            long end = System.currentTimeMillis();
            log.info("END   組織情報ファイルの導入処理:{}", end);
            log.info("組織情報ファイルの導入処理時間: {}ms", end - start);
        }
    }

    /**
     * エラー情報ファイルをアップロードする
     *
     * @param outputFilename
     * @param csvWriter
     * @param outputFile
     * @param headerLength
     */
    private void uploadErrorMessageFile(String outputFilename, CsvWriter csvWriter, File outputFile, long headerLength) {
        if (csvWriter != null) {
            csvWriter.close();
            if (outputFile.length() > headerLength) {
                if (!StringUtils.isEmpty(OUTPUT_CSV_FILE_PATH.get().getCodValue2())) {
                    sftpClient2.upLoadFile(outputFile, OUTPUT_CSV_FILE_PATH.get().getCodValue());
                } else {
                    File outFile = new File(OUTPUT_CSV_FILE_PATH.get().getCodValue() + File.separator + outputFilename);
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
            }
            outputFile.delete();
        }
    }

    private static String[] appendElement(String[] original,
                                          String element) {
        int length = original.length;
        String[] destination = new String[length + 1];
        System.arraycopy(original, 0, destination, 0, length);
        destination[length] = element;
        return destination;
    }

    /**
     * エラーメッセージを出力
     *
     * @param csvReader     csv読み取り
     * @param csvWriter     csv書き込み
     * @param errorMessage  ログ出力のメッセージ
     * @param errorMessage2 csvファイル出力のメッセージ
     * @throws IOException
     */
    private void outputErrorMessage(CsvReader csvReader, CsvWriter csvWriter, String errorMessage, String errorMessage2) throws IOException {
        log.error(errorMessage);
        String rawData = csvReader.getRawRecord();
        String[] strings = appendElement(rawData.replaceAll("\"", "").split(",", -1), errorMessage2);
        csvWriter.writeRecord(strings);
        csvWriter.flush();
    }

    /**
     * 管理者と組織のプロセスデータ
     *
     * @param csvReader              csv読み取り
     * @param csvWriter              csv書き込み
     * @param loginId                ログインID
     * @param rawNumber              データ行の数
     * @param btgka1004ManaTidOrgDto データエンティティクラス
     * @param mapOfOrgId2Usr         組織とユーザーデータ間のマップ
     * @throws IOException
     */
    private void handleTidOrgData(CsvReader csvReader, CsvWriter csvWriter, String loginId, int rawNumber, BTGKA1004ManaTidOrgDto btgka1004ManaTidOrgDto, Map<String, BTGKA1004UsrDto> mapOfOrgId2Usr) throws IOException {
        String key = orgPrefix.get().getCodValue() + btgka1004ManaTidOrgDto.getOrgCd();
        if (mapOfOrgId2Usr.containsKey(key)) {
            btgka1004Dao.activeTeacherByUserId(mapOfOrgId2Usr.get(key).getUsrId());
            btgka1004Dao.activeTeacherUserByUserId(mapOfOrgId2Usr.get(key).getUsrId());
            //組織IDが存在します
            mapOfOrgId2Usr.remove(key);
        } else {
            try {
                if (mapOfOrgId2Usr.containsKey(null)) {
                    //初めてインポートされた管理者データ
                    btgka1004TransactionBean.updateManaTidOrgData(btgka1004ManaTidOrgDto, mapOfOrgId2Usr.get(null), loginId, orgPrefix.get().getCodValue());
                    btgka1004Dao.activeTeacherByUserId(mapOfOrgId2Usr.get(null).getUsrId());
                    btgka1004Dao.activeTeacherUserByUserId(mapOfOrgId2Usr.get(null).getUsrId());
                    mapOfOrgId2Usr.remove(null);
                } else {
                    //新しい組織データ
                    List<BTGKA1004UsrDto> btgka1004UsrDtos = btgka1004Dao.managerCheck(btgka1004ManaTidOrgDto.getMgrCd(), btgka1004ManaTidOrgDto.getManagerGidPk());
                    if (btgka1004UsrDtos.size() > 0) {
                        btgka1004TransactionBean.saveManaTidOrgData(btgka1004ManaTidOrgDto, btgka1004UsrDtos.get(0), loginId, orgPrefix.get().getCodValue());
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                outputErrorMessage(csvReader, csvWriter, "method saveManaTidOrgData process failed with raw number :" + rawNumber, e.getMessage());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updatePassword(){
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SWITCH_FOR_UPDATE_PASSWORD"));
        if (mstCodDEntity != null){
            List<MstUsrEntity> mstUsrEntities = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>()
                    .select("id, usr_id")
                    .eq("pw_up_flg", "0")
                    .eq("spec_auth_flg", "0")
                    .eq("system_kbn", "1"));
            mstUsrEntities.forEach(userEntity -> {
                String usrId = userEntity.getUsrId();
                userEntity.setUsrPassword(ShiroUtils.sha256(ShiroUtils.stringToAscii(usrId), usrId));
                mstUsrDao.updateById(userEntity);
            });
            return R.ok();
        }else {
            return R.error("404 not found");
        }
    }
}
