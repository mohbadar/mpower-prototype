package af.dfi.api.interceptor;

import af.dfi.lang.ParamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class ResourceGatewayInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    ResourceGatewayInterceptor resourceGatewayInterceptor;

    public ResourceGatewayInterceptorConfig(){ }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourceGatewayInterceptor).addPathPatterns(ParamsConfig.INTERCEPTOR_PATTERNS.split(","));
    }

}
