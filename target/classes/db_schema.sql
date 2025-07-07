-- Eliminar tablas si existen para una nueva creación
DROP TABLE IF EXISTS Servicios_Tecnicos_Precios, Servicios_Tecnicos, Soporte_Tecnico, Adquisiciones, Productos_Alquiler, Productos_Alta_Tecnologia, Clientes, Impresoras, Monitores, CPUs, Otros_Productos, Productos, Proveedores, Fabricantes CASCADE;

-- Tabla para Fabricantes
CREATE TABLE Fabricantes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    numero_empleados INT
);

-- Tabla base para Productos
CREATE TABLE Productos (
    codigo VARCHAR(50) PRIMARY KEY,
    modelo VARCHAR(255) NOT NULL,
    tipo_producto VARCHAR(50) NOT NULL -- 'CPU', 'Impresora', 'Monitor', 'Otro'
);

-- Tabla para CPUs
CREATE TABLE CPUs (
    codigo_producto VARCHAR(50) PRIMARY KEY REFERENCES Productos(codigo) ON DELETE CASCADE,
    memoria_principal VARCHAR(100)
);

-- Tabla para Impresoras
CREATE TABLE Impresoras (
    codigo_producto VARCHAR(50) PRIMARY KEY REFERENCES Productos(codigo) ON DELETE CASCADE,
    velocidad_impresion VARCHAR(100)
);

-- Tabla para Monitores
CREATE TABLE Monitores (
    codigo_producto VARCHAR(50) PRIMARY KEY REFERENCES Productos(codigo) ON DELETE CASCADE,
    resolucion_maxima VARCHAR(100)
);

-- Tabla para Otros Productos (si tienen campos adicionales)
-- Por ahora, no tiene campos adicionales más allá de los de la tabla Productos.
-- Se puede crear si en el futuro se necesitan campos específicos.
CREATE TABLE Otros_Productos (
    codigo_producto VARCHAR(50) PRIMARY KEY REFERENCES Productos(codigo) ON DELETE CASCADE
);


-- Tabla para Proveedores
CREATE TABLE Proveedores (
    nif VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    direccion VARCHAR(255)
);

-- Tabla de relación muchos a muchos entre Productos y Proveedores (Adquisiciones)
CREATE TABLE Adquisiciones (
    id SERIAL PRIMARY KEY,
    producto_codigo VARCHAR(50) REFERENCES Productos(codigo),
    proveedor_nif VARCHAR(20) REFERENCES Proveedores(nif),
    fecha_adquisicion DATE NOT NULL,
    UNIQUE (producto_codigo, proveedor_nif, fecha_adquisicion)
);

-- Tabla para Productos de Alta Tecnología
CREATE TABLE Productos_Alta_Tecnologia (
    codigo_producto VARCHAR(50) PRIMARY KEY REFERENCES Productos(codigo) ON DELETE CASCADE,
    pais_origen VARCHAR(100),
    fecha_fabricacion DATE,
    fabricante_id INT REFERENCES Fabricantes(id)
);

-- Tabla para Productos en Alquiler
CREATE TABLE Productos_Alquiler (
    codigo_producto VARCHAR(50) PRIMARY KEY REFERENCES Productos(codigo) ON DELETE CASCADE,
    precio_por_hora NUMERIC(10, 2) NOT NULL,
    capacidad_almacenamiento VARCHAR(100) -- Específico para discos duros de alquiler
);


-- Tabla para Soporte Técnico entre fabricantes
CREATE TABLE Soporte_Tecnico (
    id SERIAL PRIMARY KEY,
    fabricante_proveedor_id INT REFERENCES Fabricantes(id),
    producto_alta_tecnologia_codigo VARCHAR(50) REFERENCES Productos_Alta_Tecnologia(codigo_producto),
    precio NUMERIC(10, 2) NOT NULL,
    UNIQUE (fabricante_proveedor_id, producto_alta_tecnologia_codigo)
);

-- Tabla para Clientes
CREATE TABLE Clientes (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);

-- Tabla para registrar la compra de impresoras por clientes
CREATE TABLE Servicios_Tecnicos (
    id SERIAL PRIMARY KEY,
    impresora_codigo VARCHAR(50) REFERENCES Impresoras(codigo_producto),
    cliente_dni VARCHAR(20) REFERENCES Clientes(dni),
    fecha_compra DATE NOT NULL,
    detalle_servicio TEXT,
    total_facturado NUMERIC(10, 2)
);

-- Tabla para historial de precios de servicios técnicos
CREATE TABLE Servicios_Tecnicos_Precios (
    id SERIAL PRIMARY KEY,
    servicio_id INT REFERENCES Servicios_Tecnicos(id),
    precio NUMERIC(10, 2) NOT NULL,
    fecha_aplicacion DATE NOT NULL
);

-- Inserciones de ejemplo
INSERT INTO Fabricantes (nombre, direccion, numero_empleados) VALUES
('Intel Corporation', 'Santa Clara, CA, USA', 110000),
('AMD', 'Santa Clara, CA, USA', 15000),
('HP Inc.', 'Palo Alto, CA, USA', 55000),
('Dell Technologies', 'Round Rock, TX, USA', 158000),
('Seagate Technology', 'Fremont, CA, USA', 40000);

INSERT INTO Productos (codigo, modelo, tipo_producto) VALUES
('CPU001', 'Core i9-12900K', 'CPU'),
('CPU002', 'Ryzen 9 5950X', 'CPU'),
('IMP001', 'HP LaserJet Pro M404dn', 'Impresora'),
('MON001', 'Dell UltraSharp U2721DE', 'Monitor'),
('HDD001', 'Seagate BarraCuda 2TB', 'Otro'),
('SSD001', 'Samsung 980 Pro 1TB', 'Otro');

INSERT INTO CPUs (codigo_producto, memoria_principal) VALUES
('CPU001', '128GB DDR5'),
('CPU002', '128GB DDR4');

INSERT INTO Impresoras (codigo_producto, velocidad_impresion) VALUES
('IMP001', '40 ppm');

INSERT INTO Monitores (codigo_producto, resolucion_maxima) VALUES
('MON001', '2560x1440');

INSERT INTO Otros_Productos (codigo_producto) VALUES
('HDD001'),
('SSD001');

INSERT INTO Proveedores (nif, nombre, direccion) VALUES
('B86000000', 'PC Componentes', 'Alhama de Murcia, España'),
('A12345678', 'Amazon Web Services EMEA SARL', 'Madrid, España');

INSERT INTO Adquisiciones (producto_codigo, proveedor_nif, fecha_adquisicion) VALUES
('CPU001', 'B86000000', '2024-01-15'),
('IMP001', 'A12345678', '2024-02-20');

INSERT INTO Productos_Alta_Tecnologia (codigo_producto, pais_origen, fecha_fabricacion, fabricante_id) VALUES
('CPU001', 'USA', '2023-11-01', 1),
('CPU002', 'USA', '2023-10-15', 2),
('HDD001', 'USA', '2023-09-01', 5);

INSERT INTO Productos_Alquiler (codigo_producto, precio_por_hora, capacidad_almacenamiento) VALUES
('MON001', 15.50, NULL),
('HDD001', 5.00, '2TB');

INSERT INTO Clientes (dni, nombre, telefono, direccion) VALUES
('12345678Z', 'Juan Perez', '600112233', 'Calle Falsa 123, Cartagena');

INSERT INTO Servicios_Tecnicos (impresora_codigo, cliente_dni, fecha_compra, detalle_servicio, total_facturado) VALUES
('IMP001', '12345678Z', '2024-03-01', 'Instalación y configuración inicial.', 50.00);

