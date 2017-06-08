package br.edu.ifpb.ajudeMais.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 
 * <p>
 * <b> Entrega </b>
 * </p>
 *
 * <p>
 *	Classe que representa uma entrega no sistema
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@Entity
public class Entrega implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4093900769276107078L;
	
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	/**
	 * 
	 */
	private Integer quantidade;
	
	/**
	 * 
	 */
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	@ManyToOne
	private Donatario donatario;

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

	/**
	 * @return the quantidade
	 */
	public Integer getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the dataEntrega
	 */
	public Date getDataEntrega() {
		return dataEntrega;
	}

	/**
	 * @param dataEntrega the dataEntrega to set
	 */
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	/**
	 * @return the donatario
	 */
	public Donatario getDonatario() {
		return donatario;
	}

	/**
	 * @param donatario the donatario to set
	 */
	public void setDonatario(Donatario donatario) {
		this.donatario = donatario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataEntrega == null) ? 0 : dataEntrega.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrega other = (Entrega) obj;
		if (dataEntrega == null) {
			if (other.dataEntrega != null)
				return false;
		} else if (!dataEntrega.equals(other.dataEntrega))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		return true;
	}
}
