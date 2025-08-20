package com.darwinruiz.shoplite.services;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.repositories.UserRepository;

import java.util.Optional;

/**
 * Capa de servicios para la lógica de negocio de usuarios
 */
public class UserService {
    private final UserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    /**
     * Autentica un usuario con email y contraseña
     */
    public Optional<User> authenticate(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return Optional.empty();
        }
        
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (password.equals(user.getPassword())) {
                return userOpt;
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Busca un usuario por email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Crea un nuevo usuario
     */
    public boolean createUser(String email, String password, String role) {
        if (email == null || password == null || role == null || 
            email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return false;
        }
        
        // Validar que el rol sea válido
        if (!role.equals("USER") && !role.equals("ADMIN")) {
            return false;
        }
        
        return userRepository.createUser(email, password, role);
    }
    
    /**
     * Actualiza un usuario existente
     */
    public boolean updateUser(String email, String password, String role) {
        if (email == null || password == null || role == null || 
            email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return false;
        }
        
        // Validar que el rol sea válido
        if (!role.equals("USER") && !role.equals("ADMIN")) {
            return false;
        }
        
        return userRepository.updateUser(email, password, role);
    }
    
    /**
     * Elimina un usuario
     */
    public boolean deleteUser(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        return userRepository.deleteUser(email);
    }
}
