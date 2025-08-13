package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: permitir /admin solo a usuarios con rol "ADMIN"; los demás ven 403.jsp.
 */
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        // Requisito: validar sesión existente y atributo "role" con valor "ADMIN".
        // Si no cumple, hacer forward a /403.jsp. Si cumple, continuar.
        HttpSession session = r.getSession(false);
        if (session == null || session.getAttribute("auth") == null || 
            !(Boolean) session.getAttribute("auth")) {
            p.sendRedirect(r.getContextPath() + "/login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (role == null || !"ADMIN".equals(role)) {
            try {
                r.getRequestDispatcher("/403.jsp").forward(r, p);
            } catch (Exception e) {
                throw new IOException(e);
            }
            return;
        }

        chain.doFilter(req, res);
    }
}
