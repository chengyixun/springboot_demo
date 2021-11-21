package com.example.admin.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import com.example.admin.entity.response.ResponseMessage;
import com.example.admin.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static com.example.admin.commons.constant.RedisConstant.TOKEN_KEY;

/**
 * {@link TokenServiceImpl}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-06
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME="token";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new RuntimeException("请求格式有误");
            }
        }

        if(redisTemplate.hasKey(token)){
            throw new RuntimeException("数据异常");
        }

        Boolean delete = redisTemplate.delete(token);
        if (!delete) {
            throw new RuntimeException("清除数据失败");
        }

    }

    @Override
    public ResponseMessage createToken() {
        String str = RandomUtil.randomUUID();
        StrBuilder token = new StrBuilder();
        token.append(TOKEN_KEY).append(str);
        redisTemplate.opsForValue().set(TOKEN_KEY,token.toString(),1, TimeUnit.HOURS);
        return ResponseMessage.builder().result(token).build();
    }
}
