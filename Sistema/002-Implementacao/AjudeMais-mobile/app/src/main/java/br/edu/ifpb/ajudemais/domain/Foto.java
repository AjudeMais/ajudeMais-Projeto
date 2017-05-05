package br.edu.ifpb.ajudemais.domain;


/**
 * <p>
 * <b>Foto</b>
 * </p>
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class Foto {

    private Long id;
    private String nome;
    private String pathFoto;

    public Foto(String nome, String pathFoto) {
        this.nome = nome;
        this.pathFoto = pathFoto;
    }

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @return
     */
    public String getPathFoto() {
        return pathFoto;
    }

    /**
     *
     * @param pathFoto
     */
    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Foto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pathFoto='" + pathFoto + '\'' +
                '}';
    }
}
