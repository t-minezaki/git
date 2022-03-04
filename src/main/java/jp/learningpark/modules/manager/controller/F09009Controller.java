package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09009Dto;
import jp.learningpark.modules.manager.service.F09009Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/manager/F09009")
public class F09009Controller {
    // 2020/11/9 zhangminghao modify start
    private final F09009Service f09009Service;

    public F09009Controller(F09009Service f09009Service) {
        this.f09009Service = f09009Service;
    }

    @GetMapping("/init")
    public R init() {
        return f09009Service.init();
    }

    @PostMapping("/edit")
    public R edit(@RequestBody F09009Dto f09009Dto){
        return f09009Service.edit(f09009Dto);
    }
    // 2020/11/9 zhangminghao modify end
}
