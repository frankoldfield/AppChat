package umu.tds.apps.AppChat.controlador;

import java.time.LocalDate;

import umu.tds.apps.AppChat.dominio.ContactoIndividual;
import umu.tds.apps.AppChat.dominio.Grupo;
import umu.tds.apps.AppChat.dominio.TipoMensaje;
import umu.tds.apps.AppChat.dominio.Usuario;
import umu.tds.apps.AppChat.persistencia.RepositorioUsuarios;

public class AppChat {

	public Usuario usuarioActual;
	public RepositorioUsuarios repositorio;
	public static AppChat INSTANCE = null;

	//Patrón singleton
	public static AppChat getInstance() {
		if (INSTANCE == null)
			INSTANCE = new AppChat();
		return INSTANCE;
	}
	
	public void enviarMensajeContacto(ContactoIndividual ContactoDestino, String string, int i, TipoMensaje tipo_mensaje) {
		// TODO Auto-generated method stub
		//Llama a usuario_emisor.registrarMensaje(usuario_receptor)
		//Llama a usuario_receptor.registrarMensaje(usuario_emisor)
	}
	
	public void enviarMensajeGrupo(Grupo grupo_receptor, String string, int i, TipoMensaje tipo_mensaje) {
		// TODO Auto-generated method stub
		//Llama a usuario_emisor.registrarMensaje(grupo_receptor)
		//for usuario_receptor in grupo_receptor:
		//	Llama a usuario_receptor.registrarMensaje(usuario_emisor)
		
	}
	
	public Usuario getUsuario(String numero_telefono) { //Desde aquí se llama a getUsuario del repositorio
		
		return repositorio.buscarUsuarioPorMovil(numero_telefono);
	}
	
	public ContactoIndividual agregarContacto(String numeroTelefono, String username) { //Desde aquí se le dice al usuario que registre un nuevo contacto
		//usuarioActual.registrarContacto(numeroTelefono, username)
		return null;
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
	public void registrarUsuario(String nombre, String apellidos, String password, String telefono, String confirma_password, LocalDate fecha, String ruta_imagen, String saludo) {
		// TODO Auto-generated method stub
		//Comprobar que no esté ya registrado el número de teléfono
		//Comprobar fecha válida
		//Pillar imagen
		
		Usuario usuarioNuevo = new Usuario(nombre, apellidos, password, telefono, fecha, ruta_imagen, saludo);
		
		//Añadir usuarioNuevo a repositorio
		//retornar a vista
	}

	//Called from: VentanaLogin, CargarAppChat
	public void login(String telefono, String password) {
		// TODO Comprobar log-in correcto y guardar usuario actual
		//Buscar en repositorio
		//Si está el número pero la contraseña es errónea lanzar un error
		//Si no está el número lanzar otro error
		//Si todo correcto
		//Usuario actual = repositorio.buscarUsuario(telefono)
		//return sin error
		
	}

	
}
