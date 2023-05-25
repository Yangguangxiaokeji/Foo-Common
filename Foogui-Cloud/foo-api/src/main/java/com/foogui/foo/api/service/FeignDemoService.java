package com.foogui.foo.api.service;

import com.foogui.foo.api.config.FeignConfig;
import com.foogui.foo.api.dto.DemoVO;
import com.foogui.foo.api.fallback.DemoFallbackFactory;
import com.foogui.foo.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "foo-modules",contextId = "demo", configuration = FeignConfig.class, fallbackFactory = DemoFallbackFactory.class)
public interface FeignDemoService {
    @PostMapping("/demo/insert")
    public Result<String> insert(@RequestBody DemoVO demoVO);
}
