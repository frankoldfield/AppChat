package umu.tds.apps.AppChat.controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.DocumentException;

import umu.tds.apps.AppChat.dominio.Contacto;
import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.RepositorioMensajes;
import umu.tds.apps.AppChat.persistencia.RepositorioUsuarios;
import umu.tds.apps.AppChat.servicios.ExportPDF;

public class AppChat {

	public Usuario usuarioActual;
	public static AppChat INSTANCE = null;

	//Patrón singleton
	public static AppChat getInstance() {
		if (INSTANCE == null)
			INSTANCE = new AppChat();
		return INSTANCE;
	}
	
	public void enviarMensajeContacto(ContactoIndividual ContactoDestino, String texto, int emoji, TipoMensaje tipo_mensaje) {
		//TODO PERSISTENCIA
		
		Mensaje mensaje = new Mensaje(texto, LocalDateTime.now(), emoji, tipo_mensaje, usuarioActual.getContactoPropio(), ContactoDestino);
		ContactoDestino.addMensaje(mensaje);
		RepositorioMensajes.INSTANCE.mensajes.add(mensaje);
		
		Mensaje mensajeReceptor = new Mensaje(texto, LocalDateTime.now(), emoji, TipoMensaje.RECIBIDO, usuarioActual.getContactoPropio(), ContactoDestino);
		
		getUsuario(ContactoDestino.getMovil()).registrarMensaje(mensajeReceptor);
		
	}
	
	public void enviarMensajeGrupo(Grupo grupo_receptor, String texto, int emoji, TipoMensaje tipo_mensaje) { 
		//TODO PERSISTENCIA
		
		Mensaje mensaje;
		mensaje = new Mensaje(texto, LocalDateTime.now(), emoji, tipo_mensaje, usuarioActual.getContactoPropio(), grupo_receptor);
		grupo_receptor.addMensaje(mensaje);
		RepositorioMensajes.INSTANCE.mensajes.add(mensaje);
		
		
		for(ContactoIndividual contacto: grupo_receptor.getContactos()) {
			Mensaje mensajeReceptor = new Mensaje(texto, LocalDateTime.now(), emoji, TipoMensaje.RECIBIDO, usuarioActual.getContactoPropio(), contacto);
			getUsuario(contacto.getMovil()).registrarMensaje(mensajeReceptor);
		}
	}
	
	public Usuario getUsuario(String numero_telefono) { //Desde aquí se llama a getUsuario del repositorio
		
		return RepositorioUsuarios.INSTANCE.buscarUsuarioPorMovil(numero_telefono);
	}
	
