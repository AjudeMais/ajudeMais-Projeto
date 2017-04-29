package br.edu.ifpb.ajudemais.dto;

/**
 * Created by Franck Aragão on 4/27/17.
 */

public class MessageErrorDTO {

    private String msg;

    public MessageErrorDTO() {

    }

    public MessageErrorDTO(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
