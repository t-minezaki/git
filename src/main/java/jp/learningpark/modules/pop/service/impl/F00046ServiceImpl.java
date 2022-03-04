/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.pop.dao.F00046Dao;
import jp.learningpark.modules.pop.dto.F00046Dto;
import jp.learningpark.modules.pop.service.F00046Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>組織検索追加画面 ServiceImpl</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/10/14: zmh: 新規<br />
 * @version 1.0
 */
@Service
public class F00046ServiceImpl implements F00046Service {
    private final F00046Dao f00046Dao;
    private final CommonService commonService;

    public F00046ServiceImpl(F00046Dao f00046Dao, CommonService commonService) {
        this.f00046Dao = f00046Dao;
        this.commonService = commonService;
    }

    @Override
    public List<F00046Dto> search(String searchOrgId, String orgName) {
        String brandCd = ShiroUtils.getBrandcd();

        List<String> thisAndLowerOrgId = getCurrentUserOrgAndLowerOrgIds();

        return f00046Dao.findOrgListByIdOrName(searchOrgId, orgName, brandCd, thisAndLowerOrgId);
    }

    /**
     * 現在のユーザー組織と下位組織のIDセットを取得します
     */
    private List<String> getCurrentUserOrgAndLowerOrgIds(){
        String brandCd = ShiroUtils.getBrandcd();
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<OrgAndLowerOrgIdDto> thisAndLowerOrgId = commonService.getThisAndLowerOrgId(brandCd, orgId);

        return thisAndLowerOrgId.stream()
                .map(MstOrgEntity::getOrgId)
                .collect(Collectors.toList());
    }

}
