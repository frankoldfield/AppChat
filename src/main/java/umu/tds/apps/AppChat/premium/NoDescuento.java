package umu.tds.apps.AppChat.premium;

public class NoDescuento implements EstrategiaDescuento{

	public double aplicaDescuento(double precio) {
        return precio;
    }

}
