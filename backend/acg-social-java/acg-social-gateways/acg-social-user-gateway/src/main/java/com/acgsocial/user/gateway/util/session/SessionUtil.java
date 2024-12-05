package com.acgsocial.user.gateway.util.session;

import com.acgsocial.user.gateway.domain.dto.SessionDetail;
import org.springframework.web.server.ServerWebExchange;

import java.util.concurrent.atomic.AtomicReference;

public class SessionUtil {

    public static SessionDetail getSessionDetail(ServerWebExchange exchange) {
        SessionDetail sessionDetail = new SessionDetail();
        exchange.getSession().subscribe(
                session -> {
                    if (!session.isStarted()) {
                        session.start();
                    }
                    sessionDetail.setSessionId(session.getId());
                    sessionDetail.setUserId(session.getAttributeOrDefault("userId", null));
                }
        );
        return sessionDetail;
    }
}
