package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.database.DbConnection;
import com.darwinruiz.shoplite.models.User;

import java.sql.*;
import java.util.Optional;

public class UserRepository {
    
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, password, role FROM users WHERE email = ?";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                return Optional.of(user);
            }
            
        } catch (SQLException e) {
            System.err.println("Error buscando usuario por email: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    public boolean createUser(String email, String password, String role) {
        String sql = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, role);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creando usuario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateUser(String email, String password, String role) {
        String sql = "UPDATE users SET password = ?, role = ? WHERE email = ?";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, password);
            stmt.setString(2, role);
            stmt.setString(3, email);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error actualizando usuario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error eliminando usuario: " + e.getMessage());
            return false;
        }
    }
}
