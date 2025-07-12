-- Crear base de datos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = N'ElDoradoDB')
BEGIN
    CREATE DATABASE ElDoradoDB;
END;
GO

USE ElDoradoDB;
GO

-- Tablas lookup para roles, método de pago, estados
CREATE TABLE Rol (
    rol VARCHAR(20) PRIMARY KEY
);
INSERT INTO Rol VALUES ('admin'), ('cajero'), ('cocinero');

CREATE TABLE MetodoPago (
    metodo_pago VARCHAR(20) PRIMARY KEY
);
INSERT INTO MetodoPago VALUES ('efectivo'), ('tarjeta'), ('transferencia');

CREATE TABLE EstadoReserva (
    estado VARCHAR(20) PRIMARY KEY
);
INSERT INTO EstadoReserva VALUES ('confirmada'), ('cancelada'), ('completada');

CREATE TABLE EstadoMesa (
    estado VARCHAR(20) PRIMARY KEY
);
INSERT INTO EstadoMesa VALUES ('disponible'), ('ocupada'), ('reservada'), ('unida');

CREATE TABLE TipoMovimiento (
    tipo_movimiento VARCHAR(20) PRIMARY KEY
);
INSERT INTO TipoMovimiento VALUES ('entrada'), ('salida');

CREATE TABLE TipoPromocion (
    tipo VARCHAR(20) PRIMARY KEY
);
INSERT INTO TipoPromocion VALUES ('porcentaje'), ('monto_fijo');

CREATE TABLE TipoPedido (
    tipo VARCHAR(20) PRIMARY KEY
);
INSERT INTO TipoPedido VALUES ('delivery'), ('para_llevar');

CREATE TABLE EstadoPedido (
    estado VARCHAR(20) PRIMARY KEY
);
INSERT INTO EstadoPedido VALUES ('pendiente'), ('en_preparacion'), ('en_camino'), ('entregado'), ('cancelado');

-- Tabla Usuario
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Usuario') AND type = N'U')
BEGIN
CREATE TABLE Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES Rol(rol),
    clave VARCHAR(255) NOT NULL,
    fecha_creacion DATETIME2 DEFAULT SYSUTCDATETIME()
);
END;
GO

-- Tabla Proveedor
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Proveedor') AND type = N'U')
BEGIN
CREATE TABLE Proveedor (
    id_proveedor INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(50),
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(MAX)
);
END;
GO

-- Tabla Categoria
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Categoria') AND type = N'U')
BEGIN
CREATE TABLE Categoria (
    id_categoria INT IDENTITY(1,1) PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(MAX)
);
END;
GO

-- Tabla Producto
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Producto') AND type = N'U')
BEGIN
CREATE TABLE Producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    stock_actual INT NOT NULL CHECK (stock_actual >= 0),
    stock_minimo INT NOT NULL CHECK (stock_minimo >= 0),
    precio_unitario DECIMAL(10, 2) NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    id_categoria INT NULL FOREIGN KEY REFERENCES Categoria(id_categoria) ON DELETE SET NULL,
    descripcion VARCHAR(MAX)
);
END;
GO

-- Tabla Pertenecer
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Pertenecer') AND type = N'U')
BEGIN
CREATE TABLE Pertenecer (
    id_categoria INT NOT NULL FOREIGN KEY REFERENCES Categoria(id_categoria) ON DELETE CASCADE,
    id_producto INT NOT NULL FOREIGN KEY REFERENCES Producto(id_producto) ON DELETE CASCADE,
    CONSTRAINT PK_Pertenecer PRIMARY KEY (id_categoria, id_producto)
);
END;
GO

-- Tabla Detalle_Proveedor
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Detalle_Proveedor') AND type = N'U')
BEGIN
CREATE TABLE Detalle_Proveedor (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_proveedor INT NOT NULL FOREIGN KEY REFERENCES Proveedor(id_proveedor) ON DELETE CASCADE,
    id_producto INT NOT NULL FOREIGN KEY REFERENCES Producto(id_producto) ON DELETE CASCADE
);
END;
GO

-- Tabla Plato
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Plato') AND type = N'U')
BEGIN
CREATE TABLE Plato (
    id_plato INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion VARCHAR(MAX)
);
END;
GO

-- Tabla Plato_Producto
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Plato_Producto') AND type = N'U')
BEGIN
CREATE TABLE Plato_Producto (
    id_plato INT NOT NULL FOREIGN KEY REFERENCES Plato(id_plato) ON DELETE CASCADE,
    id_producto INT NOT NULL FOREIGN KEY REFERENCES Producto(id_producto) ON DELETE CASCADE,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    CONSTRAINT PK_Plato_Producto PRIMARY KEY (id_plato, id_producto)
);
END;
GO

-- Tabla Venta
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Venta') AND type = N'U')
BEGIN
CREATE TABLE Venta (
    id_venta INT IDENTITY(1,1) PRIMARY KEY,
    fecha_venta DATETIME2 DEFAULT SYSUTCDATETIME(),
    id_usuario INT NULL FOREIGN KEY REFERENCES Usuario(id_usuario) ON DELETE SET NULL,
    metodo_pago VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES MetodoPago(metodo_pago),
    monto_total DECIMAL(10, 2) NOT NULL
);
END;
GO

