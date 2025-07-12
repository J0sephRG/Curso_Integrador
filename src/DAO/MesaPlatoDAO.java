package DAO;

import ConexionSQL.Conexion; 
import java.math.BigDecimal;
import Modelo.VENTAenMesa.MesaPlato;
import java.util.List;
import Modelo.Plato;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class MesaPlatoDAO {
    private Connection conn; 

    public MesaPlatoDAO(Connection conn) {
        this.conn = conn;
    }
    // Obtener lista de platos de una mesa
    public List<Plato> obtenerPlatosPorMesa(int idMesa) {
        List<Plato> lista = new ArrayList<>();
        String sql = "SELECT p.id_plato, p.nombre, p.precio, p.descripcion " +
                     "FROM mesa_plato mp " +
                     "JOIN plato p ON mp.id_plato = p.id_plato " +
                     "WHERE mp.id_mesa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) { 
            ps.setInt(1, idMesa);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPlato = rs.getInt("id_plato");
                String nombre = rs.getString("nombre");
                BigDecimal precio = rs.getBigDecimal("precio");
                String descripcion = rs.getString("descripcion");
                lista.add(new Plato(idPlato, nombre, precio, descripcion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar un plato de una mesa (por ejemplo, al cancelar un pedido)
    public boolean eliminarPlatoDeMesa(int idMesa, int idPlato) {
        String sql = "DELETE FROM mesa_plato WHERE id_mesa = ? AND id_plato = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) { 
            ps.setInt(1, idMesa);
            ps.setInt(2, idPlato);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }  
    public boolean actualizarEstadoMesa(int idMesa, String estado) {
    String sql = "UPDATE Mesa SET estado = ? WHERE id_mesa = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) { 
        ps.setString(1, estado);
        ps.setInt(2, idMesa);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // Método para agregar plato a mesa y actualizar estado
    public boolean agregarPlatoAMesa(int idMesa, Plato plato, int cantidad) {
        String sqlInsert = "INSERT INTO mesa_plato (id_mesa, id_plato, cantidad) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE Mesa SET estado = 'ocupada' WHERE id_mesa = ?";
        
        try {
            conn.setAutoCommit(false);
            
            // Insertar plato en mesa_plato
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, idMesa);
                psInsert.setInt(2, plato.getId_plato());
                psInsert.setInt(3, cantidad);
                psInsert.executeUpdate();
            }
            
            // Actualizar estado de la mesa
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, idMesa);
                psUpdate.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para limpiar mesa y actualizar estado
    public boolean limpiarMesa(int idMesa) {
        String sqlDelete = "DELETE FROM mesa_plato WHERE id_mesa = ?";
        String sqlUpdate = "UPDATE Mesa SET estado = 'disponible' WHERE id_mesa = ?";
        
        try {
            conn.setAutoCommit(false);
            
            // Eliminar platos de mesa_plato
            try (PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
                psDelete.setInt(1, idMesa);
                psDelete.executeUpdate();
            }
            
            // Actualizar estado de la mesa
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, idMesa);
                psUpdate.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public boolean transferirPlato(int idMesaOrigen, int idMesaDestino, Plato plato) {
    String sqlInsert = "INSERT INTO mesa_plato (id_mesa, id_plato, cantidad) VALUES (?, ?, ?)";
    String sqlDelete = "DELETE FROM mesa_plato WHERE id_mesa = ? AND id_plato = ?";
    String sqlUpdate = "UPDATE Mesa SET estado = 'ocupada' WHERE id_mesa = ?";
    try {
        conn.setAutoCommit(false);

        // Insertar el plato en la mesa de destino
        try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
            psInsert.setInt(1, idMesaDestino);
            psInsert.setInt(2, plato.getId_plato());
            psInsert.setInt(3, 1); // Suponiendo que se transfiere una unidad
            psInsert.executeUpdate();
        }

        // Eliminar el plato de la mesa de origen
        try (PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
            psDelete.setInt(1, idMesaOrigen);
            psDelete.setInt(2, plato.getId_plato());
            psDelete.executeUpdate();
        }
        
        // Actualizar estado de la mesa
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, idMesaDestino);
                psUpdate.executeUpdate();
            }
        
                conn.commit();
                return true;
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                return false;
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    
}
