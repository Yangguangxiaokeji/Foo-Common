package com.foogui.foo.modules.user.controller;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.modules.user.domain.SysUserDTO;
import com.foogui.foo.modules.user.domain.SysUserSearchCondition;
import com.foogui.foo.modules.user.domain.SysUserVO;
import com.foogui.foo.modules.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys-user")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/search")
    public Result<Object> search(@RequestBody SysUserSearchCondition condition){
        return Result.success(sysUserService.search(condition));
    }

    @PostMapping("/searchById/{id}")
    public Result<SysUserVO> searchById(@PathVariable String id){
        return Result.success(sysUserService.getById(id).convert2VO());
    }

    @PostMapping("/insert")
    public Result<String> insert(@RequestBody SysUserDTO sysUserDTO){

        return sysUserService.save(sysUserDTO.convert2PO()) ? Result.success("新增成功") : Result.fail("新增失败");
    }


    @PostMapping("/update")
    public Result<String> update(@RequestBody SysUserDTO sysUserDTO){
        return sysUserService.updateById(sysUserDTO.convert2PO()) ? Result.success("修改成功") : Result.fail("修改失败");
    }
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable String id){
        return sysUserService.removeById(id) ? Result.success("删除成功") : Result.fail("删除失败");
    }


    @PostMapping("/deleteBatch")
    public Result<String> deleteBatch(@RequestBody List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            return Result.fail("请选择数据");
        }
        return sysUserService.removeBatchByIds(ids) ? Result.success("批量删除成功！") : Result.fail("批量删除失败！");
    }

    @PostMapping("/queryByUsername")
    public Result<SysUserDTO> queryByUsername(@RequestBody SysUserDTO sysUserDTO){
        return sysUserService.queryByUsername(sysUserDTO.getUsername());
    }

}
