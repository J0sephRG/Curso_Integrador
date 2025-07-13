
package DAO;

import ConexionSQL.Conexion;
import Modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProveedorDAO {
    private Connection connection;

    public ProveedorDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarProveedor(Proveedor proveedor) throws SQLException {
        String query = "INSERT INTO Proveedor(numero_documento, nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getnumeroDocumento());
            statement.setString(2, proveedor.getNombre());
            statement.setString(3, proveedor.getApellido());
            statement.setString(4, proveedor.getTelefono());
            statement.setString(5, proveedor.getEmail());
            statement.setString(6, proveedor.getDireccion());
            statement.executeUpdate();
        }
    }

    public Proveedor obtenerProveedor(int id_proveedor) throws SQLException {
        String query = "SELECT * FROM Proveedor WHERE id_proveedor = ?";
        Proveedor proveedor = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_proveedor);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor(rs.getString("numeroDocumento"), rs.getString("nombre"),
                                          rs.getString("apellido"), rs.getString("telefono"),
                                          rs.getString("email"), rs.getString("direccion"));
            }
        }
        return proveedor;
    }

    public List<Proveedor> listarProveedores() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        String query = "SELECT * FROM Proveedor";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                proveedores.add(new Proveedor(rs.getString("numero_documento"), rs.getString("nombre"),
                                               rs.getString("apellido"), rs.getString("telefono"),
                                               rs.getString("email"), rs.getString("direccion")));
            }
        }
        return proveedores;
    }

    public void actualizarProveedor(Proveedor proveedor) throws SQLException {
        String query = "UPDATE Proveedor SET numero_documento  = ?, nombre = ?, apellido = ?, telefono = ?, email = ?, direccion = ? WHERE id_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getnumeroDocumento());
            statement.setString(1, proveedor.getNombre());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getEmail());
            statement.setString(5, proveedor.getDireccion());
            statement.executeUpdate();
        }
    }

    public void eliminarProveedor(int id) throws SQLException {
        String query = "DELETE FROM Proveedor WHERE id_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public List<Proveedor> obtenerTodos() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";
    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Proveedor p = new Proveedor(
                rs.getString("numero_documento"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("direccion")
            );
            lista.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
}