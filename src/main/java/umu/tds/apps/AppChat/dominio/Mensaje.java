package umu.tds.apps.AppChat.dominio;

import java.time.LocalDateTime;

public class Mensaje {
	
	private String id;
	private String texto;
	private LocalDateTime hora;
	private int emoji;
	private TipoMensaje tipo;
	private String numero_emisor;
	private String numero_receptor;
	
	public Mensaje(String texto, LocalDateTime hora, int emoji, TipoMensaje tipo, String numero_emisor, String numero_receptor) {
		this.texto = texto;
		this.hora = hora;
		this.emoji = emoji;
		this.tipo = tipo;
		this.numero_emisor = numero_emisor;
		this.numero_receptor = numero_receptor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public int getEmoji() {
		return emoji;
	}

	public void setEmoji(int emoji) {
		this.emoji = emoji;
	}

	public TipoMensaje getTipo() {
		return tipo;
	}

	public void setTipo(TipoMensaje tipo) {
		this.tipo = tipo;
	}

	public String getNumero_emisor() {
		return numero_emisor;
	}

	public void setNumero_emisor(String numero_emisor) {
		this.numero_emisor = numero_emisor;
	}

	public String getNumero_receptor() {
		return numero_receptor;
	}

	public void setNumero_receptor(String numero_receptor) {
		this.numero_receptor = numero_receptor;
	}

}
