package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

/**
 * Servlet para manejar la autenticación de usuarios
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        String contextPath = req.getContextPath();

        if (email == null || pass == null || email.isEmpty() || pass.isEmpty()) {
            resp.sendRedirect(contextPath + "/login.jsp?err=1");
            return;
        }

        Optional<User> userOpt = userService.authenticate(email, pass);
        if (userOpt.isPresent()) {
            // Invalidar sesión previa por seguridad
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // Crear nueva sesión
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("auth", true);
            newSession.setAttribute("userEmail", userOpt.get().getEmail());
            newSession.setAttribute("role", userOpt.get().getRole());
            newSession.setMaxInactiveInterval(30 * 60); // 30 minutos

            resp.sendRedirect(contextPath + "/home");
        } else {
            resp.sendRedirect(contextPath + "/login.jsp?err=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
