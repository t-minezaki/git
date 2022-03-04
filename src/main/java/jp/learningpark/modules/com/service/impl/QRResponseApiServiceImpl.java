package jp.learningpark.modules.com.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.QRResponseApiDao;
import jp.learningpark.modules.com.dto.QRResponseApiDto;
import jp.learningpark.modules.com.dto.QRResponseDownloadInfoDto;
import jp.learningpark.modules.com.dto.QRResponsePdfInfoDto;
import jp.learningpark.modules.com.dto.QRResponseVerInfoDto;
import jp.learningpark.modules.com.service.QRResponseApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import jp.learningpark.modules.sys.service.ShiroService;
import org.apache.shiro.authc.LockedAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * QR解答相关 ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/24 ： NWT)hxl ： 新規作成
 * @date 2020/02/24 11:44
 */
@Service
public class QRResponseApiServiceImpl implements QRResponseApiService {

    /**
     * respaSubjtVerMstDao
     */
    @Autowired
    RespaSubjtVerMstDao respaSubjtVerMstDao;

    /**
     * appVerMstDao
     */
    @Autowired
    AppVerMstDao appVerMstDao;

    /**
     * respaFileMstDao
     */
    @Autowired
    RespaFileMstDao respaFileMstDao;

    /**
     * qrRespaTokenMstDao
     */
    @Autowired
    QrRespaTokenMstDao qrRespaTokenMstDao;

    /**
     * qrResponseApiDao
     */
    @Autowired
    QRResponseApiDao qrResponseApiDao;

    /**
     * mstOrgSubDwnDao
     */
    @Autowired
    MstOrgSubDwnDao mstOrgSubDwnDao;

    /**
     * mstLoginDao
     */
    @Autowired
    MstLoginDao mstLoginDao;
    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;
    /**
     * shiroService
     */
    @Autowired
    ShiroService shiroService;

    private static final Logger log = LoggerFactory.getLogger(QRResponseApiServiceImpl.class);

