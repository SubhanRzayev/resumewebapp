package com.company.resume.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "JspFileFilter", urlPatterns = {"*.jsp"})
    public class JspFilter implements Filter{

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("JspFileFilter initialized");
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        try {
            res.sendRedirect("error?msg=not found");
        }catch (IOException e) {
            e.printStackTrace();

        }
    }


    public void destroy() {

    }
}






