-- Script de inicialización para ShopLite
-- Base de datos: shoplite

-- Crear base de datos (ejecutar como superusuario)
-- CREATE DATABASE shoplite;

-- Conectar a la base de datos shoplite
-- \c shoplite;

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('USER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar usuarios de ejemplo
INSERT INTO users (email, password, role) VALUES
    ('admin@demo.com', 'admin123', 'ADMIN'),
    ('user@demo.com', 'user123', 'USER')
ON CONFLICT (email) DO NOTHING;

-- Insertar productos de ejemplo
INSERT INTO products (name, price) VALUES
    ('Teclado mecánico', 59.99),
    ('Mouse inalámbrico', 29.90),
    ('Monitor 24"', 139.00),
    ('Headset', 49.90),
    ('Webcam', 39.50)
ON CONFLICT DO NOTHING;

-- Crear índices para mejorar rendimiento
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_products_name ON products(name);
CREATE INDEX IF NOT EXISTS idx_products_price ON products(price);

-- Verificar datos insertados
SELECT 'Usuarios:' as info;
SELECT id, email, role FROM users;

SELECT 'Productos:' as info;
SELECT id, name, price FROM products;
