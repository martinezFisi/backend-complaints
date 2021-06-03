package com.martinez.complaints.controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ControllerLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var req = (HttpServletRequest) servletRequest;
        var res = (HttpServletResponse) servletResponse;

        log.info("STARTING REQUEST: method[{}], contentType[{}], URI[{}]",
                req.getMethod(), req.getContentType(), req.getRequestURI());

        filterChain.doFilter(req, res);

        log.info("FINISHED RESPONSE: contentType[{}], status[{}]",
                res.getContentType(), res.getStatus());
    }
}
