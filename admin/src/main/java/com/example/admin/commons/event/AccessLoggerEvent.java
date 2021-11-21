package com.example.admin.commons.event;

import com.example.admin.entity.AccessLoggerInfo;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-29 16:48
 * @Description: 日志事件
 * @Modified By:
 */
public class AccessLoggerEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public AccessLoggerEvent(AccessLoggerInfo source) {
        super(source);
    }

    @Override
    public AccessLoggerInfo getSource() {
        return (AccessLoggerInfo) super.getSource();
    }
}
