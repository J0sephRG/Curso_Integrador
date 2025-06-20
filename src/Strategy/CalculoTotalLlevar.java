package Strategy;

import Modelo.PedidoLlevar;

public class CalculoTotalLlevar implements CalculoTotalStrategy {
    private static final double COSTO_BASE = 5.0;
    private static final double DESCUENTO_LLEVAR = 0.9; // 10% de descuento
    
    private final PedidoLlevar pedido;
    
    public CalculoTotalLlevar(PedidoLlevar pedido) {
        this.pedido = pedido;
    }
    
    @Override
    public double calcularTotal() {
        // Lógica de cálculo específica para llevar
        return COSTO_BASE * DESCUENTO_LLEVAR;
    }
}
