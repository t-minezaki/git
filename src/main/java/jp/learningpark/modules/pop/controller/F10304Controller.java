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
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.utils.dto.BlockDto;
import jp.learningpark.modules.pop.service.F10304Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>そのたブロック登録画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10304")
public class F10304Controller extends AbstractController {

    /**
     * そのたブロック登録画面 Service
     */
    @Autowired
    private F10304Service f10304Service;

    /**
     * ブロッグマスタ Service
     */
    @Autowired
    private MstBlockService blockMstService;

    /**
     * <p>初期表示</p>
     * <p>そのたブロック情報を入手する</p>
     *
     * @param blockId 大分類ブロックid
     * @return 画面情報
     */
    @RequestMapping(value = "/init/{blockId}", method = RequestMethod.GET)
    public R init(@PathVariable int blockId) {
        //小分類ブロックリスト
        List<MstBlockEntity> list = null;
        //大分類ブロック
        MstBlockEntity blockMstEntity = null;
        //大分類ブロック情報を入手する
        blockMstEntity = blockMstService.getById(blockId);
        //小分類ブロックリスト情報を入手する
        list = f10304Service.getBlockListByUpplevBlockIdAndBlockTypeDiv(blockId, blockMstEntity.getBlockTypeDiv());
        return R.ok().put("upBlock", blockMstEntity).put("blockList", list);
    }

    /**
     * <p>登録ボタンの処理</p>
     *
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submitFn(String blockNm, Integer blockId) {
        //学生ID
        String stuId = getUserCd();
        //ブロック情報を入手する
        MstBlockEntity blockMstEntity = blockMstService.getOne(new QueryWrapper<MstBlockEntity>().and(w -> w.eq("stu_id", stuId).eq("block_dispy_nm", blockNm).eq("del_flg", 0)));
        //重複チェック
        if (blockMstEntity != null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0046", blockNm, "ブロックマスタ"));
        } else {
            //追加
            blockMstEntity = blockMstService.getById(blockId);
            //ID
            blockMstEntity.setId(null);
            //生徒ID
            blockMstEntity.setStuId(stuId);
            //作成日時
            blockMstEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            blockMstEntity.setCretUsrId(stuId);
            //更新日時
            blockMstEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            blockMstEntity.setUpdUsrId(stuId);
            blockMstService.save(blockMstEntity);
            //TO F10301
            blockMstEntity = blockMstService.getOne(new QueryWrapper<MstBlockEntity>().and(w -> w.eq("stu_id", stuId).eq("block_dispy_nm", blockNm).eq("del_flg", 0)));
        }
        BlockDto dto = new BlockDto();
        // bLockID
        dto.setBlockId(StringUtils.defaultString(blockMstEntity.getId()));
        // ブロック表示名
        dto.setBlockDispyNm(blockMstEntity.getBlockDispyNm());
        // ブロック種類区分
        dto.setBlockTypeDiv(blockMstEntity.getBlockTypeDiv());
        // ブロック画像区分
        dto.setBlockPicDiv(blockMstEntity.getBlockPicDiv().replace("_grey",""));
        // 上層ブロックID
        dto.setUpplevBlockId(blockMstEntity.getUpplevBlockId());
        // 理解度
        dto.setLearnLevUnds("0");
        return R.ok().put("blockDto", dto);
    }
}
