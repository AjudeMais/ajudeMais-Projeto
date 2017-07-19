package br.edu.ifpb.ajudeMais.api.dto;

public class DoacoesPeriodoDTO {

	private String date;

	private Integer count;

	public DoacoesPeriodoDTO(String date, Integer count) {
		this.date = date;
		this.count = count;
	}

	/**
	 * @return o atributo date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param o
	 *            parametro date é setado em date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return o atributo count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param o
	 *            parametro count é setado em count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
