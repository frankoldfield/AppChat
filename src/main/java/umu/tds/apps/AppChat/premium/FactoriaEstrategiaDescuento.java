package umu.tds.apps.AppChat.premium;

public class FactoriaEstrategiaDescuento {
	
 public static EstrategiaDescuento create(String tipoDescuento, double diasTranscurridos, double numeroMensajes) {
     switch (tipoDescuento.toLowerCase()) {
         case "descuento_por_fecha":
             return new DescuentoFechaRegistro(diasTranscurridos);
         case "descuento_por_mensajes":
             return new DescuentoNumeroMensajes(numeroMensajes);
         default:
             return new NoDescuento();
     }
 }
}
