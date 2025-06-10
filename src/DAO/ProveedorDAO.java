
package DAO;

import Conexion.DatabaseConnection;
import model.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private Connection connection;

    public ProveedorDAO(Connection connection) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarProveedor(Proveedor proveedor) throws SQLException {
        String query = "INSERT INTO Proveedor(nombre, contacto, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getContacto());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getEmail());
            statement.setString(5, proveedor.getDireccion());
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
                proveedor = new Proveedor(rs.getInt("id_proveedor"), rs.getString("nombre"),
                                          rs.getString("contacto"), rs.getString("telefono"),
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
                proveedores.add(new Proveedor(rs.getInt("id_proveedor"), rs.getString("nombre"),
                                               rs.getString("contacto"), rs.getString("telefono"),
                                               rs.getString("email"), rs.getString("direccion")));
            }
        }
        return proveedores;
    }

    public void actualizarProveedor(Proveedor proveedor) throws SQLException {
        String query = "UPDATE Proveedor SET nombre = ?, contacto = ?, telefono = ?, email = ?, direccion = ? WHERE id_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getContacto());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getEmail());
            statement.setString(5, proveedor.getDireccion());
            statement.setInt(6, proveedor.getId_proveedor());
            statement.executeUpdate();
        }
    }

    public void eliminarProveedor(int id_proveedor) throws SQLException {
        String query = "DELETE FROM Proveedor WHERE id_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_proveedor);
            statement.executeUpdate();
        }
    }
}
