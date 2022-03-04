/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.dao.MstColorBlockDao;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstColorBlockEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstColorBlockService;
import jp.learningpark.modules.student.dto.F10004Dto;
import jp.learningpark.modules.student.service.F10004Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F10004_スケジュールブロックの色設定画面</p >
 *
 * @author NWT : zhaoxiaoqin <br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10004")
public class F10004Controller extends AbstractController {
    /**
     * F10004Service
     */
    @Autowired
    private F10004Service f10004Service;

    /**
     * mstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * mstColorBlockService
     */
    @Autowired
    private MstColorBlockService mstColorBlockService;

    /**
     * mstColorBlockService
     */
    @Autowired
    private MstColorBlockDao mstColorBlockDao;
    /**
     * mstBlockService
     */
    @Autowired
    private MstBlockService mstBlockService;

    /**
     * <p>F10004_スケジュールブロックの色設定画面 初期化</p>
     *
     * @return R
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @RequiresPermissions("student")
    public R init() {

        // コードマスタをもとに、教科のマスタ存在チェックを行う
        Integer subjtNum = mstCodDService.count(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SUBJT_DIV"));
        // 存在しない場合、処理を中断し、画面上部のエラーメッセージ領域でエラーメッセージ（MSGCOMN0017）を表示する。
        if (subjtNum <= 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "コードマスタの教科"));
        }

        // セッションデータ．生徒ＩＤ
        String stuId = ShiroUtils.getUserEntity().getUsrId();
        // セッションデータ．塾ＩＤ
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 生徒教科情報取得
        List<F10004Dto> subjtList = f10004Service.selectSubjtInfo(stuId, orgId);
        //教科書の色初期判定
        colorInitCheck(subjtList);
        //生徒フリーブロック情報取得
        List<F10004Dto> freeBlockList = f10004Service.selectFreeInfo(stuId);
        //フリーブロックの色初期判定
        colorInitCheck(freeBlockList);
        //生徒固定ブロック情報取得
        List<F10004Dto> fixedBlockList = f10004Service.selectFixedInfo(stuId);
        //固定ブロックの色初期判定
        colorInitCheck(fixedBlockList);
        //生徒固定ブロック情報取得
        List<F10004Dto> otherBlockList = f10004Service.selectOtherInfo(stuId);
        //固定ブロックの色初期判定
        colorInitCheck(otherBlockList);
        return R.ok().put("subjtList", subjtList).put("freeBlockList", freeBlockList).put("fixedBlockList", fixedBlockList).put("otherBlockList", otherBlockList);
    }

    /**
     * <p>F10004_スケジュールブロックの色設定画面 登録・更新</p>
     * <p>
     * subjtDiv 画面．教科区
     * color 画面．選択色
     *
     * @return R
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("student")
    public R save(Integer id, String color, Integer blockId, String subjtDiv) {

        // セッションデータ．生徒ＩＤ
        String stuId = ShiroUtils.getUserEntity().getUsrId();

        // セッションデータ．ユーザーID
        String userId = ShiroUtils.getUserEntity().getUsrId();

        MstColorBlockEntity mstColorBlockEntity = new MstColorBlockEntity();
        if (id != null) {
            // カラーブロック管理に存在するかどうかを判定する
            mstColorBlockEntity = mstColorBlockService.getOne(new QueryWrapper<MstColorBlockEntity>().select("id").eq("id", id).eq(
                    "del_flg", 0));
            //カラーCD
            mstColorBlockEntity.setColorId(color);
            //更新日時
            mstColorBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstColorBlockEntity.setUpdUsrId(userId);
            // 登録処理を行
            mstColorBlockService.update(mstColorBlockEntity, new QueryWrapper<MstColorBlockEntity>().select("id").eq("id", id).eq(
                    "del_flg", 0));
        } else {
            if (!StringUtils.isEmpty(subjtDiv)) {
                // 取得できない場合、下記登録処理を行う

                mstColorBlockEntity = new MstColorBlockEntity();
                // 生徒ID
                mstColorBlockEntity.setStuId(stuId);
                // カラーCD
                mstColorBlockEntity.setColorId(color);
                // ブロック種類区分
                mstColorBlockEntity.setBlockTypeDiv("S1");
                // 教科区分
                mstColorBlockEntity.setSubjtDiv(subjtDiv);
                // ブロックID
                mstColorBlockEntity.setBlockId(null);
                // 作成日時
                mstColorBlockEntity.setCretDatime(DateUtils.getSysTimestamp());
                // 作成ユーザＩＤ
                mstColorBlockEntity.setCretUsrId(userId);
                // 更新日時
                mstColorBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ
                mstColorBlockEntity.setUpdUsrId(userId);
                // 削除フラグ
                mstColorBlockEntity.setDelFlg(0);

                // 登録処理を行
                mstColorBlockService.save(mstColorBlockEntity);
            } else {
                MstBlockEntity mstBlockEntity = mstBlockService.getById(blockId);
                //ブロッグマスタ．ブロック種類区分 = 「R1：復習」 、「W1：学校の宿題」、「V1：塾の宿題」の場合
                if(StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"R1")||StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"W1")||StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"V1")){
                    mstColorBlockEntity = mstColorBlockService.getOne(new QueryWrapper<MstColorBlockEntity>().eq("stu_id",
                            stuId).ne("block_type_div",null).eq("block_type_div",mstBlockEntity.getBlockTypeDiv()).eq("del_flg",0));
                    if (mstColorBlockEntity != null){
                        //カラーブロック管理．ブロックID IS  NULL
                        mstColorBlockEntity.setBlockId(null);
                        //カラーCD
                        mstColorBlockEntity.setColorId(color);
                        //更新日時
                        mstColorBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mstColorBlockEntity.setUpdUsrId(userId);
                        // 登録処理を行
                        mstColorBlockDao.updateAllColumnById(mstColorBlockEntity);
                    }else {
                        mstColorBlockEntity = new MstColorBlockEntity();
                        // 生徒ID
                        mstColorBlockEntity.setStuId(stuId);
                        // カラーCD
                        mstColorBlockEntity.setColorId(color);
                        // ブロック種類区分
                        mstColorBlockEntity.setBlockTypeDiv(mstBlockEntity.getBlockTypeDiv());
                        // 教科区分
                        mstColorBlockEntity.setSubjtDiv(mstBlockEntity.getBlockTypeDiv());
                        // ブロックID
                        mstColorBlockEntity.setBlockId(null);
                        //作成ユーザＩＤ
                        mstColorBlockEntity.setUpdUsrId(userId);
                        //作成日時
                        mstColorBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mstColorBlockEntity.setCretUsrId(userId);
                        //更新日時
                        mstColorBlockEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //削除フラグ
                        mstColorBlockService.save(mstColorBlockEntity);
                    }
                }
                //ブロッグマスタ．ブロック種類区分 = 「C1：通常」 、「O1：その他（趣味）」、「O2：その他（習い事）」、「O3：その他（その他）」の場合
                if (StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"C1")||StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"O1")||StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"O2")||StringUtils.equals(mstBlockEntity.getBlockTypeDiv(),"O3")){
                    mstColorBlockEntity = mstColorBlockService.getOne(new QueryWrapper<MstColorBlockEntity>().eq("stu_id",
                            stuId).ne("block_type_div",null).eq("block_id",mstBlockEntity.getId()).eq("del_flg",0));
                    if (mstColorBlockEntity != null){
                        //カラーブロック管理．内容区分 IS  NULL
                        mstColorBlockEntity.setSubjtDiv(null);
                        //カラーCD
                        mstColorBlockEntity.setColorId(color);
                        //更新日時
                        mstColorBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mstColorBlockEntity.setUpdUsrId(userId);
                        // 登録処理を行
                        mstColorBlockDao.updateAllColumnById(mstColorBlockEntity);
                    }else {
                        mstColorBlockEntity = new MstColorBlockEntity();
                        // 生徒ID
                        mstColorBlockEntity.setStuId(stuId);
                        // カラーCD
                        mstColorBlockEntity.setColorId(color);
                        // ブロック種類区分
                        mstColorBlockEntity.setBlockTypeDiv(mstBlockEntity.getBlockTypeDiv());
                        // 教科区分
                        mstColorBlockEntity.setSubjtDiv(null);
                        // ブロックID
                        mstColorBlockEntity.setBlockId(mstBlockEntity.getId());
                        //作成ユーザＩＤ
                        mstColorBlockEntity.setUpdUsrId(userId);
                        //作成日時
                        mstColorBlockEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mstColorBlockEntity.setCretUsrId(userId);
                        //更新日時
                        mstColorBlockEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //削除フラグ
                        mstColorBlockService.save(mstColorBlockEntity);
                    }
                }
            }
        }
        return R.ok();
    }

    /**
     * その他ブロックの色初期判定
     * @param list
     * @return
     */
    public List<F10004Dto> colorInitCheck(List<F10004Dto> list) {
        for (F10004Dto f10004Dto : list) {
            f10004Dto.setBlockPicDiv("../img/" + f10004Dto.getBlockPicDiv());
            //カラーブロック管理．カラーCD IS NULL
            if (f10004Dto.getColor() == null) {
                //ブロック
                f10004Dto.setColor("#ffffff");
                //ブロック表示名
                f10004Dto.setColorId("#000000");
            } else {
                //ブロック表示名
                f10004Dto.setColorId("#ffffff");
            }
        }
        return list;
    }
}
