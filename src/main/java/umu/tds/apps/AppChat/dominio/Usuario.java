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
				if(((ContactoIndividual) contacto).getMovil().equals(numero_telefono)) {
					return (ContactoIndividual)contacto;
				}
			}
		}
		ContactoIndividual contactoVacio = new ContactoIndividual(numero_telefono);
		Contactos.add(contactoVacio);
		return contactoVacio;
	}

	
	
	public Contacto getContactoActual() {return contactoActual;}

	public ArrayList<Contacto> getContactos() {return Contactos;}
	
	public ContactoIndividual addContacto(String movil, String nombre) {
		ContactoIndividual contactoNuevo = new ContactoIndividual(nombre, movil);
		Contactos.add(contactoNuevo);
		return contactoNuevo;
	}
	
	public ContactoIndividual addContacto(String movil) {
		ContactoIndividual contactoNuevo = new ContactoIndividual(movil);
		Contactos.add(contactoNuevo);
		return contactoNuevo;
	}
	
	public int addGrupo(String nombre, ArrayList<ContactoIndividual> ContactosGrupo) {
		Contactos.add(new Grupo(nombre, ContactosGrupo));
		return 0;
	}
	
	public int registrarMensaje(Mensaje mensaje, String numero_telefono) { //USAR LAMBDAS
		boolean registrado = false;
		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getMovil().equals(numero_telefono)) {
					contacto.addMensaje(mensaje);
					registrado = true;
				}
			}
		}
		if(!registrado) {
			ContactoIndividual contactoVacio = new ContactoIndividual(numero_telefono);
			contactoVacio.addMensaje(mensaje);
			Contactos.add(contactoVacio);
		}
		
		return 0;
	}

	public String getSaludo() {
		return saludo;
	}
	
}
