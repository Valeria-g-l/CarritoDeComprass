package ec.edu.modelo;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String pregunta1;
    private String respuesta1;
    private String pregunta2;
    private String respuesta2;
    private String pregunta3;
    private String respuesta3;



    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol) {
        this.username = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    public String getPregunta1() {
        return pregunta1;
    }
    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }
    public String getRespuesta1() {
        return respuesta1;
    }
    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }
    public String getPregunta2() {
        return pregunta2;
    }
    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }
    public String getRespuesta2() {
        return respuesta2;
    }
    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }
    public String getPregunta3() {
        return pregunta3;
    }
    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }
    public String getRespuesta3() {
        return respuesta3;
    }
    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreDeUsuario='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                '}';
    }
}
