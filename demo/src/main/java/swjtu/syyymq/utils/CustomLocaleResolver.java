package swjtu.syyymq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class CustomLocaleResolver implements LocaleResolver {

    Logger logger = LoggerFactory.getLogger(CustomLocaleResolver.class);

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if(StringUtils.hasText(language)){
            String[] split = language.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
