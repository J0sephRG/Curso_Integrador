package DAO;

import Modelo.Plato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class PlatoDAO {
    private final Connection conn;

    public PlatoDAO(Connection conn) {
        this.conn = conn;
    }

   
    private void validarPlato(Plato plato) {
        if (plato == null) throw new IllegalArgumentException("El platillo no puede ser nulo.");
        if (plato.getNombre() == null || plato.getNombre().trim().isEmpty())
            throw new IllegalArgumentException("El nombre del platillo es obligatorio.");
        if (plato.getPrecio() == null || plato.getPrecio().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        if (plato.getDescripcion() == null || plato.getDescripcion().trim().isEmpty())
            throw new IllegalArgumentException("La descripción es obligatoria.");
    }

    // Reutilización para mapear resultado de la base de datos
    private Plato mapearPlato(ResultSet rs) throws SQLException {
        return new Plato(
            rs.getInt("id_plato"),
            rs.getString("nombre"),
            rs.getBigDecimal("precio"),
            rs.getString("descripcion")
        );
    }

    public void agregarPlato(Plato plato) throws SQLException {
        validarPlato(plato);

        String query = "INSERT INTO Plato(nombre, precio, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, plato.getNombre().trim());
            statement.setBigDecimal(2, plato.getPrecio());
            statement.setString(3, plato.getDescripcion().trim());
            statement.executeUpdate();
        }
    }

    public Plato obtenerPlato(int id_plato) throws SQLException {
        String query = "SELECT * FROM Plato WHERE id_plato = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id_plato);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapearPlato(rs);
                }
            }
        }
        return null;
    }

    public List<Plato> listarPlatos() throws SQLException {
        if (conn == null) throw new SQLException("La conexión a la base de datos no está inicializada.");

        List<Plato> platos = new ArrayList<>();
        String query = "SELECT * FROM Plato";

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                platos.add(mapearPlato(rs));
            }
        }
        return platos;
    }

    public void actualizarPlato(Plato plato) throws SQLException {
        validarPlato(plato);

        String query = "UPDATE Plato SET nombre = ?, precio = ?, descripcion = ? WHERE id_plato = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, plato.getNombre().trim());
            statement.setBigDecimal(2, plato.getPrecio());
            statement.setString(3, plato.getDescripcion().trim());
            statement.setInt(4, plato.getId_plato());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No se encontró el platillo con ID: " + plato.getId_plato());
            }
        }
    }

    public void eliminarPlato(int id_plato) throws SQLException {
        String query = "DELETE FROM Plato WHERE id_plato = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id_plato);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("No se encontró un platillo con ID: " + id_plato);
            }
        }
    }

    public Plato buscarPlatoPorId(int idPlato) throws SQLException {
        return obtenerPlato(idPlato); 
    }

    // Búsqueda segura por nombre
    public List<Plato> buscarPlatosPorNombre(String criterio) throws SQLException {
        List<Plato> resultados = new ArrayList<>();
        String query = "SELECT * FROM Plato WHERE LOWER(nombre) LIKE ?";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, "%" + criterio.toLowerCase().trim() + "%");

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapearPlato(rs));
                }
            }
        }

        return resultados;
    }
}
