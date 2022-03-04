package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.manager.service.F08025Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>08025 テンプレート新規編集画面Controller</p >
 *
 * @author NWT : cuikailin <br />
 * 変更履歴 <br />
 * 2021/07/27 : cuikailin: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08025")
public class F08025Controller {

    /**
     * テンプレートマスタ　Service
     */
    @Autowired
    MstTmplateService mstTmplateService;

    /**
     * テンプレート新規編集画面Service
     */
    @Autowired
    F08025Service f08025Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>初期表示</p>
     *
     * @param id テンプレートid
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id) {
        return f08025Service.getTmplateById(id);
    }

    /**
     * <p>登録项目</p>
     *
     * @param tmplateEntity テンプレートentuty
     * @param file          添付ファイルパス
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R add(MstTmplateEntity tmplateEntity, MultipartFile[] file, String filePaths) {
        R info = null;
        try {
            // 登録项目
            info = f08025Service.addTmplate(tmplateEntity, file, filePaths);
        } catch (IOException e) {
            info = R.error();
            logger.error(e.getMessage());
        }
        return info;
    }

    /**
     * <p>更新项目</p>
     *
     * @param tmplateEntity テンプレートentuty
     * @param file          添付ファイルパス
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(MstTmplateEntity tmplateEntity, MultipartFile[] file, String filePaths) throws UnsupportedEncodingException {

        //idによってイベントを取得する
        MstTmplateEntity mstTmplateEntity = mstTmplateService.getOne(new QueryWrapper<MstTmplateEntity>().select("id", "tmplate_cd", "upd_datime", "TMPLATE_TYPE_DIV").eq("id", tmplateEntity.getId()));

        if (!DateUtils.format(mstTmplateEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).equals(DateUtils.format(tmplateEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))) {
            //排他チェックエラーの場合 MSGCOMN0019に戻る
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0019"));
        }
        tmplateEntity.setTmplateCd(mstTmplateEntity.getTmplateCd());
        // 更新项目
        return f08025Service.updateTmplate(tmplateEntity, file, filePaths);
    }
}
