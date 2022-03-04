/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dao.F00061Dao;
import jp.learningpark.modules.manager.dto.F00061Dto;
import jp.learningpark.modules.manager.service.F00061Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F00061_メンター生徒関係検索一覧 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/18 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F00061ServiceImpl implements F00061Service {

    /**
     * F00061_メンター生徒関係検索一覧 Dao
     */
    @Autowired
    private F00061Dao f00061Dao;

    /**
     * 生徒メンター関係一覧のリストの情報
     *
     * @param dto   　画面．検索条件
     * @param limit 　画面表示の数
     * @param page  　現在のページ
     * @return
     */
    @Override
    public R getList(F00061Dto dto, Integer limit, Integer page) {
        List<F00061Dto> f00061DtoList=f00061Dao.selectList(dto,limit,(page-1)*limit);
        if(f00061DtoList.size()==0){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","先生生徒関係"));
        }
        for(F00061Dto dto1 : f00061DtoList){
            if(dto1.getBirthd()!=null){
                dto1.setBirthdStr(DateUtils.format(dto1.getBirthd(), GakkenConstant.DATE_FORMAT_YYYYMD));
            }else{
                dto1.setBirthdStr("");
            }

           dto1.setUpdDatimeStr(DateUtils.format(dto1.getUpdDatime(),GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        Integer total=f00061Dao.count(dto);
        return R.ok().put("page",new PageUtils(f00061DtoList,total,limit,page));
    }
}
