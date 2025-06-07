package umu.tds.apps.AppChat.dominio;

import java.util.ArrayList;

public class Grupo extends Contacto{

	private ArrayList<ContactoIndividual> contactos;
	
	public Grupo(String nombre, ArrayList<ContactoIndividual> contactos) {
		super(nombre);
		this.contactos = new ArrayList<>();
	}
	
	public int addContactos(ContactoIndividual contacto) {
		contactos.add(contacto);
		return 0;
	}
	
	public ArrayList<ContactoIndividual> getMiembros(){
		return contactos;
	}
}
