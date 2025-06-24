-- Creando la base de datos
CREATE DATABASE IF NOT EXISTS ElDoradoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ElDoradoDB;

-- Tabla Usuario
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    rol ENUM('admin', 'cajero', 'cocinero') NOT NULL,
    clave VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Proveedor
CREATE TABLE Proveedor (
    id_proveedor INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(50),
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion TEXT
);

-- Tabla Categoria
CREATE TABLE Categoria (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Tabla Producto
CREATE TABLE Producto (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    stock_actual INT NOT NULL CHECK (stock_actual >= 0),
    stock_minimo INT NOT NULL CHECK (stock_minimo >= 0),
    precio_unitario DECIMAL(10, 2) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    id_categoria INT,
    descripcion TEXT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria) ON DELETE SET NULL
);

-- Tabla Pertenecer (relación Producto-Categoria)
CREATE TABLE Pertenecer (
    id_categoria INT,
    id_producto INT,
    PRIMARY KEY (id_categoria, id_producto),
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- Tabla Detalle_Proveedor (productos suministrados por proveedor)
CREATE TABLE Detalle_Proveedor (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_proveedor INT NOT NULL,
    id_producto INT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- Tabla Plato
CREATE TABLE Plato (
    id_plato INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion TEXT
);

-- Tabla Plato_Producto (relación platos con productos e ingredientes)
CREATE TABLE Plato_Producto (
    id_plato INT,
    id_producto INT,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    PRIMARY KEY (id_plato, id_producto),
    FOREIGN KEY (id_plato) REFERENCES Plato(id_plato) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- Tabla Venta
CREATE TABLE Venta (
    id_venta INT PRIMARY KEY AUTO_INCREMENT,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT,
    metodo_pago ENUM('efectivo', 'tarjeta', 'transferencia') NOT NULL,
    monto_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE SET NULL
);

-- Tabla Detalle_Venta
CREATE TABLE Detalle_Venta (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_venta INT,
    id_producto INT,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) AS (cantidad * precio_unitario) STORED,
    FOREIGN KEY (id_venta) REFERENCES Venta(id_venta) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE RESTRICT
);

-- Tabla Movimiento_Inventario
CREATE TABLE Movimiento_Inventario (
    id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
    id_producto INT NOT NULL,
    fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento ENUM('entrada', 'salida') NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- Tabla Cliente
CREATE TABLE Cliente (
    id_cliente     INT PRIMARY KEY AUTO_INCREMENT,
    dni            VARCHAR(8) NOT NULL,
    nombre         VARCHAR(50) NOT NULL,
    apellido       VARCHAR(50) NOT NULL,
    telefono       VARCHAR(15),
    email          VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Reserva
CREATE TABLE Reserva (
    id_reserva INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    fecha_reserva DATETIME NOT NULL,
    numero_personas INT NOT NULL CHECK (numero_personas > 0),
    estado ENUM('confirmada', 'cancelada', 'completada') DEFAULT 'confirmada',
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE SET NULL
);

-- Tabla Mesa
CREATE TABLE Mesa (
    id_mesa INT PRIMARY KEY AUTO_INCREMENT,
    numero_mesa INT NOT NULL UNIQUE,
    capacidad INT NOT NULL CHECK (capacidad > 0),
    estado ENUM('disponible', 'ocupada', 'reservada') DEFAULT 'disponible'
);

-- tabla Mesa y sus platos
CREATE TABLE Mesa_Plato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_mesa INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    FOREIGN KEY (id_mesa) REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    FOREIGN KEY (id_plato) REFERENCES Plato(id_plato) ON DELETE CASCADE
);

-- Tabla Mesa_Unida (unión de mesas)
CREATE TABLE Mesa_Unida (
    id_unida INT PRIMARY KEY AUTO_INCREMENT,
    id_mesa_principal INT,
    id_mesa_secundaria INT,
    FOREIGN KEY (id_mesa_principal) REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    FOREIGN KEY (id_mesa_secundaria) REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    UNIQUE (id_mesa_principal, id_mesa_secundaria)
);

-- Tabla Historial_Precio (historial cambios precio productos)
CREATE TABLE Historial_Precio (
    id_historial INT PRIMARY KEY AUTO_INCREMENT,
    id_producto INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- Tabla Promocion
CREATE TABLE Promocion (
    id_promocion INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
    tipo ENUM('porcentaje', 'monto_fijo') NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE
);

-- Tabla Actividad (registro acciones usuarios)
CREATE TABLE Actividad (
    id_actividad INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    descripcion TEXT NOT NULL,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE SET NULL
);

-- Tabla Notificacion (notificaciones internas)
CREATE TABLE Notificacion (
    id_notificacion INT PRIMARY KEY AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
    estado ENUM('leído', 'no_leído') DEFAULT 'no_leído',
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('delivery', 'para_llevar') NOT NULL,
    id_cliente INT,
    direccion_entrega TEXT, -- Solo para delivery
    estado ENUM('pendiente', 'en_preparacion', 'en_camino', 'entregado', 'cancelado') DEFAULT 'pendiente',
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_venta INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE SET NULL,
    FOREIGN KEY (id_venta) REFERENCES Venta(id_venta) ON DELETE SET NULL
);

CREATE TABLE Pedido_Plato (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_plato) REFERENCES Plato(id_plato) ON DELETE CASCADE
);


-- Índices para performance
CREATE INDEX idx_producto_categoria ON Producto(id_categoria);
CREATE INDEX idx_venta_usuario ON Venta(id_usuario);
CREATE INDEX idx_detalle_venta_producto ON Detalle_Venta(id_producto);
CREATE INDEX idx_movimiento_producto ON Movimiento_Inventario(id_producto);
CREATE INDEX idx_historial_precio_producto ON Historial_Precio(id_producto);
CREATE INDEX idx_actividad_usuario ON Actividad(id_usuario);

-- Inserts de ejemplo para Restaurante El Dorado
-- Tabla Usuario
INSERT INTO Usuario (nombre, apellido, rol, clave) VALUES
('Carlos', 'González', 'admin', 'hashpassword1'),
('Ana', 'Martínez', 'cajero', 'hashpassword2'),
('Luis', 'Fernández', 'cocinero', 'hashpassword3'),
('Marta', 'Ramírez', 'cocinero', 'hashpassword4'),
('Javier', 'Torres', 'admin', 'hashpassword5');

-- Tabla Proveedor
INSERT INTO Proveedor (nombre, contacto, telefono, email, direccion) VALUES
('Proveedor Norte', 'Luis Pérez', '555-1234', 'norte@proveedor.com', 'Av. Central 123'),
('Distribuciones Sur', 'Ana Ruiz', '555-5678', 'sur@distribuciones.com', 'Calle Sur 456'),
('Suministros XYZ', 'Carlos López', '555-9101', 'xyz@suministros.com', 'Zona Industrial 789'),
('Importadora ABC', 'María García', '555-1122', 'abc@importadora.com', 'Parque Industrial 321'),
('Logística Rápida', 'Jorge Fernández', '555-3344', 'rapida@logistica.com', 'Av. Logística 654');

-- Tabla Categoria
INSERT INTO Categoria (nombre_categoria, descripcion) VALUES
('Bebidas', 'Bebidas frías y calientes'),
('Entradas', 'Entradas y aperitivos'),
('Platos Fuertes', 'Platos principales'),
('Postres', 'Dulces y postres'),
('Especiales', 'Ofertas y menús especiales');

-- Tabla Producto
INSERT INTO Producto (nombre, stock_actual, stock_minimo, precio_unitario, unidad_medida, id_categoria, descripcion) VALUES
('Coca Cola 500ml', 100, 10, 1.50, 'unidad', 1, 'Bebida carbonatada'),
('Papas Fritas', 50, 5, 2.00, 'ración', 2, 'Papas fritas crujientes'),
('Pollo Asado', 30, 3, 8.00, 'porción', 3, 'Pollo entero asado'),
('Flan de Vainilla', 20, 2, 3.50, 'porción', 4, 'Flan de postre clásico'),
('Menú Diario', 15, 1, 10.00, 'menú', 5, 'Menú con entrada, plato y postre');

-- Tabla Pertenecer
INSERT INTO Pertenecer (id_categoria, id_producto) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Tabla Detalle_Proveedor
INSERT INTO Detalle_Proveedor (id_proveedor, id_producto) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Tabla Plato
INSERT INTO Plato (nombre, precio, descripcion) VALUES
('Combo Pollo', 12.00, 'Pollo asado con papas fritas y bebida'),
('Ensalada Mixta', 7.50, 'Ensalada fresca con aderezo'),
('Sándwich de Jamón', 5.00, 'Sándwich con jamón, queso y vegetales'),
('Postre del Día', 4.00, 'Postre casero según disponibilidad'),
('Menú Ejecutivo', 15.00, 'Entrada, plato fuerte y postre');

-- Tabla Plato_Producto
INSERT INTO Plato_Producto (id_plato, id_producto, cantidad) VALUES
(1, 3, 1),  -- Combo Pollo incluye Pollo Asado
(1, 2, 1),  -- Combo Pollo incluye Papas Fritas
(1, 1, 1),  -- Combo Pollo incluye Coca Cola
(5, 2, 1),  -- Menú Ejecutivo incluye Papas Fritas
(5, 3, 1);  -- Menú Ejecutivo incluye Pollo Asado

-- Tabla Cliente
INSERT INTO Cliente (dni, nombre, apellido, telefono, email) VALUES
('12345678', 'Luis', 'Gómez', '555-6789', 'luis.gomez@mail.com'),
('12345679', 'Maria', 'López', '555-4321', 'maria.lopez@mail.com'),
('12345610', 'Pedro', 'Jiménez', '555-8765', 'pedro.jimenez@mail.com'),
('12345611', 'Sofia', 'Mendoza', '555-3456', 'sofia.mendoza@mail.com'),
('12345612', 'Jorge', 'Ramirez', '555-9876', 'jorge.ramirez@mail.com');

-- Tabla Venta
INSERT INTO Venta (id_usuario, metodo_pago, monto_total) VALUES
(1, 'efectivo', 12.00),  -- Carlos González
(2, 'tarjeta', 7.50),    -- Ana Martínez
(3, 'transferencia', 15.00),  -- Luis Fernández
(1, 'tarjeta', 10.00),   -- Carlos González
(2, 'efectivo', 4.00);    -- Ana Martínez

-- Tabla Detalle_Venta
INSERT INTO Detalle_Venta (id_venta, id_producto, cantidad, precio_unitario) VALUES
(1, 3, 1, 8.00),  -- Venta 1 incluye Pollo Asado
(1, 2, 1, 2.00),  -- Venta 1 incluye Papas Fritas
(1, 1, 1, 1.50),  -- Venta 1 incluye Coca Cola
(2, 2, 1, 7.50),  -- Venta 2 incluye Ensalada Mixta
(3, 5, 1, 15.00); -- Venta 3 incluye Menú Ejecutivo

-- Tabla Movimiento_Inventario
INSERT INTO Movimiento_Inventario (id_producto, tipo_movimiento, cantidad) VALUES
(1, 'entrada', 50),  -- Coca Cola
(2, 'entrada', 30),  -- Papas Fritas
(3, 'entrada', 20),  -- Pollo Asado
(4, 'entrada', 15),  -- Flan de Vainilla
(5, 'entrada', 10);  -- Menú Diario

-- Tabla Reserva
INSERT INTO Reserva (id_cliente, fecha_reserva, numero_personas, estado) VALUES
(1, '2024-07-10 20:00:00', 4, 'confirmada'),  -- Luis Gómez
(2, '2024-07-11 19:00:00', 2, 'confirmada'),  -- María López
(3, '2024-07-12 18:30:00', 6, 'cancelada'),   -- Pedro Jiménez
(4, '2024-07-13 21:00:00', 3, 'completada'),   -- Sofía Mendoza
(5, '2024-07-14 17:00:00', 5, 'confirmada');   -- Jorge Ramírez

-- Tabla Mesa
INSERT INTO Mesa (numero_mesa, capacidad, estado) VALUES
(1, 4, 'disponible'),
(2, 2, 'ocupada'),
(3, 6, 'reservada'),
(4, 4, 'disponible'),
(5, 8, 'ocupada');

-- Tabla Mesa_Unida
INSERT INTO Mesa_Unida (id_mesa_principal, id_mesa_secundaria) VALUES
(1, 4),  -- Mesa 1 unida con Mesa 4
(2, 3),  -- Mesa 2 unida con Mesa 3
(5, 1);  -- Mesa 5 unida con Mesa 1

-- Tabla Historial_Precio
INSERT INTO Historial_Precio (id_producto, precio) VALUES
(1, 1.50),  -- Coca Cola
(2, 2.00),  -- Papas Fritas
(3, 8.00),  -- Pollo Asado
(4, 3.50),  -- Flan de Vainilla
(5, 10.00); -- Menú Diario

-- Tabla Promocion
INSERT INTO Promocion (descripcion, tipo, valor, fecha_inicio, fecha_fin) VALUES
('Descuento 10% en Bebidas', 'porcentaje', 10.00, '2024-07-01', '2024-07-31'),
('2x1 en Postres', 'monto_fijo', 3.50, '2024-07-05', '2024-07-10'),
('Descuento 5% en eventos especiales', 'porcentaje', 5.00, '2024-07-01', '2024-07-15'),
('Oferta Menú Ejecutivo', 'monto_fijo', 2.00, '2024-07-01', '2024-08-01'),
('Promoción Verano', 'porcentaje', 15.00, '2024-06-01', '2024-08-31');

-- Tabla Actividad
INSERT INTO Actividad (id_usuario, descripcion) VALUES
(1, 'Inicio de sesión'),
(2, 'Creó una venta'),
(3, 'Registro de movimiento de inventario'),
(4, 'Modificó producto'),
(5, 'Actualizó perfil de usuario');

-- Tabla Notificacion
INSERT INTO Notificacion (mensaje, estado) VALUES
('Inventario bajo en Papas Fritas', 'no_leído'),
('Reserva confirmada para 4 personas en mesa 1', 'leído'),
('Nuevo usuario creado: Marta Ramírez', 'no_leído'),
('Promoción 2x1 activa en postres', 'leído'),
('Actualización de menú diario', 'no_leído');

-- mesas con platos pedidos (Mesa_Plato)
INSERT INTO Mesa_Plato (id_mesa, id_plato, cantidad) VALUES
(1, 1, 2), -- Mesa 1: 2 x Combo Pollo
(1, 4, 1), -- Mesa 1: 1 x Flan Casero
(2, 2, 1), -- Mesa 2: 1 x Ensalada Mixta
(3, 5, 3), -- Mesa 3: 3 x Menú Ejecutivo
(4, 3, 2), -- Mesa 4: 2 x Sándwich de Jamón y Queso
(5, 1, 1), -- Mesa 5: 1 x Combo Pollo
(5, 5, 2); -- Mesa 5: 2 x Menú Ejecutivo
