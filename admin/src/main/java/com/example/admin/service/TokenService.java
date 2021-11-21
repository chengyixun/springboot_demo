package com.example.admin.service;

import com.example.admin.entity.response.ResponseMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * {@link TokenService}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-06
 */
public interface TokenService {

    void checkToken(HttpServletRequest request);


    ResponseMessage createToken();
}
