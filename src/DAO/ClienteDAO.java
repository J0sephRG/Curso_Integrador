package DAO;

import Conexion.DatabaseConnection;
import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;

    public ClienteDAO(Connection connection) throws SQLException {
         this.connection = DatabaseConnection.getConnection(); // Obtener la conexión de la base de datos
    }

    public void agregarCliente(Cliente cliente) throws SQLException {
        String query = "INSERT INTO Cliente(nombre, apellido, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.executeUpdate();
        }
    }

    public Cliente obtenerCliente(int id_cliente) throws SQLException {
        String query = "SELECT * FROM Cliente WHERE id_cliente = ?";
        Cliente cliente = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_cliente);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"),
                                      rs.getString("apellido"), rs.getString("telefono"),
                                      rs.getString("email"), rs.getTimestamp("fecha_registro"));
            }
        }
        return cliente;
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM Cliente";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"),
                                         rs.getString("apellido"), rs.getString("telefono"),
                                         rs.getString("email"), rs.getTimestamp("fecha_registro")));
            }
        }
        return clientes;
    }

    public void actualizarCliente(Cliente cliente) throws SQLException {
        String query = "UPDATE Cliente SET nombre = ?, apellido = ?, telefono = ?, email = ? WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.setInt(5, cliente.getId_cliente());
            statement.executeUpdate();
        }
    }

    public void eliminarCliente(int id_cliente) throws SQLException {
        String query = "DELETE FROM Cliente WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id_cliente);
            statement.executeUpdate();
        }
    }
}
