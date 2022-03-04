package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.controller.F05002Controller;
import jp.learningpark.modules.manager.dao.F04012Dao;
import jp.learningpark.modules.manager.dto.F04012Dto;
import jp.learningpark.modules.manager.service.F04012Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * マナミルチャンネル新規·編集 ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/10 ： NWT)hxl ： 新規作成
 */
@Service
public class F04012ServiceImpl implements F04012Service {

    /**
     * commonDao
     */
    @Autowired
    CommonDao commonDao;

    /**
     * f04012Dao
     */
    @Autowired
    F04012Dao f04012Dao;

    /**
     * mstNoticeDao
     */
    @Autowired
    MstNoticeDao mstNoticeDao;

    /**
     * mstGuardDao
     */
    @Autowired
    MstGuardDao mstGuardDao;

    /**
     * mstNoticeDeliverDao
     */
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;

    /**
     * guardReadingStsDao
     */
    @Autowired
    GuardReadingStsDao guardReadingStsDao;

    /**
     * mstOrgDao
     */
    @Autowired
    MstOrgDao mstOrgDao;
    @Autowired
    F05002Controller f05002Controller;
    @Autowired
    MstTmplateService mstTmplateService;
    /**
     *NWT　楊　2021/08/13　MANAMIRU1-733　Add　
     */
    @Autowired
    F04012ServiceImpl f04012Service;
	
    private Logger logger = LoggerFactory.getLogger(getClass());
	
    // 2021/1/4 huangxinliang modify start
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    // 2021/1/4 huangxinliang modify end

