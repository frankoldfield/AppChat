package umu.tds.apps.AppChat.dominio;

public class ContactoIndividual extends Contacto {
	
	private String movil;
	
	public ContactoIndividual(String movil) {
		super("");
		this.movil = movil;
	}
	
	public ContactoIndividual(String nombre, String movil) {
		super(nombre);
		this.movil = movil;
	}
	
	public String getMovil() {
		return this.movil;
	}
	
	@Override
    public String toString() {
		if(nombre.isEmpty()) {
			return movil;
		}
        return nombre;
    }
}
