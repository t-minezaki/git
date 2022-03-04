package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.pop.dto.F08004Dto;
import jp.learningpark.modules.pop.service.F08004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pop/F08004")
public class F08004Controller {

    @Autowired
    private F08004Service f08004Service;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId){

        F08004Dto f08004Dto = f08004Service.getEventEntity(eventId);

        List<MstOrgEntity> mstOrgEntityList = f08004Service.getOrgList(eventId);
        return R.ok().put("eventEntity", f08004Dto).put("orgList", mstOrgEntityList);
    }
}