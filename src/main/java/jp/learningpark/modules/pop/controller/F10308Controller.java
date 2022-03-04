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
import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.StuWeeklyPlanPerfService;
import jp.learningpark.modules.common.utils.dto.BlockDto;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>F10308_そのたブロック名前登録画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/pop/F10308")
public class F10308Controller extends AbstractController {

    /**
     * ブロッグマスタ Service
     */
    @Autowired
    private MstBlockService blockMstService;

    /**
     * 生徒ウィークリー計画実績設定 Service
     */
    @Autowired
    private StuWeeklyPlanPerfService weeklyPlanPerfService;

    /**
     * <p>初期表示</p>
     * <p>そのたブロック情報を入手する</p>
     *
     * @param bigBlockId   大分類ブロックid
     * @param smallBlockId 小分類ブロックid
     * @return 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public R init(int bigBlockId, int smallBlockId) {
        //小分類ブロック
        MstBlockEntity smallBlockMstEntity;
        //大分類ブロック
        MstBlockEntity bigBlockMstEntity;
        //F10304
        if (bigBlockId != 0) {
            bigBlockMstEntity = blockMstService.getById(bigBlockId);
            return R.ok().put("bigBlock", bigBlockMstEntity);
        } else {
            //F10301
            if (smallBlockId != 0) {
                smallBlockMstEntity = blockMstService.getById(smallBlockId);
                if (smallBlockMstEntity == null) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "小分類の情報"));
                }
                bigBlockMstEntity = blockMstService.getById(smallBlockMstEntity.getUpplevBlockId());
                return R.ok().put("bigBlock", bigBlockMstEntity).put("smallBlock", smallBlockMstEntity);
            } else {
                return R.error();
            }
        }
    }

    /**
     * <p>登録ボタンの処理</p>
     *
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submitFn(Integer blockId, String smallBlockNm) {
        //生徒Id
        String stuId = getUserCd();
        //上層ブロック情報を入手する
        MstBlockEntity blockMstEntity = blockMstService.getById(blockId);
        if (blockMstEntity == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "大分類の情報", ""));
        }
        //小分類をブロック
        MstBlockEntity smallBlockMstEntity =blockMstService.getOne(new QueryWrapper<MstBlockEntity>().and(wrapper->wrapper.eq("stu_id",stuId).eq("block_dispy_nm",smallBlockNm).eq("del_flg",0)));
        //重複チェック
        if (smallBlockMstEntity != null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0046", smallBlockMstEntity.getBlockDispyNm(), "ブロックマスタ"));
        } else {
            try {
                smallBlockMstEntity = new MstBlockEntity();
                //ブロック表示名
                smallBlockMstEntity.setBlockDispyNm(smallBlockNm);
                //上層ブロックID
                smallBlockMstEntity.setUpplevBlockId(blockMstEntity.getId());
                //生徒ID
                smallBlockMstEntity.setStuId(stuId);
                //ブロック種類区分
                smallBlockMstEntity.setBlockTypeDiv(blockMstEntity.getBlockTypeDiv());
                //ブロック画像区分
                smallBlockMstEntity.setBlockPicDiv(blockMstEntity.getBlockPicDiv().replace("_grey",""));
                //作成日時
                smallBlockMstEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                smallBlockMstEntity.setCretUsrId(stuId);
                //更新日時
                smallBlockMstEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                smallBlockMstEntity.setUpdUsrId(stuId);
                //追加
                blockMstService.save(smallBlockMstEntity);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return R.error(500, "不明なシステムエラー！");
            }
        }
        BlockDto dto = new BlockDto();
        // bLockID
        dto.setBlockId(StringUtils.defaultString(smallBlockMstEntity.getId()));
        // ブロック表示名
        dto.setBlockDispyNm(smallBlockMstEntity.getBlockDispyNm());
        // ブロック種類区分
        dto.setBlockTypeDiv(smallBlockMstEntity.getBlockTypeDiv());
        // ブロック画像区分
        dto.setBlockPicDiv(smallBlockMstEntity.getBlockPicDiv());
        // 上層ブロックID
        dto.setUpplevBlockId(smallBlockMstEntity.getUpplevBlockId());
        // 理解度
        dto.setLearnLevUnds("0");
        return R.ok().put("blockDto", dto);
    }

    /**
     * <p>削除ボタンの処理</p>
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R deltFn(Integer blockId) {
        //生徒Id
        String stuId=getUserCd();
        try {
            StuWeeklyPlanPerfEntity weeklyPlanPerfEntity= weeklyPlanPerfService.getOne(new QueryWrapper<StuWeeklyPlanPerfEntity>().and(wrapepr->wrapepr.eq("stu_id",stuId).eq("unit_id",blockId).eq("del_flg",0)));
            //存在チェック
            if(weeklyPlanPerfEntity!=null){
                MstBlockEntity blockMstEntity=blockMstService.getById(blockId);
                return R.error(MessageUtils.getMessage("MSGCOMN0049",blockMstEntity.getBlockDispyNm()));
            }
            blockMstService.removeById(blockId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error("当小分類が削除に失敗しました。");
        }
        return R.ok();
    }
}