    /**
     * 画像の整合ルール
     */
    private Pattern imgPattern = Pattern.compile(
            "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE);

    /**
     * お知らせ登録
     *
     * @param params パラメータ
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R save(Map<String, Object> params) {
        //システム時間
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //ページ転送の「entity」
        F04012Dto dto = (F04012Dto)params.get("dto");
        try {
            dto.setTitle(URLDecoder.decode(dto.getTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //ページ転送の「MultipartFile」
        MultipartFile[] files = (MultipartFile[])params.get("files");
        String filePaths = dto.getFilePaths();
        if ((files == null || files.length == 0) && !StringUtils.isEmpty(filePaths)) {
            files = FileUtils.generateMultipartFiles(filePaths);
        }
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        //ページに渡されたパラメーターに従って「MstNoticeEntity」を構成します
        mstNoticeEntity = configMstNoticeEntity(mstNoticeEntity, dto, files, (String)params.get("orgId"), sysTimestamp, (String)params.get("userId"));
        //作成日時
        mstNoticeEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        mstNoticeEntity.setCretUsrId((String)params.get("userId"));
        try {
            //データ登録
            mstNoticeDao.insert(mstNoticeEntity);
        } catch (Exception e) {
            //出力データ登録例外情報
            logger.error(e.getMessage());
            //データ登録例外をスローする
            throw new RRException("お知らせ登録エラー");
        }
        //データ登録後に主キーIDを取得します
        Integer noticeId = mstNoticeEntity.getId();
        // 2021/1/4 huangxinliang modify start
        threadPoolTaskExecutor.execute(()->{
            //お知らせ配信先、保護者お知らせ閲覧状況マスタ情報を登録する。
            //NWT　楊　2021/08/13　MANAMIRU1-733　Edit　Start
            try {
                f04012Service.insertMstNoticeDeliverAndGuardReadingSts(dto.getOrgIdList(), noticeId, sysTimestamp, (String)params.get("userId"));
            } catch (Exception e) {
                //NWT　楊　2021/08/25　MANAMIRU1-733　Edit　Start
                //ERRORレベルのログを出力
                logger.error(e.getMessage());
                //NWT　楊　2021/08/25　MANAMIRU1-733　Edit　End
                e.printStackTrace();
            }
            //NWT　楊　2021/08/13　MANAMIRU1-733　Edit　End
        });
        // 2021/1/4 huangxinliang modify end
        //全部正常終了の場合
        return R.ok();
    }

    /**
     * お知らせ編集
     *
     * @param params パラメータ
     * @return R
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R update(Map<String, Object> params) {
        //前のページで送信されたIDに基づいて「MstNoticeEntity」データを取得します
        MstNoticeEntity mstNoticeEntity = mstNoticeDao.selectById((Integer)params.get("id"));
        //最後に変更されたデータの時間が等しいかどうかを判別
        if (!StringUtils.equals(DateUtils.format(mstNoticeEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS),
                DateUtils.format((Timestamp)params.get("lastUpdateTime"), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS))) {
            //情報は期限切れです
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //システム時間
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //ページ転送の「entity」
        F04012Dto dto = (F04012Dto)params.get("dto");
        try {
            dto.setTitle(URLDecoder.decode(dto.getTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //ページ転送の「MultipartFile」
        MultipartFile[] files = (MultipartFile[])params.get("files");
        String filePaths = dto.getFilePaths();
        logger.info("FileParam2:"+filePaths);
        if ((files == null || files.length == 0) && !StringUtils.isEmpty(filePaths)) {
            files = FileUtils.generateMultipartFiles(filePaths);
        }
        logger.info("FilesParam:"+files);
        //ページに渡されたパラメーターに従って「MstNoticeEntity」を構成します
        mstNoticeEntity = configMstNoticeEntity(mstNoticeEntity, dto, files, (String)params.get("orgId"), sysTimestamp, (String)params.get("userId"));
        try {
            //データ登録
            mstNoticeDao.updateById(mstNoticeEntity);
        } catch (Exception e) {
            //出力データ登録例外情報
            logger.error(e.getMessage());
            //データ登録例外をスローする
            throw new RRException(MessageUtils.getMessage("MSGCOMB0016", "お知らせ"));
        }
        //お知らせ配信先の削除
        mstNoticeDeliverDao.delete(new QueryWrapper<MstNoticeDeliverEntity>().eq("notice_id", params.get("id")));
        //保護者お知らせ閲覧状況の削除
        guardReadingStsDao.delete(new QueryWrapper<GuardReadingStsEntity>().eq("notice_id", params.get("id")));
        // 2021/1/4 huangxinliang modify start
        threadPoolTaskExecutor.execute(()->{
            //お知らせ配信先、保護者お知らせ閲覧状況マスタ情報を登録する。
            //NWT　楊　2021/08/13　MANAMIRU1-733　Edit　Start
            try {
                f04012Service.insertMstNoticeDeliverAndGuardReadingSts(dto.getOrgIdList(), (Integer)params.get("id"), sysTimestamp, (String)params.get("userId"));
            } catch (Exception e) {
                //NWT　楊　2021/08/25　MANAMIRU1-733　Edit　Start
                //ERRORレベルのログを出力
                logger.error(e.getMessage());
                //NWT　楊　2021/08/25　MANAMIRU1-733　Edit　End
                e.printStackTrace();
            }
            //NWT　楊　2021/08/13　MANAMIRU1-733　Edit　End
        });
        // 2021/1/4 huangxinliang modify end
        //全部正常終了の場合
        return R.ok();
    }

    /**
     * 初期化
     *
     * @param id お知らせID
     * @return R
     */
    @Override
    public R init(Integer id) {
        //返されたインスタンスを作成する
        R r = R.ok();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //組織
        MstOrgEntity org = mstOrgDao.selectOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        //現在の組織情報
        r.put("org", org);
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonDao.selectThisAndLowerOrgId(brandCd, orgId);
        //本組織、下層組織IDリスト
        r.put("orgIdList", orgIdList);
        //新しいルールと編集ステータスの判定
        if (id != -1) {
            //「編集」
            MstNoticeEntity mstNoticeEntity = mstNoticeDao.selectById(id);
            //IDに基づいたデータのクエリ
            r.put("notice", mstNoticeEntity);
            //選択された組織クエリ
            List<String> chosenOrgIdList = f04012Dao.selectChosenOrgIdList(id);
            //選択された組織リスト
            r.put("chosenOrgIdList", chosenOrgIdList);
        }
        //テンプレートリスト初期化
        List<MstTmplateEntity> mstTmplateEntityList = f05002Controller.getMstTemp(brandCd,orgId,"7");
        r.put("mstTmplateEntityList", mstTmplateEntityList);
        //全部正常終了の場合
        return r;
    }

    /**
     * お知らせ配信先&保護者お知らせ閲覧状況登録データ
     *
     * @param orgIdList 組織IDリスト
     * @param noticeId お知らせID
     * @param sysTimestamp システム時間
     * @param userId ログインユーザーID
     */
    //NWT　楊　2021/08/19　MANAMIRU1-733　Edit　Start
    @Transactional(rollbackFor = Exception.class)
    public void insertMstNoticeDeliverAndGuardReadingSts(List<String> orgIdList, Integer noticeId, Timestamp sysTimestamp, String userId) throws Exception {

        //お知らせ配信先 insert
        f04012Dao.doInsertMstNoticeDeliver(noticeId, orgIdList, sysTimestamp, userId);

        //保護者お知らせ閲覧状況 insert
        f04012Dao.doInsertGuardReadingSts(noticeId);//2021/08/26　MANAMIRU1-733　Edit

        //お知らせを取得
        MstNoticeEntity mstNoticeEntity = mstNoticeDao.selectOne(new QueryWrapper<MstNoticeEntity>().eq("id",noticeId).eq("del_flg",0).last(" for update"));

        if (mstNoticeEntity != null){

            //お知らせのを更新日時を取得する
            Timestamp updaTime = mstNoticeEntity.getUpdDatime();

            //リクエストの日時とお知らせのを更新日時を比較する
            if (sysTimestamp.compareTo(updaTime) != 0){
                //2021/08/26　MANAMIRU1-733　Edit Start
                logger.info(updaTime + "にmst_notice のデータが更新されたため rollback しました、");
                //2021/08/26　MANAMIRU1-733　Edit End
                //異常を投げ出して事務がロールバックする
                throw new Exception();
            }
        }
    }
    //NWT　楊　2021/08/19　MANAMIRU1-733　Edit　End

    /**
     * noticeEntityを構成する
     *
     * @param dto dto
     * @param files ファイル
     * @param orgId 組織ID
     * @param sysTimestamp システム時間
     * @param userId ログインユーザーID
     * @return
     */
    private MstNoticeEntity configMstNoticeEntity(MstNoticeEntity mstNoticeEntity, F04012Dto dto, MultipartFile[] files, String orgId, Timestamp sysTimestamp, String userId) {
        logger.info("files:"+files);
        //画像パスを取得
        List<String> pathList;
        //content
        String noticeCont = dto.getContent();
        //新しい画像パスを取得
        if (dto.getTempId() != null){
            String titleImgPath = mstTmplateService.getById(dto.getTempId()).getTitleImgPath();
            mstNoticeEntity.setTitleImgPath(titleImgPath);
        }else {
            try {
                //画像パスを取得
                pathList = getSrcList(URLDecoder.decode(dto.getContent(), "UTF-8"), imgPattern, "/gakken/image");
            } catch (UnsupportedEncodingException e) {
                //例外情報
                logger.error(e.getMessage());
                //URL変換の例外
                throw new RRException("URLデコードエラー");
            }
            //画像
            Map<String, String> map = FileUtils.getRealPath(pathList, "/ueditorUpload", "imageUrlPrefix");
            //元のパスをサーバーパスに置き換えます
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try {
                    //パス変換
                    noticeCont = noticeCont.replace(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
                    //タイトル画像パス
                    if (pathList.size() == 0) {
                        //タイトル画像パスを設定する
                        mstNoticeEntity.setTitleImgPath("");
                    } else {
                        //タイトル画像パスを設定する
                        mstNoticeEntity.setTitleImgPath(entry.getValue());
                    }
                } catch (UnsupportedEncodingException e) {
                    //例外情報
                    logger.error(e.getMessage());
                    //パス変換の例外
                    throw new RRException("URLエンコードエラー");
                }
            }
        }
        //添付ファイルパス
        if (files.length != 0) {
            //StringBuilder連結文字列
            StringBuilder filePath = new StringBuilder();
            try {
                //配列を反復処理する
                for (int i = 0, len = files.length; i < len; i++) {
                    logger.info("files[i]:"+files[i]);
                    //接続ファイル名
                    filePath.append(getPath(files[i]));
                    //最後のファイルかどうかを判別
                    if (i != len - 1) {
                        //接続セパレーター
                        filePath.append(",");
                    }
                }
            } catch (IOException e) {
                //例外情報
                logger.error(e.getMessage());
                //ファイル保存エラー
                throw new RRException("ファイル保存エラー");
            }
            if (!StringUtils.isEmpty(filePath.toString())) {
                //添付ファイルパスを設定する
                mstNoticeEntity.setAttachFilePath(filePath.toString());
            } else {
                //添付ファイルパスを設定する
                mstNoticeEntity.setAttachFilePath("");
            }
        } else if (StringUtils.isEmpty(dto.getFilePaths())) {
            //添付ファイルパスを設定する
            mstNoticeEntity.setAttachFilePath("");
        }
        //掲載予定開始日時&掲載予定終了日時
        try {
            //掲載予定開始日時
            mstNoticeEntity.setPubPlanStartDt(
                    new Timestamp(Objects.requireNonNull(DateUtils.parse(dto.getStartDate() + ":00", Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)).getTime()));
            //掲載予定終了日時
            mstNoticeEntity.setPubPlanEndDt(
                    new Timestamp(Objects.requireNonNull(DateUtils.parse(dto.getEndDate() + ":00", Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)).getTime()));
        } catch (Exception e) {
            //例外情報
            logger.error(e.getMessage());
            //時間解析例外
            throw new RRException("時間変換エラー");
        }
        //組織ID
        mstNoticeEntity.setOrgId(orgId);
        //お知らせタイトル
        mstNoticeEntity.setNoticeTitle(dto.getTitle());
        //お知らせ内容
        mstNoticeEntity.setNoticeCont(noticeCont);
        //お知らせタップ区分:「 6: マナミルチャンネル」
        mstNoticeEntity.setNoticeTypeDiv("6");
        //お知らせレベル区分
        mstNoticeEntity.setNoticeLevelDiv(dto.getNoticeLevel());
        //全体配信フラグ
        mstNoticeEntity.setAllDeliverFlg(dto.getAllDeliverFlg());
        //更新日時
        mstNoticeEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        mstNoticeEntity.setUpdUsrId(userId);
        //削除フラグ:「0：有効」
        mstNoticeEntity.setDelFlg(0);
        //設定されたインスタンスを返します
        return mstNoticeEntity;
    }

    /**
     * パスを取得
     *
     * @param htmlCode htmlCode
     */
    public List<String> getSrcList(String htmlCode, Pattern pattern, String contains) {
        //パスリスト
        List<String> srcList = new ArrayList<>();
        //マッチャー
        Matcher m = pattern.matcher(htmlCode);
        //中間変数
        String quote;
        //一致するファイルパスを保存する
        String src;
        //一致するテキスト
        while (m.find()) {
            //一致する最初の文字列を取得します
            quote = m.group(1);
            //空でないファイルパスを取得する
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            if (src.contains(contains)) {
                //一致するファイルパスを保存する
                srcList.add(src);
            }
        }
        //「パス」を返します
        return srcList;
    }

    /**
     * ファイルパス
     *
     * @param file 　ファイル
     * @return fileName
     * @throws IOException
     */
    public String getPath(MultipartFile file) throws IOException {
        logger.info("file:" + file);
        if (file != null){
            logger.info("fileName:" + file.getOriginalFilename());
        }
        // ファイル名を生成
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().lastIndexOf(".")) + DateUtils.format(
                DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS) + file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        //ローカルパス
        String realPath = "channel"+ File.separator + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH).replace(
                "/", File.separator) + File.separator + fileName;
        logger.info("===realPath:"+realPath);
        logger.info("===separator:"+File.separator);
        // 生成サーバパス（データベースパス）
        String savePath = GakkenConstant.FILE_PATH_PREFIX + realPath;
        // ファイルを生成
        File destFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath);
        if (!destFile.exists()) {
            boolean mkdirs = destFile.getParentFile().mkdirs();
            boolean newFile = destFile.createNewFile();
        }
        InputStream in = file.getInputStream();
        FileOutputStream out = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        // データベースパス

        return savePath;
    }
}
