package com.foogui.foo.common.log.task;

import com.foogui.foo.common.log.domain.LogPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class LogAsyncTask {
    @Async("logExecutor")
    public CompletableFuture<Integer> recordLog(LogPO logPO) throws Exception {
        log.info("开始任务：{}", logPO);
        long start = System.currentTimeMillis();
        // 记录日志
        // Todo...
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("日志插入耗时：{} 毫秒", end - start);
        return CompletableFuture.completedFuture(1);
    }
}
