package umu.tds.apps.AppChat.dominio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {

	private String id;
	private String telefono;
	private String nombre;
	private String apellidos;
	private String password;
	private String imagen;
	private String saludo;
	private boolean isPremium;
	private Contacto contactoActual;
	private ArrayList<Contacto> Contactos;
	
	public Usuario(String nombre, String apellidos, String password, String telefono, LocalDate fecha, String imagen, String saludo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.telefono = telefono;
		this.nombre = nombre;
		this.imagen = imagen;
		this.saludo = saludo;
		this.isPremium = false;
		Contactos = new ArrayList<Contacto>();
	}

	public String getMovil() {return telefono;}

	public String getNombre() {return nombre;}

	public String getPassword() {return password;}

	public String getImagen() {return imagen;}

	public boolean isPremium() {return isPremium;}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	
	public ContactoIndividual getContactoIndividual(String numero_telefono) {

		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getNumero() == numero_telefono) {
					return (ContactoIndividual)contacto;
				}
			}
		}
		return null;
	}

	
	
	public Contacto getContactoActual() {return contactoActual;}

	public ArrayList<Contacto> getContactos() {return Contactos;}
	
	public int addContacto(String movil, String nombre) {
		Contactos.add(new ContactoIndividual(nombre, movil));
		return 0;
	}
	
	public int addContacto(String movil) {
		Contactos.add(new ContactoIndividual(movil));
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
