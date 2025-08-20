package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.database.DbConnection;
import com.darwinruiz.shoplite.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    
    public List<Product> findAll() {
        String sql = "SELECT id, name, price FROM products ORDER BY id";
        List<Product> products = new ArrayList<>();
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                );
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error obteniendo productos: " + e.getMessage());
        }
        
        return products;
    }
    
    public List<Product> findAllPaginated(int page, int size) {
        String sql = "SELECT id, name, price FROM products ORDER BY id LIMIT ? OFFSET ?";
        List<Product> products = new ArrayList<>();
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, size);
            stmt.setInt(2, (page - 1) * size);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                );
                products.add(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error obteniendo productos paginados: " + e.getMessage());
        }
        
        return products;
    }
    
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM products";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error obteniendo conteo total: " + e.getMessage());
        }
        
        return 0;
    }
    
    public Optional<Product> findById(long id) {
        String sql = "SELECT id, name, price FROM products WHERE id = ?";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Product product = new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                );
                return Optional.of(product);
            }
            
        } catch (SQLException e) {
            System.err.println("Error buscando producto por ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    public boolean add(Product product) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getLong(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error agregando producto: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setLong(3, product.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error actualizando producto: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean delete(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error eliminando producto: " + e.getMessage());
        }
        
        return false;
    }
    
    public long nextId() {
        String sql = "SELECT nextval('products_id_seq')";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error obteniendo siguiente ID: " + e.getMessage());
        }
        
        return System.currentTimeMillis(); // Fallback
    }
}