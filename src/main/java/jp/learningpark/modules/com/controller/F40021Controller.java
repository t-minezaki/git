
package jp.learningpark.modules.com.controller;
/**
 * <p>利用規約同意画面</p>
 * <p>Controller</p>
 * <p></p>
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/8/7 : wang: 新規<br />
 * @version 8.0
 */
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.com.service.F40021Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/common")
@RestController
public class F40021Controller {
    /**
     * f40021Service
     */
    @Autowired
    F40021Service f40021Service;

    /**
     * mstUsrService
     */
    @Autowired
    MstUsrService mstUsrService;

    @RequestMapping(value = "/F40021/reset", method = RequestMethod.POST)
    public R getCompare(String updDatime, Boolean gidFlg, Boolean manaFlg) {
//        usrIDを取得
        MstUsrEntity mstusrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",ShiroUtils.getUserId()));
//        排他チェックエラーの場合
        if(!DateUtils.format(mstusrEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS).equals(updDatime)){
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        return f40021Service.upDateId(gidFlg, manaFlg);
    }

    @RequestMapping(value = "/F40021/init", method = RequestMethod.POST)
    public R getData(){
        return f40021Service.getDate();
    }
}
