package jp.learningpark.modules.job.service.impl;
import jp.learningpark.framework.utils.GoogleSheetApiUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import com.google.common.io.Files;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.MstMaxIdService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.job.dao.BTGKA1006Dao;
import jp.learningpark.modules.job.service.BTGKA1006Service;
import jp.learningpark.modules.manager.dao.F00004Dao;
import jp.learningpark.modules.manager.dto.F00004Dto;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)lgx
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/8/24 ： NWT)lgx ： 新規作成
 * @date 2020/12/24 12:18
 */
@Service
public class BTGKA1006ServiceImpl implements BTGKA1006Service {

    /**
     * マスタ_明細Dao
     */
    @Autowired
    MstCodDDao mstCodDDao;
    /**
     * ユーザ基本マスタDao
     */
    @Autowired
    MstUsrDao mstUsrDao;
    /**
     * 生徒基本マスタDao
     */
    @Autowired
    MstStuDao mstStuDao;
    /**
     * btgka1006Dao
     */
    @Autowired
    private BTGKA1006Dao btgka1006Dao;
    /**
     * f00004Dao
     */
    @Autowired
    F00004Dao f00004Dao;
    /**
     * ユーザ初期パスワード管理Dao
     */
    @Autowired
    MstUsrFirtPwDao mstUsrFirtPwDao;
    /**
     * MAX採番 Service
     */
    @Autowired
    MstMaxIdService mstMaxIdService;
    /**
     * sysUserRoleDao
     */
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    /**
     * mstNumassDao
     */
    @Autowired
    MstNumassDao mstNumassDao;
    /**
     * mstNumassDao
     */
    @Autowired
    MstUsrService mstUsrService;

    @Autowired
    BTGKA1006ServiceImpl btgka1006Service;
    /**
     * 出力ファイルのパス
     */
    private static final AtomicReference<MstCodDEntity> OUTPUT_CSV_FILE_PATH = new AtomicReference<>();
    File outputFile = null;
    CsvWriter csvWriter = null;
    SftpClient sftpClient = null;
    int maxIdOfStu;
    private static final Logger log = LoggerFactory.getLogger(BTGKA1006ServiceImpl.class);

