package com.foogui.foo.common.web.foo.api.fallback;

import com.foogui.foo.common.web.foo.api.dto.DemoVO;
import com.foogui.foo.common.web.foo.api.service.FeignDemoService;
import com.foogui.foo.common.core.domain.Result;

public class DemoFallbackFactory implements FeignDemoService {
    @Override
    public Result<String> insert(DemoVO demoVO) {
        return Result.fail("Feign调用失败，降级处理");
    }
}