-- Tabla Detalle_Venta
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Detalle_Venta') AND type = N'U')
BEGIN
CREATE TABLE Detalle_Venta (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_venta INT NOT NULL FOREIGN KEY REFERENCES Venta(id_venta) ON DELETE CASCADE,
    id_producto INT NOT NULL FOREIGN KEY REFERENCES Producto(id_producto) ON DELETE NO ACTION,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal AS (cantidad * precio_unitario) PERSISTED
);
END;
GO

-- Tabla Movimiento_Inventario
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Movimiento_Inventario') AND type = N'U')
BEGIN
CREATE TABLE Movimiento_Inventario (
    id_movimiento INT IDENTITY(1,1) PRIMARY KEY,
    id_producto INT NOT NULL FOREIGN KEY REFERENCES Producto(id_producto) ON DELETE CASCADE,
    fecha_movimiento DATETIME2 DEFAULT SYSUTCDATETIME(),
    tipo_movimiento VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES TipoMovimiento(tipo_movimiento),
    cantidad INT NOT NULL CHECK (cantidad > 0)
);
END;
GO

-- Tabla Cliente
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Cliente') AND type = N'U')
BEGIN
CREATE TABLE Cliente (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    dni CHAR(8) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    fecha_registro DATETIME2 DEFAULT SYSUTCDATETIME()
);
END;
GO

-- Tabla Reserva
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Reserva') AND type = N'U')
BEGIN
CREATE TABLE Reserva (
    id_reserva INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT NULL FOREIGN KEY REFERENCES Cliente(id_cliente) ON DELETE SET NULL,
    fecha_reserva DATETIME NOT NULL,
    numero_personas INT NOT NULL CHECK (numero_personas > 0),
    estado VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES EstadoReserva(estado) DEFAULT 'confirmada'
);
END;
GO

-- Tabla Mesa
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Mesa') AND type = N'U')
BEGIN
CREATE TABLE Mesa (
    id_mesa INT IDENTITY(1,1) PRIMARY KEY,
    numero_mesa INT NOT NULL UNIQUE,
    capacidad INT NOT NULL CHECK (capacidad > 0),
    estado VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES EstadoMesa(estado) DEFAULT 'disponible'
);
END;
GO

-- Tabla Mesa_Plato
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Mesa_Plato') AND type = N'U')
BEGIN
CREATE TABLE Mesa_Plato (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_mesa INT NOT NULL FOREIGN KEY REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    id_plato INT NOT NULL FOREIGN KEY REFERENCES Plato(id_plato) ON DELETE CASCADE,
    cantidad INT NOT NULL DEFAULT 1
);
END;
GO

-- Tabla Mesa_Unida
CREATE TABLE Mesa_Unida (
    id_unida INT IDENTITY(1,1) PRIMARY KEY,
    id_mesa_principal INT NOT NULL,
    id_mesa_secundaria INT NOT NULL,
    CONSTRAINT FK_Mesa_Principal FOREIGN KEY (id_mesa_principal) REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    CONSTRAINT FK_Mesa_Secundaria FOREIGN KEY (id_mesa_secundaria) REFERENCES Mesa(id_mesa) ON DELETE NO ACTION,
    CONSTRAINT UQ_Mesa_Unida UNIQUE (id_mesa_principal, id_mesa_secundaria)
);


-- Historial_Precio
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Historial_Precio') AND type = N'U')
BEGIN
CREATE TABLE Historial_Precio (
    id_historial INT IDENTITY(1,1) PRIMARY KEY,
    id_producto INT NOT NULL FOREIGN KEY REFERENCES Producto(id_producto) ON DELETE CASCADE,
    precio DECIMAL(10, 2) NOT NULL,
    fecha_cambio DATETIME2 DEFAULT SYSUTCDATETIME()
);
END;
GO

-- Promocion
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Promocion') AND type = N'U')
BEGIN
CREATE TABLE Promocion (
    id_promocion INT IDENTITY(1,1) PRIMARY KEY,
    descripcion VARCHAR(100) NOT NULL,
    tipo VARCHAR(20) NOT NULL FOREIGN KEY REFERENCES TipoPromocion(tipo),
    valor DECIMAL(10, 2) NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE
);
END;
GO

-- Actividad
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Actividad') AND type = N'U')
BEGIN
CREATE TABLE Actividad (
    id_actividad INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NULL FOREIGN KEY REFERENCES Usuario(id_usuario) ON DELETE SET NULL,
    descripcion VARCHAR(MAX) NOT NULL,
    fecha DATETIME2 DEFAULT SYSUTCDATETIME()
);
END;
GO

-- Notificacion
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Notificacion') AND type = N'U')
BEGIN
CREATE TABLE Notificacion (
    id_notificacion INT IDENTITY(1,1) PRIMARY KEY,
    mensaje VARCHAR(MAX) NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('leído','no_leído')) DEFAULT 'no_leído',
    fecha DATETIME2 DEFAULT SYSUTCDATETIME()
);
END;
GO

CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY IDENTITY(1,1),
    id_cliente INT NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('delivery', 'llevar')),
    estado VARCHAR(50) NOT NULL,
    fecha_pedido DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE
);


CREATE TABLE PedidoDelivery (
    id_pedido INT PRIMARY KEY,
    direccion_entrega TEXT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido) ON DELETE CASCADE
);

CREATE TABLE Pedido_Plato (
    id INT PRIMARY KEY IDENTITY(1,1),
    id_pedido INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
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
GO

--INSERTS
SELECT * FROM Pedido;

INSERT INTO Usuario (nombre, apellido, rol, clave)
VALUES 
('Juan', 'Pérez', 'admin', '1234'),
('Ana', 'García', 'cajero', 'abc123'),
('Luis', 'Mendoza', 'cocinero', 'pass123');

INSERT INTO Pedido (id_cliente, tipo, estado)
VALUES 
(1, 'llevar', 'pendiente'),
(2, 'llevar', 'en_preparacion');

INSERT INTO Pedido (id_cliente, tipo, estado)
VALUES 
(3, 'delivery', 'pendiente'),
(1, 'delivery', 'en_camino');

INSERT INTO PedidoDelivery (id_pedido, direccion_entrega)
VALUES 
(3, 'Av. Los Robles 123, Lima'),
(4, 'Calle Falsa 456, Arequipa');

INSERT INTO Mesa (numero_mesa, capacidad, estado)
VALUES
    (1, 4, 'disponible'),
    (2, 4, 'disponible'),
    (3, 2, 'disponible'),
    (4, 6, 'disponible'),
    (5, 2, 'disponible'),
    (6, 4, 'disponible'),
    (7, 6, 'disponible'),
    (8, 4, 'disponible'),
    (9, 2, 'disponible'),
    (10, 6, 'disponible');


INSERT INTO Categoria (nombre_categoria, descripcion)
VALUES 
('Pizzas', 'Platos principales de pizza'),
('Bebidas', 'Bebidas frías y calientes'),
('Postres', 'Dulces y postres'),
('Pasta', 'Comidas basadas en pasta'),
('Ensaladas', 'Platos frescos y saludables');

INSERT INTO Producto (nombre, stock_actual, stock_minimo, precio_unitario, unidad_medida, id_categoria, descripcion)
VALUES 
('Mozzarella', 50, 10, 1.5, 'kg', 1, 'Queso mozzarella para pizzas'),
('Pepsi', 100, 20, 1.2, 'l', 2, 'Bebida gaseosa en lata'),
('Tiramisú', 30, 5, 4.5, 'porción', 3, 'Postre italiano tradicional'),
('Espagueti', 200, 50, 2.3, 'kg', 4, 'Pasta italiana tipo espagueti'),
('Lechuga', 80, 10, 0.5, 'unidad', 5, 'Lechuga fresca para ensaladas');

INSERT INTO Pertenecer (id_categoria, id_producto)
VALUES 
(1, 1), -- Producto 'Mozzarella' pertenece a 'Pizzas'
(2, 2), -- Producto 'Pepsi' pertenece a 'Bebidas'
(3, 3), -- Producto 'Tiramisú' pertenece a 'Postres'
(4, 4), -- Producto 'Espagueti' pertenece a 'Pasta'
(5, 5); -- Producto 'Lechuga' pertenece a 'Ensaladas'

INSERT INTO Plato (nombre, precio, descripcion)
VALUES 
('Pizza Margarita', 18.00, 'Pizza clásica con tomate y albahaca'),
('Espagueti Carbonara', 15.00, 'Pasta en salsa cremosa de huevo y panceta'),
('Ensalada César', 12.00, 'Ensalada fresca con pollo y aderezo César'),
('Tiramisu', 6.00, 'Postre italiano con café y cacao'),
('Lasaña', 20.00, 'Pasta al horno con carne y salsa bechamel');

INSERT INTO Plato_Producto (id_plato, id_producto, cantidad)
VALUES 
(1, 1, 150), -- Pizza Margarita usa 150g de Mozzarella
(2, 4, 200), -- Espagueti Carbonara usa 200g de Espagueti
(3, 5, 50),  -- Ensalada César usa 50g de Lechuga
(4, 3, 1),   -- Tiramisu usa 1 porción de Tiramisu
(5, 4, 250); -- Lasaña usa 250g de Espagueti

INSERT INTO Cliente (dni, nombre, apellido, telefono, email)
VALUES 
('12345678', 'Juan', 'Pérez', '987654321', 'juan.perez@email.com'),
('87654321', 'Lucía', 'Gómez', '912345678', 'lucia.gomez@email.com'),
('12398745', 'Ana', 'Sánchez', '998877665', 'ana.sanchez@email.com'),
('45612398', 'Carlos', 'López', '987123654', 'carlos.lopez@email.com'),
('78945612', 'Maria', 'Martínez', '963852741', 'maria.martinez@email.com');


