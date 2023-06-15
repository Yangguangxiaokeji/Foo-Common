package com.foogui.foo.common.web.foo.modules.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
public class DemoDTO {
    private Long id;

    private String demoData;

    private String context;

    private String createUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime createTime;

    private String updateUser;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private LocalDateTime updateTime;

    private Integer version;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public DemoPO convert2PO() {
        DemoPO po = new DemoPO();
        BeanUtils.copyProperties(this, po);
        return po;
    }
}
