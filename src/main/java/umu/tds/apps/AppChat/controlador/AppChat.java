package umu.tds.apps.AppChat.controlador;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import umu.tds.apps.AppChat.persistencia.abstracta.ContactoIndividualDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSContactoIndividualDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSFactoriaDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSGrupoDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSMensajeDAO;
import umu.tds.apps.AppChat.persistencia.imp.TDSUsuarioDAO;
import umu.tds.apps.AppChat.premium.EstrategiaDescuento;
import umu.tds.apps.AppChat.premium.ExportPDF;
import umu.tds.apps.AppChat.premium.FactoriaEstrategiaDescuento;
import umu.tds.apps.AppChat.premium.ServicioPremium;

public class AppChat {

	public Usuario usuarioActual;
	public static AppChat INSTANCE = null;
	public RepositorioMensajes repoMensajes;
	public RepositorioUsuarios repoUsuarios;
	public TDSGrupoDAO grupoDAO;
	public TDSContactoIndividualDAO contactoIndividualDAO;
	public double precioPremium;

	//Patrón singleton
	public static AppChat getInstance() {
		if (INSTANCE == null)
			INSTANCE = new AppChat();
		return INSTANCE;
	}
	
	public AppChat() {
		TDSFactoriaDAO factoriaDAO = TDSFactoriaDAO.getInstance();
        contactoIndividualDAO = factoriaDAO.getContactoIndividualDAO();
        grupoDAO = factoriaDAO.getGrupoDAO();
        
        repoMensajes= RepositorioMensajes.getInstance();
        repoUsuarios= RepositorioUsuarios.getInstance();
        
	}
	
	public void enviarMensajeContacto(ContactoIndividual ContactoDestino, String texto, int emoji, TipoMensaje tipo_mensaje) {
		//TODO PERSISTENCIA
		//Coger el contacto receptor del usuario emisor
		//Si no existe registrar el contacto solo con el número en el usuario
		//Registrar ese contacto en persistencia
		ContactoIndividual contactoReceptor = usuarioActual.getContactoIndividual(ContactoDestino.getMovil());

		//Crear el mensaje
		//Registrar mensaje en persistencia
		Mensaje mensaje = repoMensajes.add(new Mensaje(texto, LocalDateTime.now(), emoji, tipo_mensaje, usuarioActual.getContactoPropio(), contactoReceptor));

		//Registrar el mensaje en ese contacto en el usuario emisor
		//Actualizar el contacto
		usuarioActual.actualizarContactoMensaje(mensaje); //No se si se actualiza en el usuario emisor
		repoUsuarios.update(usuarioActual);

		
		//Hacer lo mismo para el usuario receptor
		//Recuperar al usuario receptor
		Usuario usuarioReceptor = getUsuario(contactoReceptor.getMovil());
		
		//Coger el contacto del emisor en el usuario receptor
		//Si no existe registrar el contacto solo con el número en el usuario
		//Registrar ese contacto en persistencia
		ContactoIndividual contactoEmisor = usuarioReceptor.getContactoIndividual(usuarioActual.getContactoPropio().getMovil());

		//Crear el mensaje
		//Registrar mensaje en persistencia
		Mensaje mensajeReceptor = repoMensajes.add(new Mensaje(texto, LocalDateTime.now(), emoji, TipoMensaje.RECIBIDO, contactoEmisor, contactoReceptor));

		//Registrar el mensaje en ese contacto emisor en el usuario receptor
		//Actualizar el contacto
		usuarioReceptor.actualizarContactoMensaje(mensajeReceptor); //No se si se actualiza en el usuario emisor

		repoUsuarios.update(usuarioReceptor);
		
		
		
	}
	
