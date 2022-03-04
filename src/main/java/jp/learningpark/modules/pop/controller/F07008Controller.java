/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstBlockDao;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.MstOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * <p>教科追加・編集画面</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/12: yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/f07008")
@RestController
public class F07008Controller {

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
     *  <p>初期表示</p>
     *  
     * @param id
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f07008Init(Integer id) {
        R info = new R();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("org", org);
        MstBlockEntity mstBlockEntity;
        if(id != null){
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().select("block_dispy_nm","block_type_div").and(w-> w.eq("id",id).eq("del_flg",0)));
            if(mstBlockEntity == null){
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "その他ブロック")).put("org", org);
            }
            info.put("mstBlockEntity",mstBlockEntity);
        }
        return info;
    }
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public R f07008Submit(Integer id,String blockDispyNm,String blockTypeDiv,Integer flg,Integer upid) throws UnsupportedEncodingException {
        R info = new R();
        String usrId = ShiroUtils.getUserId();
        String codedblockDispyNm = URLDecoder.decode(blockDispyNm, "UTF-8");
        MstBlockEntity mstBlockEntity = null;
        // 編集
        if(flg == 0){
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w-> w.eq("id",id)
                    .isNotNull("stu_id").eq("del_flg",0)));
            //上記が取得できた場合、エラーとなり、画面上部のエラーメッセージ領域でワーニングメッセージを表示する。
            if(mstBlockEntity != null){
                return R.error(MessageUtils.getMessage("MSGCOMN0081","その他ブロック"));
            }
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w-> w.ne("id",id)
                        .likeRight("block_type_div","O").isNotNull("upplev_block_id").eq("block_dispy_nm",codedblockDispyNm).eq("del_flg",0)));
            if(mstBlockEntity!=null){
                return R.error(MessageUtils.getMessage("MSGCOMN0097","'小分類名"));
            }

            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().and(w-> w.eq("id",id).eq("del_flg",0)));
            //ブロック表示名
            mstBlockEntity.setBlockDispyNm(codedblockDispyNm);
            //ブロック種類区分
            mstBlockEntity.setBlockTypeDiv("O"+blockTypeDiv);
            //ブロック画像区分
            //更新日時
            mstBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstBlockEntity.setUpdUsrId(usrId);
            mstBlockService.update(mstBlockEntity, new QueryWrapper<MstBlockEntity>().eq("id",id));
            info.put("id",mstBlockEntity.getUpplevBlockId());
        }
        // 追加
        else {
            mstBlockEntity = mstBlockService.getOne(new QueryWrapper<MstBlockEntity>().likeRight("block_type_div","O").isNotNull("upplev_block_id")
                    .eq("block_dispy_nm",codedblockDispyNm).eq("del_flg",0));
            if (mstBlockEntity !=null){
                return R.error(MessageUtils.getMessage("MSGCOMN0097","小分類名"));
            }
            mstBlockEntity = new MstBlockEntity();
            //ブロック表示名
            mstBlockEntity.setBlockDispyNm(codedblockDispyNm);
            //上層ブロックID
            mstBlockEntity.setUpplevBlockId(upid);
            //ブロック種類区分
            mstBlockEntity.setBlockTypeDiv("O"+blockTypeDiv);
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
            mstBlockService.update(mstBlockEntity, new QueryWrapper<MstBlockEntity>().eq("id",mstBlockEntity.getId()));
            info.put("id",upid);
        }
        return info;
    }
}
