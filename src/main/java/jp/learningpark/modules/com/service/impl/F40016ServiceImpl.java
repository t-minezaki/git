package jp.learningpark.modules.com.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.dao.F40016Dao;
import jp.learningpark.modules.com.dto.F40016Dto;
import jp.learningpark.modules.com.service.F40016Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>教室選択画面(生徒スマホ画面)</p >
 *
 * @author NWT : lpp <br />
 * 変更履歴 <br />
 * 2020/08/05 : lpp: 新規<br />
 * @version 8.0
 */
@Service
public class F40016ServiceImpl implements F40016Service {


    /**
     * f40016Dao
     */
    @Autowired
    F40016Dao f40016Dao;


    /**
     * 組織情報データを取得する
     * @param afterUsrId セッションデータ．ログインID
     * @return
     */
    @Override
    public R search(String afterUsrId){
        //組織情報データを取得する
        List<F40016Dto> f40016DtoList =  f40016Dao.search(afterUsrId);
        //データを返す
        return R.ok().put("list",f40016DtoList);
    }

    /**
     * @param afterUsrId セッションデータ．ログインID
     * @param pageSize ページあたりのアイテム数
     * @return
     */
    @Override
    public R searchMore(String afterUsrId,Integer pageSize,String orgId,String orgName){
        //組織情報データを取得する
        List<F40016Dto> f40016DtoList =  f40016Dao.searchMore(afterUsrId,pageSize,orgId,orgName);
        //データを返す
        return R.ok().put("tableList",f40016DtoList);
    }

}