	public ContactoIndividual agregarContacto(String nombre, String movil) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//TODO PERSISTENCIA
		return usuarioActual.addContacto(nombre, movil);
	}
	
	public Grupo CrearOActualizarGrupo(String nombreGrupo, List<ContactoIndividual> contactosGrupo) {
		//TODO PERSISTENCIA
		
		Grupo grupoNuevo = usuarioActual.addOrUpdateGrupo(nombreGrupo, contactosGrupo);
		
		return grupoNuevo;
	}

	//Called from: VentanaRegistro, CargarAppChat
	public int registrarUsuario(String nombre, String apellidos, String password, String telefono, String confirma_password, LocalDate fecha, String ruta_imagen, String saludo) {
		//TODO PERSISTENCIA
		
		int returnCode = 0;
		//Comprobar que no esté ya registrado el número de teléfono
		for(Usuario usuarioRegistrado: RepositorioUsuarios.INSTANCE.usuarios) {
			if(usuarioRegistrado.getMovil().equals(telefono)) {
				returnCode = -1;
			}
		}
		//Comprobar fecha válida
		if(fecha.isBefore(LocalDate.of(1930, 1, 1)) || fecha==null) {
			returnCode = -2;
		}
		
		if(ruta_imagen.isEmpty()) {
			returnCode = -3;
		}
		
		if(!password.equals(confirma_password)) {
			
		}
		
		if(returnCode==0) {
			Usuario usuarioNuevo = new Usuario(nombre, apellidos, password, telefono, fecha, ruta_imagen, saludo);
			RepositorioUsuarios.INSTANCE.usuarios.add(usuarioNuevo);
		}
		
		return returnCode;
	}

	//Called from: VentanaLogin, CargarAppChat
	public int login(String telefono, String password) {
		//TODO PERSISTENCIA
		
		int returnCode = -1;
		for(Usuario usuarioRegistrado: RepositorioUsuarios.INSTANCE.usuarios) {
			if(usuarioRegistrado.getMovil().equals(telefono)) {
				if(usuarioRegistrado.getPassword().equals(password)) {
					usuarioActual = usuarioRegistrado;
					returnCode = 0;
				}
				else {
					returnCode = -2;
				}
				return returnCode;
			}
		}
		return returnCode;
	}
	
	public List<Mensaje> buscarMensajes(String texto, String numero, String nombre_contacto) {
		if(texto.isEmpty() && numero.isEmpty() && !nombre_contacto.isEmpty()) {
			return RepositorioMensajes.INSTANCE.buscar_Contacto(usuarioActual.getMovil(), nombre_contacto);
		}
		else if (texto.isEmpty() && !numero.isEmpty() && nombre_contacto.isEmpty()) {
			return RepositorioMensajes.INSTANCE.buscar_Numero(usuarioActual.getMovil(), numero);
		}
		else if (!texto.isEmpty() && numero.isEmpty() && !nombre_contacto.isEmpty()) {
			return RepositorioMensajes.INSTANCE.buscar_Texto_y_Contacto(texto, usuarioActual.getMovil(), nombre_contacto);
		}
		
		else if (!texto.isEmpty() && !numero.isEmpty() && nombre_contacto.isEmpty()) {
			return RepositorioMensajes.INSTANCE.buscar_Texto_y_Numero(texto, usuarioActual.getMovil(), numero);
		}
		
		else if (texto.isEmpty() && numero.isEmpty() && nombre_contacto.isEmpty()) {
			return RepositorioMensajes.INSTANCE.buscar_Todos(usuarioActual.getMovil());
		}
		else if (!texto.isEmpty() && numero.isEmpty() && nombre_contacto.isEmpty()) {
			return RepositorioMensajes.INSTANCE.buscar_Texto(texto, usuarioActual.getMovil());
		}
		else {
			return new ArrayList<Mensaje>();
		}
		
		
	}
	
	public List<ContactoIndividual> getListaContactos() {
		List<ContactoIndividual> listaContactos = usuarioActual.getContactos().stream()
			    .filter(c -> c instanceof ContactoIndividual)
			    .map(c -> (ContactoIndividual) c)
			    .collect(Collectors.toList());
		return listaContactos;
	}
	
	public List<Grupo> getListaGrupos() {
		List<Grupo> listaGrupos = usuarioActual.getContactos().stream()
			    .filter(c -> c instanceof Grupo)
			    .map(c -> (Grupo) c)
			    .collect(Collectors.toList());
		return listaGrupos;
	}
	
	public Grupo getGrupo(String nombreGrupo) {
		return usuarioActual.getGrupo(nombreGrupo);
	}

	public String getImagenContacto(String movil) {
		Usuario usuarioRecuperado = getUsuario(movil);
		if(usuarioRecuperado!=null) {
			return getUsuario(movil).getImagen();
		}
		else {
			return "/usuarios/abbetatkir.jpg";
		}
	}

	public void cambiarImagen(String rutaAbsoluta) {
		// TODO PERSISTENCIA
		int indice = rutaAbsoluta.toLowerCase().lastIndexOf("usuarios" + File.separator);
		String rutaRelativa = (indice != -1) ? rutaAbsoluta.substring(indice) : "";
		rutaRelativa = '/'+rutaRelativa.replace('\\', '/');

	}
	
	public int buyPremium() {
		//TODO PERSISTENCIA
		
		usuarioActual.setPremium(true);
		return 0;
	}

	
	public int removePremium() {
		//TODO PERSISTENCIA
		
		usuarioActual.setPremium(false);
		return 0;
	}
	
	public boolean isPremium() {
		return usuarioActual.isPremium();
	}
	
	public List<Mensaje> getConversacion(Contacto contacto) {
		return contacto.getMensajes().stream().sorted(Comparator.comparing(Mensaje::getHora))
        .collect(Collectors.toList());
	}
	
	public int numMensajes() {
		return RepositorioMensajes.INSTANCE.buscar_Todos(usuarioActual.getMovil()).size();
	}
	
	public LocalDateTime getFechaRegistro() {
		return usuarioActual.getFechaCreacion();
	}
	
	public void exportPDF(Contacto contacto) {
		if(contacto!=null) {
			try {
				ExportPDF.INSTANCE.exportarAPDF(getConversacion(contacto), usuarioActual.getNombre());
			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
