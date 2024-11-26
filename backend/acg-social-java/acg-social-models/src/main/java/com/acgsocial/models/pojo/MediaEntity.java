package com.acgsocial.models.pojo;

import com.acgsocial.models.enums.MediaType;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class MediaEntity {
    @Column(name = "cover_url", nullable = false)
    private String url;

    @Column(name = "cover_type", nullable = false)
    private MediaType type;

    @Column(name = "cover_size", nullable = false)
    private Integer size;
}
