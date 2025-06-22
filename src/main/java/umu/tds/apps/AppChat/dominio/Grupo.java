package umu.tds.apps.AppChat.dominio;

import java.util.ArrayList;
import java.util.List;

public class Grupo extends Contacto{

	private List<ContactoIndividual> contactos;
	
	public Grupo(String nombre) {
		super(nombre);
		this.contactos = new ArrayList<>();
	}
	
	public Grupo(String nombre, List<ContactoIndividual> contactos) {
		super(nombre);
		this.contactos = contactos;
	}
	
	public List<ContactoIndividual> getContactos(){
		return contactos;
	}
	
	public void setContactos(List<ContactoIndividual> contactos){
		this.contactos = contactos;
	}
}
