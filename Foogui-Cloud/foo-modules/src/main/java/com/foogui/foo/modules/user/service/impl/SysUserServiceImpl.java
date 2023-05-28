package com.foogui.foo.modules.user.service.impl;

import com.foogui.foo.common.core.domain.Result;
import com.foogui.foo.common.dao.mybatis.domain.FooPage;
import com.foogui.foo.common.dao.mybatis.service.FooBaseServiceImpl;
import com.foogui.foo.modules.user.dao.SysUserMapper;
import com.foogui.foo.modules.user.domain.SysUserDTO;
import com.foogui.foo.modules.user.domain.SysUserPO;
import com.foogui.foo.modules.user.domain.SysUserSearchCondition;
import com.foogui.foo.modules.user.service.SysUserService;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;

@Service
public class SysUserServiceImpl extends FooBaseServiceImpl<SysUserMapper, SysUserPO> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public FooPage<SysUserPO> search(SysUserSearchCondition condition) {
        startPage(condition);
        return getPage(condition);
    }

    @Override
    public Result<SysUserDTO> queryByUsername(String username) {
        SysUserPO sysUserPO = lambdaQuery().eq(SysUserPO::getUsername, username).one();
        SysUserDTO userDTO=null;
        if (sysUserPO!=null){
            userDTO = sysUserPO.convert2DTO();
            // todo查询permissions
            HashSet<String> admin = Sets.newHashSet("ROLE_ADMIN");
            userDTO.setPermissions(admin);
        }

        return Result.success(userDTO);
    }
}
