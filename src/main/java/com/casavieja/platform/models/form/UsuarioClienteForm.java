package com.casavieja.platform.models.form;

public class UsuarioClienteForm {
    private String celular, codeVerify,password, repassword;

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCodeVerify() {
        return codeVerify;
    }

    public void setCodeVerify(String codeVerify) {
        this.codeVerify = codeVerify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
