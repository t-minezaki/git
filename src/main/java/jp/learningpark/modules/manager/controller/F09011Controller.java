package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09011SaveDto;
import jp.learningpark.modules.manager.service.F09011Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>ポイント設定確認画面</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F09011")
public class F09011Controller {

    private final F09011Service f09011Service;

    public F09011Controller(F09011Service f09011Service) {
        this.f09011Service = f09011Service;
    }

    @GetMapping("/init")
    public R init(String stuIdList, Integer limit, Integer page){
        List<String> stuIds = JSON.parseArray(stuIdList, String.class);
        // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (stuIds.isEmpty()){
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "ユーザー"));
        }
        return f09011Service.init(stuIds, limit, page);
    }

    @PostMapping("/save")
    public R save(@RequestBody F09011SaveDto saveDto){
        f09011Service.saveOrUpdate(saveDto);
        return R.ok();
    }

}
