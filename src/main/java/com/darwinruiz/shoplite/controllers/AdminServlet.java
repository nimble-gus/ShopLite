package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Requisito (POST): validar y crear un nuevo producto en memoria y redirigir a /home.
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final ProductRepository repo = new ProductRepository();

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
        // Requisito:
        //  - Leer name y price del formulario.
        //  - Validar: nombre no vacío, precio > 0.
        //  - Generar id con repo.nextId(), crear y guardar en repo.
        //  - Redirigir a /home si es válido; si no, regresar a /admin?err=1.
        
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        
        // Validar que el nombre no esté vacío
        if (name == null || name.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }
        
        // Validar el precio
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }
        
        // Crear y guardar el producto
        long id = repo.nextId();
        Product product = new Product(id, name.trim(), price);
        repo.add(product);
        
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}