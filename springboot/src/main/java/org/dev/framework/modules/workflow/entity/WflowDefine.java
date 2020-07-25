package org.dev.framework.modules.workflow.entity;

import lombok.Data;

@Data
public class WflowDefine {

    private String id;

    private String category;

    private String name;

    private String key;

    private String description;

    private int version;

    private String resourceName;

    private String deploymentId;

    private String diagramResourceName;

    private boolean startFormKey;

    private boolean graphicalNotation;

    private boolean suspended;

    private String tnantId;

    private String engineVersion;

    private Integer appVersione;
}
