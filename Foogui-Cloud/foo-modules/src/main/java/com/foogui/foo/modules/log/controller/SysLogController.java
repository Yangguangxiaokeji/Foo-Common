package com.foogui.foo.modules.log.controller;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.modules.log.domain.SysLogSearchCondition;
import com.foogui.foo.modules.log.service.SysLogService;
import com.foogui.foo.modules.log.domain.SysLogDTO;
import com.foogui.foo.modules.log.domain.SysLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys-log")
@Slf4j
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/search")
    public Result<Object> search(@RequestBody SysLogSearchCondition condition){
        return Result.success(sysLogService.search(condition));
    }

    @PostMapping("/searchById/{id}")
    public Result<SysLogVO> searchById(@PathVariable String id){
        return Result.success(sysLogService.getById(id).convert2VO());
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody SysLogDTO sysLogDTO){

        return sysLogService.save(sysLogDTO.convert2PO()) ? Result.success("新增成功") : Result.fail("新增失败");
    }


    @PostMapping("/update")
    public Result<String> update(@RequestBody SysLogDTO sysLogDTO){
        return sysLogService.updateById(sysLogDTO.convert2PO()) ? Result.success("修改成功") : Result.fail("修改失败");
    }
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        return sysLogService.removeById(id) ? Result.success("删除成功") : Result.fail("删除失败");
    }


    @PostMapping("/deleteBatch")
    public Result<String> deleteBatch(@RequestBody List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            return Result.fail("请选择数据");
        }
        return sysLogService.removeBatchByIds(ids) ? Result.success("批量删除成功！") : Result.fail("批量删除失败！");
    }

}
