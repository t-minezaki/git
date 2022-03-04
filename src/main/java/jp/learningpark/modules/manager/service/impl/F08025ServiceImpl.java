package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstMaxIdDao;
import jp.learningpark.modules.common.dao.MstTmplateDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.service.F08025Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>08025 テンプレート新規編集画面Service</p >
 *
 * @author NWT : cuikailin <br />
 * 変更履歴 <br />
 * 2021/01/27 : cuikailin: 新規<br />
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class F08025ServiceImpl implements F08025Service {

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
     * コードマスタ_明細 dao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    private static Logger logger = LoggerFactory.getLogger(F08025ServiceImpl.class);

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
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "TMPLATE_TYPE_DIV").orderByAsc("sort").eq("del_flg", 0)
                        .between("cod_cd","4","7"));
        if (id != null) {
            String orgId = ShiroUtils.getUserEntity().getOrgId();
            MstTmplateEntity mstTmplateEntity = mstTmplateDao.selectOne(
                    new QueryWrapper<MstTmplateEntity>().and(w -> w.eq("id", id).eq("org_id", orgId).eq("del_flg", 0)));
            if (mstTmplateEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "テンプレート"));
            }
            info.put("tmplate", mstTmplateEntity);
        }
        return info.put("mstCodDEntityList", mstCodDEntityList);
    }

    /**
     * <p>テンプレートの保存</p>
     *
     * @param tmplateEntity テンプレートentity
     * @param file          添付ファイルパス
     * @return
     */
    @Override
    public R addTmplate(MstTmplateEntity tmplateEntity, MultipartFile[] file, String filePaths) throws UnsupportedEncodingException {
        // セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ログインユーザＩＤ
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //MAX採番
        MstMaxIdEntity maxIdEntity = maxIdDao.selectOne(new QueryWrapper<MstMaxIdEntity>().and(w -> w.eq("org_id", orgId).eq("type_div", "t").eq("del_flg", 0)));
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
        tmplateEntity.setCtgyNm(null);
        tmplateEntity.setTmplateTitle(URLDecoder.decode(tmplateEntity.getTmplateTitle(), "UTF-8"));
        // 組織IDを設定する
        tmplateEntity.setOrgId(orgId);
        // テンプレートタイプ区分を設定する
        tmplateEntity.setTmplateTypeDiv(URLDecoder.decode(tmplateEntity.getTmplateTypeDiv(), "UTF-8"));
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

        return R.ok();
    }

    /**
     * <p>テンプレートの更新</p>
     *
     * @param tmplateEntity テンプレートentity
     * @param file          添付ファイルパス
     * @return
     */
    @Override
    public R updateTmplate(MstTmplateEntity tmplateEntity, MultipartFile[] file, String filePaths) throws UnsupportedEncodingException {
        if (tmplateEntity.getUpdDatime()!=null){
            MstTmplateEntity entity = mstTmplateDao.selectById(tmplateEntity.getId());
            if (entity.getUpdDatime().equals(tmplateEntity.getUpdDatime())){
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
        }
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
        tmplateEntity.setCtgyNm(null);
        tmplateEntity.setTmplateTitle(URLDecoder.decode(tmplateEntity.getTmplateTitle(), "UTF-8"));
        //テンプレートタイプ区分を設定する
        tmplateEntity.setTmplateTypeDiv(URLDecoder.decode(tmplateEntity.getTmplateTypeDiv(), "UTF-8"));
        //更新日時を設定する
        tmplateEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤを設定する
        tmplateEntity.setUpdUsrId(userId);
        //削除フラグを設定する
        tmplateEntity.setDelFlg(0);
        //テンプレートの更新
        mstTmplateDao.updateById(tmplateEntity);
        return R.ok();
    }

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
        String realPath = ("temp"+ File.separator + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH)).replace(
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