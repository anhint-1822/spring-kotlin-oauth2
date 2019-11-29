package com.baotrung.authorizationserver.config

import org.springframework.context.annotation.Bean
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@EnableAsync
class AsyncConfig {

    @Bean
    fun defaultTaskExecutor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 10
        executor.maxPoolSize = 20
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("DefaultTaskExecutor")
        executor.initialize()
        return executor
    }
}
