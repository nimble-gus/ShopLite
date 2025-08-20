package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filtro de autenticación para proteger rutas /app/*
 */
public class AuthFilter implements Filter {
    private static final List<String> PUBLIC_URIS = Arrays.asList(
            "/index.jsp",
            "/login.jsp",
            "/auth/login",
            "/auth/logout",
            "/",
            "/403.jsp",
            "/404.jsp",
            "/500.jsp"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        String relativeUri = uri.startsWith(contextPath) && contextPath.length() > 0
                ? uri.substring(contextPath.length()) : uri;

        // Verificar si es una ruta pública
        boolean esPublica = PUBLIC_URIS.stream()
                .anyMatch(publicUri -> relativeUri.equals(publicUri) || 
                        relativeUri.startsWith(publicUri) ||
                        relativeUri.endsWith(".css") ||
                        relativeUri.endsWith(".js") ||
                        relativeUri.endsWith(".ico") ||
                        relativeUri.endsWith(".png") ||
                        relativeUri.endsWith(".jpg") ||
                        relativeUri.endsWith(".jpeg") ||
                        relativeUri.endsWith(".gif"));

        if (esPublica) {
            chain.doFilter(req, res);
            return;
        }

        // Verificar autenticación para rutas protegidas
        HttpSession session = request.getSession(false);
        boolean isAuthenticated = session != null && Boolean.TRUE.equals(session.getAttribute("auth"));

        if (isAuthenticated) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(contextPath + "/login.jsp");
        }
    }
}