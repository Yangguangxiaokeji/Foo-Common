package com.foogui.foo.modules.demo.controller;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.modules.demo.domain.DemoDTO;
import com.foogui.foo.modules.demo.domain.DemoSearchCondition;
import com.foogui.foo.modules.demo.domain.DemoVO;
import com.foogui.foo.modules.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/search")
    public Result<Object> search(@RequestBody DemoSearchCondition condition){
        return Result.success(demoService.search(condition));
    }

    @PostMapping("/searchById/{id}")
    public Result<DemoVO> searchById(@PathVariable String id){
        return Result.success(demoService.getById(id).convert2VO());
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody DemoDTO demoDTO){

        return demoService.save(demoDTO.convert2PO()) ? Result.success("新增成功") : Result.fail("新增失败");
    }


    @PostMapping("/update")
    public Result<String> update(@RequestBody DemoDTO demoDTO){
        return demoService.updateById(demoDTO.convert2PO()) ? Result.success("修改成功") : Result.fail("修改失败");
    }
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        return demoService.removeById(id) ? Result.success("删除成功") : Result.fail("删除失败");
    }


    @PostMapping("/deleteBatch")
    public Result<String> deleteBatch(@RequestBody List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            return Result.fail("请选择数据");
        }
        return demoService.removeBatchByIds(ids) ? Result.success("批量删除成功！") : Result.fail("批量删除失败！");
    }

}
