package umu.tds.apps.AppChat.premium;

public class ServicioPremium {
	private double precioBase;
    private EstrategiaDescuento estrategiaDescuento;

    public ServicioPremium(double precioBase, EstrategiaDescuento estrategiaDescuento) {
        this.precioBase = precioBase;
        this.estrategiaDescuento = estrategiaDescuento;
    }

    public double calculateFinalPrice() {
        return estrategiaDescuento.aplicaDescuento(precioBase);
    }
}
