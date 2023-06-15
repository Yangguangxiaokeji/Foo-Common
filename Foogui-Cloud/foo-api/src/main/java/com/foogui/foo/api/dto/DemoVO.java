package com.foogui.foo.common.web.foo.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DemoVO implements Serializable {


    private Long id;

    private String demoData;

    private String createUser;

    private LocalDateTime createTime;

    private String updateUser;

    private LocalDateTime updateTime;

    private Integer version;

    private String context;

}
