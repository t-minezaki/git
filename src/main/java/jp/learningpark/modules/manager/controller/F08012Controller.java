package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.manager.dto.F08012Dto;
import jp.learningpark.modules.manager.service.F08012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/manager/F08012"})
public class F08012Controller {

    /**
     * MstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * MstGrpService
     */
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * F08012Service
     */
    @Autowired
    private F08012Service f08012Service;

    /**
     * MstEventService
     */
    @Autowired
    private MstEventService mstEventService;

    /**
     * GuardEventApplyStsService
     */
    @Autowired
    private GuardEventApplyStsService guardEventApplyStsService;

    /**
     * <p>初期表示</p>
     * eventId イベントID
     * @return R 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId) {

        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        // 学年リストボックスを表示
        List<MstCodDEntity> SchyDivList =
                mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd","cod_value").eq("cod_key","SCHY_DIV").orderByAsc("cod_cd").eq("del_flg"
                        , 0).orderByAsc("sort"));
        if (SchyDivList.size() <= 0) {

            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年")).put("orgId",orgId);
        }

        // グループリストボックスを表示
        List<MstGrpEntity> mstGrpEntityList = mstGrpService.list(new QueryWrapper<MstGrpEntity>().select("grp_id","grp_nm").eq("org_id", orgId).eq("del_flg", 0));
        if (mstGrpEntityList.size() <= 0) {

            return R.error(MessageUtils.getMessage("MSGCOMN0017", "グループ")).put("SchyDivList", SchyDivList);
        }

        return R.ok().put("SchyDivList", SchyDivList).put("mstGrpEntityList", mstGrpEntityList).put("orgId",orgId);
    }

    /**
     * <p>参照元一覧検索</p>
     * @param params 検索条件
     * @return R 画面情報
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R init(String params) {

        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        Map<String,String> paramsMap = (Map) JSON.parse(params);
        String grpIdListStr = paramsMap.get("grpIdList");
        List<Integer> grpIdList = JSON.parseArray(grpIdListStr, Integer.class);
        if (grpIdList.size() == 0){
            grpIdList = null;
        }

        String schyDivListStr = paramsMap.get("schyDivList");
        // 画面．追加対象一覧を表示
        List<F08012Dto> f08012DtoList = f08012Service.selectGuardAndStudent(null, orgId, paramsMap ,grpIdList, schyDivListStr);

        if (f08012DtoList.size() <= 0) {

            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        for (F08012Dto dto:f08012DtoList) {
            GuardEventApplyStsEntity guardEventApplyStsEntity = guardEventApplyStsService.getOne(new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id",
                    Integer.parseInt(paramsMap.get("eventId"))).eq("stu_id", dto.getStuId()).eq("guard_id", dto.getGuardId()).eq("del_flg",0));

            if (guardEventApplyStsEntity != null && StringUtils.equals("1", guardEventApplyStsEntity.getReadingStsDiv())) {
                dto.setReadFlg(false);
            } else {
                dto.setReadFlg(true);
            }
        }
        return R.ok().put("searchGuardAndStudentList", f08012DtoList);
    }
}
