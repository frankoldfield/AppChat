package umu.tds.apps.AppChat;

public class Mensaje {
	
	private String texto;
	private String hora;
	private String emoji;
	private String tipo;
	
	public Mensaje (String texto, String hora, String emoji, String tipo) {
		this.texto = texto;
		this.hora = hora;
		this.emoji = emoji;
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getEmoji() {
		return emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