    /**
     * 解答集アプリのログイン
     *
     * @param qrResponseApiDto  QRResponseのJSON情報
     * @return
     */
    @Override
    // 2021/09/22 manamiru1-772 cuikl del
    public R qrDownloadLogin(QRResponseApiDto qrResponseApiDto) {
        log.info("--------------start download login process--------------");
        //GID認証処理
        // 2020/12/7 huangxinliang modify start
        CM0003.LoginCheckResultDto loginCheckResultDto;
        //GID認証処理
        try {
            //ログインIDのGID認証処理
            loginCheckResultDto = CM0003.loginCheck(qrResponseApiDto.getLoginId(), qrResponseApiDto.getPassword());
            ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GID_FLG, loginCheckResultDto.getGidCheckFlag());
        } catch (LockedAccountException e) {
            R r =  R.error().put("code", 500).put("message", MessageUtils.getMessage("MSGCOMN0002"));
            r.remove("msg");
            return r;
        }
        R loginCheck = CM0006.loginCheck(loginCheckResultDto, qrResponseApiDto.getLoginId(), GakkenConstant.LOGIN_TYPE_KBN.QR_RESPONSE_LOGIN.getValue(), "/QRAPI");
        if (StringUtils.equals(GakkenConstant.OK_FLAG, loginCheckResultDto.getLoginCheckFlag())) {
            if (StringUtils.equals(loginCheckResultDto.getGidCheckFlag(), GakkenConstant.OK_FLAG)){
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_MAIL_ADDRESS, loginCheckResultDto.getMailad());
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GIDPK, loginCheckResultDto.getGakkenIDshort().getGakkenID().getGidpk());
                if (!StringUtils.isEmpty(loginCheckResultDto.getTchCd())){
                    ShiroUtils.setSessionAttribute(GakkenConstant.TCH_CD, loginCheckResultDto.getTchCd());
                }
            }
        }
        if ((Integer)loginCheck.get("code") != 0){
            R r =  R.error().put("code", 500).put("message", "ログインユーザはデータベースに存在しません。");
            r.remove("msg");
            return r;
        }
        String loginId = qrResponseApiDto.getLoginId();
        String manaFlag = "1";
        String brandCd = GakkenConstant.SERVICE_CD;
        String gidFlg = (String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GID_FLG);
        String tchCdFlg = "0";
        if (StringUtils.isEmpty((String)ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD))){
            loginId =  StringUtils.equals("0",gidFlg)?loginId:(String)ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_GIDPK);
        }else {
            loginId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.TCH_CD);
            tchCdFlg = "1";
        }
        //2.1　ユーザーがデータベースに存在するかどうかを判定します。
        QRResponseApiDto user = qrResponseApiDao.selectLoginUser(loginId, manaFlag, brandCd, gidFlg, tchCdFlg);
        //ユーザーが取得できない場合
        if (user == null){
            //アプリ側返信情報の編集処理
            R r =  R.error().put("code", 500).put("message", "ログインユーザはデータベースに存在しません。");
            r.remove("msg");
            return r;
        }
        //ユーザーが取得できる場合、ログイン管理を登録する。
        CM0006.insertMstLoginData("0", null
                , qrResponseApiDto.getLoginId(), "/QRAPI", GakkenConstant.LOGIN_TYPE_KBN.QR_RESPONSE_LOGIN.getValue(), null);
        // 2020/12/14 huangxinliang modify start
        ShiroUtils.setSessionAttribute(GakkenConstant.LOGIN_ID, qrResponseApiDto.getLoginId());
        // 2020/12/14 huangxinliang modify end
        // 2020/11/30 huangxinliang modify start
        List<RespaSubjtVerMstEntity> mstOrgSubDwnEntityList =
                respaSubjtVerMstDao.selectList(new QueryWrapper<RespaSubjtVerMstEntity>().select("distinct subjt_cod").eq("del_flg", 0).orderByAsc(
                        "subjt_cod"));
        // 2020/11/30 huangxinliang modify end
        //複数の場合は、カンマで分割します
        StringBuilder stringBuilder = new StringBuilder();
        int length = mstOrgSubDwnEntityList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(mstOrgSubDwnEntityList.get(i).getSubjtCod());
            if (i < length - 1){
                stringBuilder.append(',');
            }
        }
        log.info("--------------end download login process--------------");
        //教科コードが取得した場合
        R r = R.ok().put("code", 200).put("message", user.getUserName() + "先生").put("kyouka", stringBuilder.toString());
        r.remove("msg");
        return r;
    }

    /**
     * 教科書のダウンロード情報に戻る
     *
     * @param qrResponseApiDto  QRResponseのJSON情報
     * @return
     */
    @Override
    public R getResponse(QRResponseApiDto qrResponseApiDto) {
        log.info("--------------start api process--------------");
        MstCodDEntity one = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "QR_DORUN_KBN").eq("del_flg", 0).last("limit 1"));
        if (one == null){
            return R.error().put("message", "「QR_DORUN_KBN」の詳細データがありません").put("token", "");
        }
        if (StringUtils.equals("0", one.getCodValue2())){
            // 2020/12/14 huangxinliang modify start
            String loginId = (String) ShiroUtils.getSessionAttribute(GakkenConstant.LOGIN_ID);
            if (StringUtils.isEmpty(loginId)){
                //処理を中断する。
                R r =  R.error().put("code", 500).put("message", "エラーメッセージ。");
                r.remove("msg");
                return r;
            }
            // 2020/12/14 huangxinliang modify end
        }
        //ユーザーが取得した場合、組織別教科ダウンロードテーブルへ当組織の教科を取得する
//        List<MstOrgSubDwnEntity> mstOrgSubDwnEntityList = mstOrgSubDwnDao.selectList(new QueryWrapper<MstOrgSubDwnEntity>().eq("org_id", orgId).eq("del_flg", 0));
        //教科コードが取得できない場合
