package com.bootexample.auth;

import com.bootexample.util.ExpiredHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created: antosha4e
 * Date: 12.05.16
 */
@Component
public class TokenManager implements InitializingBean {
    @Value("${time.token.unit}")
    protected TimeUnit tokenToLiveUnit;

    @Value("${time.token.value}")
    protected long tokenToLive;

    private Map<String, Authentication> tokenMap;
    private final Object lock = new Object();

    public Authentication getUserByToken(String xAuth) {
        synchronized (lock) {
            return tokenMap.get(xAuth);
        }
    }

    public String generateToken(Authentication auth) {
        String token = UUID.randomUUID().toString();

        synchronized (lock) {
            tokenMap.put(token, auth);
        }

        return token;
    }

    public void removeToken(String xAuth) {
        synchronized (lock) {
            tokenMap.remove(xAuth);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        tokenMap = new ExpiredHashMap<>(tokenToLiveUnit, tokenToLive);
    }
}