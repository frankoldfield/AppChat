package umu.tds.apps.AppChat.dominio;

import java.util.ArrayList;

public class Usuario {

	private String movil;
	private String nombre;
	private String password;
	private String imagen;
	private String saludo;
	private boolean isPremium;
	private Contacto contactoActual;
	private ArrayList<Contacto> Contactos;
	
	public Usuario(String movil, String nombre, String password, String imagen, boolean isPremium) {
		this.movil = movil;
		this.nombre = nombre;
		this.password = password;
		this.imagen = imagen;
		this.isPremium = isPremium;
	}

	public String getMovil() {return movil;}

	public String getNombre() {return nombre;}

	public String getPassword() {return password;}

	public String getImagen() {return imagen;}

	public boolean isPremium() {return isPremium;}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	
	public ContactoIndividual getContactoIndividual(String numero_telefono) {return null;}

	
	
	public Contacto getContactoActual() {return contactoActual;}

	public ArrayList<Contacto> getContactos() {return Contactos;}
	
	public int addContacto(String movil, String nombre) {
		Contactos.add(new ContactoIndividual(nombre, movil));
		return 0;
	}
	
	public int addGrupo(String nombre, ArrayList<ContactoIndividual> ContactosGrupo) {
		Contactos.add(new Grupo(nombre, ContactosGrupo));
		return 0;
	}
	
	public int registrarMensaje(Mensaje mensaje, Contacto contacto) {
		contacto.addMensaje(mensaje);
		return 0;
	}

	public String getSaludo() {
		return saludo;
	}
	
}
