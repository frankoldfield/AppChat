package umu.tds.apps.AppChat.persistencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import umu.tds.apps.AppChat.dominio.Mensaje;

public class RepositorioMensajes {
	
	public List<Mensaje> mensajes = new ArrayList<Mensaje>();
	public static RepositorioUsuarios INSTANCE = new RepositorioUsuarios();
	
	// 1) Mensajes que envía un usuario a otro, ORDENADOS por hora
	public List<Mensaje> buscarMensajesPorEmisorReceptorOrdenados(
	        String numero_Emisor,
	        String numero_Receptor) {

	    return mensajes.stream()
	        .filter(m -> m.getNumero_emisor().equals(numero_Emisor)
	                  && m.getNumero_receptor().equals(numero_Receptor))
	        .sorted(Comparator.comparing(Mensaje::getHora)) // ascendente. Usa .reversed() si quieres descendente
	        .collect(Collectors.toList());
	}

	/* -------------------------------------------------------------------- */

	// 2) Todos los mensajes entre dos usuarios, ORDENADOS por hora
	public List<Mensaje> buscarConversacionOrdenada(
	        String numeroA,
	        String numeroB) {

	    return mensajes.stream()
	        .filter(m -> (m.getNumero_emisor().equals(numeroA) && m.getNumero_receptor().equals(numeroB))
	                  || (m.getNumero_emisor().equals(numeroB) && m.getNumero_receptor().equals(numeroA)))
	        .sorted(Comparator.comparing(Mensaje::getHora))
	        .collect(Collectors.toList());
	}

	/* -------------------------------------------------------------------- */

	// 3) Mensajes que ha RECIBIDO un usuario de otro, ORDENADOS por hora
	public List<Mensaje> buscarMensajesRecibidosOrdenados(
	        String numero_Receptor,
	        String numero_Emisor) {

	    return mensajes.stream()
	        .filter(m -> m.getNumero_emisor().equals(numero_Emisor)
	                  && m.getNumero_receptor().equals(numero_Receptor))
	        .sorted(Comparator.comparing(Mensaje::getHora))
	        .collect(Collectors.toList());
	}
	
	// 4) Todos los mensajes entre dos usuarios, ORDENADOS por hora
		public List<Mensaje> buscarConversacionOrdenadaConTexto(
		        String texto,
				String numeroA,
		        String numeroB) {

		    return mensajes.stream()
		        .filter(m -> (m.getTexto().contains(texto))
		        		  && ((m.getNumero_emisor().equals(numeroA) && m.getNumero_receptor().equals(numeroB))
		                  || (m.getNumero_emisor().equals(numeroB) && m.getNumero_receptor().equals(numeroA))))
		        .sorted(Comparator.comparing(Mensaje::getHora))
		        .collect(Collectors.toList());
		}
}
