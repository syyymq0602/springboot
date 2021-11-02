package swjtu.syyymq.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableCaching
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        // 为了开发方便，我们一般创建String类型的redis模板
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        // 采用json序列化并对其作出配置
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 该方法是指定序列化输入的类型，就是将数据库里的数据安装一定类型存储到redis缓存中。
        om.activateDefaultTyping( LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(om);//完成配置，放在jackson2JsonRedisSerializer序列化中
        // 创建一个String类型的序列化对象
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);// key采用String的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);// value采用上面定义的json序列化方式
        template.setHashKeySerializer(jackson2JsonRedisSerializer);// hash采用上面定义的json序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);// hash的value采用上面定义的json序列化方式
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 重写key的生成策略，用【类名+方法名+参数名】这样就可以保证key不为空
     * @return key生成器
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(".").append(method.getName()).append(Arrays.toString(objects));
                return sb.toString();
            }
        };
    }
}
