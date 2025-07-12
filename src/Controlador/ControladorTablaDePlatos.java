package Controlador;

import DAO.PlatoDAO;
import Modelo.Plato;
import Vista.TablaDeLosPlatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ControladorTablaDePlatos {

    private static final int MAX_LONGITUD_NOMBRE = 100;
    private static final int MAX_LONGITUD_DESCRIPCION = 255;

    private final TablaDeLosPlatos vista;
    private final PlatoDAO platoDAO;

    public ControladorTablaDePlatos(TablaDeLosPlatos vista, Connection connection) {
        this.vista = vista;
        this.platoDAO = new PlatoDAO(connection);
        cargarPlatosEnTabla();
    }

    public void cargarPlatosEnTabla() {
        try {
            List<Plato> platos = platoDAO.listarPlatos();
            actualizarTabla(platos);
        } catch (SQLException ex) {
            logError(ex);
            mostrarError("Error al cargar platillos. Por favor intente nuevamente.");
        }
    }

    public void guardarPlato() {
        vista.setBotonesActivos(false); // Desactivar botones mientras se guarda
        try {
            Plato plato = vista.getPlato();
            if (plato == null || !esPlatoValido(plato)) return;

            if (plato.getId_plato() <= 0) {
                platoDAO.agregarPlato(plato);
                mostrarMensaje("Platillo agregado correctamente");
            } else {
                platoDAO.actualizarPlato(plato);
                mostrarMensaje("Platillo actualizado correctamente");
            }

            limpiarFormulario();
            cargarPlatosEnTabla();

        } catch (SQLException ex) {
            logError(ex);
            mostrarError("Error al guardar el platillo. Intente nuevamente.");
        } finally {
            vista.setBotonesActivos(true); // Reactivar botones
        }
    }

    public void cargarDatosSeleccionados() {
        int filaSeleccionada = vista.getjtablePlatosEnLaBaseDeDatos().getSelectedRow();

        if (filaSeleccionada >= 0) {
            int idPlato = (Integer) vista.getjtablePlatosEnLaBaseDeDatos().getValueAt(filaSeleccionada, 0);
            if (idPlato <= 0) {
                mostrarAdvertencia("ID del platillo no válido.");
                return;
            }

            try {
                Plato plato = platoDAO.obtenerPlato(idPlato);
                vista.setPlato(plato);
            } catch (SQLException ex) {
                logError(ex);
                mostrarError("Error al cargar el platillo.");
            }
        } else {
            mostrarAdvertencia("Debe seleccionar un platillo para modificar");
        }
    }

    public void eliminarPlatoSeleccionado() {
        int filaSeleccionada = vista.getjtablePlatosEnLaBaseDeDatos().getSelectedRow();

        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    vista, "¿Está seguro de eliminar este platillo?", "Confirmación", JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                int idPlato = (Integer) vista.getjtablePlatosEnLaBaseDeDatos().getValueAt(filaSeleccionada, 0);

                if (idPlato <= 0) {
                    mostrarAdvertencia("ID del platillo no válido.");
                    return;
                }

                try {
                    platoDAO.eliminarPlato(idPlato);
                    mostrarMensaje("Platillo eliminado correctamente");
                    cargarPlatosEnTabla();
                } catch (SQLException ex) {
                    logError(ex);
                    mostrarError("Error al eliminar el platillo.");
                }
            }
        } else {
            mostrarAdvertencia("Seleccione un platillo para eliminar");
        }
    }

    public void buscarPlatos() {
        String criterio = vista.getTextBuscarPlatillo().getText().trim().toLowerCase();

        if (criterio.isEmpty()) {
            mostrarAdvertencia("Ingrese un criterio de búsqueda.");
            return;
        }

        try {
            List<Plato> platos = platoDAO.listarPlatos();
            List<Plato> filtrados = platos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(criterio))
                    .toList();

            actualizarTabla(filtrados);

        } catch (SQLException ex) {
            logError(ex);
            mostrarError("Error al buscar platillos.");
        }
    }

    private boolean esPlatoValido(Plato plato) {
        if (plato.getNombre() == null || plato.getNombre().trim().isEmpty()) {
            mostrarAdvertencia("El nombre del platillo es obligatorio.");
            return false;
        }

        if (plato.getNombre().length() > MAX_LONGITUD_NOMBRE) {
            mostrarAdvertencia("El nombre no debe superar " + MAX_LONGITUD_NOMBRE + " caracteres.");
            return false;
        }

        if (!plato.getNombre().matches("[\\p{L}0-9 .,'áéíóúÁÉÍÓÚñÑ\\-]+")) {
            mostrarAdvertencia("El nombre contiene caracteres no permitidos.");
            return false;
        }

        if (plato.getDescripcion() != null && plato.getDescripcion().length() > MAX_LONGITUD_DESCRIPCION) {
            mostrarAdvertencia("La descripción no debe superar " + MAX_LONGITUD_DESCRIPCION + " caracteres.");
            return false;
        }

        if (plato.getPrecio() == null || plato.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            mostrarAdvertencia("El precio debe ser mayor que cero.");
            return false;
        }

        if (plato.getPrecio().compareTo(new BigDecimal("100000")) > 0) {
            mostrarAdvertencia("El precio es demasiado alto.");
            return false;
        }

        return true;
    }

    public void actualizarTabla(List<Plato> platos) {
        DefaultTableModel modelo = (DefaultTableModel) vista.getjtablePlatosEnLaBaseDeDatos().getModel();
        modelo.setRowCount(0);
        for (Plato plato : platos) {
            modelo.addRow(new Object[]{
                    plato.getId_plato(),
                    plato.getNombre(),
                    plato.getPrecio(),
                    plato.getDescripcion()
            });
        }
    }

    public void limpiarFormulario() {
        vista.limpiarFormulario();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void logError(Exception ex) {
        // Aquí puedes usar un logger real si usas log4j, SLF4J, etc.
        System.err.println("[ERROR] " + ex.getMessage());
        ex.printStackTrace();
    }

    public void cancelarEdicion() {
        limpiarFormulario();
    }
}
