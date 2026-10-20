package com.yin.common.err;

import lombok.Data;

/**
 * @author yin
 * @date 2024-12-02
 * @Description 自定义异常
 */
@Data
public class YException extends Exception {

    private String msg;

    public YException(String message) {
        super(message);
        this.msg = message;
    }
}
