package umu.tds.apps.AppChat.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import umu.tds.apps.AppChat.persistencia.DAOException;
import umu.tds.apps.AppChat.persistencia.abstracta.ContactoIndividualDAO;
import umu.tds.apps.AppChat.persistencia.abstracta.FactoriaDAO;
import umu.tds.apps.AppChat.persistencia.abstracta.GrupoDAO;

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
		FactoriaDAO factoriaDAO;
		ContactoIndividualDAO contactoIndividualDAO;
		try {
			
			factoriaDAO = FactoriaDAO.getInstancia();
			contactoIndividualDAO = factoriaDAO.getContactoIndividualDAO();
			this.contactoPropio = contactoIndividualDAO.create(new ContactoIndividual(nombre, movil));
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
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
	
	public String getSaludo() {
		return saludo;
	}

	public ContactoIndividual getContactoPropio() {
		return contactoPropio;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
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

	public void setContactoPropio(ContactoIndividual contactoPropio) {
		this.contactoPropio = contactoPropio;
		
	}

	public ArrayList<Contacto> getContactos() {return Contactos;}
	
	
	public void setContactos(ArrayList<Contacto> Contactos) {
		this.Contactos=Contactos;
		
	}
	
	public ContactoIndividual getContactoIndividual(String numero_telefono) {

		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getMovil().equals(numero_telefono)) {
					return (ContactoIndividual)contacto;
				}
			}
		}
		
		FactoriaDAO factoriaDAO;
		ContactoIndividualDAO contactoIndividualDAO;
		ContactoIndividual contactoVacio = null;
		try {
			factoriaDAO = FactoriaDAO.getInstancia();
			contactoIndividualDAO = factoriaDAO.getContactoIndividualDAO();
			contactoVacio = contactoIndividualDAO.create(new ContactoIndividual(numero_telefono));
			Contactos.add(contactoVacio);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		
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

	public ContactoIndividual addContacto(ContactoIndividual contacto) {
		Contactos.add(contacto);
		return contacto;
	}
	
	public Grupo addOrUpdateGrupo(String nombreGrupo, List<ContactoIndividual> contactosGrupo) {
		FactoriaDAO factoriaDAO;
		GrupoDAO grupoDAO;
		Grupo grupoNuevo = null;
		try {
			
			factoriaDAO = FactoriaDAO.getInstancia();
			grupoDAO = factoriaDAO.getGrupoDAO();
			for(Contacto contacto: Contactos) {
				if (contacto instanceof Grupo && contacto.getNombre().equals(nombreGrupo)) {
					((Grupo) contacto).setContactos(contactosGrupo);
					grupoDAO.update((Grupo) contacto);
					return (Grupo) contacto;
				}
			}
			
			grupoNuevo =grupoDAO.create(new Grupo(nombreGrupo, contactosGrupo));
			Contactos.add(grupoNuevo);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		
		return grupoNuevo;
	}
	
	public ContactoIndividual addNombreContacto(String nombre, String movil) {
		for(Contacto contacto: Contactos) {
			if(contacto instanceof ContactoIndividual) {
				if(((ContactoIndividual) contacto).getMovil().equals(movil)) {
					contacto.setNombre(nombre);
					FactoriaDAO factoriaDAO;
					ContactoIndividualDAO contactoIndividualDAO;
					try {
						
						factoriaDAO = FactoriaDAO.getInstancia();
						contactoIndividualDAO = factoriaDAO.getContactoIndividualDAO();
						contactoIndividualDAO.update((ContactoIndividual) contacto);
					} catch (DAOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

}
