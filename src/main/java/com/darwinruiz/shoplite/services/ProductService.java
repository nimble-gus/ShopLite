package com.darwinruiz.shoplite.services;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Capa de servicios para la lógica de negocio de productos
 */
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    
    /**
     * Obtiene todos los productos
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Obtiene productos paginados
     */
    public List<Product> getProductsPaginated(int page, int size) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        
        return productRepository.findAllPaginated(page, size);
    }
    
    /**
     * Obtiene el conteo total de productos
     */
    public int getTotalProductCount() {
        return productRepository.getTotalCount();
    }
    
    /**
     * Busca un producto por ID
     */
    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }
    
    /**
     * Crea un nuevo producto
     */
    public boolean createProduct(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        if (price <= 0) {
            return false;
        }
        
        Product product = new Product(0, name.trim(), price);
        return productRepository.add(product);
    }
    
    /**
     * Actualiza un producto existente
     */
    public boolean updateProduct(long id, String name, double price) {
        if (id <= 0) {
            return false;
        }
        
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        if (price <= 0) {
            return false;
        }
        
        Product product = new Product(id, name.trim(), price);
        return productRepository.update(product);
    }
    
    /**
     * Elimina un producto
     */
    public boolean deleteProduct(long id) {
        if (id <= 0) {
            return false;
        }
        
        return productRepository.delete(id);
    }
    
    /**
     * Valida los parámetros de paginación
     */
    public static class PaginationParams {
        private final int page;
        private final int size;
        private final int total;
        
        public PaginationParams(int page, int size, int total) {
            this.page = Math.max(1, page);
            this.size = Math.max(1, size);
            this.total = Math.max(0, total);
        }
        
        public int getPage() { return page; }
        public int getSize() { return size; }
        public int getTotal() { return total; }
        public int getTotalPages() { 
            return (int) Math.ceil((double) total / size); 
        }
        public boolean hasNext() { 
            return page < getTotalPages(); 
        }
        public boolean hasPrevious() { 
            return page > 1; 
        }
    }
}
