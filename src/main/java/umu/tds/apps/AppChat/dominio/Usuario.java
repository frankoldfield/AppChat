package umu.tds.apps.AppChat.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private int id;
	private String movil;
	private String nombre;
	private String apellidos;
	private String password;
	private LocalDate fechaNacimiento;
	private String imagen;
	private String saludo;
	private LocalDateTime fechaCreacion;
	private boolean isPremium;
	private ContactoIndividual contactoPropio;
	private ArrayList<Contacto> Contactos;
	
	public Usuario(String nombre, String apellidos, String password, String movil, LocalDate fechaNacimiento, String imagen, String saludo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.movil = movil;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.imagen = imagen;
		this.saludo = saludo;
		this.fechaCreacion = LocalDateTime.now();
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
	
	public Grupo getGrupo(String nombreGrupo) {
		for(Contacto contacto: Contactos) {
			if(contacto instanceof Grupo) {
				if(((Grupo) contacto).getNombre().equals(nombreGrupo)) {
					return (Grupo)contacto;
				}
			}
		}
		return null;
	}

	

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
		Contactos.add(grupoNuevo);
		
		return grupoNuevo;
	}
	
	
	public int registrarMensaje(Mensaje mensaje) { //USAR LAMBDAS
		boolean registrado = false;
		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getMovil().equals(mensaje.getContacto_emisor().getMovil())) {
					mensaje.setContacto_emisor((ContactoIndividual) contacto);
					contacto.addMensaje(mensaje);
					registrado = true;
				}
			}
		}
		if(!registrado) {
			ContactoIndividual contactoVacio = new ContactoIndividual(mensaje.getContacto_emisor().getMovil());
			mensaje.setContacto_emisor(contactoVacio);
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

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public ContactoIndividual getContactoIndividualConNombre(String nombreContacto) {
		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getNombre().equals(nombreContacto)) {
					return (ContactoIndividual)contacto;
				}
			}
		}
		return null;
	}

	public String getApellidos() {
		return apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;		
	}

	
	
}
