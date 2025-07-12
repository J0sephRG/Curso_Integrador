
import Vista.DescripcionDeLaMesas;
import apiclientes.ApiClienteService;
import apiclientes.ApiClienteServiceImpl;
import java.sql.*;

public class DescripcionDeLaMesasController {

    private DescripcionDeLaMesas vista;
    private ApiClienteService apiClienteService;

    public DescripcionDeLaMesasController(Connection connection, int numeroMesa, DescripcionDeLaMesas vista) {
        this.vista = vista;
        this.apiClienteService = new ApiClienteServiceImpl(); // se puede inyectar si lo deseas
    }

    public void buscarClientePorDNI() {
        String documento = vista.getDni();

        if (documento.length() == 8) {
            String nombre = apiClienteService.obtenerNombrePorDni(documento);
            if (nombre != null) {
                vista.setTextNombre(nombre);
            } else {
                vista.mostrarMensaje("No se encontró cliente con DNI.");
            }

        } else if (documento.length() == 11) {
            String razon = apiClienteService.obtenerRazonSocialPorRuc(documento);
            if (razon != null) {
                vista.setTextNombre(razon);
            } else {
                vista.mostrarMensaje("No se encontró empresa con RUC.");
            }

        } else {
            vista.mostrarMensaje("DNI o RUC inválido.");
        }
    }
}
