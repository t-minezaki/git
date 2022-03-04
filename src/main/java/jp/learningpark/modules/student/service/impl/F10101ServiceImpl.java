/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import jp.learningpark.modules.student.dao.F10101Dao;
import jp.learningpark.modules.student.dto.F10101Dto;
import jp.learningpark.modules.student.service.F10101Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>F10101固定スケジュール表示・編集画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/13 : gong: 新規<br />
 * @version 1.0
 */
@Service
public class F10101ServiceImpl implements F10101Service {
    /**
     * F10101固定スケジュール表示・編集画面 Extend Dao
     */
    @Autowired
    private F10101Dao f10101Dao;

    /**
     * <p>スケジュールエリア情報で表示する(F10101)</p>
     *
     * @param stuId     生徒ID
     * @param startDate 対象週開始日
     * @param endDate   対象週終了日
     * @return
     */
    @Override
    public List<SchdlDto> getSchdlListOfF10101(Integer id,String stuId, Date startDate, boolean checkStart, boolean checkEnd) {
//        int weekFlg=1;
//        List<F10101Dto> result=new ArrayList<>();
//        List<F10101FixDto> fixdSchdlEntityList=new ArrayList<>();
//        F10101Dto schdlDto=new F10101Dto();
//        for (Date date = startDate; date.compareTo(endDate) <= 0; date = DateUtils.addDays(date, 1)) {
//            fixdSchdlEntityList=new ArrayList<>();
//            //生徒固定ブロック情報取得
//            fixdSchdlEntityList=f10101Dao.selectFixList(stuId,date, StringUtils.defaultString(weekFlg));
//            for (F10101FixDto entity : fixdSchdlEntityList) {
//                schdlDto = new F10101Dto();
//                //編集可否
//                schdlDto.setEditable(true);
//                //固定ブロック
//                schdlDto.setIsFixed(true);
//                //ID
//                schdlDto.setId(entity.getId());
//                // 対象日
//                schdlDto.setTgtYmd(date);
//                // ブロックID
//                schdlDto.setBlockId(StringUtils.defaultString(entity.getBlockId()));
//                // ブロック表示名
//                schdlDto.setBlockDispyNm(entity.getBlockDispyNm());
//                // ブロック開始時間
//                schdlDto.setStartTime(DateUtils.setTimeToISO(date, entity.getBlockStartTime()));
//                // ブロック終了時間
//                schdlDto.setEndTime(DateUtils.setTimeToISO(date, entity.getBlockEndTime()));
//                if(StringUtils.equals("1",entity.getDwChocFlg())){
//                    result.add(schdlDto);
//                }
//            }
//            weekFlg++;
//        }
        List<SchdlDto> result= f10101Dao.selectSchdlList(id, stuId, startDate, checkStart, checkEnd);
        return result;
    }

    /**
     * <p>固定ブロックエリア情報の取得処理</p>
     *
     * @return ブロックエリア情報
     */
    @Override
    public List<F10101Dto> getBlock() {
        return f10101Dao.getBlock(ShiroUtils.getUserId());
    }
}
