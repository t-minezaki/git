package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.dao.F21014Dao;
import jp.learningpark.modules.manager.dto.F21014AttendBookDto;
import jp.learningpark.modules.manager.dto.F21014LineDto;
import jp.learningpark.modules.manager.service.F21014Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * F21014ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/05 ： NWT)hxl ： 新規作成
 * @date 2019/12/05 14:22
 */
@Service
public class F21014ServiceImpl implements F21014Service {
    /**
     * f21014Dao
     */
    @Autowired
    F21014Dao f21014Dao;
    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * 該当生徒の全部指導報告書及出席簿明細内容を取得する
     *
     * @param month 年月
     * @param orgId 組織ID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public R getAttendBookByMonth(String month, String orgId, String stuId) {
        List<String> subjectList = f21014Dao.getSubjectByMonth(month, orgId, stuId);
        if (subjectList == null || subjectList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "個人の出席簿と指導報告情報"));
        }
        //授業様子コードリスト
        List<MstCodDEntity> lectShapeDivList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().eq("cod_key", "LECT_SHAPE_DIV"));
        //list -> map
        Map<String, String> lectShapeDivMap = new HashMap(16);
        for (MstCodDEntity mstCodDEntity : lectShapeDivList) {
            lectShapeDivMap.put(mstCodDEntity.getCodCd(), mstCodDEntity.getCodValue2());
        }
        //返された結果リスト
        List<F21014LineDto> result = new ArrayList<>();
        Map<String, List<F21014LineDto>> subjectLineMap = new HashMap<>();
        //教科書のサイクル
        subjectList.forEach(v -> {
            if (StringUtils.isEmpty(v)){
                return;
            }
            List<F21014LineDto> oneSubjectColumnList = new ArrayList<>();
            //エクスプレスアイテムのサイクル
            for (int i = 0; i < 12; i++) {
                F21014LineDto f21014LineDto = new F21014LineDto();
                switch (i) {
                    case 0:
                        f21014LineDto.setContent("使用テキスト");
                        break;
                    case 1:
                        f21014LineDto.setContent("指導内容");
                        break;
                    case 2:
                        f21014LineDto.setContent("宿題内容");
                        break;
                    case 3:
                        f21014LineDto.setContent("小テスト単元名");
                        break;
                    case 4:
                        f21014LineDto.setContent("連絡事項内容");
                        break;
                    case 5:
                        f21014LineDto.setContent("前回宿題");
                        break;
                    case 6:
                        f21014LineDto.setContent("進捗ステータス");
                        break;
                    case 7:
                        f21014LineDto.setContent("授業様子");
                        break;
                    case 8:
                        f21014LineDto.setContent("小テスト");
                        break;
                    case 9:
                        f21014LineDto.setContent("宿題");
                        break;
                    case 10:
                        f21014LineDto.setContent("ケア");
                        break;
                    case 11:
                        f21014LineDto.setContent("出欠ステータス");
                        break;
                    default:
                        break;
                }
                f21014LineDto.setSubject(v);
                oneSubjectColumnList.add(f21014LineDto);
            }
            //テキスト内のすべてのアイテムのマッピング
            subjectLineMap.put(v, oneSubjectColumnList);
        });
        //31日サイクル
        for (int i = 0; i < 31; i++) {
            //ラムダのインデックス値
            final Integer day = i;
            //日ごとの出席記録のクエリ
            List<F21014AttendBookDto> attendBookByDayList = f21014Dao.getAttendBookByDay(month + "-" + ((day + 1) < 10 ? ("0" + (day + 1)) : (day + 1)), orgId, stuId);
            if (attendBookByDayList != null && attendBookByDayList.size() > 0) {
                attendBookByDayList.forEach(attendBook -> {
                    //データ入力
                    String subjtValue = attendBook.getSubjtValue();
                    if(StringUtils.isEmpty(subjtValue)){
                        return;
                    }
                    List<F21014LineDto> f21014LineDtos = subjectLineMap.get(subjtValue);
                    for (int j = 0; j < 12; j++) {
                        String[] content = f21014LineDtos.get(j).getDay();
                        switch (j) {
                            case 0:
                                content[day] = attendBook.getUseContent();
                                break;
                            case 1:
                                content[day] = attendBook.getGuidContent();
                                break;
                            case 2:
                                content[day] = attendBook.getHwkContent();
                                break;
                            case 3:
                                content[day] = attendBook.getTestUnitName();
                                break;
                            case 4:
                                content[day] = attendBook.getConnectItemContent();
                                break;
                            case 5:
                                content[day] = attendBook.getLastHwkValue();
                                break;
                            case 6:
                                content[day] = attendBook.getSchStsValue();
                                break;
                            case 7:
                                String lectShapeCd = attendBook.getLectShapeValue();
                                if (StringUtils.isEmpty(lectShapeCd)){
                                    break;
                                }
                                String[] lectShapeCds = lectShapeCd.split(",");
                                if (lectShapeCds.length == 0){
                                    break;
                                }
                                StringBuilder lectShapeStringBuilder = new StringBuilder();
                                lectShapeStringBuilder.append(lectShapeDivMap.get(lectShapeCds[0]));
                                for (int k = 1; k < lectShapeCds.length; k++){
                                    lectShapeStringBuilder.append(",").append(lectShapeDivMap.get(lectShapeCds[k]));
                                }
                                content[day] = lectShapeStringBuilder.toString();
                                break;
                            case 8:
                                content[day] = attendBook.getTestPoints() == null ? null :  (attendBook.getTestPoints() + "");
                                break;
                            case 9:
                                content[day] = attendBook.getHomeworkValue();
                                break;
                            case 10:
                                content[day] = attendBook.getCareValue();
                                break;
                            case 11:
                                content[day] = attendBook.getAbsStsValue();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        }
        //map -> list
        subjectLineMap.forEach((subject, value) -> {
            result.addAll(value);
        });
        //並べ替え
        result.sort(new F21014LineDto.SortByContent());
        return R.ok().put("result", result);
    }
}
