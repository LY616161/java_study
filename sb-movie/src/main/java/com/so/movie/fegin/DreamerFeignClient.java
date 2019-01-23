package com.so.movie.fegin;

import com.so.movie.entity.Dreamer;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "spring-cloud-dreamer",fallbackFactory = FeignClientFallback.class)
public interface DreamerFeignClient {
    @RequestMapping(value = "/dreamer/{id}",method = RequestMethod.GET)
    Dreamer findDreamerById(@PathVariable("id") int id);
}

@Component
class FeignClientFallback implements FallbackFactory<DreamerFeignClient>{
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallback.class);
    @Override
    public DreamerFeignClient create(Throwable throwable) {
        return new DreamerFeignClient() {
            @Override
            public Dreamer findDreamerById(int id) {
                FeignClientFallback.LOGGER.info("fallback reason: ",throwable);
                Dreamer dreamer = new Dreamer();
                dreamer.setId(-1);
                dreamer.setName("默认用户");
                return dreamer;
            }
        };
    }
}
