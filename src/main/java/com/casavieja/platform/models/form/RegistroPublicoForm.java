package com.casavieja.platform.models.form;

public class RegistroPublicoForm {
    private Long id;
    private String email, username, password, repassword;
    private Short ciudadId;
    private String celular, codigoCelular;
    private String foto;
    private String name, firstLastname, secondLastname, gender, ci;
    private String codigoVerificacion;

    public String getCodigoCelular() {
        return codigoCelular;
    }

    public void setCodigoCelular(String codigoCelular) {
        this.codigoCelular = codigoCelular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Short getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Short ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastname() {
        return firstLastname;
    }

    public void setFirstLastname(String firstLastname) {
        this.firstLastname = firstLastname;
    }

    public String getSecondLastname() {
        return secondLastname;
    }

    public void setSecondLastname(String secondLastname) {
        this.secondLastname = secondLastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }
}
