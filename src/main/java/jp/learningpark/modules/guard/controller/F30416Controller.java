/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstGuardService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUsrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * <p>子供 プロフィール</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/21 : lyh: 新規<br />
 * @version 6.0
 */
@RequestMapping(value = "/guard/F30416")
@RestController
public class F30416Controller {
    /**
     * 生徒基本マスタ Service
     */
    @Autowired
    MstStuService mstStuService;
    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * 保護者基本マスタ
     */
    @Autowired
    MstGuardService mstGuardService;
    /**
     * ユーザ基本マスタ
     */
    @Autowired
    MstUsrService mstUsrService;

    private static Logger logger = LoggerFactory.getLogger(F30416Controller.class);

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init() {
        //生徒ID
        String stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        //生徒基本情報データを取得する。
        MstStuEntity studentInfo = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        //ユーザ基本マスタ
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", stuId));
        //生徒情報が存在しません
        if (studentInfo == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒情報"));
        }
        MstCodDEntity schyDiv = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "SCHY_DIV").eq("cod_cd", studentInfo.getSchyDiv()).eq("del_flg", 0));
        //最初のマネージャー情報
        List<String> guardIdList = new ArrayList();
        if (StringUtils.isNotBlank(studentInfo.getGuardId())) {
            guardIdList.add(studentInfo.getGuardId());
        }
        if (StringUtils.isNotBlank(studentInfo.getGuard1Id())) {
            guardIdList.add(studentInfo.getGuard1Id());
        }
        if (StringUtils.isNotBlank(studentInfo.getGuard2Id())) {
            guardIdList.add(studentInfo.getGuard2Id());
        }
        if (StringUtils.isNotBlank(studentInfo.getGuard3Id())) {
            guardIdList.add(studentInfo.getGuard3Id());
        }
        if (StringUtils.isNotBlank(studentInfo.getGuard4Id())) {
            guardIdList.add(studentInfo.getGuard4Id());
        }
        List<MstGuardEntity> guardList = mstGuardService.list(new QueryWrapper<MstGuardEntity>().in("guard_id", guardIdList).eq("del_flg", 0));
        for (MstGuardEntity dto : guardList) {
            dto.setFlnmNm(dto.getFlnmNm() + " " + dto.getFlnmLnm());
        }
        return R.ok().put("studentInfo", studentInfo).put("schy", schyDiv.getCodValue()).put("guardInfoList", guardList).put("mstUsrEntity", mstUsrEntity);
    }

    /**
     * @param file ファイル
     * @param time データ更新時間への早期アクセス
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(String file, String time) {
        //生徒ID
        String stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        //生徒存在判断
        MstStuEntity stu = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        Map<String, Object> params = new HashMap<>(8);
        //ログインユーザーID
        params.put("userId", ShiroUtils.getUserId());
        if (file != null) {
            //写真交換判定
            params.put("file", file);
        }
        if (stu == null) {
            //生徒情報が存在しません
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒情報"));
        }
        //写真交換判定
        if (params.get("file") != null) {
            try {
                //写真が変更されました
                stu.setPhotPath(Base64ToImage(params.get("file").toString(), stu.getStuId()));
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RRException("ファイル保存エラー");
            }
        }
        if (!StringUtils.equals(DateUtils.format(stu.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO), time)) {
            //情報は期限切れです
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        //システム時間
        stu.setUpdDatime(DateUtils.getSysTimestamp());
        //ログインユーザーID
        stu.setUpdUsrId((String)params.get("userId"));
        //情報の変更
        mstStuService.updateById(stu);
        return R.ok(MessageUtils.getMessage("MSGCOMN0014", "生徒基本マスタ"));
    }

    public static String Base64ToImage(String imgStr, String stuId) throws IOException {
        // 画像データが空です
        if (StringUtils.isEmpty(imgStr)) {
            return null;
        }
        String realPath = "img" + File.separator + "photo" + File.separator + stuId + ".png";
        // 生成サーバパス（データベースパス）
        String savePath = GakkenConstant.FILE_PATH_PREFIX + realPath;
        File newFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath);
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            // Base64デコード
            byte[] b = decoder.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 異常なデータを調整する
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(newFile);
            out.write(b);
            out.flush();
            out.close();
            return savePath;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return savePath;
        }

    }

}