	public void enviarMensajeGrupo(Grupo grupo_receptor, String texto, int emoji, TipoMensaje tipo_mensaje) { 
		//Coger el contacto receptor del usuario emisor
		//Si no existe registrar el contacto solo con el número en el usuario
		//Registrar ese contacto en persistencia
		Grupo grupoReceptor = usuarioActual.getGrupo(grupo_receptor.getNombre());

		//Crear el mensaje
		//Registrar mensaje en persistencia
		Mensaje mensaje = repoMensajes.add(new Mensaje(texto, LocalDateTime.now(), emoji, tipo_mensaje, usuarioActual.getContactoPropio(), grupoReceptor));

		//Registrar el mensaje en ese contacto en el usuario emisor
		//Actualizar el contacto
			//		usuarioActual.actualizarContactoMensaje(mensaje); //No se si se actualiza en el usuario emisor
			//		repoUsuarios.update(usuarioActual);
		
		
		
		for(ContactoIndividual contactoReceptor: grupo_receptor.getContactos()) {
			//Hacer lo mismo para el usuario receptor
			//Recuperar al usuario receptor
			Usuario usuarioReceptor = getUsuario(contactoReceptor.getMovil());
			
			//Coger el contacto del emisor en el usuario receptor
			//Si no existe registrar el contacto solo con el número en el usuario
			//Registrar ese contacto en persistencia
			ContactoIndividual contactoEmisor = usuarioReceptor.getContactoIndividual(usuarioActual.getContactoPropio().getMovil());

			//Crear el mensaje
			//Registrar mensaje en persistencia
			Mensaje mensajeReceptor = repoMensajes.add(new Mensaje(texto, LocalDateTime.now(), emoji, TipoMensaje.RECIBIDO, contactoEmisor, contactoReceptor));

			//Registrar el mensaje en ese contacto emisor en el usuario receptor
			//Actualizar el contacto
			usuarioReceptor.actualizarContactoMensaje(mensajeReceptor); //No se si se actualiza en el usuario emisor

			repoUsuarios.update(usuarioReceptor);
			
			
//			Mensaje mensajeReceptor = new Mensaje(texto, LocalDateTime.now(), emoji, TipoMensaje.RECIBIDO, usuarioActual.getContactoPropio(), contacto);
//			Usuario usuarioReceptor = getUsuario(contacto.getMovil());
//			if(usuarioReceptor!=null) {
//				usuarioReceptor.actualizarContactoMensaje(mensajeReceptor);
//			}
		}
	}
	
	public Usuario getUsuario(String numero_telefono) { //Desde aquí se llama a getUsuario del repositorio
		
		return repoUsuarios.buscarUsuarioPorMovil(numero_telefono);
	}
	
	public ContactoIndividual agregarContacto(String nombre, String movil) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//TODO PERSISTENCIA
		
		if(!contactoYaGuardado(movil)) {
			ContactoIndividual nuevoContacto = usuarioActual.addContacto(contactoIndividualDAO.create(new ContactoIndividual(nombre, movil)));
			System.out.println("Nuevo contacto: "+nuevoContacto.getId());
			repoUsuarios.update(usuarioActual);
			
			return nuevoContacto;
		}
		
