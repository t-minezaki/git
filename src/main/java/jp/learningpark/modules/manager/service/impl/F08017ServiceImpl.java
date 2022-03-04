package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstAskTalkTmplateDao;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstMaxIdDao;
import jp.learningpark.modules.common.dao.MstTmplateDao;
import jp.learningpark.modules.common.entity.MstAskTalkTmplateEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dao.F08017Dao;
import jp.learningpark.modules.manager.dto.F08017Dto;
import jp.learningpark.modules.manager.service.F08017Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>08017 テンプレート新規編集画面Service</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/08/06 : hujiaxing: 新規<br />
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class F08017ServiceImpl implements F08017Service {

    /**
     * MAX採番　Dao
     */
    @Autowired
    MstMaxIdDao mstMaxIdDao;
    /**
     * テンプレート dao
     */
    @Autowired
    MstTmplateDao mstTmplateDao;
    /**
     * MAX採番 dao
     */
    @Autowired
    MstMaxIdDao maxIdDao;
    /**
     * F08017Dao
     */
    @Autowired
    F08017Dao f08017Dao;
    /**
     * コードマスタ_明細 dao
     */
    @Autowired
    MstCodDDao mstCodDDao;
    /**
     * 質問面談テンプレート dao
     */
    @Autowired
    MstAskTalkTmplateDao mstAskTalkTmplateDao;

    private static Logger logger = LoggerFactory.getLogger(F08017ServiceImpl.class);

    /**
     * 画像の整合ルール
     */
    private Pattern imgPattern = Pattern.compile(
            "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE);

    /**
     * <p>IDによるテンプレートの取得</p>
     *
     * @param id テンプレート id
     * @return
     */
    @Override
    public R getTmplateById(Integer id) {
        R info = R.ok();
        List<MstCodDEntity> mstCodDEntityList = mstCodDDao.selectList(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "ANSWER_TYPE_DIV").orderByAsc("sort").eq("del_flg", 0));
        if (id != null) {
            String orgId = ShiroUtils.getUserEntity().getOrgId();
            MstTmplateEntity mstTmplateEntity = mstTmplateDao.selectOne(
                    new QueryWrapper<MstTmplateEntity>().and(w->w.eq("id", id).eq("org_id", orgId).eq("del_flg", 0)));
            if (mstTmplateEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "テンプレート"));
            }
            //編集の場合、質問面談テンプレートから取得データをそのままで表示する。
            List<F08017Dto> mstAskTalkTmplateEntities = f08017Dao.selectMstAskTalkData(id, orgId);
            if (mstTmplateEntity.getUpdDatime() != null) {
                Date updateTm = new Date(mstTmplateEntity.getUpdDatime().getTime());
                String updateTmStr = DateUtils.format(updateTm, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS);
                info.put("updateTm", updateTmStr);
            }
            info.put("tmplate", mstTmplateEntity).put("mstAskTalkTmplateEntities", mstAskTalkTmplateEntities);
        }
        return info.put("mstCodDEntityList", mstCodDEntityList);
    }

    /**
     * <p>テンプレートの保存</p>
     *
     * @param tmplateEntity テンプレートentity
     * @param file 添付ファイルパス
     * @return
     */
    @Override
    public R addTmplate(MstTmplateEntity tmplateEntity, MultipartFile[] file, String mstAskTalkTmplateList, String filePaths) throws UnsupportedEncodingException {
        // セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ログインユーザＩＤ
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //MAX採番
        MstMaxIdEntity maxIdEntity = maxIdDao.selectOne(new QueryWrapper<MstMaxIdEntity>().and(w->w.eq("org_id", orgId).eq("type_div", "t").eq("del_flg", 0)));
        //画像パスを取得
        List<String> pathList = null;
        try {
            pathList = getSrcList(URLDecoder.decode(tmplateEntity.getTmplateCnt(), "UTF-8"), imgPattern, "/gakken/image");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //画像
        Map<String, String> map = FileUtils.getRealPath(pathList, "/ueditorUpload", "imageUrlPrefix");
        //テンプレート内容
        String cont = tmplateEntity.getTmplateCnt();
        int i = map.size();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i == 1) {
                tmplateEntity.setTitleImgPath(entry.getValue());
            }
            try {
                cont = cont.replace(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
                i--;
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
        }
        //テンプレート内容を設定する
        tmplateEntity.setTmplateCnt(cont);
        //MAX採番
        if (maxIdEntity != null) {
            tmplateEntity.setTmplateCd(maxIdEntity.getTypeDiv() + (maxIdEntity.getMaxId() + 1));
            //MAXID
            maxIdEntity.setMaxId(maxIdEntity.getMaxId() + 1);
            //更新日時
            maxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            maxIdEntity.setUpdUsrId(userId);

            mstMaxIdDao.update(maxIdEntity, new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "t").eq("del_flg", 0));
        } else {
            maxIdEntity = new MstMaxIdEntity();

            //組織ID
            maxIdEntity.setOrgId(orgId);
            //MAXID
            maxIdEntity.setMaxId(1);
            //種類区分
            maxIdEntity.setTypeDiv("t");
            //作成日時
            maxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            maxIdEntity.setCretUsrId(userId);
            //更新日時
            maxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            maxIdEntity.setUpdUsrId(userId);
            //削除フラグ
            maxIdEntity.setDelFlg(0);
            tmplateEntity.setTmplateCd(maxIdEntity.getTypeDiv() + maxIdEntity.getMaxId());

            mstMaxIdDao.insert(maxIdEntity);
        }
        //添付ファイルパスの保存
        String filePath = null;
        try {
            if (file != null) {
                //添付ファイルパスの保存
                StringBuilder filePathTemp = new StringBuilder();
                for (MultipartFile fileItem : file) {
                    String path = getPath(fileItem);
                    // パスが空の場合はスキップします
                    if (!StringUtils.isEmpty(filePathTemp.toString())) {
                        filePathTemp.append(",");
                    }
                    filePathTemp.append(path);
                }
                filePath = filePathTemp.toString();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        //添付ファイルパスを設定する
        if (!StringUtils.isEmpty(filePath)) {
            tmplateEntity.setAttachFilePath(filePath);
        }
        tmplateEntity.setCtgyNm(URLDecoder.decode(tmplateEntity.getCtgyNm(), "UTF-8"));
        tmplateEntity.setTmplateTitle(URLDecoder.decode(tmplateEntity.getTmplateTitle(), "UTF-8"));
        // 組織IDを設定する
        tmplateEntity.setOrgId(orgId);
        // テンプレートタイプ区分を設定する
        tmplateEntity.setTmplateTypeDiv("2");
        // 作成日時を設定する
        tmplateEntity.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤを設定する
        tmplateEntity.setCretUsrId(userId);
        // 更新日時を設定する
        tmplateEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤを設定する
        tmplateEntity.setUpdUsrId(userId);
        // 削除フラグを設定する
        tmplateEntity.setDelFlg(0);
        //テンプレートの保存
        mstTmplateDao.insert(tmplateEntity);
        //画面を元に、質問面談テンプレートマスタへ登録する。
        List<MstAskTalkTmplateEntity> mstAskTalkTmplateEntityList = JSON.parseArray(mstAskTalkTmplateList, MstAskTalkTmplateEntity.class);
        for (MstAskTalkTmplateEntity dto : mstAskTalkTmplateEntityList) {
            // 2020/11/24 huangxinliang modify start
            insertAskTalkTemplate(tmplateEntity, orgId, userId, dto);
            // 2020/11/24 huangxinliang modify end
        }
        return R.ok();
    }

    /**
     * <p>テンプレートの更新</p>
     *
     * @param tmplateEntity テンプレートentity
     * @param file 添付ファイルパス
     * @return
     */
    @Override
    public R updateTmplate(MstTmplateEntity tmplateEntity, MultipartFile[] file, String mstAskTalkTmplateList, String filePaths) throws UnsupportedEncodingException {
        // セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ログインユーザＩＤ
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //画像パスを取得
        List<String> pathList = null;
        try {
            pathList = getSrcList(URLDecoder.decode(tmplateEntity.getTmplateCnt(), "UTF-8"), imgPattern, "/gakken/image");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        //画像
        Map<String, String> map = FileUtils.getRealPath(pathList, "/ueditorUpload", "imageUrlPrefix");
        //テンプレート内容
        String cont = tmplateEntity.getTmplateCnt();
        int i = map.size();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (i == 1) {
                tmplateEntity.setTitleImgPath(entry.getValue());
            }
            try {
                cont = cont.replace(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
                i--;
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
        }
        //添付ファイルパス
        String filePath = null;
        //テンプレート内容を設定する
        tmplateEntity.setTmplateCnt(cont);
        try {
            if (file != null) {
                //添付ファイルパスの保存
                StringBuilder filePathTemp = new StringBuilder();
                for (MultipartFile fileItem : file) {
                    String path = getPath(fileItem);
                    // パスが空の場合はスキップします
                    if (!StringUtils.isEmpty(filePathTemp.toString())) {
                        filePathTemp.append(",");
                    }
                    filePathTemp.append(path);
                }
                filePath = filePathTemp.toString();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        //添付ファイルパスを設定する
        if (!StringUtils.isEmpty(filePath)) {
            tmplateEntity.setAttachFilePath(filePath);
        } else {
            if ((file == null || file.length == 0) && StringUtils.isEmpty(filePaths)) {
                tmplateEntity.setAttachFilePath("");
            }
        }
        tmplateEntity.setCtgyNm(URLDecoder.decode(tmplateEntity.getCtgyNm(), "UTF-8"));
        tmplateEntity.setTmplateTitle(URLDecoder.decode(tmplateEntity.getTmplateTitle(), "UTF-8"));
        //テンプレートタイプ区分を設定する
        tmplateEntity.setTmplateTypeDiv("2");
        //更新日時を設定する
        tmplateEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤを設定する
        tmplateEntity.setUpdUsrId(userId);
        //削除フラグを設定する
        tmplateEntity.setDelFlg(0);
        //テンプレートの更新
        mstTmplateDao.updateById(tmplateEntity);
        //質問面談テンプレート編集内容を変更するため、質問面談テンプレート情報へ更新する。
        // 2020/11/24 huangxinliang modify start
        List<MstAskTalkTmplateEntity> mstAskTalkTmplateEntityList = JSON.parseArray(mstAskTalkTmplateList, MstAskTalkTmplateEntity.class);
        for (MstAskTalkTmplateEntity dto : mstAskTalkTmplateEntityList) {
            if (dto.getId() == null) {
                insertAskTalkTemplate(tmplateEntity, orgId, userId, dto);
            } else {
                updateAskTalkTemplate(userId, dto);
            }
        }
        // 2020/11/24 huangxinliang modify end
        return R.ok();
    }

    // 2020/11/24 huangxinliang modify start

    /**
     * 新規
     *
     * @param tmplateEntity エンティティ
     * @param orgId 組織ID
     * @param userId ユーザーID
     * @param dto
     */
    private void insertAskTalkTemplate(MstTmplateEntity tmplateEntity, String orgId, String userId, MstAskTalkTmplateEntity dto) {
        //組織ID
        dto.setOrgId(orgId);
        //テンプレートID
        dto.setTempId(tmplateEntity.getId());
        //作成日時
        dto.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        dto.setCretUsrId(userId);
        //更新日時
        dto.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        dto.setUpdUsrId(userId);
        //削除フラグ
        dto.setDelFlg(0);
        mstAskTalkTmplateDao.insert(dto);
    }

    /**
     * 更新
     *
     * @param userId ユーザーID
     * @param dto エンティティ
     */
    private void updateAskTalkTemplate(String userId, MstAskTalkTmplateEntity dto) {
        MstAskTalkTmplateEntity mstAskTalkTmplateEntity = mstAskTalkTmplateDao.selectById(dto.getId());
        if (!DateUtils.format(mstAskTalkTmplateEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).equals(
                DateUtils.format(dto.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))) {
            //排他チェックエラーの場合 MSGCOMN0019に戻る
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //更新日時
        dto.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        dto.setUpdUsrId(userId);
        //削除フラグ
        dto.setDelFlg(0);
        mstAskTalkTmplateDao.updateById(dto);
    }
    // 2020/11/24 huangxinliang modify end

    /**
     * パスを取得
     *
     * @param htmlCode
     */
    public List<String> getSrcList(String htmlCode, Pattern pattern, String contains) {
        List<String> srcList = new ArrayList<String>();
        Matcher m = pattern.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            if (src.contains(contains)) {
                srcList.add(src);
            }
        }
        return srcList;
    }

    /**
     * ファイルパス
     *
     * @param file 　画像
     * @return fileName
     * @throws IOException
     */
    public String getPath(MultipartFile file) throws IOException {
        // ファイル名を生成
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + DateUtils.format(DateUtils.getSysTimestamp(),
                GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //ローカルパス
        String realPath = ("temp/" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH)).replace(
                "/", File.separator) + File.separator + fileName;
        // 生成パス
        String savePath = GakkenConstant.FILE_PATH_PREFIX + realPath;
        // ファイルを生成
        File destFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath);
        if (!destFile.exists()) {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();
        }
        InputStream in = file.getInputStream();
        FileOutputStream out = new FileOutputStream(destFile);
        byte buffer[] = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        // データベースパス
        String destFileName = savePath;

        return destFileName;
    }
}
