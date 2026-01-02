package com.oa.system.vo;

import lombok.Data;
import java.util.List;

@Data
public class RouterVO {

    private String path;

    private String component;

    private String name;

    private boolean hidden;

    private MetaVO meta;

    private List<RouterVO> children;
}
