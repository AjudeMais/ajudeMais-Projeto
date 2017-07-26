package br.edu.ifpb.ajudeMais.api.dto;

import br.edu.ifpb.ajudeMais.domain.entity.Campanha;

public class CampanhaMetaDTO {

	private Campanha campanha;

	private Float percentualAtingido;
	
	
	public CampanhaMetaDTO() {
	}

	/**
	 * 
	 * @param campanha
	 * @param percentualAtingido
	 */
	public CampanhaMetaDTO(Campanha campanha, Float percentualAtingido) {
		this.campanha = campanha;
		this.percentualAtingido = percentualAtingido;
	}

	/**
	 * @return o atributo campanha
	 */
	public Campanha getCampanha() {
		return campanha;
	}

	/**
	 * @param o
	 *            parametro campanha é setado em campanha
	 */
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	/**
	 * @return o atributo percentualAtingido
	 */
	public Float getPercentualAtingido() {
		return percentualAtingido;
	}

	/**
	 * @param o
	 *            parametro percentualAtingido é setado em percentualAtingido
	 */
	public void setPercentualAtingido(Float percentualAtingido) {
		this.percentualAtingido = percentualAtingido;
	}

}
