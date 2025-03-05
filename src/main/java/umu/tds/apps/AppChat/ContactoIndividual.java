package umu.tds.apps.AppChat;

public class ContactoIndividual extends Contacto {
	
	private String numero;
	
	public ContactoIndividual(String numero, String nombre) {
		super(nombre);
		this.numero = numero;
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
}
