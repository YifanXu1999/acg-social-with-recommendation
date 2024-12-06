package com.acgsocial.user.gateway.domain.dto;

import jakarta.annotation.PostConstruct;
import lombok.*;
import org.redisson.api.RMap;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SessionDetail {
    private String sessionId;
    private Long userId=-1L;
    private Date createdDate;
    private Date lastAccessedDate;

    public SessionDetail(String sessionId, Long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        init();
    }

    public SessionDetail(String sessionId) {
        this.sessionId = sessionId;
        init();
    }

    public SessionDetail(RMap<String, Object> map) {

        this.sessionId = (String) map.get("sessionId");;
        this.userId = (Long) map.get("userId");
        this.createdDate = (Date) map.get("createdDate");
        this.lastAccessedDate = (Date) map.get("accessDate");
    }


    @PostConstruct
    private void init() {
        createdDate = new Date();
        lastAccessedDate = new Date();
    }

    public List<KeyValue> getKeyValueList() {
        // TODO Need to add createdDate and lastAccessedDate if we have Date type converter
        return List.of(
                new KeyValue("userId", userId),
                new KeyValue("sessionId", sessionId)
//                new KeyValue("createdDate", createdDate.getTime()),
//                new KeyValue("lastAccessedDate", lastAccessedDate.getTime())
        );
    }
}