//        if (mstOrgSubDwnEntityList.size() == 0){
//            //処理を中断する。
//            R r =  R.error().put("code", 500).put("message", "該組織には教科書データがありません。");
//            r.remove("msg");
//            return r;
//        }
        //複数の場合は、カンマで分割します
//        List<String> kyokaCodeList = new ArrayList<>();
//        int length = mstOrgSubDwnEntityList.size();
//        for (int i = 0; i < length; i++) {
//            kyokaCodeList.add(mstOrgSubDwnEntityList.get(i).getSubjtCod());
//        }
        AtomicReference<Boolean> isException = new AtomicReference<>(false);
        Date sysDate = DateUtils.getSysDate();
        //解答集バージョン情報を取得するため、下記条件で解答集バージョン管理テータを取得する。
        List<RespaSubjtVerMstEntity> respaSubjtVerMstEntityList = respaSubjtVerMstDao.selectList(new QueryWrapper<RespaSubjtVerMstEntity>().eq("del_flg", 0)
//                .in("subjt_cod", kyokaCodeList)
                .orderByAsc("subjt_cod"));
        List<RespaFileMstEntity> respaFileMstEntityList = new ArrayList<>();
        for (RespaSubjtVerMstEntity respaFileMstEntity : respaSubjtVerMstEntityList) {
            //教科毎のPDFファイル情報を取得するため、下記条件で解答集ファイル管理テータを取得する。
            List<RespaFileMstEntity> respaFileMstEntities = null;
            if (StringUtils.equals("0", one.getCodValue2())){
                respaFileMstEntities = respaFileMstDao.selectList(new QueryWrapper<RespaFileMstEntity>().eq("subjt_cod", respaFileMstEntity.getSubjtCod()).eq("grade_div", respaFileMstEntity.getGradeDiv()).eq("del_flg", 0).orderByAsc("text_book_cod"));
            }else {
                respaFileMstEntities = respaFileMstDao.selectList(new QueryWrapper<RespaFileMstEntity>().eq("subjt_cod", respaFileMstEntity.getSubjtCod()).eq("del_flg", 0).orderByAsc("text_book_cod"));
            }
            respaFileMstEntityList.addAll(respaFileMstEntities);
        }
        //アプリのバージョンが最新であるかどうかの判断を行うため、下記条件でアプリバージョン管理テータを取得する。
        AppVerMstEntity appVerMstEntity = appVerMstDao.selectOne(new QueryWrapper<AppVerMstEntity>().eq("del_flg", 0).orderByDesc("ver_num").last("limit 1"));
        //上記の任意データ取得できない場合
        if (respaSubjtVerMstEntityList.size() == 0 || appVerMstEntity == null || respaFileMstEntityList.size() == 0){
            //処理を中断する。
            R r = R.error().put("code", 500).put("message", "ＱＲ解答集マスタデータを取得失敗しました。").put("downloadinfo", null);
            r.remove("msg");
            return r;
        }
        //送信JSON1・appversion＜取得したバージョンNUMの場合、エラーを返却する。
        if (!StringUtils.equals(qrResponseApiDto.getAppv(), appVerMstEntity.getVerNum())){
            //処理を中断する。
            R r = R.error().put("code", 904).put("message", "アプリバージョンが古いので、最新バージョンをダウンロードしてください。").put("downloadinfo", null);
            r.remove("msg");
            return r;
        }
        //ダウンロード情報リスト
        List<QRResponseDownloadInfoDto> qrResponseDownloadInfoDtoList = new ArrayList<>();
        //テキストコードとインデックス間のマッピング
        Map<String, Integer> subjtCDIndexMap = new HashMap<>();
        respaSubjtVerMstEntityList.forEach(v -> {
            QRResponseVerInfoDto qrResponseVerInfoDto = null;
            //教科書コードの同じデータを見つける
            for (int i = 0; i < qrResponseApiDto.getVerinfo().size(); i++){
                if (StringUtils.equals(qrResponseApiDto.getVerinfo().get(i).getKyokacd(), v.getSubjtCod())){
                    qrResponseVerInfoDto = qrResponseApiDto.getVerinfo().get(i);
                    break;
                }
            }
            //一致するデータがある
            if (qrResponseVerInfoDto != null){
                // add at 2021/10/8 for V9.02 by NWT HuangXL START
                String gradeDiv = StringUtils.trim(v.getGradeDiv());
                // add at 2021/10/8 for V9.02 by NWT HuangXL END
                //downloadinfoデータをカプセル化する
                QRResponseDownloadInfoDto qrResponseDownloadInfoDto = new QRResponseDownloadInfoDto();
                // 2020/11/30 huangxinliang modify start
//                qrResponseApiDto.getVerinfo().remove(qrResponseVerInfoDto);
                // 2020/11/30 huangxinliang modify end
                if (StringUtils.equals("0", one.getCodValue2())){
                    // modify at 2021/10/6 for V9.02 by NWT HuangXL START
                    qrResponseDownloadInfoDto.setKyokacd(v.getSubjtCod() + "-" + gradeDiv);
                    // modify at 2021/10/6 for V9.02 by NWT HuangXL END
                }else {
                    qrResponseDownloadInfoDto.setKyokacd(v.getSubjtCod());
                }
                //送信JSON1・verdatetime[i]≧取得した最新バージョン日時の場合、
                if (qrResponseVerInfoDto.getVerdatetime().compareTo(DateUtils.format(v.getNewVerDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS)) >= 0){
                    qrResponseDownloadInfoDto.setDownloadflg("0");
                    qrResponseDownloadInfoDto.setDownloadurl("");
                    qrResponseDownloadInfoDto.setPassword("");
                    qrResponseDownloadInfoDto.setStartdate("");
                    qrResponseDownloadInfoDto.setPdfinfo(new ArrayList<>());
                    qrResponseDownloadInfoDto.setToken("");
                }else if (sysDate.compareTo(v.getDownStartDatime()) < 0) {
                    //マナミルのシステム日時＜取得したダウンロード開始日時の場合、
                    qrResponseDownloadInfoDto.setDownloadflg("0");
                    qrResponseDownloadInfoDto.setStartdate(DateUtils.format(v.getDownStartDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS));
                    qrResponseDownloadInfoDto.setDownloadurl("");
                    qrResponseDownloadInfoDto.setPassword("");
                    qrResponseDownloadInfoDto.setPdfinfo(new ArrayList<>());
                    qrResponseDownloadInfoDto.setToken("");
                }else if (sysDate.compareTo(v.getDownStartDatime()) >= 0) {
                    //マナミルのシステム日時≧取得したダウンロード開始日時の場合、
                    qrResponseDownloadInfoDto.setDownloadflg("1");
                    qrResponseDownloadInfoDto.setDownloadurl(v.getDownldUrl());
                    qrResponseDownloadInfoDto.setPassword(v.getFileGetPassword());
                    qrResponseDownloadInfoDto.setStartdate(DateUtils.format(v.getDownStartDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSS));
                    // 2020/12/14 huangxinliang modify start
                    if (StringUtils.equals("0", one.getCodValue2())) {
                        // modify at 2021/10/6 for V9.02 by NWT HuangXL START
                        subjtCDIndexMap.put(v.getSubjtCod() + "-" + gradeDiv, qrResponseDownloadInfoDtoList.size());
                        // modify at 2021/10/6 for V9.02 by NWT HuangXL END
                    }else {
                        subjtCDIndexMap.put(v.getSubjtCod(), qrResponseDownloadInfoDtoList.size());
                    }
                    // 2020/12/14 huangxinliang modify end
                    qrResponseDownloadInfoDto.setPdfinfo(new ArrayList<>());
                    //トークンを生成する
                    String token = Constant.QR_PDF_GET_TOKEN + v.getSubjtCod() + SnowFlake.nextId();
                    qrResponseDownloadInfoDto.setToken(token);
                    //トークン情報をカプセル化する
                    QrRespaTokenMstEntity qrRespaTokenMstEntity = new QrRespaTokenMstEntity();
                    qrRespaTokenMstEntity.setDownldToken(token);
                    qrRespaTokenMstEntity.setTokenValidEndDt(DateUtils.addMonths(sysDate, 1));
                    qrRespaTokenMstEntity.setCretDatime(DateUtils.toTimestamp(sysDate));
                    qrRespaTokenMstEntity.setCretUsrId("PDF FILE GET");
                    qrRespaTokenMstEntity.setUpdDatime(DateUtils.toTimestamp(sysDate));
                    qrRespaTokenMstEntity.setUpdUsrId("PDF FILE GET");
                    qrRespaTokenMstEntity.setDelFlg(0);
                    try{
                        //トークン情報を保存する
                        qrRespaTokenMstDao.insert(qrRespaTokenMstEntity);
                    }catch (Exception e){
                        log.error(e.getMessage());
                        isException.set(true);
                    }
                }
                qrResponseDownloadInfoDtoList.add(qrResponseDownloadInfoDto);
            }
        });
        //パッケージpdf情報
        respaFileMstEntityList.forEach(v -> {
            // add at 2021/10/8 for V9.02 by NWT HuangXL START
            String gradeDiv = StringUtils.trim(v.getGradeDiv());
            // add at 2021/10/8 for V9.02 by NWT HuangXL END
            //downloadinfoに対応するインデックスを見つける
            // 2020/12/14 huangxinliang modify start
            Integer index;
            if (StringUtils.equals("0", one.getCodValue2())) {
                // modify at 2021/10/6 for V9.02 by NWT HuangXL START
                index = subjtCDIndexMap.get(v.getSubjtCod() + "-" + gradeDiv);
                // modify at 2021/10/6 for V9.02 by NWT HuangXL END
            }else {
                index = subjtCDIndexMap.get(v.getSubjtCod());
            }
            // 2020/12/14 huangxinliang modify end
            if (index != null){
                //パッケージPDFインデックス
                QRResponsePdfInfoDto qrResponsePdfInfoDto = new QRResponsePdfInfoDto();
                qrResponsePdfInfoDto.setBookcd(v.getTextBookCod());
                qrResponsePdfInfoDto.setPdfname(v.getPdfFileNm());
                try {
                    //modify at 2021/08/19 for V9.02 by NWT LiGX START
                    qrResponsePdfInfoDto.setGrade(v.getGradeName());
                    qrResponsePdfInfoDto.setStage(v.getCollName());
                    //modify at 2021/08/19 for V9.02 by NWT LiGX END
                    // modify at 2021/10/6 for V9.02 by NWT HuangXL START
                    qrResponsePdfInfoDto.setNo(Integer.parseInt(StringUtils.trim(v.getBrandDiv())));
                    // modify at 2021/10/6 for V9.02 by NWT HuangXL END
                }catch (NumberFormatException e){
                    log.error(e.getMessage());
                    isException.set(true);
                }
                qrResponsePdfInfoDto.setPage(v.getPageNum());
                //add at 2021/09/08 for V9.02 by NWT LiGX START
                qrResponsePdfInfoDto.setGradesort(v.getDisGradeSort());
                qrResponsePdfInfoDto.setStagesort(v.getDisCollSort());
                //add at 2021/09/08 for V9.02 by NWT LiGX END
                //PDF情報をdownloadinfoに追加します
                qrResponseDownloadInfoDtoList.get(index).getPdfinfo().add(qrResponsePdfInfoDto);
            }
        });
        if (isException.get()){
            R r = R.ok().put("code", 500).put("message", "ダウンロード処理にはエラーが発生しました。").put("downloadinfo", null);
            r.remove("msg");
            return r;
        }
        log.info("--------------end api process--------------");
        R r = R.ok().put("code", 200).put("message", "成功").put("downloadinfo", qrResponseDownloadInfoDtoList);
        r.remove("msg");
        return r;
    }

    /**
     * ダウンロードファイル
     *
     * @param fileName  ファイル名
     * @param token     トークン
     * @return
     */
    @Override
    public Object download(HttpServletResponse response, String fileName, String token) {
        AtomicReference<Boolean> isException = new AtomicReference<>(false);
        //システム時間
        Date sysDate = DateUtils.getSysDate();
        //トークンに基づいてデータを検索する
        QrRespaTokenMstEntity qrRespaTokenMstEntity = qrRespaTokenMstDao.selectOne(new QueryWrapper<QrRespaTokenMstEntity>().eq("downld_token", token).eq("del_flg", 0));
        //データ取得できない場合、PDFファイルダウンロード不可です。
        if (qrRespaTokenMstEntity == null){
            //処理を中断する。
            R r = R.ok().put("code", 902).put("message", "token利用済。");
            r.remove("msg");
            return r;
        }
        //取得したダウンロード用TOKENより、該当データをQR解答集用TOKEN管理から削除する。
        qrRespaTokenMstDao.deleteById(qrRespaTokenMstEntity.getId());
        //取得したTOKEN有効終了日＜サーバのシステム日付（ＹＹＹＹＭＭＤＤ）場合、PDFファイルダウンロード不可です。
        if (qrRespaTokenMstEntity.getTokenValidEndDt().compareTo(sysDate) < 0){
            //処理を中断する。
            R r = R.ok().put("code", 903).put("message", "token有効期限過ぎった。");
            r.remove("msg");
            return r;
        }
        RespaSubjtVerMstEntity respaSubjtVerMstEntity = respaSubjtVerMstDao.selectOne(new QueryWrapper<RespaSubjtVerMstEntity>().eq("subjt_zip_file_nm", fileName).orderByDesc("new_ver_datime").last("limit 1"));
        if (respaSubjtVerMstEntity == null){
            isException.set(true);
        }
        if (!isException.get()){
            log.info("--------------start download process--------------");
            String filePath = respaSubjtVerMstEntity.getSubjtZipFilePath();
            // 2020/12/18 huangxinliang modify start
            File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), filePath);
            if (!file.exists()){
                log.info("--------------file not exists: " + file.getAbsolutePath() + "--------------");
                R r = R.ok().put("code", 500).put("message", "ダウンロードエラー。");
                r.remove("msg");
                return r;
            }
            // 2020/12/18 huangxinliang modify end
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            try {
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(new InputStreamResource(new FileInputStream(file)));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            log.info("--------------end download process--------------");
        }
        if (isException.get()){
            R r = R.ok().put("code", 500).put("message", "ダウンロードエラー。");
            r.remove("msg");
            return r;
        }
        JSONObject res = new JSONObject();
        res.put("code",200);
        res.put("message","ダウンロード成功！");
//        response.setHeader("X-Extra-Info-JSON", res.toString());
        R r = R.ok().put("code", 200).put("message", "ダウンロード成功！");
        r.remove("msg");
        return r;
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param file 文件
     * @param os 输出流
     * @return
     */
//    private void writeBytes(File file, OutputStream os) throws IOException
//    {
//        BufferedInputStream fis = null;
//        try
//        {
//            if (!file.exists())
//            {
//                throw new FileNotFoundException(file.getPath());
//            }
//            fis = new BufferedInputStream(new FileInputStream(file));
//            byte[] b = new byte[1024];
//            int length;
//            while ((length = fis.read(b)) > 0)
//            {
//                os.write(b, 0, length);
//                os.flush();
//            }
//        }
//        catch (IOException e)
//        {
//            throw e;
//        }
//        finally
//        {
//            if (os != null)
//            {
//                try
//                {
//                    os.close();
//                }
//                catch (IOException e1)
//                {
//                    log.error(e1.getMessage());
//                }
//            }
//            if (fis != null)
//            {
//                try
//                {
//                    fis.close();
//                }
//                catch (IOException e1)
//                {
//                    log.error(e1.getMessage());
//                }
//            }
//        }
//    }
}
