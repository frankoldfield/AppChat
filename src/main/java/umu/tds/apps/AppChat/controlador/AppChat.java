package umu.tds.apps.AppChat.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.Mensaje;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.RepositorioMensajes;
import umu.tds.apps.AppChat.persistencia.RepositorioUsuarios;

public class AppChat {

	public Usuario usuarioActual;
	public RepositorioUsuarios repositorio;
	public static AppChat INSTANCE = null;
	public List<Mensaje> mensajes= new ArrayList<Mensaje>();

	//Patrón singleton
	public static AppChat getInstance() {
		if (INSTANCE == null)
			INSTANCE = new AppChat();
		return INSTANCE;
	}
	
	public void enviarMensajeContacto(ContactoIndividual ContactoDestino, String texto, int emoji, TipoMensaje tipo_mensaje) {
		// TODO Auto-generated method stub
		//Llama a usuario_emisor.registrarMensaje(usuario_receptor)
		//Llama a usuario_receptor.registrarMensaje(usuario_emisor)
		
		Mensaje mensaje = new Mensaje(texto, LocalDateTime.now(), emoji, tipo_mensaje, usuarioActual.getContactoPropio(), ContactoDestino);
		ContactoDestino.addMensaje(mensaje);
		mensaje.setTipo(TipoMensaje.RECIBIDO);
		for(Usuario usuarioReceptor: RepositorioUsuarios.INSTANCE.usuarios) {
			if(usuarioReceptor.getMovil().equals(ContactoDestino.getMovil())) {
				usuarioReceptor.registrarMensaje(mensaje);
			}
		}
		
		mensajes.add(mensaje);
	}
	
	public void enviarMensajeGrupo(Grupo grupo_receptor, String texto, int emoji, TipoMensaje tipo_mensaje) {
		// TODO Auto-generated method stub
		//Llama a usuario_emisor.registrarMensaje(grupo_receptor)
		//for usuario_receptor in grupo_receptor:
		//	Llama a usuario_receptor.registrarMensaje(usuario_emisor)
		Mensaje mensaje;
		mensaje = new Mensaje(texto, LocalDateTime.now(), emoji, tipo_mensaje, usuarioActual.getContactoPropio(), grupo_receptor);
		grupo_receptor.addMensaje(mensaje);
		mensajes.add(mensaje);
		for(ContactoIndividual contacto: grupo_receptor.getMiembros()) {
			
			for(Usuario usuarioReceptor: RepositorioUsuarios.INSTANCE.usuarios) {
				if(usuarioReceptor.getMovil().equals(contacto.getMovil())) {
					mensaje.setTipo(TipoMensaje.RECIBIDO);
					usuarioReceptor.registrarMensaje(mensaje);
				}
			}	
		}
	}
	
	public Usuario getUsuario(String numero_telefono) { //Desde aquí se llama a getUsuario del repositorio
		
		return repositorio.buscarUsuarioPorMovil(numero_telefono);
	}
	
	public ContactoIndividual agregarContacto(String nombre, String movil) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono, username)
		return usuarioActual.addContacto(nombre, movil);
	}
	
	public ContactoIndividual agregarContacto_Empty(int numeroTelefono) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono)
		return null;
	}
	
	public Grupo registrarGrupo(String[] contactos_grupo) { //Desde aquí se le dice al usuario que registre un nuevo grupo
		//usuarioActual.registrarContacto(nuevo_contacto)
		return null;
	}
	
	public Grupo addContactoAGrupo(Grupo grupo, ContactoIndividual contacto_nuevo) {
		
		return null;
	}
	
	public int getPremium() {
		//usuarioActual.getPremium();
		return 0;
	}

	
	public int removePremium() {
		//usuarioActual.removePremium();
		//saveUsuarioActual
		return 0;
	}

	//Called from: VentanaRegistro, CargarAppChat
	public int registrarUsuario(String nombre, String apellidos, String password, String telefono, String confirma_password, LocalDate fecha, String ruta_imagen, String saludo) {
		// TODO Auto-generated method stub
		int returnCode = 0;
		//Comprobar que no esté ya registrado el número de teléfono
		for(Usuario usuarioRegistrado: RepositorioUsuarios.INSTANCE.usuarios) {
			if(usuarioRegistrado.getMovil().equals(telefono)) {
				returnCode = -1;
			}
		}
		//Comprobar fecha válida
		if(fecha.isBefore(LocalDate.of(1930, 1, 1))) {
			returnCode = -2;
		}
		// TODO Pillar imagen
		
		if(returnCode==0) {
			Usuario usuarioNuevo = new Usuario(nombre, apellidos, password, telefono, fecha, ruta_imagen, saludo);
			RepositorioUsuarios.INSTANCE.usuarios.add(usuarioNuevo);
		}
		System.out.println("Código de registro: "+ returnCode);
		return returnCode;
	}

	//Called from: VentanaLogin, CargarAppChat
	public int login(String telefono, String password) {
		
		// TODO Comprobar log-in correcto y guardar usuario actual
		//Buscar en repositorio
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
				System.out.println("Código de login: "+ returnCode);
				return returnCode;
			}
		}
		System.out.println("Código de login: "+ returnCode);
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
			
		}
		
		return null;
	}

	
}
