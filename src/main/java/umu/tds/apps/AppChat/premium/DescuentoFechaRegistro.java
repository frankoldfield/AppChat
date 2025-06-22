package umu.tds.apps.AppChat.premium;

public class DescuentoFechaRegistro implements EstrategiaDescuento{
	private double diasPasados;

    public DescuentoFechaRegistro(double diasPasados) {
        this.diasPasados = diasPasados;
    }

    public double aplicaDescuento(double precio) {
        return precio * (100 - Math.min(50, diasPasados))/100;
    }
}
