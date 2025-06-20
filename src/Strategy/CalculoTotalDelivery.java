package Strategy;

import Modelo.PedidoDelivery;

public class CalculoTotalDelivery implements CalculoTotalStrategy {
    private static final double COSTO_BASE = 5.0;
    private static final double TARIFA_DELIVERY = 2.5;
    
    private final PedidoDelivery pedido;
    
    public CalculoTotalDelivery(PedidoDelivery pedido) {
        this.pedido = pedido;
    }
    
    @Override
    public double calcularTotal() {
        // Lógica de cálculo específica para delivery
        return COSTO_BASE + TARIFA_DELIVERY;
    }
}

