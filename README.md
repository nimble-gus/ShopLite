# ShopLite - Aplicación Web Java EE con PostgreSQL

## 📋 Descripción

ShopLite es una aplicación web Java EE que implementa un sistema de gestión de productos con autenticación y autorización. La aplicación utiliza PostgreSQL como base de datos y sigue una arquitectura por capas (MVC).

## 🏗️ Arquitectura

- **Backend:** Java 21 + Jakarta EE 10.0.0
- **Base de Datos:** PostgreSQL
- **Frontend:** JSP + JSTL + Bootstrap 5.3.3
- **Build Tool:** Maven
- **Servidor:** WildFly

### Estructura del Proyecto

```
ShopLite/
├── src/main/java/com/darwinruiz/shoplite/
│   ├── controllers/     # Servlets (Controladores)
│   ├── services/        # Lógica de negocio
│   ├── repositories/    # Acceso a datos (JDBC)
│   ├── models/          # Entidades
│   ├── filters/         # Filtros de seguridad
│   └── database/        # Conexión a BD
├── src/main/webapp/     # Vistas JSP
├── src/main/resources/  # Configuración
└── database/           # Scripts SQL
```

## 🚀 Instalación y Configuración

### Prerrequisitos

1. **Java 21** o superior
2. **Maven 3.6+**
3. **PostgreSQL 12+**
4. **WildFly 30+** (opcional, se puede usar Maven plugin)

### 1. Configurar Base de Datos

```sql
-- Conectar a PostgreSQL como superusuario
psql -U postgres

-- Crear base de datos
CREATE DATABASE shoplite;

-- Conectar a la base de datos
\c shoplite;

-- Ejecutar script de inicialización
\i database/init.sql
```

### 2. Configurar Conexión

Editar `src/main/java/com/darwinruiz/shoplite/database/DbConnection.java`:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/shoplite";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_password";
```

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean compile

# Empaquetar
mvn package

# Ejecutar con WildFly Maven Plugin
mvn wildfly:run
```

O desplegar el WAR en WildFly manualmente.

## 👥 Usuarios de Prueba

| Email | Contraseña | Rol |
|-------|------------|-----|
| admin@demo.com | admin123 | ADMIN |
| user@demo.com | user123 | USER |

## 🔐 Seguridad

### Filtros Implementados

1. **AuthFilter:** Protege rutas `/app/*`
2. **AdminFilter:** Restringe acceso a `/app/users/*` solo para ADMIN

### Rutas Públicas

- `/login.jsp`
- `/auth/login`
- `/auth/logout`
- `/index.jsp`
- `/403.jsp`, `/404.jsp`, `/500.jsp`

## 📊 Funcionalidades

### Para Todos los Usuarios Autenticados

- ✅ Ver lista de productos con paginación
- ✅ Navegación entre páginas
- ✅ Cerrar sesión

### Solo Administradores

- ✅ Crear nuevos productos
- ✅ Acceso a panel de administración

## 🗄️ Base de Datos

### Tablas

#### `users`
```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('USER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### `products`
```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Datos de Ejemplo

El script `database/init.sql` incluye:
- 2 usuarios de prueba
- 5 productos de ejemplo

## 🔧 Configuración de Desarrollo

### Variables de Entorno (Opcional)

```bash
export DB_URL=jdbc:postgresql://localhost:5432/shoplite
export DB_USER=postgres
export DB_PASSWORD=postgres
```

### Logs

Los logs se muestran en la consola del servidor. Para debugging:

```bash
# Ver logs de WildFly
tail -f wildfly/standalone/log/server.log
```

## 🧪 Testing

```bash
# Ejecutar tests (si existen)
mvn test

# Verificar compilación
mvn clean compile
```

## 📝 API Endpoints

| Método | Ruta | Descripción | Acceso |
|--------|------|-------------|--------|
| GET | `/` | Redirige a login | Público |
| GET/POST | `/auth/login` | Autenticación | Público |
| POST | `/auth/logout` | Cerrar sesión | Autenticado |
| GET | `/home` | Lista de productos | Autenticado |
| GET/POST | `/admin` | Panel admin | Solo ADMIN |

## 🚨 Solución de Problemas

### Error de Conexión a BD

1. Verificar que PostgreSQL esté ejecutándose
2. Confirmar credenciales en `DbConnection.java`
3. Verificar que la base de datos `shoplite` exista

### Error 403

- Verificar que el usuario tenga rol ADMIN para acceder a `/admin`
- Revisar configuración de filtros en `web.xml`

### Error 500

- Revisar logs del servidor
- Verificar sintaxis SQL en repositories
- Confirmar que las tablas existan en la BD

## 📈 Mejoras Futuras

- [ ] Encriptación de contraseñas (BCrypt)
- [ ] Edición y eliminación de productos
- [ ] Gestión de usuarios (CRUD)
- [ ] Búsqueda y filtros de productos
- [ ] Carrito de compras
- [ ] Tests unitarios
- [ ] Logging estructurado
- [ ] Docker support

## 📄 Licencia

Este proyecto es para fines educativos.

## 👨‍💻 Autor

Darwin Ruiz - ShopLite Project
