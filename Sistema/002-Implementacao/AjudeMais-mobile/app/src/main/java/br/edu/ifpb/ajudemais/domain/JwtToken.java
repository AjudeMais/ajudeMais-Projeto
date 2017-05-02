package br.edu.ifpb.ajudemais.domain;

import java.util.Date;

/**
 * Created by rafaelfeitosa on 12/04/17.
 *
 * Representa objeto que tem o token de acesso ao sistema.
 */

public class JwtToken {

    private String token;

    private Date date = new Date();

    public JwtToken() {

    }

    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JwtToken [token=" + token + ", date=" + date + "]";
    }
}
