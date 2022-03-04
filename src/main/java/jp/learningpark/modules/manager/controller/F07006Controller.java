/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstBlockDao;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.MstOrgService;
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

/**
 * <p>その他ブロック大分類追加・編集画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/12: yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f07006")
@RestController
public class F07006Controller {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;
    @Autowired
    private MstBlockDao mstBlockDao;

    @Autowired
    private MstBlockService mstBlockService;

    /**
     * <p>初期表示</p>
     *
     * @param id ブロックID
     * @return 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f07006Init(Integer id) {
        R info = new R();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //
//        String brandCd = ShiroUtils.getBrandcd();
        // 組織
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("org", org);
        MstBlockEntity mstBlockEntity;
        String path = ".." + GakkenConstant.SERVER_IMG_PREFIX;
        if (id != null) {
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().select("id", "block_dispy_nm", "block_type_div", "block_pic_div").and(w -> w.eq("id", id).eq("del_flg", 0)));
            if (mstBlockEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "その他ブロック")).put("org", org);
            }
            info.put("mstBlockEntity", mstBlockEntity).put("path", path);
        }
        return info;
    }

    /**
     * <P>登録ボタン押下<p/>
     *
     * @param id           ブロックID
     * @param blockDispyNm ブロック表示名
     * @param blockTypeDiv ブロック種類区分
     * @param file1        大分類画像
     * @param file2        小分類画像
     * @param flg          判断するflg
     * @return R
     * @throws IOException
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R f07006Submit(Integer id, String blockDispyNm, String blockTypeDiv, MultipartFile file1, MultipartFile file2, Integer flg) throws IOException {
        R info = new R();
        String codedBlockDisplayNm = URLDecoder.decode(blockDispyNm, "UTF-8");
        String usrId = ShiroUtils.getUserId();
        // ブランドコードを取得する
        String brandCd = ShiroUtils.getBrandcd();
        MstBlockEntity mstBlockEntity = null;
        // 編集
        if (flg == 0) {
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w -> w.ne("id", id)
                    .likeRight("block_type_div", "O").isNull("upplev_block_id").eq("block_dispy_nm", codedBlockDisplayNm).eq("del_flg", 0)));
            //上記取得できた場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
            if (mstBlockEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "大分類名"));
            }
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w -> w.eq("id", id)
                    .likeRight("block_type_div", "O").isNull("upplev_block_id").eq("del_flg", 0)));
            //ブロック表示名
            mstBlockEntity.setBlockDispyNm(codedBlockDisplayNm);
            //ブロック種類区分
            mstBlockEntity.setBlockTypeDiv("O" + blockTypeDiv);
            //ブロック画像区分
            if (file1 != null) {
                String fileName = "schedule" + id + "_grey.png";
                getPath(brandCd, file1, fileName);
                mstBlockEntity.setBlockPicDiv("schedule" + id + ".png");
            }
            if (file2 != null) {
                String fileName = "schedule" + id + ".png";
                getPath(brandCd, file2, fileName);
                mstBlockEntity.setBlockPicDiv("schedule" + id + ".png");
            }
            //更新日時
            mstBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstBlockEntity.setUpdUsrId(usrId);
            mstBlockService.update(mstBlockEntity, new QueryWrapper<MstBlockEntity>().eq("id", id));
        }
        // 追加
        else {
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().likeRight("block_type_div", "O").isNull("upplev_block_id").eq("block_dispy_nm", codedBlockDisplayNm).eq("del_flg", 0));
            if (mstBlockEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "大分類名"));
            }
            mstBlockEntity = new MstBlockEntity();
            //ブロック表示名
            mstBlockEntity.setBlockDispyNm(codedBlockDisplayNm);
            //ブロック種類区分
            mstBlockEntity.setBlockTypeDiv("O" + blockTypeDiv);
            //作成日時
            mstBlockEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstBlockEntity.setCretUsrId(usrId);
            //更新日時
            mstBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstBlockEntity.setUpdUsrId(usrId);
            //削除フラグ
            mstBlockEntity.setDelFlg(0);
            mstBlockDao.insert(mstBlockEntity);
            //ブロック画像区分
            if (file1 != null) {
                String fileName = "schedule" + mstBlockEntity.getId() + "_grey.png";
                getPath(brandCd, file1, fileName);
                mstBlockEntity.setBlockPicDiv("schedule" + mstBlockEntity.getId() + ".png");
            }
            if (file2 != null) {
                String fileName = "schedule" + mstBlockEntity.getId() + ".png";
                getPath(brandCd, file2, fileName);
                mstBlockEntity.setBlockPicDiv("schedule" + mstBlockEntity.getId() + ".png");
            }
            mstBlockService.update(mstBlockEntity, new QueryWrapper<MstBlockEntity>().eq("id", mstBlockEntity.getId()));
        }
        return info;
    }

    /**
     * <p>画像をアップロード<p/>
     *
     * @param brandCd  ブランドコード
     * @param file     アップロードされたファイル
     * @param fileName 画像名
     * @return fileName 画像名
     * @throws IOException
     */
    public void getPath(String brandCd, MultipartFile file, String fileName) throws IOException {
        // ファイル名を生成
//        String fileName = System.currentTimeMillis() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // 生成パス
        String savePath = File.separator + fileName;

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
//        String destFileName = ".." + GakkenConstant.SERVER_IMG_PREFIX + savePath;
    }
}
