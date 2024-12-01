package com.acgsocial.user.util.oauth2;

import com.acgsocial.user.domain.enums.Oauth2ProviderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Oauth2HandlerFactory {

    private final Map<Oauth2ProviderEnum, Oauth2Handler> handlers = new HashMap<>();

    @Autowired
    public  Oauth2HandlerFactory(List<Oauth2Handler> oauth2Handlers) {
       oauth2Handlers.forEach(handler -> handlers.put(handler.getProvider(), handler));
    }

    public Oauth2Handler getHandler(Oauth2ProviderEnum provider) {
        return handlers.get(provider);
    }
}
