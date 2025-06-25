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
INSERT INTO EstadoMesa VALUES ('disponible'), ('ocupada'), ('reservada');

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
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'Mesa_Unida') AND type = N'U')
BEGIN
CREATE TABLE Mesa_Unida (
    id_unida INT IDENTITY(1,1) PRIMARY KEY,
    id_mesa_principal INT NOT NULL FOREIGN KEY REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    id_mesa_secundaria INT NOT NULL FOREIGN KEY REFERENCES Mesa(id_mesa) ON DELETE CASCADE,
    CONSTRAINT UQ_Mesa_Unida UNIQUE (id_mesa_principal, id_mesa_secundaria)
);
END;
GO

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
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE SET NULL
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
