package br.edu.ifpb.ajudemais.domain;

/**
 * Created by rafaelfeitosa on 10/04/17.
 * Classe que representa um Doador no sistema.
 */

public class Conta {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String facebookId;
    private String tokenFCM;

    public Conta(String name, String phone, String email, String facebookId, String tokenFCM) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.facebookId = facebookId;
        this.tokenFCM = tokenFCM;
    }

    public Conta(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     *
     * @return Long
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
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /***
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return String
     */
    public String getFacebookId() {
        return facebookId;
    }

    /**
     *
     * @param facebookId
     */
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    /**
     *
     * @return String
     */
    public String getTokenFCM() {
        return tokenFCM;
    }

    /**
     *
     * @param tokenFCM
     */
    public void setTokenFCM(String tokenFCM) {
        this.tokenFCM = tokenFCM;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", tokenFCM='" + tokenFCM + '\'' +
                '}';
    }
}