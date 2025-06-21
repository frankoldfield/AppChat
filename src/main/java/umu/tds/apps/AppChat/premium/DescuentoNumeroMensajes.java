package umu.tds.apps.AppChat.premium;

public class DescuentoNumeroMensajes implements EstrategiaDescuento{
	private double numeroMensajes;

    public DescuentoNumeroMensajes(double numeroMensajes) {
        this.numeroMensajes = numeroMensajes;
    }

    public double aplicaDescuento(double price) {
        return Math.max(50, price - numeroMensajes);
    }
}
