package swjtu.syyymq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import swjtu.syyymq.utils.CustomLocaleResolver;

@Configuration
public class i18nConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver(){
        return new CustomLocaleResolver();
    }
}
