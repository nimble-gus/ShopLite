package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: bloquear todo lo no público si no existe una sesión con auth=true.
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        String uri = r.getRequestURI();
        boolean esPublica =
                uri.endsWith("/index.jsp") || uri.endsWith("/login.jsp") ||
                        uri.contains("/auth/login") || uri.endsWith("/");

        if (esPublica) {
            chain.doFilter(req, res);
            return;
        }

        // Requisito: si no hay sesión válida (atributo "auth" true), redirigir a login.jsp.
        HttpSession session = r.getSession(false);
        if (session == null || session.getAttribute("auth") == null || 
            !(Boolean) session.getAttribute("auth")) {
            p.sendRedirect(r.getContextPath() + "/login.jsp");
            return;
        }

        chain.doFilter(req, res);
    }
}