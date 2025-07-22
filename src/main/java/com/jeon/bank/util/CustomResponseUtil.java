package com.jeon.bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeon.bank.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void unAuthentication(HttpServletResponse response, String msg) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<Object> responseDto = new ResponseDto<>(-1, msg, null);
            String responseBody = om.writeValueAsString(responseDto);
            response.getWriter().print(responseBody);

            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}