		return null;
	}
	
	public ContactoIndividual actualizarContacto(String nombre, String movil) {
		return usuarioActual.addNombreContacto(nombre, movil);
	}
	
	public Grupo CrearOActualizarGrupo(String nombreGrupo, List<ContactoIndividual> contactosGrupo) {
		//TODO PERSISTENCIA
		
		Grupo grupoNuevo = usuarioActual.addOrUpdateGrupo(nombreGrupo, contactosGrupo);
		repoUsuarios.update(usuarioActual);
		return grupoNuevo;
	}

	//Called from: VentanaRegistro, CargarAppChat
	public int registrarUsuario(String nombre, String apellidos, String password, String telefono, String confirma_password, LocalDate fecha, String ruta_imagen, String saludo) {
		//TODO PERSISTENCIA
		
		int returnCode = 0;
		//Comprobar que no esté ya registrado el número de teléfono
		for(Usuario usuarioRegistrado: repoUsuarios.getAll()) {
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
			
			ContactoIndividual contactoConId = contactoIndividualDAO.create(usuarioNuevo.getContactoPropio());
			usuarioNuevo.setContactoPropio(contactoConId);
			repoUsuarios.add(usuarioNuevo);
		}
		
		return returnCode;
	}

	//Called from: VentanaLogin, CargarAppChat
	public int login(String telefono, String password) {
		//TODO PERSISTENCIA
		
		int returnCode = -1;
		for(Usuario usuarioRegistrado: repoUsuarios.getAll()) {
			if(usuarioRegistrado.getMovil().equals(telefono)) {
				if(usuarioRegistrado.getPassword().equals(password)) {
					usuarioActual = usuarioRegistrado;
					for(Contacto contacto: usuarioActual.getContactos()) {
						System.out.println("Contacto: "+contacto.getNombre());
					}
					System.out.println("ID DEL USUARIO ACTUAL: "+usuarioActual.getId());
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
			return repoMensajes.buscar_Contacto(usuarioActual.getMovil(), nombre_contacto);
		}
		else if (texto.isEmpty() && !numero.isEmpty() && nombre_contacto.isEmpty()) {
			return repoMensajes.buscar_Numero(usuarioActual.getMovil(), numero);
		}
		else if (!texto.isEmpty() && numero.isEmpty() && !nombre_contacto.isEmpty()) {
			return repoMensajes.buscar_Texto_y_Contacto(texto, usuarioActual.getMovil(), nombre_contacto);
		}
		
		else if (!texto.isEmpty() && !numero.isEmpty() && nombre_contacto.isEmpty()) {
			return repoMensajes.buscar_Texto_y_Numero(texto, usuarioActual.getMovil(), numero);
		}
		
		else if (texto.isEmpty() && numero.isEmpty() && nombre_contacto.isEmpty()) {
			return repoMensajes.buscar_Todos(usuarioActual.getMovil());
		}
		else if (!texto.isEmpty() && numero.isEmpty() && nombre_contacto.isEmpty()) {
			return repoMensajes.buscar_Texto(texto, usuarioActual.getMovil());
		}
		else {
			return new ArrayList<Mensaje>();
		}
		
		
	}
	
	public List<Mensaje> getConversacionIndividual(String numeroReceptor) {
		return repoMensajes.getConversacion(usuarioActual.getMovil(), numeroReceptor);
	}
	
	public List<Mensaje> getConversacionGrupo(String nombreGrupo) {
		return repoMensajes.getConversacionGrupo(usuarioActual.getMovil(), nombreGrupo);
	}
	
	public List<Contacto> getListaContactos() {
		return usuarioActual.getContactos();
	}
	
	public List<ContactoIndividual> getListaContactosIndividuales() {
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
		repoUsuarios.update(usuarioActual);
		return 0;
	}

	
	public int removePremium() {
		//TODO PERSISTENCIA
		
		usuarioActual.setPremium(false);
		repoUsuarios.update(usuarioActual);
		return 0;
	}
	
	public boolean isPremium() {
		return usuarioActual.isPremium();
	}
	
	public int numMensajes() {
		return repoMensajes.buscar_Todos(usuarioActual.getMovil()).size();
	}
	
	public LocalDateTime getFechaRegistro() {
		return usuarioActual.getFechaCreacion();
	}
	
	public void exportPDF(Contacto contacto) {
		if(contacto!=null) {
			List<Mensaje> conversacion;
			if(contacto instanceof ContactoIndividual) {
				conversacion = getConversacionIndividual(((ContactoIndividual) contacto).getMovil());
			}else {
				conversacion = getConversacionGrupo(contacto.getNombre());
			}
			try {
				ExportPDF.INSTANCE.exportarAPDF(conversacion, usuarioActual.getNombre());
			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public String getSaludo(String movil) {
		for(Usuario usuario: repoUsuarios.getAll()) {
			if(usuario.getMovil().equals(movil)) {
				return usuario.getSaludo();
			}
		}
		return "";
	}

	public boolean existeUsuario(String movil) {
		Usuario usuarioReceptor = getUsuario(movil);
		if(usuarioReceptor==null) {
			return false;
		}
		return true;
	}
	
	public boolean contactoYaGuardado(String movil) {
		for(Contacto contacto: usuarioActual.getContactos()) {
			if(contacto instanceof ContactoIndividual) {
				if(movil.equals(((ContactoIndividual) contacto).getMovil())) {
					return true;
				}
			}
		}
		return false;
	}

	public double getPrecioPremium(String estrategiaString) {
		
		double diasTranscurridos = ChronoUnit.DAYS.between(usuarioActual.getFechaCreacion(), LocalDateTime.now());
		EstrategiaDescuento estrategiaDescuento = FactoriaEstrategiaDescuento.create(estrategiaString, diasTranscurridos, (double) numMensajes());
        ServicioPremium servicio = new ServicioPremium(precioPremium, estrategiaDescuento);
        double precioFinal = servicio.calculateFinalPrice();
        System.out.println("Precio final: "+precioFinal+". Número de mensajes: "+numMensajes());
        return precioFinal;
	}

	public void setPrecioPremium(double precioPremium) {
		this.precioPremium = precioPremium;
	}
	
}
