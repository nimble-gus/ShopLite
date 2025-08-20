package com.darwinruiz.shoplite.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet para cerrar sesión
 */
@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logout(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logout(req, resp);
    }
    
    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Invalidar sesión si existe
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Redirigir a login con mensaje de despedida
        resp.sendRedirect(req.getContextPath() + "/login.jsp?bye=1");
    }
}