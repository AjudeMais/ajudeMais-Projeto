package br.edu.ifpb.ajudeMais.service.fcm.dto;

public class Data {

	private String tipo;
	private Long id;

	public Data(String tipo, Long id) {
		this.tipo = tipo;
		this.id = id;
	}

	public Data() {
	}

	/**
	 * @return o atributo tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param o
	 *            parametro tipo é setado em tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return o atributo id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param o
	 *            parametro id é setado em id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return o atributo content
	 */
	public String getContent() {
		return tipo;
	}

	/**
	 * @param o
	 *            parametro content é setado em content
	 */
	public void setContent(String content) {
		this.tipo = content;
	}

}
