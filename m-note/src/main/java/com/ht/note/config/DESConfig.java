package com.ht.note.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class DESConfig {

    /**
     * des秘钥
     */
    public String password = "keyints-note-password-0230606";

    /**
     * des偏移向量
     */
    public String ivParameter = "3nt%93&k";
}
