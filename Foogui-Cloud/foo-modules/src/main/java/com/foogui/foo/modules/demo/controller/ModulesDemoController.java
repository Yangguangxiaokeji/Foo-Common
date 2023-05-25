package com.foogui.foo.modules.demo.controller;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.log.anotation.Log;
import com.foogui.foo.common.log.enums.Action;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class ModulesDemoController {

    @GetMapping("/test/{id}")
    @Log(description = "Controller测试",action = Action.INSERT)
    public Result<String> test(@PathVariable("id") Long id){
        return Result.success();
    }

    @GetMapping("/black")
    public Result<String> black(){
        return Result.success();
    }


}