    @Override
    public R importDateFromGoogleSheet() {
        log.info("START GoogleSheetApi");
        //システム時刻
        String today = DateUtils.format(DateUtils.addDays(DateUtils.getSysDate(), 0), Constant.DATE_FORMAT_YYYYMMDD);
        //エラー CSVファイル物理名
        String googleSheetFilePath = GakkenConstant.ERR_PREFIX + "sogaku_" + today + GakkenConstant.CSV_FILE_SUFFIX;
        // 1.1 GoogleSheetAPI使ってために、下記条件でコードマスタ_明細から連携情報を取得する。
        MstCodDEntity mstCodGoogleParameter = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SOUGAKU_GOOGLESHEET").eq("del_flg", 0).last("limit 1"));
        if(mstCodGoogleParameter == null){
            log.error("パラメータが空白です。");
            return R.error(MessageUtils.getMessage("MSGCOMN0035", "GoogleSheetAPI"));
        }
        if (StringUtils.isEmpty(mstCodGoogleParameter.getCodValue())){
            log.error("パラメータ（account）が空白です。");
            return R.error(MessageUtils.getMessage("MSGCOMN0035", "GoogleSheetAPI"));
        }
        if (StringUtils.isEmpty(mstCodGoogleParameter.getCodValue2())){
            log.error("パラメータ（URL）が空白です。");
            return R.error(MessageUtils.getMessage("MSGCOMN0035", "GoogleSheetAPI"));
        }
        if (StringUtils.isEmpty(mstCodGoogleParameter.getCodValue3())){
            log.error("パラメータ（range）が空白です。");
            return R.error(MessageUtils.getMessage("MSGCOMN0035", "GoogleSheetAPI"));
        }
        // 1.2 チェックエラーデータを出力するために、マスタ_明細からエラー出力ファイルのパスを取得する。
        MstCodDEntity mstCodErrorFlie = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SOUGAKU_OUT_FILE_PATH").eq("del_flg", 0).last("limit 1"));
        if(mstCodErrorFlie == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0035", "ファイルパス"));
        }
        mstCodErrorFlie.setCodValue(mstCodErrorFlie.getCodValue() + '/' + today);
        OUTPUT_CSV_FILE_PATH.set(mstCodErrorFlie);
        // 2.1 上記1.1取得したデータをパラメータとして、googleSheetAPIのアクセスを行う。
        List<List<Object>> googleSheetdata = new ArrayList<>() ;
        try {
            log.info("START GoogleApi");
            googleSheetdata = GoogleSheetApiUtils.googleSheet(mstCodGoogleParameter.getCodValue(),mstCodGoogleParameter.getCodValue2(),mstCodGoogleParameter.getCodValue3());
        }
        catch ( Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            log.error(sw.toString().split("\n")[0]);
            //googleSheetAPIのアクセスが成功しない場合、下記メッセージをログに出力する。
            return R.error(MessageUtils.getMessage("MSGCOMB0025", "GoogleSheet"));
        }
        if(googleSheetdata==null){
            log.error("Googlesheetが取ったデータは空です。");
            //googleSheetAPIのアクセスが成功しない場合、下記メッセージをログに出力する。
            return R.error(MessageUtils.getMessage("MSGCOMB0025", "GoogleSheet"));
        }
        // 2.1.1 以前の創学高等部のデータを全部論理的削除
        try {
            //ユーザ基本マスタデータを削除する。※論理削除
            btgka1006Dao.delMstUsrByLogic();
            //生徒基本マスタデータを削除する。※論理削除
            btgka1006Dao.delMstStuByLogic();
        }catch (Exception e){
            e.printStackTrace();
            log.error("理論的削除の失敗");
        }
        long lenth = 0L;
        try {
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), googleSheetFilePath);
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
        try {
            for (int i = 0; i < googleSheetdata.size(); i++) {
                //生涯番号が空白の場合
                if (googleSheetdata.get(i).size() < 16 || StringUtils.isEmpty(googleSheetdata.get(i).get(15).toString().trim())) {
                    int line = i + 2;
                    String errorMessage = MessageUtils.getMessage("MSGCOMN0063", "" + line + "", "生涯番号");
                    //チェックエラーの場合
                    try {
                        outputErrorMessage(csvWriter, errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                //所属組織ID存在チェック
                String[] orgId = googleSheetdata.get(i).get(12).toString().split(",", -1);
                boolean existence = true;
                String errorOrg = "";
                for (String orgNum : orgId) {
                    String exisNum = btgka1006Dao.orgIdExistenceCheck(orgNum);
                    if (StringUtils.isEmpty(exisNum)) {
                        existence = false;
                        errorOrg += orgNum + ",";
                    }
                }
                if (!existence) {
                    //チェックエラーの場合
                    try {
                        int line = i + 2;
                        if (!StringUtils.isEmpty(errorOrg)) {
                            errorOrg = errorOrg.substring(0, errorOrg.length() - 1);
                        }
                        String errorMessage = MessageUtils.getMessage("MSGCOMN0165", "" + line + "", errorOrg);
                        outputErrorMessage(csvWriter, errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //3）　下記処理をスキップして、次データの処理を行う。
                    continue;
                }
                //生涯番号と所属組織IDをもとに、存在チェック行う。
                for (String orgValue : orgId) {
                    //生涯番号
                    Integer careerId = Integer.parseInt(googleSheetdata.get(i).get(15).toString());
                    //ユーザ基本マスタ．ユーザID
                    String userId = btgka1006Dao.getUser(orgValue, careerId);
                    try {
                        btgka1006Service.saveOrUpdate(googleSheetdata, i, orgValue, userId);
                    } catch (Exception ex) {
                        int errorLine = i + 2;
                        String errorMessage = "" + errorLine + "" + "行目の" + ex.getMessage();
                        outputErrorMessage(csvWriter, errorMessage);
                        continue;
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        log.info("メッセージをログに出力する。");
        //メッセージをログに出力する。
        uploadErrorMessageFile(googleSheetFilePath, csvWriter, outputFile, lenth);
        log.info("END GoogleSheetApi");
        return R.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
    public void saveOrUpdate(List<List<Object>> googleSheetdata, int i, String orgValue, String userId) {
        if (StringUtils.isEmpty(userId)){
            //ア-1 生徒MAX番号取得
            MstNumassEntity mstNumassEntity = new MstNumassEntity();
            Map<String, Integer> maxId = getMaxId();
            getStuMaxId(maxId, mstNumassEntity);
            // 新規登録
            maxIdOfStu++;
            // ア-2 ユーザログインPWの設定について
            String psw = ShiroUtils.sha256(ShiroUtils.stringToAscii("s" + maxIdOfStu), "s" + maxIdOfStu);
            //ア-3 ユーザ基本マスタに登録
            usrInsert(googleSheetdata.get(i),maxIdOfStu, psw , orgValue);
            //ア-4 生徒基本マスタに登録
            stuInsert(googleSheetdata.get(i),maxIdOfStu);
            //ア-5 ユーザ初期パスワード管理に登録
            insertUsrFirtPw("s" + maxIdOfStu,psw);
            //ア-6 ユーザーキャラ登録
            insertSysUsrRole("s" + maxIdOfStu);
            //処理完了後、採番マスタに最大MAXIDを更新する
            MstNumassEntity updateMst = new MstNumassEntity();
            //更新日時
            updateMst.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            updateMst.setUpdUsrId("BTGKA1006");
            setStuMaxId(updateMst, maxIdOfStu);
        } else{
            // ユーザ基本マスタ、それぞれの基本情報マスタテーブルに更新処理を行う。
            usrUpdate(googleSheetdata.get(i),userId,orgValue);
            stuUpdate(googleSheetdata.get(i),userId);
        }
    }
    /**
     * エラーメッセージを出力
     */
    private void outputErrorMessage(CsvWriter csvWriter, String errorMessage) throws IOException {
        log.error(errorMessage);
        String[] strings  = {errorMessage};
        csvWriter.writeRecord(strings);
        csvWriter.flush();
    }
    private Map<String, Integer> getMaxId() {
        //MaxId
        List<F00004Dto> f00004Dtos = f00004Dao.selectMax(null);
        Map<String, Integer> maxIdMap = new HashMap<>();
        for (F00004Dto dto : f00004Dtos) {
            if (dto.getMax() != null) {
                maxIdMap.put(dto.getRoleDiv().trim(), dto.getMax());
            }
        }
        return maxIdMap;
    }
    /**
     * 採番マスタ取得
     */
    private Integer getStuMaxId(Map<String, Integer> maxIdMap, MstNumassEntity mstNumassEntity) {
        if (maxIdMap.get("4") == null) {
            //ロール区分
            mstNumassEntity.setRoleDiv("4");
            //MAXID
            mstNumassEntity.setMaxId("s1");
            mstNumassDao.insert(mstNumassEntity);
            maxIdOfStu = 0;
        } else {
            maxIdOfStu = maxIdMap.get("4");
        }
        return maxIdOfStu;
    }
    /**
     * エラー情報ファイルをアップロードする
     */
    private void uploadErrorMessageFile(String outputFilename, CsvWriter csvWriter, File outputFile, long headerLength) {
        log.info("START メッセージをログに出力する。");
        if (csvWriter != null) {
            csvWriter.close();
            if (outputFile.length() > headerLength) {
                File outFile = new File(OUTPUT_CSV_FILE_PATH.get().getCodValue() + File.separator + outputFilename);
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
            }
            outputFile.delete();
        }
    /**
     * 採番マスタ更新
     */
    public void setStuMaxId(MstNumassEntity updateMst, Integer maxIdOfStu) {
        updateMst.setMaxId("s" + maxIdOfStu);
        mstNumassDao.update(updateMst, new QueryWrapper<MstNumassEntity>().and(w -> w.eq("role_div", "4").eq("del_flg", 0)));
    }
    /**
     * ユーザ基本マスタに登録
     */
    public  void  usrInsert( List<Object> data,int maxIdOfStu,String psw ,String orgValue){
        MstUsrEntity mstUsrEntity = new MstUsrEntity();
        //ユーザID
        mstUsrEntity.setUsrId("s" + maxIdOfStu);
        // ユーザログインPW
        mstUsrEntity.setUsrPassword(psw);
        // 初回登録フラグ
        mstUsrEntity.setFstLoginFlg("0");
        // ユーザ名
        mstUsrEntity.setUsrNm(data.get(3).toString()+"　"+data.get(4).toString());
        // ロール区分
        mstUsrEntity.setRoleDiv("4");
        // 組織ID
        mstUsrEntity.setOrgId(orgValue);
        // PW更新フラグ
        mstUsrEntity.setPwUpFlg("0");
        // ユーザステータス
        mstUsrEntity.setUsrSts("1");
        // 特殊権限フラグ
        mstUsrEntity.setSpecAuthFlg("0");
        // 変更後ユーザーID
        mstUsrEntity.setAfterUsrId(data.get(1).toString());
        // エラー回数
        mstUsrEntity.setErrorCount(0);
        // ロックフラグ
        mstUsrEntity.setLockFlg("0");
        // 学研IDプライマリキー
        mstUsrEntity.setGidpk(null);
        // GIDフラグ
        mstUsrEntity.setGidFlg(data.get(13).toString());
        // 他システム区分
        mstUsrEntity.setSystemKbn("3");
        // 組織共用キー
        mstUsrEntity.setOrgCommKey(orgValue + "_key");
        //所属組織フラグ
        mstUsrEntity.setOwnerOrgFlg("1");
        // GakkenID規約フラグ
        mstUsrEntity.setGidRuleFlg("0");
        // マナミル規約フラグ
        mstUsrEntity.setManaRuleFlg("0");
        // 個人情報保護規約フラグ
        mstUsrEntity.setPerlInfoRuleFlg("0");
        // 自分修正可否フラグ
        mstUsrEntity.setSafModifyFlg("0");
        // 管理者修正可否フラグ
        mstUsrEntity.setMgrModifyFlg("0");
        // 作成日時
        mstUsrEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ
        mstUsrEntity.setCretUsrId("BTGKA1006");
        // 更新日時
        mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ
        mstUsrEntity.setUpdUsrId("BTGKA1006");
        // 削除フラグ
        if (data.get(14).toString().equals("1")){
            mstUsrEntity.setDelFlg(0);
        }else {
            mstUsrEntity.setDelFlg(1);
        }
        try {
            mstUsrDao.insert(mstUsrEntity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ユーザ基本マスタデータがDBへ登録失敗した/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
            throw new RRException("ユーザ基本マスタデータがDBへ登録失敗した/[DBセット]シートのDBセット名[ユーザ基本マスタ登録]");
        }
    }
    /**
     * 生徒基本マスタ登録
     */
    public  void  stuInsert( List<Object> data,int maxIdOfStu){
        MstStuEntity mstStuEntity = new MstStuEntity();
        //[DBセット]シートのDBセット名[生徒基本マスタ登録]
        mstStuEntity.setId(null);
        //生徒ID
        mstStuEntity.setStuId("s" +maxIdOfStu);
        //学校名
        mstStuEntity.setSch(data.get(10).toString());
        //保護者ID
        mstStuEntity.setGuardId(null);
        //保護者1ID
        mstStuEntity.setGuard1Id(null);
        //保護者2ID
        mstStuEntity.setGuard2Id(null);
        //保護者3ID
        mstStuEntity.setGuard3Id(null);
        //保護者4ID
        mstStuEntity.setGuard4Id(null);
        //姓名_姓
        mstStuEntity.setFlnmNm(data.get(3).toString());
        //姓名_名
        mstStuEntity.setFlnmLnm(data.get(4).toString());
        //姓名_カナ姓
        mstStuEntity.setFlnmKnNm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(data.get(5).toString()));
        //姓名_カナ名
        mstStuEntity.setFlnmKnLnm(HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(data.get(6).toString()));
        //性別区分
        mstStuEntity.setGendrDiv(data.get(8).toString());
        //生年月日
        mstStuEntity.setBirthd(DateUtils.parse(data.get(7).toString(),Constant.DATE_FORMAT_YYYYMMDD));
        //写真パス
        mstStuEntity.setPhotPath(null);
        //学年区分
        mstStuEntity.setSchyDiv(data.get(9).toString());
        //ＱＲコード
        mstStuEntity.setQrCod(null);
        //オリジナルCD
        mstStuEntity.setOriaCd(data.get(11).toString());
        //英語氏名
        mstStuEntity.setEnglishNm(null);
        //通塾曜日区分
        mstStuEntity.setDayweekDiv(null);
        //メモ
        mstStuEntity.setMemoCont(null);
        //担当者氏名
        mstStuEntity.setResptyNm(null);
        //習い事
        mstStuEntity.setStudyCont(null);
        //得意科目区分
        mstStuEntity.setGoodSubjtDiv(null);
        //不得意科目区分
        mstStuEntity.setNogoodSubjtDiv(null);
        //希望職種
        mstStuEntity.setHopeJobNm(null);
        //希望大学
        mstStuEntity.setHopeUnityNm(null);
        //希望学部学科
        mstStuEntity.setHopeLearnSub(null);
        //特記事項
        mstStuEntity.setSpecCont(null);
        //会員コード
        mstStuEntity.setMemberCd(null);
        //コース
        mstStuEntity.setCourseId(null);
        //入会年月日
        mstStuEntity.setAdmissionDate(null);
        //生涯番号
        mstStuEntity.setCareeresNum(Integer.parseInt(data.get(15).toString()));
        //作成日時
        mstStuEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstStuEntity.setCretUsrId("BTGKA1006");
        //更新日時
        mstStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstStuEntity.setUpdUsrId("BTGKA1006");
        //削除フラグ
        if (data.get(14).toString().equals("1")){
            mstStuEntity.setDelFlg(0);
        }else {
            mstStuEntity.setDelFlg(1);
        }
        try {
            mstStuDao.insert(mstStuEntity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生徒データがDBへ登録失敗した/[DBセット]シートのDBセット名[生徒基本マスタ登録]");
            throw new RRException("生徒データがDBへ登録失敗した/[DBセット]シートのDBセット名[生徒基本マスタ登録]");
        }
    }
    /**
     *  ユーザ初期パスワード管理登録
     */
    public  void  insertUsrFirtPw(String userID,String psw)throws RRException {
        MstUsrFirtPwEntity mstUsrFirtPwEntity = new MstUsrFirtPwEntity();
        //ID
        mstUsrFirtPwEntity.setId(null);
        //ユーザID
        mstUsrFirtPwEntity.setUsrId(userID);
        //ロール区分
        mstUsrFirtPwEntity.setRoleDiv("4");
        //ユーザ初期PW
        mstUsrFirtPwEntity.setUsrFstPassword(psw);
        //システム区分
        mstUsrFirtPwEntity.setSysDiv("1");
        //作成日時
        mstUsrFirtPwEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        mstUsrFirtPwEntity.setCretUsrId("BTGKA1006");
        //更新日時
        mstUsrFirtPwEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mstUsrFirtPwEntity.setUpdUsrId("BTGKA1006");
        //削除フラグ
        mstUsrFirtPwEntity.setDelFlg(0);
        try {
            mstUsrFirtPwDao.insert(mstUsrFirtPwEntity);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[DBセット]シートのDBセット名[ユーザ初期パスワード管理登録]");
            throw new RRException("[DBセット]シートのDBセット名[ユーザ初期パスワード管理登録]");
        }
    }
    /**
     *  ユーザーキャラ登録
     */
    public void insertSysUsrRole(String usrId) {
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().select("id").eq("usr_id", usrId));
        sysUserRoleEntity.setUserId(Long.valueOf(mstUsrEntity.getId()));
        sysUserRoleEntity.setRoleId(2L);
        try {
            sysUserRoleDao.insert(sysUserRoleEntity);
        } catch (Exception e){
            e.printStackTrace();
            throw new RRException("ユーザーキャラクター登録に失敗");
        }

    }
    /**
     * ユーザ基本マスタに更新
     */
    public  void  usrUpdate( List<Object> data,String usrId,String orgValue){
        //ユーザ名
        String userName = data.get(3).toString()+"　"+data.get(4).toString();
        //変更後ユーザーID
        String afterUsrId = data.get(1).toString();
        //GIDフラグ
        String gidFlg = data.get(13).toString();
        //更新日時
        Timestamp updDatime = DateUtils.getSysTimestamp();
        //削除フラグ
        int flg;
        if (data.get(14).toString().equals("1")){
            flg = 0;
        }else {
            flg =1;
        }
        try{
            btgka1006Dao.mstUsrUpdate(usrId,userName,orgValue,afterUsrId,gidFlg,updDatime,flg);
        }catch (Exception e){
            e.printStackTrace();
            log.error("ユーザ基本マスタデータがDBへ更新失敗した/[DBセット]シートのDBセット名[ユーザ基本マスタ更新]");
        }
    }
    /**
     * 生徒基本マスタに更新
     */
    public  void  stuUpdate( List<Object> data,String usrId){
        //学校名
        String sch = data.get(10).toString();
        //姓名_姓
        String flnm_nm = data.get(3).toString();
        //姓名_名
        String flnm_lnm = data.get(4).toString();
        //姓名_カナ姓
        String flnm_kn_nm = HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(data.get(5).toString());
        //姓名_カナ名
        String flnm_kn_lnm =  HalfConversionUtils.hankakuKatakanaToZenkakuKatakana(data.get(6).toString());
        //性別区分
        String gendr_div = data.get(8).toString();
        //生年月日
        Date birthd = (DateUtils.parse(data.get(7).toString(),Constant.DATE_FORMAT_YYYYMMDD));
        //学年区分
        String schy_div = data.get(9).toString();
        //オリジナルCD
        String oria_cd = data.get(11).toString();
        //更新日時
        Timestamp updDatime = DateUtils.getSysTimestamp();
        //削除フラグ
        int flg;
        if (data.get(14).toString().equals("1")){
            flg = 0;
        }else {
            flg =1;
        }
        try{
            btgka1006Dao.mstStuUpdate(usrId,sch,flnm_nm,flnm_lnm,flnm_kn_nm,flnm_kn_lnm,gendr_div,birthd,schy_div,oria_cd,updDatime,flg);
        }catch (Exception e){
            e.printStackTrace();
            log.error("生徒基本マスタデータがDBへ更新失敗した/[DBセット]シートのDBセット名[生徒基本マスタ更新]");
        }
    }
}
