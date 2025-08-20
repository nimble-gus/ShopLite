package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.services.ProductService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet para administración de productos (solo ADMIN)
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final ProductService productService;

    public AdminServlet() {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String contextPath = req.getContextPath();

        // Validar parámetros
        if (name == null || name.trim().isEmpty() || priceStr == null || priceStr.trim().isEmpty()) {
            resp.sendRedirect(contextPath + "/admin?err=1");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr.trim());
            if (price <= 0) {
                resp.sendRedirect(contextPath + "/admin?err=1");
                return;
            }

            // Crear producto usando el servicio
            boolean success = productService.createProduct(name.trim(), price);
            
            if (success) {
                resp.sendRedirect(contextPath + "/home");
            } else {
                resp.sendRedirect(contextPath + "/admin?err=1");
            }
            
        } catch (NumberFormatException e) {
            resp.sendRedirect(contextPath + "/admin?err=1");
        }
    }
}

