/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.pop.dto.F00055GuardDto;
import jp.learningpark.modules.pop.dto.F00055MentorDto;
import jp.learningpark.modules.pop.dto.F00055StudentDto;
import jp.learningpark.modules.pop.service.F00055Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F00055 ユーザー選択画面（POP） Controller</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/03/20 : wq: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/F00055")
@RestController
public class F00055Controller {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F00055 ユーザー選択画面（POP） Service
     */
    @Autowired
    F00055Service f00055Service;

    /**
     * マスタコード　Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * <p>学生情報リストを取得</p>
     *
     * @param f00055StudentDto 画面引渡データ
     * @param limit            当ページ数
     * @param page             各ページの最大記録数
     * @return　学生情報
     */
    @RequestMapping(value = "/stuList", method = RequestMethod.GET)
    public R f00055GetStuList(F00055StudentDto f00055StudentDto, Integer limit, Integer page) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        //学生情報取得
        List<F00055StudentDto> f00055StudentDtoList = f00055Service.getStudentInfo(f00055StudentDto.getStuId(), f00055StudentDto.getStudentNm(), f00055StudentDto.getSchyDiv(), orgId, (page - 1) * limit);
        //学生情報記録数取得
        Integer totalSize = f00055Service.getStudentInfoCount(f00055StudentDto.getStuId(), f00055StudentDto.getStudentNm(), f00055StudentDto.getSchyDiv(), orgId);
        //学年取得
        List<MstCodDEntity> mstCodDEntitySchyDivList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(w -> w.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        if (mstCodDEntitySchyDivList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年"));
        }
        //取得できない場合
        if (f00055StudentDtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0183"));
        }
        return R.ok().put("mstOrgEntity", mstOrgEntity).put("mstCodDEntitySchyDivList", mstCodDEntitySchyDivList).put("page", new PageUtils(f00055StudentDtoList, totalSize, limit, page));
    }

    /**
     * @param f00055MentorDto 画面引渡データ
     * @param limit           当ページ数
     * @param page            各ページの最大記録数
     * @return　メンター情報
     */
    @RequestMapping(value = "/mentorList", method = RequestMethod.GET)
    public R f00055GetMentorList(F00055MentorDto f00055MentorDto, Integer limit, Integer page) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        //メンター情報取得
        List<F00055MentorDto> f00055MentorDtoList = f00055Service.getMentorInfo(f00055MentorDto.getMentorId(), f00055MentorDto.getMentorNm(), orgId, (page - 1) * limit);
        //メンター情報記録数取得
        Integer totalSize = f00055Service.getMentorInfoCount(f00055MentorDto.getMentorId(), f00055MentorDto.getMentorNm(), orgId);
        //取得できない場合
        if (f00055MentorDtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0183"));
        }
        return R.ok().put("mstOrgEntity", mstOrgEntity).put("page", new PageUtils(f00055MentorDtoList, totalSize, limit, page));
    }

    /**
     * @param f00055GuardDto 画面引渡データ
     * @param limit          当ページ数
     * @param page           各ページの最大記録数
     * @return　保護者情報
     */
    @RequestMapping(value = "/guardList", method = RequestMethod.GET)
    public R f00055GetGuardList(F00055GuardDto f00055GuardDto, Integer limit, Integer page)  {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        //保護者情報取得
        List<F00055GuardDto> f00055GuardDtoList = f00055Service.getGuardInfo(f00055GuardDto.getGuardId(), f00055GuardDto.getGuardNm(), f00055GuardDto.getMailad(), orgId, (page - 1) * limit);
        //保護者情報記録数取得
        Integer totalSize = f00055Service.getGuardInfoCount(f00055GuardDto.getGuardId(), f00055GuardDto.getGuardNm(), f00055GuardDto.getMailad(), orgId);
        //取得できない場合
        if (f00055GuardDtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0183"));
        }
        return R.ok().put("mstOrgEntity", mstOrgEntity).put("page", new PageUtils(f00055GuardDtoList, totalSize, limit, page));
    }
}
