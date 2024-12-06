package com.acgsocial.user.gateway.domain.dto;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.redisson.api.RMap;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SessionDetail {
    private String sessionId;
    private String userId="";
    private Date createdDate;
    private Date lastAccessedDate;

    public SessionDetail(String sessionId, String userId) {
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
        this.userId = (String) map.get("userId");;
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
