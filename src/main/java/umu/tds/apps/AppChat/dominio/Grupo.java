package umu.tds.apps.AppChat.dominio;

import java.util.ArrayList;

public class Grupo extends Contacto{

	private String id;
	private ArrayList<ContactoIndividual> contactos;
	
	public Grupo(String nombre) {
		super(nombre);
		this.contactos = new ArrayList<>();
	}
	
	public Grupo(String nombre, ArrayList<ContactoIndividual> contactos) {
		super(nombre);
		this.contactos = contactos;
	}
	
	public int addContacto(ContactoIndividual contacto) {
		contactos.add(contacto);
		return 0;
	}
	
	public ArrayList<ContactoIndividual> getMiembros(){
		return contactos;
	}
}
