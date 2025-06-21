package umu.tds.apps.AppChat.premium;

//3. Método Factoría para crear estrategias según algún criterio
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
