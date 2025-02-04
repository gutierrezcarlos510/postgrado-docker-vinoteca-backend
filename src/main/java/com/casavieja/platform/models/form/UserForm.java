package com.casavieja.platform.models.form;

import com.casavieja.platform.enums.SexoEnum;
import com.casavieja.platform.enums.SexoEnumConverter;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;

/**
 * Modelo de todos los usuarios que interactuan con el sistema
 *
 * @author CARLOS GUTIERREZ
 */
public class UserForm {
    @NotNull(message = "No se cuenta con datos personales")
    private Person person;
    @NotNull(message = "No se cuenta con datos de usuario")
    private User user;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private Cliente cliente;
    @NotNull(message = "No se cuenta con roles asignados")
    private int roles[];
    @NotNull(message = "No se cuenta con empresas asignadas")
    private int empresas[];
    public UserForm(){
        person = new Person();
        user = new User();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    public int[] getEmpresas() {
        return empresas;
    }

    public void setEmpresas(int[] empresas) {
        this.empresas = empresas;
    }

    public class Person {
        private Long personId;
        @NotNull(message = "Nombre. Campo requerido")
        private String name;
        @NotNull(message = "Primer Apellido. Campo requerido")
        private String firstLastname;
        private String secondLastname;
        @NotNull(message = "Genero. Campo requerido")
        @Convert(converter = SexoEnumConverter.class)
        private SexoEnum gender;
        @NotNull(message = "Carnet de identidad. Campo requerido")
        private String ci;
        @NotNull(message = "Estado de persona. Campo requerido")
        private Boolean status;
        private String codigoCelular;
        private String numeroCelular;

        public String getCodigoCelular() {
            return codigoCelular;
        }

        public void setCodigoCelular(String codigoCelular) {
            this.codigoCelular = codigoCelular;
        }

        public String getNumeroCelular() {
            return numeroCelular;
        }

        public void setNumeroCelular(String numeroCelular) {
            this.numeroCelular = numeroCelular;
        }

        public Long getPersonId() {
            return personId;
        }

        public void setPersonId(Long personId) {
            this.personId = personId;
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

        public SexoEnum getGender() {
            return gender;
        }

        public void setGender(SexoEnum gender) {
            this.gender = gender;
        }

        public String getCi() {
            return ci;
        }

        public void setCi(String ci) {
            this.ci = ci;
        }

        public Boolean isStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
        public String getFullname() {
            return name+" "+firstLastname+(secondLastname!=null?(" "+secondLastname):"");
        }
        public String getNameGender() {
            if(gender!=null) {
                if(gender.equals("M"))
                    return "Masculino";
                else
                    return "Femenino";
            }
            return "";
        }
    }
    public class User {
        private Long systemUserId;
        @NotNull(message = "Usuario. Campo requerido")
        private String username;
        @NotNull(message = "Alias. Campo requerido")
        private String alias;
        @NotNull(message = "Email. Campo requerido")
        private String email;
        @NotNull(message = "Avatar. Campo requerido")
        private String avatar;
        @NotNull(message = "Tipo de usuario. Campo requerido")
        private String typeSystemUser;
        @NotNull(message = "Estado de usuario. Campo requerido")
        private Boolean status;
        public Long getSystemUserId() {
            return systemUserId;
        }

        public void setSystemUserId(Long systemUserId) {
            this.systemUserId = systemUserId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTypeSystemUser() {
            return typeSystemUser;
        }

        public void setTypeSystemUser(String typeSystemUser) {
            this.typeSystemUser = typeSystemUser;
        }

        public Boolean isStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
        public void generateAlias() {
            if(this.email!=null) {
                int indexArroba = this.email.indexOf("@");
                if(indexArroba>-1)
                    this.alias = this.email.substring(0,indexArroba);
                else
                    this.alias = this.email;
            }
        }
        public String getExtension() {
            String ext = "";
            if(avatar != null) {
                int ind = avatar.lastIndexOf(".");
                if(ind > -1) {
                    ext = avatar.substring(ind+1);
                }
            }
            return ext;
        }
        public String getNameAvatar() {
            String nameAvatar = "";
            if(avatar != null) {
                int ind = avatar.lastIndexOf(".");
                if(ind > -1) {
                    nameAvatar = avatar.substring(0,ind);
                }
            }
            return nameAvatar;
        }
    }
    public class Cliente {
        private Long id;
        private String alias;
        private String direccion;
        private String email;
        private String nombreNegocio;
        private String descripcionNegocio;
        private Short tipoCliente;
        private Short ciudadId, zonaId, barrioId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNombreNegocio() {
            return nombreNegocio;
        }

        public void setNombreNegocio(String nombreNegocio) {
            this.nombreNegocio = nombreNegocio;
        }

        public String getDescripcionNegocio() {
            return descripcionNegocio;
        }

        public void setDescripcionNegocio(String descripcionNegocio) {
            this.descripcionNegocio = descripcionNegocio;
        }

        public Short getTipoCliente() {
            return tipoCliente;
        }

        public void setTipoCliente(Short tipoCliente) {
            this.tipoCliente = tipoCliente;
        }

        public Short getCiudadId() {
            return ciudadId;
        }

        public void setCiudadId(Short ciudadId) {
            this.ciudadId = ciudadId;
        }

        public Short getZonaId() {
            return zonaId;
        }

        public void setZonaId(Short zonaId) {
            this.zonaId = zonaId;
        }

        public Short getBarrioId() {
            return barrioId;
        }

        public void setBarrioId(Short barrioId) {
            this.barrioId = barrioId;
        }
    }
}