/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F07012Dto;
import jp.learningpark.modules.manager.service.F07012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;

/**
 * <p>F07012 成績教科追加・編集画面 Controller/p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * <p>
 * 2019/04/26: yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f07012")
@RestController
public class F07012Controller {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;
    /**
     * F07012Service
     */
    @Autowired
    F07012Service f07012Service;
    /**
     * MstCodDService
     */
    @Autowired
    MstCodDService mstCodDService;
    //MstCodDDao
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * <p>初期表示</p>
     *
     * @param codCd 教科CD
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f07012Init(String codCd) {
        R info = new R();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("org", org);
        List<MstCodDEntity> mstCodDEntityList =
                mstCodDService.list(new QueryWrapper<MstCodDEntity>().and(w -> w.eq("cod_key", "SCHY_DIV").eq("del_flg",0)).orderByAsc(
                "sort"));
        if (mstCodDEntityList.size() == 0) {
            //上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年")).put("org", org);
        }
        info.put("schyList", mstCodDEntityList);
        F07012Dto mstcodEntity = null;
        if (codCd != null) {
            //上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            mstcodEntity = f07012Service.getF07012Entity(codCd);
            if (mstcodEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("org", org);
            }
            info.put("mstcodEntity", mstcodEntity);
        }
        return info;
    }


    /**
     * <p>登録</p>
     *
     * @param schy       学年
     * @param sort       ソート
     * @param codCd      教科CD
     * @param subName    教科名
     * @param backGround 背景色
     * @param file       表示用画像パス
     * @param flg        状態flg
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f07012Submit(String schy, Integer sort, String codCd, String subName, String backGround, MultipartFile file, Integer flg) throws IOException {
        R info = new R();
        String userId = ShiroUtils.getUserId();
        String brandCd = ShiroUtils.getBrandcd();
        MstCodDEntity mstcodEntity = null;
        String decodedSubName = URLDecoder.decode(subName, "UTF-8");
        // 編集
        if (flg == 0) {
            mstcodEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().and(w -> w.ne("cod_cd", codCd).eq("cod_key", "TEST_SUBJT_DIV")
                    .eq("cod_value_5", schy).eq("cod_value", decodedSubName).eq("del_flg", 0)));
            //下記条件で、教科名が重複するかどうかをチェックする。
            if (mstcodEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "教科名"));
            }
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().and(w -> w.eq("cod_cd", codCd)
                    .eq("cod_key", "TEST_SUBJT_DIV").eq("del_flg", 0)));
            //コード値５
            mstCodDEntity.setCodValue5(schy);
            //ソート
            mstCodDEntity.setSort(sort);
            //コード値
            mstCodDEntity.setCodValue(decodedSubName);
            if (file != null) {
                //コード内容
                mstCodDEntity.setCodCont(getPath(brandCd, file));
            }
            //コード値２
            mstCodDEntity.setCodValue2(backGround);
            //更新日時
            mstCodDEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstCodDEntity.setUpdUsrId(userId);
            //削除フラグ
            mstCodDEntity.setDelFlg(0);
            //コードマスタ更新
            mstCodDService.update(mstCodDEntity, new QueryWrapper<MstCodDEntity>().eq("cod_cd", codCd).eq("cod_key", "TEST_SUBJT_DIV").eq("del_flg", 0));
        }
        //追加
        else {
            //下記条件で、教科名が重複するかどうかをチェックする。
            mstcodEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().and(w -> w.ne("cod_cd", codCd).eq("cod_key", "TEST_SUBJT_DIV")
                    .eq("cod_value_5", schy).eq("cod_value", decodedSubName).eq("del_flg", 0)));
            //下記条件で、教科名が重複するかどうかをチェックする。
            if (mstcodEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "教科名"));
            }
            //下記条件で、教科CDが重複するかどうかをチェックする
            mstcodEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().and(w -> w.eq("cod_key", "TEST_SUBJT_DIV").eq("cod_cd", codCd).eq("del_flg", 0)));
            if (mstcodEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "教科CD"));
            }
            mstcodEntity = new MstCodDEntity();
            //コードキー
            mstcodEntity.setCodKey("TEST_SUBJT_DIV");
            //コード値
            mstcodEntity.setCodValue(decodedSubName);
            //コードCD
            mstcodEntity.setCodCd(codCd);
            //コード値２
            mstcodEntity.setCodValue2(backGround);
            //コード値５
            mstcodEntity.setCodValue5(schy);
            ////コード内容
            if (file != null) {
                mstcodEntity.setCodCont(getPath(brandCd, file));
            }
            //ソート
            mstcodEntity.setSort(sort);
            //更新日時
            mstcodEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstcodEntity.setUpdUsrId(userId);
            //作成日時
            mstcodEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstcodEntity.setCretUsrId(userId);
            //削除フラグ
            mstcodEntity.setDelFlg(0);
            //コードマスタ登録
            mstCodDDao.insert(mstcodEntity);
        }
        return info;
    }

    /**
     * <p>画像をアップロード</p>
     *
     * @param brandCd ブランドコード
     * @param file    アップロードされたファイル
     * @return
     * @throws IOException
     */
    private String getPath(String brandCd, MultipartFile file) throws IOException {
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
        FileOutputStream out = new FileOutputStream(destFile);
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










