package ru.job4j.filter;

import javax.servlet.*;
import java.io.IOException;

public class TypeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
