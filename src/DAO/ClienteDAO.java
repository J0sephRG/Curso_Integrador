package DAO;

import ConexionSQL.Conexion; // Asegúrate de que esta clase maneje la conexión a SQL Server
import Modelo.Cliente;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ClienteDAO {
    private Connection conn; 

    public ClienteDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarCliente(Cliente cliente) throws SQLException {
        String query = "INSERT INTO Cliente(dni, nombre, apellido, telefono, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setString(1, cliente.getDni());
            statement.setString(2, cliente.getNombre());
            statement.setString(3, cliente.getApellido());
            statement.setString(4, cliente.getTelefono());
            statement.setString(5, cliente.getEmail());
            statement.executeUpdate();
        }
    }

    public Cliente obtenerCliente(int id_cliente) throws SQLException {
        String query = "SELECT * FROM Cliente WHERE id_cliente = ?";
        Cliente cliente = null;
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_cliente);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("id_cliente"), rs.getString("dni"), rs.getString("nombre"),
                                      rs.getString("apellido"), rs.getString("telefono"),
                                      rs.getString("email"), rs.getTimestamp("fecha_registro"));
            }
        }
        return cliente;
    }

    public List<Cliente> listarClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM Cliente";
        try (PreparedStatement statement = conn.prepareStatement(query); // Cambiado 'connection' a 'conn'
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id_cliente"), rs.getString("dni"), rs.getString("nombre"),
                                         rs.getString("apellido"), rs.getString("telefono"),
                                         rs.getString("email"), rs.getTimestamp("fecha_registro")));
            }
        }
        return clientes;
    }

    public void actualizarCliente(Cliente cliente) throws SQLException {
        String query = "UPDATE Cliente SET nombre = ?, apellido = ?, telefono = ?, email = ? WHERE id_cliente = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
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
        try (PreparedStatement statement = conn.prepareStatement(query)) { // Cambiado 'connection' a 'conn'
            statement.setInt(1, id_cliente);
            statement.executeUpdate();
        }
    }
}
