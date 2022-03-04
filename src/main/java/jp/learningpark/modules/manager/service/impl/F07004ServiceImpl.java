/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F07004Dto;
import jp.learningpark.modules.manager.service.F07004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * <p>教科追加・編集画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/04/04: xiong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F07004ServiceImpl implements F07004Service {

    /**
     * コードマスタService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * コードマスタDao
     */
    @Autowired
    private MstCodDDao mstCodDDao;

    /**
     * 登録ボタン押下
     *
     * @param f07004Dto 画面内容
     * @param file1     画面．表示用画像
     * @param file2     画面．ボタン用画像
     * @return R
     * @throws IOException
     */
    @Override
    public R setSubjtInfo(F07004Dto f07004Dto, MultipartFile file1, MultipartFile file2) throws IOException {
        R info = new R();
        // ログインユーザＩＤ
        String usrId = ShiroUtils.getUserId();
        // ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        String decodeSubNm = URLDecoder.decode(f07004Dto.getSubNm(), "UTF-8");

        // 下記条件で、教科名が重複するかどうかをチェックする。
        MstCodDEntity subjt = null;
        // 編集
        if (f07004Dto.getFlg() == 0) {
            // 教科名が重複
            subjt = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().ne("cod_cd", f07004Dto.getSubCd()).eq("cod_key", "SUBJT_DIV").eq("cod_value", decodeSubNm).eq("del_flg", 0));
            if (subjt != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "教科名"));
            }
            subjt = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SUBJT_DIV").eq("cod_cd", f07004Dto.getSubCd()).eq("del_flg", 0));
            // コード値
            subjt.setCodValue(decodeSubNm);
            // コード値２
            if(f07004Dto.getBackgroudColor() != null && !"".equals(f07004Dto.getBackgroudColor())){
                subjt.setCodValue2(f07004Dto.getBackgroudColor());
            }
            // コード値３
            if (file1 != null) {
                subjt.setCodValue3(getPath(brandCd, file1));
            }
            // コード内容
            if (file2 != null) {
                subjt.setCodCont(getPath(brandCd, file2));
            }
            // コード値４
            subjt.setCodValue4(f07004Dto.getCodValue4());
            // コード値５
            subjt.setCodValue5(f07004Dto.getCodValue5());
            // ソート
            subjt.setSort(f07004Dto.getSort());
            // 更新日時
            subjt.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            subjt.setUpdUsrId(usrId);
            mstCodDService.update(subjt, new QueryWrapper<MstCodDEntity>().eq("cod_cd", f07004Dto.getSubCd()).eq("cod_key", "SUBJT_DIV"));
        }
        // 追加
        else {
            // 教科名が重複
            subjt = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SUBJT_DIV").eq("cod_value", decodeSubNm).eq("del_flg", 0));
            if (subjt != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "教科名"));
            }
            // 教科重複
            subjt = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SUBJT_DIV").eq("cod_cd", f07004Dto.getSubCd()).eq("del_flg", 0));
            if (subjt != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "教科CD"));
            }
            subjt = new MstCodDEntity();
            // コードキー
            subjt.setCodKey("SUBJT_DIV");
            // コードCD
            subjt.setCodCd(f07004Dto.getSubCd());
            // コード値
            subjt.setCodValue(decodeSubNm);
            // コード値２
            subjt.setCodValue2(f07004Dto.getBackgroudColor());
            // コード値３
            if (file1 != null) {
                subjt.setCodValue3(getPath(brandCd, file1));
            }
            // コード内容
            if (file2 != null) {
                subjt.setCodCont(getPath(brandCd, file2));
            }
            // コード値４
            subjt.setCodValue4(f07004Dto.getCodValue4());
            // コード値５
            subjt.setCodValue5(f07004Dto.getCodValue5());
            // ソート
            subjt.setSort(f07004Dto.getSort());
            // 作成ユーザＩＤ
            subjt.setCretUsrId(usrId);
            // 作成日時
            subjt.setCretDatime(DateUtils.getSysTimestamp());
            // 更新日時
            subjt.setUpdDatime(DateUtils.getSysTimestamp());
            // 更新ユーザＩＤ
            subjt.setUpdUsrId(usrId);
            mstCodDDao.insert(subjt);
        }
        return info;
    }

    /**
     * ドキュメントパス
     *
     * @param brandCd ブランドコード
     * @param file    　画像
     * @return fileName
     * @throws IOException
     */
    public String getPath(String brandCd, MultipartFile file) throws IOException {
        // ファイル名を生成
        String fileName = System.currentTimeMillis() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // 生成パス
        String savePath = brandCd + File.separator + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH).replace("/", File.separator) + File.separator + fileName;

        // ファイルを生成
        File destFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.img"), savePath);
        if (!destFile.exists()) {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();
        }

        InputStream in = file.getInputStream();
        FileOutputStream out = new FileOutputStream(FileUtils.getStorageFile(MessageUtils.getMessage("path.img"), savePath));
        byte buffer[] = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        // データベースパス
        String destFileName = ".." + GakkenConstant.SERVER_IMG_PREFIX + savePath;

        return destFileName;
    }

}
