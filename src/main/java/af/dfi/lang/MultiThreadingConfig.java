package af.dfi.lang;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class MultiThreadingConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ParamsConfig.CORE_POOLING_SIZE);
        executor.setMaxPoolSize(ParamsConfig.MAX_POOLING_SIZE);
        executor.setQueueCapacity(ParamsConfig.QUEUE_CAPACITY);
        executor.setThreadNamePrefix(ParamsConfig.DEFAULT_PREFIX);
        executor.initialize();
        return executor;
    }
}
