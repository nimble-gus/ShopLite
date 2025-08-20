package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.services.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final ProductService productService;

    public HomeServlet() {
        this.productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener parámetros de paginación
        int page = 1;
        int size = 10;

        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        try {
            String sizeParam = req.getParameter("size");
            if (sizeParam != null && !sizeParam.isEmpty()) {
                size = Integer.parseInt(sizeParam);
                if (size < 1) size = 10;
            }
        } catch (NumberFormatException e) {
            size = 10;
        }

        // Obtener productos paginados
        List<Product> items = productService.getProductsPaginated(page, size);
        int total = productService.getTotalProductCount();

        // Calcular información de paginación
        int totalPages = (int) Math.ceil((double) total / size);
        boolean hasNext = page < totalPages;
        boolean hasPrevious = page > 1;

        // Set attributes for JSP
        req.setAttribute("items", items);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("hasNext", hasNext);
        req.setAttribute("hasPrevious", hasPrevious);

        // Forward to JSP
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}