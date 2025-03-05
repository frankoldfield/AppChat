package umu.tds.apps.AppChat;

import java.util.ArrayList;

public class Grupo extends Contacto{

	private ArrayList<ContactoIndividual> contactos;
	
	public Grupo(String nombre) {
		super(nombre);
		this.contactos = new ArrayList<>();
	}
	
	public void agregarContactos(ContactoIndividual contacto) {
		contactos.add(contacto);
	}
	
	public ArrayList<ContactoIndividual> getMiembros(){
		return contactos;
	}
}
