package jp.learningpark.modules.job.controller;

import jp.learningpark.framework.gakkenID.BookEndApi;
import jp.learningpark.framework.gakkenID.dto.BookEndSendDto;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.job.service.BTGKA1010Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/22 ： NWT)hxl ： 新規作成
 * @date 2021/3/22 16:14
 */
@RestController
@RequestMapping(value = "/manager/BTGKA1010")
public class BTGKA1010Controller {

    /**
     * btgka1010Service
     */
    @Autowired
    BTGKA1010Service btgka1010Service;

    @RequestMapping(value = "/monthExit", method = RequestMethod.GET)
    public R monthExit(){
        return btgka1010Service.monthExit();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public R test(){
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        List<BookEndSendDto> bookEndSendDtoList = new ArrayList<>();
        BookEndSendDto bookEndSendDto = new BookEndSendDto();
        bookEndSendDto.setServiced(GakkenConstant.SERVICE_CD);
        bookEndSendDto.setGidpk("530427d0c5e543f8906f59245a9a0ed8");
        bookEndSendDto.setGroupid(GakkenConstant.GROUP_ID);
        bookEndSendDto.setWithdrawaldate(sysTimestamp);
        bookEndSendDtoList.add(bookEndSendDto);
        BookEndSendDto bookEndSendDto1 = new BookEndSendDto();
        bookEndSendDto1.setServiced(GakkenConstant.SERVICE_CD);
        bookEndSendDto1.setGidpk("85d54261091946559894b6e0003bfda4");
        bookEndSendDto1.setGroupid(GakkenConstant.GROUP_ID);
        bookEndSendDto1.setWithdrawaldate(sysTimestamp);
        bookEndSendDtoList.add(bookEndSendDto1);
        String result = BookEndApi.active(bookEndSendDtoList, "7NA2mxYA6Bw9VrC9CLyrp6DX1pyHW1fPyNed8yZrwD24Gn1K9bLSm112gB9Uye4Z",
                "https://begroup.gakken.jp", "/withdrawalbulk");
        if (StringUtils.isEmpty(result)){
            return R.error("呼び出しに失敗しました。");
        }else {
            return R.ok();
        }
    }
}
