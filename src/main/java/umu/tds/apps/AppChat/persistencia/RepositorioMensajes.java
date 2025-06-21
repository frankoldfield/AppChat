package umu.tds.apps.AppChat.persistencia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import umu.tds.apps.AppChat.controlador.AppChat;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.persistencia.imp.TDSFactoriaDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSMensajeDAO;

public class RepositorioMensajes {
	
	public List<Mensaje> mensajes = new ArrayList<Mensaje>();
	public static RepositorioMensajes unicaInstancia = new RepositorioMensajes();
	public TDSMensajeDAO mensajeDAO;
	
	public static RepositorioMensajes getInstance() {
		if (unicaInstancia == null) {
			unicaInstancia = new RepositorioMensajes();
		}
		return unicaInstancia;
	}
	
	public RepositorioMensajes() {
		TDSFactoriaDAO factoriaDAO = TDSFactoriaDAO.getInstance();
		mensajeDAO = factoriaDAO.getMensajeDAO();
	}
	
	public Mensaje add(Mensaje mensaje) {
		Mensaje mensajeActualizado = mensajeDAO.create(mensaje);
		mensajes.add(mensajeActualizado);
		return mensajeActualizado;
	}

	public Mensaje get(int id) {
		return mensajeDAO.get(id);
	}
	
	public List<Mensaje> getAll() {
		return mensajeDAO.getAll();
	}
	
	// Todos los mensajes de un usuario
	public List<Mensaje> buscar_Todos(String numeroUsuario) {

		return mensajeDAO.getAll().stream()
		        .filter(m -> ((m.getContacto_emisor().getMovil().equals(numeroUsuario)
		        				&& m.getTipo().equals(TipoMensaje.ENVIADO))
		        		  	  || (m.getContacto_receptor() instanceof ContactoIndividual && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario)
		        		  	  	&& m.getTipo().equals(TipoMensaje.RECIBIDO))))
		        .sorted(Comparator.comparing(Mensaje::getHora)) // ascendente. Usa .reversed() si quieres descendente
		        .collect(Collectors.toList());
	}
	
	// Mensajes que contienen un texto
	public List<Mensaje> buscar_Texto(
	        String texto,
			String numeroUsuario) {

	    return buscar_Todos(numeroUsuario).stream()
	        .filter(m -> m.getTexto().contains(texto))
	        .sorted(Comparator.comparing(Mensaje::getHora))
	        .collect(Collectors.toList());
	}
				
	// Conversación con un contacto individual
		public List<Mensaje> getConversacion(
		        String numero_Emisor,
		        String numero_Receptor) {

		    return mensajeDAO.getAll().stream()
		        .filter(m -> ((m.getContacto_emisor().getMovil().equals(numero_Emisor)
		                  		&& m.getContacto_receptor() instanceof ContactoIndividual && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numero_Receptor))
		        				&& m.getTipo().equals(TipoMensaje.ENVIADO))
		        		  	  || (m.getContacto_emisor().getMovil().equals(numero_Receptor)
				                && m.getContacto_receptor() instanceof ContactoIndividual && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numero_Emisor)
		        		  	  	&& m.getTipo().equals(TipoMensaje.RECIBIDO)))
		        .sorted(Comparator.comparing(Mensaje::getHora)) // ascendente. Usa .reversed() si quieres descendente
		        .collect(Collectors.toList());
		}
		
	// Conversación con un grupo
			public List<Mensaje> getConversacionGrupo(
			        String numero_Emisor,
			        String nombreGrupo) {

			    return mensajeDAO.getAll().stream()
			        .filter(m -> m.getContacto_emisor().getMovil().equals(numero_Emisor)
			                  		&& m.getContacto_receptor() instanceof Grupo && ((Grupo) m.getContacto_receptor()).getNombre().equals(nombreGrupo)
			        				&& m.getTipo().equals(TipoMensaje.ENVIADO))
			        .sorted(Comparator.comparing(Mensaje::getHora)) // ascendente. Usa .reversed() si quieres descendente
			        .collect(Collectors.toList());
			}

	/* -------------------------------------------------------------------- */

	// Conversación con un número
	public List<Mensaje> buscar_Numero(
	        String numeroUsuario,
	        String numeroExterno) {

		return mensajeDAO.getAll().stream()
		        .filter(m -> ((m.getTipo().equals(TipoMensaje.ENVIADO) && m.getContacto_emisor().getMovil().equals(numeroUsuario)
		                  		&& m.getContacto_receptor() instanceof ContactoIndividual && ((ContactoIndividual) m.getContacto_receptor()).getMovil().contains(numeroExterno))
		        		  	  || (m.getTipo().equals(TipoMensaje.RECIBIDO) && m.getContacto_emisor().getMovil().contains(numeroExterno)
				                && m.getContacto_receptor() instanceof ContactoIndividual && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario))))
		        .sorted(Comparator.comparing(Mensaje::getHora)) // ascendente. Usa .reversed() si quieres descendente
		        .collect(Collectors.toList());
	}

	/* -------------------------------------------------------------------- */
	
	// Mensajes que contienen un texto con un número
		public List<Mensaje> buscar_Texto_y_Numero(
		        String texto,
				String numeroUsuario,
		        String numeroExterno) {

			return buscar_Numero(numeroUsuario, numeroExterno).stream()
			        .filter(m -> m.getTexto().contains(texto))
			        .sorted(Comparator.comparing(Mensaje::getHora))
			        .collect(Collectors.toList());
		}
		
	
		
	// Mensajes de un contacto
		public List<Mensaje> buscar_Contacto(
		        String numeroUsuario,
		        String nombreContacto) {

		    return mensajeDAO.getAll().stream()
		        .filter(m -> (m.getTipo().equals(TipoMensaje.ENVIADO) && m.getContacto_emisor().getMovil().equals(numeroUsuario) && m.getContacto_receptor().getNombre().contains(nombreContacto) )
		                  || (m.getTipo().equals(TipoMensaje.RECIBIDO) && m.getContacto_receptor() instanceof ContactoIndividual && ((ContactoIndividual) m.getContacto_receptor()).getMovil().equals(numeroUsuario) && m.getContacto_emisor().getNombre().contains(nombreContacto)))
		        .sorted(Comparator.comparing(Mensaje::getHora))
		        .collect(Collectors.toList());
		}
		
	// Mensajes que contienen un texto de un contacto
		public List<Mensaje> buscar_Texto_y_Contacto(
		        String texto,
				String numeroUsuario,
		        String nombreContacto) {

			return buscar_Contacto(numeroUsuario, nombreContacto).stream()
			        .filter(m -> m.getTexto().contains(texto))
			        .sorted(Comparator.comparing(Mensaje::getHora))
			        .collect(Collectors.toList());
		}
		
	
		
	
			
		
}
