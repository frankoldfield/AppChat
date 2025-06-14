package umu.tds.apps.AppChat.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private String id;
	private String movil;
	private String nombre;
	private String apellidos;
	private String password;
	private String imagen;
	private String saludo;
	private boolean isPremium;
	private Contacto contactoActual;
	private ContactoIndividual contactoPropio;
	private ArrayList<Contacto> Contactos;
	
	public Usuario(String nombre, String apellidos, String password, String movil, LocalDate fecha, String imagen, String saludo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.movil = movil;
		this.nombre = nombre;
		this.imagen = imagen;
		this.saludo = saludo;
		this.isPremium = false;
		this.contactoPropio = new ContactoIndividual(nombre, movil);
		Contactos = new ArrayList<Contacto>();
	}

	public String getMovil() {return movil;}

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
	
	public ContactoIndividual addContacto(String nombre, String movil) {
		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getMovil().equals(movil)) {
					contacto.setNombre(nombre);
					return ((ContactoIndividual) contacto);
				}
			}
			
		}
		ContactoIndividual contactoNuevo = new ContactoIndividual(nombre, movil);
		Contactos.add(contactoNuevo);
		return contactoNuevo;
	}
	
	public ContactoIndividual addContacto(String movil) {
		ContactoIndividual contactoNuevo = new ContactoIndividual(movil);
		Contactos.add(contactoNuevo);
		return contactoNuevo;
	}
	
	public Grupo addOrUpdateGrupo(String nombreGrupo, List<ContactoIndividual> contactosGrupo) {
		for(Contacto contacto: Contactos) {
			if (contacto instanceof Grupo && contacto.getNombre().equals(nombreGrupo)) {
				((Grupo) contacto).setContactos(contactosGrupo);
				return (Grupo) contacto;
			}
		}
		
		Grupo grupoNuevo = new Grupo(nombreGrupo, contactosGrupo);
		Contactos.add(new Grupo(nombre, contactosGrupo));
		
		return grupoNuevo;
	}
	
	
	public int registrarMensaje(Mensaje mensaje) { //USAR LAMBDAS
		boolean registrado = false;
		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getMovil().equals(mensaje.getContacto_emisor().getMovil())) {
					contacto.addMensaje(mensaje);
					registrado = true;
				}
			}
		}
		if(!registrado) {
			ContactoIndividual contactoVacio = new ContactoIndividual(mensaje.getContacto_emisor().getMovil());
			contactoVacio.addMensaje(mensaje);
			Contactos.add(contactoVacio);
		}
		
		return 0;
	}

	public String getSaludo() {
		return saludo;
	}

	public ContactoIndividual getContactoPropio() {
		return contactoPropio;
	}
	
}
