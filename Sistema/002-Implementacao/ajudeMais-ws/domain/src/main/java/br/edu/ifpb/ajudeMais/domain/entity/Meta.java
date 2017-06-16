
package br.edu.ifpb.ajudeMais.domain.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.ajudeMais.domain.enumerations.UnidadeMedida;

/**
 * 
 * <p>
 * <b>{@link Meta}</b>
 * </p>
 *
 * <p>
 * Entidade que representa um meta de uma campanha
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Entity
public class Meta {
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	/**
	 * 
	 */
	private BigDecimal quantidade;
	
	/**
	 * 
	 */
	@Enumerated(EnumType.STRING) 
	private UnidadeMedida unidadeMedida;
	
	/**
	 * 
	 */
	@NotNull(message = "Ao menos um item doável deve ser adicionado a Meta.")
	@ManyToOne(cascade = { CascadeType.MERGE })
	private Categoria categoria;
	
	/**
	 * 
	 */
	@Transient
	private Float percentualAtingido = 0f;//(quantidade esperada*100)/quantidade atual.
	

	
	/**
	 * 
	 */
	@ManyToOne
	private Campanha campanha;
	

	/**
	 * @return the quantidade
	 */
	public BigDecimal getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the unidadeMedida
	 */
	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	/**
	 * @return the percentualAtingido
	 */
	public Float getPercentualAtingido() {
		return percentualAtingido;
	}

	/**
	 * @param percentualAtingido the percentualAtingido to set
	 */
	public void setPercentualAtingido(Float percentualAtingido) {
		this.percentualAtingido = percentualAtingido;
	}


	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Meta [id=" + id + ", quantidade=" + quantidade + ", unidadeMedida=" + unidadeMedida + ", categoria="
				+ categoria + ", percentualAtingido=" + percentualAtingido + ", campanha=" + campanha + "]";
	}

	

}
