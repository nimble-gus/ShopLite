package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final ProductRepository repo = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> all = repo.findAll();

        // Requisito:
        //  - Implementar paginación por query (?page, ?size) con defaults razonables.
        //  - Enviar a la JSP:
        //      items -> sublista de productos
        //      page, size, total -> para controles de navegación

        // Obtener parámetros de paginación
        int page = 1;
        int size = 5; // Default: 5 productos por página
        
        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
            
            String sizeParam = req.getParameter("size");
            if (sizeParam != null && !sizeParam.trim().isEmpty()) {
                size = Integer.parseInt(sizeParam);
                if (size < 1) size = 5;
            }
        } catch (NumberFormatException e) {
            // Usar valores por defecto si hay error en parsing
        }
        
        int total = all.size();
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, total);
        
        List<Product> items = all.subList(startIndex, endIndex);
        
        // Enviar atributos a la JSP
        req.setAttribute("items", items);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);

        try {
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
