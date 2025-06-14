package umu.tds.apps.AppChat.persistencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;

public class RepositorioMensajes {
	
	public List<Mensaje> mensajes = new ArrayList<Mensaje>();
	public static RepositorioMensajes INSTANCE = new RepositorioMensajes();
	
	// 1) Mensajes que envía un usuario a otro, ORDENADOS por hora
	public List<Mensaje> buscarMensajesPorEmisorReceptorOrdenados(
	        String numero_Emisor,
	        String numero_Receptor) {

	    return mensajes.stream()
	        .filter(m -> m.getContacto_emisor().getMovil().equals(numero_Emisor)
	                  && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numero_Receptor))
	        .sorted(Comparator.comparing(Mensaje::getHora)) // ascendente. Usa .reversed() si quieres descendente
	        .collect(Collectors.toList());
	}

	/* -------------------------------------------------------------------- */

	// 2) Todos los mensajes entre dos usuarios, ORDENADOS por hora
	public List<Mensaje> buscar_Numero(
	        String numeroUsuario,
	        String numeroExterno) {

	    return mensajes.stream()
	        .filter(m -> (m.getContacto_emisor().getMovil().equals(numeroUsuario) && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroExterno))
	                  || (m.getContacto_emisor().getMovil().equals(numeroExterno) && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario))
	                  || (m.getTipo().equals(TipoMensaje.RECIBIDO) && m.getContacto_emisor().getMovil().equals(numeroExterno) && (m.getContacto_receptor() instanceof Grupo) 
	                		    && ((Grupo) m.getContacto_receptor()).getContactos()
	                		       .stream()
	                		       .anyMatch(c -> c.getMovil().equals(numeroUsuario))))
	        .sorted(Comparator.comparing(Mensaje::getHora))
	        .collect(Collectors.toList());
	}

	/* -------------------------------------------------------------------- */

	// 3) Mensajes que ha RECIBIDO un usuario de otro, ORDENADOS por hora
	public List<Mensaje> buscarMensajesRecibidosOrdenadosConMovil(
	        String numero_Receptor,
	        String numero_Emisor) {

	    return mensajes.stream()
	        .filter(m -> m.getContacto_emisor().getMovil().equals(numero_Emisor)
	                  && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numero_Receptor))
	        .sorted(Comparator.comparing(Mensaje::getHora))
	        .collect(Collectors.toList());
	}
	
	// 4) Todos los mensajes entre dos usuarios, ORDENADOS por hora
		public List<Mensaje> buscar_Texto_y_Numero(
		        String texto,
				String numeroUsuario,
		        String numeroExterno) {

		    return mensajes.stream()
		        .filter(m -> (m.getTexto().contains(texto))
		        		  && ((m.getContacto_emisor().getMovil().equals(numeroUsuario) && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroExterno))
		                  || (m.getContacto_emisor().getMovil().equals(numeroExterno) && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario))
		                  || (m.getTipo().equals(TipoMensaje.RECIBIDO) && m.getContacto_emisor().equals(numeroExterno) && (m.getContacto_receptor() instanceof Grupo) 
		                		    && ((Grupo) m.getContacto_receptor()).getContactos()
		                		       .stream()
		                		       .anyMatch(c -> c.getMovil().equals(numeroUsuario)))))
		        .sorted(Comparator.comparing(Mensaje::getHora))
		        .collect(Collectors.toList());
		}
		
	// 5) Todos los mensajes entre salientes o entrantes del usuario, que contengan el texto dado
		public List<Mensaje> buscar_Texto(
		        String texto,
				String numeroUsuario) {

		    return mensajes.stream()
		        .filter(m -> (m.getTexto().contains(texto))
		        		  && ((m.getContacto_emisor().getMovil().equals(numeroUsuario))
		                  || ( ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario))
		                  || (m.getTipo().equals(TipoMensaje.RECIBIDO)  && (m.getContacto_receptor() instanceof Grupo) 
		                		    && ((Grupo) m.getContacto_receptor()).getContactos()
		                		       .stream()
		                		       .anyMatch(c -> c.getMovil().equals(numeroUsuario)))))
		        .sorted(Comparator.comparing(Mensaje::getHora))
		        .collect(Collectors.toList());
		}
		
	// 6) Todos los mensajes entre dos usuarios, ORDENADOS por hora
		public List<Mensaje> buscar_Texto_y_Contacto(
		        String texto,
				String numeroUsuario,
		        String nombreContacto) {

		    return mensajes.stream()
		        .filter(m -> (m.getTexto().contains(texto))
		        		  && ((m.getContacto_emisor().getMovil().equals(numeroUsuario) && m.getContacto_receptor().getNombre().equals(nombreContacto))
		                  || (m.getContacto_emisor().getNombre().equals(nombreContacto) && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario))
		                  || (m.getTipo().equals(TipoMensaje.RECIBIDO) && m.getContacto_emisor().getNombre().equals(nombreContacto) && (m.getContacto_receptor() instanceof Grupo) 
		                		    && ((Grupo) m.getContacto_receptor()).getContactos()
		                		       .stream()
		                		       .anyMatch(c -> c.getMovil().equals(numeroUsuario)))))
		        .sorted(Comparator.comparing(Mensaje::getHora))
		        .collect(Collectors.toList());
		}
		
	// 7) Mensajes que ha RECIBIDO un usuario de otro, ORDENADOS por hora
		public List<Mensaje> buscar_Contacto(
		        String numeroUsuario,
		        String nombreContacto) {

		    return mensajes.stream()
		        .filter(m -> (m.getContacto_emisor().getMovil().equals(numeroUsuario) && m.getContacto_receptor().getNombre().equals(nombreContacto))
		                  || (m.getContacto_emisor().getNombre().equals(nombreContacto) && ((ContactoIndividual) m.getContacto_receptor()).equals(numeroUsuario)
                		  || (m.getTipo().equals(TipoMensaje.RECIBIDO) && m.getContacto_emisor().getNombre().equals(nombreContacto) && (m.getContacto_receptor() instanceof Grupo) 
		                		    && ((Grupo) m.getContacto_receptor()).getContactos()
		                		       .stream()
		                		       .anyMatch(c -> c.getMovil().equals(numeroUsuario)))))
		        .sorted(Comparator.comparing(Mensaje::getHora))
		        .collect(Collectors.toList());
		}
		
	// 7) Mensajes que ha RECIBIDO un usuario de otro, ORDENADOS por hora
			public List<Mensaje> buscar_Todos(String numeroUsuario) {

			    return mensajes.stream()
			        .filter(m -> m.getContacto_emisor().getMovil().equals(numeroUsuario) || ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario)
			        		|| (m.getTipo().equals(TipoMensaje.RECIBIDO)  && (m.getContacto_receptor() instanceof Grupo) 
		                		    && ((Grupo) m.getContacto_receptor()).getContactos()
		                		       .stream()
		                		       .anyMatch(c -> c.getMovil().equals(numeroUsuario))))
			        .sorted(Comparator.comparing(Mensaje::getHora))
			        .collect(Collectors.toList());
			}
			
		
}
