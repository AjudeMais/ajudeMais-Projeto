package br.edu.ifpb.ajudeMais.api.dto;

import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;

public class MensageiroRankingDTO {

	private MensageiroAssociado mensageiro;

	private Integer quantidadeDoacoes;

	public MensageiroRankingDTO(MensageiroAssociado mensageiro, Integer quantidadeDoacoes) {
		this.mensageiro = mensageiro;
		this.quantidadeDoacoes = quantidadeDoacoes;
	}

	public MensageiroRankingDTO() {

	}

	/**
	 * @return o atributo mensageiro
	 */
	public MensageiroAssociado getMensageiro() {
		return mensageiro;
	}

	/**
	 * @param o
	 *            parametro mensageiro é setado em mensageiro
	 */
	public void setMensageiro(MensageiroAssociado mensageiro) {
		this.mensageiro = mensageiro;
	}

	/**
	 * @return o atributo quantidadeDoacoes
	 */
	public Integer getQuantidadeDoacoes() {
		return quantidadeDoacoes;
	}

	/**
	 * @param o
	 *            parametro quantidadeDoacoes é setado em quantidadeDoacoes
	 */
	public void setQuantidadeDoacoes(Integer quantidadeDoacoes) {
		this.quantidadeDoacoes = quantidadeDoacoes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MensageiroRankingDTO [mensageiro=" + mensageiro.getMensageiro().getNome() + ", quantidadeDoacoes="
				+ quantidadeDoacoes + "]";
	}

}
