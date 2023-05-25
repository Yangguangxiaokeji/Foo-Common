package com.foogui.foo.common.dao.mybatis.service;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foogui.foo.common.core.utils.ReflectUtils;
import com.foogui.foo.common.dao.mybatis.domain.BaseSearchDTO;
import com.foogui.foo.common.dao.mybatis.domain.FooPage;
import com.foogui.foo.common.dao.mybatis.domain.SearchCondition;
import com.foogui.foo.common.dao.mybatis.mapper.FooBaseMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class FooBaseServiceImpl<M extends FooBaseMapper<T>, T> extends ServiceImpl<M, T> implements FooService<T> {

    @Override
    public void startPage(BaseSearchDTO condition) {
        Page<T> page = PageHelper.startPage(condition.getPageNumber(), condition.getPageSize());
        if (StringUtils.isNoneBlank(condition.getOrderBy())){
            page.setOrderBy(condition.getOrderBy());
        }
    }

    /**
     * 获取分页信息
     * 只要dto里包含什么条件，自动全部like字段查询
     * @param condition 条件
     * @return {@link FooPage}<{@link T}>
     */
    @Override
    public FooPage<T> getPage(SearchCondition condition) {
        Object context = condition.getContext();
        HashMap<String, Object> nonNullFieldValues = ReflectUtils.getNonNullFieldValues(context);
        QueryChainWrapper<T> chainWrapper = query();
        nonNullFieldValues.forEach(chainWrapper::like);
        return new FooPage<T>(chainWrapper.list());
    }
}
