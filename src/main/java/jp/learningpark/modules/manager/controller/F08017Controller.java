package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.manager.service.F08017Service;
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
 * <p>08017 テンプレート新規編集画面Controller</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/08/06 : hujiaxing: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08017")
public class F08017Controller {

    /**
     *
     */
    @Autowired
    MstTmplateService mstTmplateService;

    /**
     * テンプレート新規編集画面Service
     */
    @Autowired
    F08017Service f08017Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>初期表示</p>
     * @param id テンプレートid
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id) {
        return f08017Service.getTmplateById(id);
    }

    /**
     * <p>登録项目</p>
     * @param tmplateEntity テンプレートentuty
     * @param file 添付ファイルパス
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R add( MstTmplateEntity tmplateEntity, MultipartFile[] file,String mstAskTalkTmplateList, String filePaths) {
        R info = null;
        try {
            // 登録项目
            info = f08017Service.addTmplate(tmplateEntity,file,mstAskTalkTmplateList,filePaths);
        } catch (IOException e) {
            info = R.error();
            logger.error(e.getMessage());
        }
        return info;
    }

    /**
     * <p>更新项目</p>
     * @param tmplateEntity テンプレートentuty
     * @param file 添付ファイルパス
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(MstTmplateEntity tmplateEntity, MultipartFile[] file, String mstAskTalkTmplateList, String filePaths) throws UnsupportedEncodingException {

        //idによってイベントを取得する
        MstTmplateEntity mstTmplateEntity = mstTmplateService.getOne(new QueryWrapper<MstTmplateEntity>().select("id", "tmplate_cd", "upd_datime").eq("id", tmplateEntity.getId()));

        if (!DateUtils.format(mstTmplateEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).equals(DateUtils.format(tmplateEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO))) {
            //排他チェックエラーの場合 MSGCOMN0019に戻る
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0019"));
        }
        tmplateEntity.setTmplateCd(mstTmplateEntity.getTmplateCd());
        // 更新项目
        return f08017Service.updateTmplate(tmplateEntity,file,mstAskTalkTmplateList,filePaths);
    }
}
