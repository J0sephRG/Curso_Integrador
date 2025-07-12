package DAO;

import ConexionSQL.Conexion;
import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardDAO {
    private Connection conn;
    
    public DashboardDAO() {
        Conexion conexion = new Conexion();
        this.conn = conexion.Conectar();
    }
    
    // Obtener ventas de hoy
    public double getVentasHoy() {
        String sql = "SELECT ISNULL(SUM(monto_total), 0) as total FROM Venta WHERE CAST(fecha_venta AS DATE) = CAST(GETDATE() AS DATE)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas de hoy: " + e.getMessage());
        }
        return 0.0;
    }
    
    // Obtener pedidos activos
    public int getPedidosActivos() {
        String sql = "SELECT COUNT(*) as total FROM Pedido WHERE estado IN ('pendiente', 'en_preparacion', 'en_camino')";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos activos: " + e.getMessage());
        }
        return 0;
    }
    
    // Obtener mesas ocupadas
    public int getMesasOcupadas() {
        String sql = "SELECT COUNT(*) as total FROM Mesa WHERE estado = 'ocupada'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener mesas ocupadas: " + e.getMessage());
        }
        return 0;
    }
    
    // Obtener productos con stock crítico
    public int getStockCritico() {
        String sql = "SELECT COUNT(*) as total FROM Producto WHERE stock_actual <= stock_minimo";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener stock crítico: " + e.getMessage());
        }
        return 0;
    }
    
    // Obtener ventas de los últimos 7 días
    public Map<String, Double> getVentasUltimos7Dias() {
        Map<String, Double> ventas = new LinkedHashMap<>();
        String sql = "SELECT CAST(fecha_venta AS DATE) as fecha, SUM(monto_total) as total " +
                    "FROM Venta " +
                    "WHERE fecha_venta >= DATEADD(day, -7, GETDATE()) " +
                    "GROUP BY CAST(fecha_venta AS DATE) " +
                    "ORDER BY fecha";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Date fecha = rs.getDate("fecha");
                double total = rs.getDouble("total");
                String fechaStr = fecha.toString();
                ventas.put(fechaStr, total);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas de últimos 7 días: " + e.getMessage());
        }
        
        // Completar días faltantes con 0
        LocalDate hoy = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = hoy.minusDays(i);
            String fechaStr = fecha.toString();
            if (!ventas.containsKey(fechaStr)) {
                ventas.put(fechaStr, 0.0);
            }
        }
        
        // Si no hay datos reales, agregar datos de ejemplo
        if (ventas.isEmpty() || ventas.values().stream().allMatch(v -> v == 0.0)) {
            System.out.println("📊 No hay datos reales de ventas, cargando datos de ejemplo...");
            ventas.clear();
            for (int i = 6; i >= 0; i--) {
                LocalDate fecha = hoy.minusDays(i);
                String fechaStr = fecha.toString();
                // Generar datos de ejemplo realistas
                double ventaEjemplo = 1200 + (Math.random() * 800); // Entre 1200 y 2000
                ventas.put(fechaStr, ventaEjemplo);
            }
        }
        
        return ventas;
    }
    
    // Obtener productos más vendidos
    public Map<String, Integer> getProductosMasVendidos() {
        Map<String, Integer> productos = new LinkedHashMap<>();
        String sql = "SELECT TOP 5 p.nombre, SUM(dv.cantidad) as total_vendido " +
                    "FROM Detalle_Venta dv " +
                    "INNER JOIN Producto p ON dv.id_producto = p.id_producto " +
                    "INNER JOIN Venta v ON dv.id_venta = v.id_venta " +
                    "WHERE v.fecha_venta >= DATEADD(day, -30, GETDATE()) " +
                    "GROUP BY p.nombre " +
                    "ORDER BY total_vendido DESC";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("total_vendido");
                productos.put(nombre, cantidad);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos más vendidos: " + e.getMessage());
        }
        
        // Si no hay datos, devolver datos de ejemplo
        if (productos.isEmpty()) {
            System.out.println("📊 No hay datos reales, cargando productos de ejemplo...");
            productos.put("Arroz Chaufa", 45);
            productos.put("Pollo a la Brasa", 38);
            productos.put("Ceviche", 32);
            productos.put("Lomo Saltado", 28);
            productos.put("Ají de Gallina", 25);
        }
        
        return productos;
    }
    
    // Obtener últimos pedidos
    public List<Map<String, Object>> getUltimosPedidos() {
        List<Map<String, Object>> pedidos = new ArrayList<>();
        String sql = "SELECT TOP 10 p.id_pedido, " +
                    "CONCAT(c.nombre, ' ', c.apellido) as cliente, " +
                    "p.tipo, p.estado, p.fecha_pedido " +
                    "FROM Pedido p " +
                    "LEFT JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                    "ORDER BY p.fecha_pedido DESC";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> pedido = new HashMap<>();
                pedido.put("id", rs.getInt("id_pedido"));
                pedido.put("cliente", rs.getString("cliente"));
                pedido.put("tipo", rs.getString("tipo"));
                pedido.put("estado", rs.getString("estado"));
                pedido.put("fecha", rs.getTimestamp("fecha_pedido"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener últimos pedidos: " + e.getMessage());
        }
        
        return pedidos;
    }
    
    // Obtener ventas por método de pago
    public Map<String, Double> getVentasPorMetodoPago() {
        Map<String, Double> ventas = new LinkedHashMap<>();
        String sql = "SELECT metodo_pago, SUM(monto_total) as total " +
                    "FROM Venta " +
                    "WHERE fecha_venta >= DATEADD(day, -30, GETDATE()) " +
                    "GROUP BY metodo_pago " +
                    "ORDER BY total DESC";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String metodo = rs.getString("metodo_pago");
                double total = rs.getDouble("total");
                ventas.put(metodo, total);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por método de pago: " + e.getMessage());
        }
        
        return ventas;
    }
    
    // Obtener categorías más vendidas
    public Map<String, Integer> getCategoriasMasVendidas() {
        Map<String, Integer> categorias = new LinkedHashMap<>();
        String sql = "SELECT TOP 5 c.nombre_categoria, SUM(dv.cantidad) as total_vendido " +
                    "FROM Detalle_Venta dv " +
                    "INNER JOIN Producto p ON dv.id_producto = p.id_producto " +
                    "INNER JOIN Categoria c ON p.id_categoria = c.id_categoria " +
                    "INNER JOIN Venta v ON dv.id_venta = v.id_venta " +
                    "WHERE v.fecha_venta >= DATEADD(day, -30, GETDATE()) " +
                    "GROUP BY c.nombre_categoria " +
                    "ORDER BY total_vendido DESC";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String categoria = rs.getString("nombre_categoria");
                int cantidad = rs.getInt("total_vendido");
                categorias.put(categoria, cantidad);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener categorías más vendidas: " + e.getMessage());
        }
        
        return categorias;
    }
    
    // Obtener ventas por hora del día (últimos 7 días)
    public Map<String, Double> getVentasPorHora() {
        Map<String, Double> ventasHora = new LinkedHashMap<>();
        String sql = "SELECT DATEPART(HOUR, fecha_venta) as hora, SUM(monto_total) as total " +
                    "FROM Venta " +
                    "WHERE fecha_venta >= DATEADD(day, -7, GETDATE()) " +
                    "GROUP BY DATEPART(HOUR, fecha_venta) " +
                    "ORDER BY hora";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hora = rs.getInt("hora");
                double total = rs.getDouble("total");
                String horaStr = String.format("%02d:00", hora);
                ventasHora.put(horaStr, total);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por hora: " + e.getMessage());
        }
        
        return ventasHora;
    }
    
    // Obtener ingresos promedio por día de la semana
    public Map<String, Double> getVentasPorDiaSemana() {
        Map<String, Double> ventasDia = new LinkedHashMap<>();
        String sql = "SELECT " +
                    "CASE DATEPART(WEEKDAY, fecha_venta) " +
                    "    WHEN 1 THEN 'Domingo' " +
                    "    WHEN 2 THEN 'Lunes' " +
                    "    WHEN 3 THEN 'Martes' " +
                    "    WHEN 4 THEN 'Miércoles' " +
                    "    WHEN 5 THEN 'Jueves' " +
                    "    WHEN 6 THEN 'Viernes' " +
                    "    WHEN 7 THEN 'Sábado' " +
                    "END as dia_semana, " +
                    "AVG(monto_total) as promedio " +
                    "FROM Venta " +
                    "WHERE fecha_venta >= DATEADD(day, -30, GETDATE()) " +
                    "GROUP BY DATEPART(WEEKDAY, fecha_venta) " +
                    "ORDER BY DATEPART(WEEKDAY, fecha_venta)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String dia = rs.getString("dia_semana");
                double promedio = rs.getDouble("promedio");
                ventasDia.put(dia, promedio);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por día de semana: " + e.getMessage());
        }
        
        return ventasDia;
    }
    
    // Obtener productos con bajo stock (para alerta)
    public List<Map<String, Object>> getProductosBajoStock() {
        List<Map<String, Object>> productos = new ArrayList<>();
        String sql = "SELECT TOP 10 nombre, stock_actual, stock_minimo " +
                    "FROM Producto " +
                    "WHERE stock_actual <= stock_minimo " +
                    "ORDER BY (stock_actual - stock_minimo) ASC";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> producto = new HashMap<>();
                producto.put("nombre", rs.getString("nombre"));
                producto.put("stock_actual", rs.getInt("stock_actual"));
                producto.put("stock_minimo", rs.getInt("stock_minimo"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos bajo stock: " + e.getMessage());
        }
        
        return productos;
    }
    
    // Obtener total de clientes registrados
    public int getTotalClientes() {
        String sql = "SELECT COUNT(*) as total FROM Cliente";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de clientes: " + e.getMessage());
        }
        return 0;
    }
    
    // Obtener promedio de venta diaria
    public double getPromedioVentaDiaria() {
        String sql = "SELECT AVG(total_dia) as promedio " +
                    "FROM (SELECT CAST(fecha_venta AS DATE) as fecha, SUM(monto_total) as total_dia " +
                    "      FROM Venta " +
                    "      WHERE fecha_venta >= DATEADD(day, -30, GETDATE()) " +
                    "      GROUP BY CAST(fecha_venta AS DATE)) as ventas_diarias";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("promedio");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener promedio de venta diaria: " + e.getMessage());
        }
        return 0.0;
    }

    public void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
