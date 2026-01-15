package com.ht.note.uploadfile.entity;

import java.io.Serializable;

public class FileData implements Serializable {

    private String base64FileStr;

    public String getBase64FileStr() {
        return base64FileStr;
    }

    public void setBase64FileStr(String base64FileStr) {
        this.base64FileStr = base64FileStr;
    }
}
