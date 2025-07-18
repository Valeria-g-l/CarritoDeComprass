package ec.edu.modelo;

import java.io.Serializable;

public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String correo;
    private String pregunta1;
    private String respuesta1;
    private String pregunta2;
    private String respuesta2;
    private String pregunta3;
    private String respuesta3;



    public Usuario() {}
    /**
     * Constructor que inicializa un usuario con datos básicos y su rol.
     *
     * @param username Nombre de usuario o cédula que identifica al usuario.
     * @param contrasenia Contraseña del usuario para autenticación.
     * @param nombre Nombre completo del usuario.
     * @param correo Correo electrónico del usuario.
     * @param rol Rol asignado al usuario (por ejemplo, administrador o usuario normal).
     */
    public Usuario(String username, String contrasenia, String nombre, String correo, Rol rol) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombre = nombre;
        this.correo = correo;
    }
    public Usuario(String username, String contrasena, Rol rol) {
        this.username = username;
        this.contrasenia = contrasena;
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
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
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

    public static boolean esCedulaValida(String cedula) {
        System.out.println("Validando cédula: " + cedula);
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (provincia < 1 || provincia > 24 || tercerDigito >= 6) return false;

        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Integer.parseInt(cedula.charAt(i) + "");
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }
            suma += digito;
        }

        int verificador = Integer.parseInt(cedula.charAt(9) + "");
        return (suma + verificador) % 10 == 0;
    }

    public static boolean esContrasenaValida(String contrasena) {
        return contrasena != null &&
                contrasena.length() >= 6 &&
                contrasena.matches(".*[A-Z].*") &&
                contrasena.matches(".*\\d.*") &&
                contrasena.matches(".*[!@#$%^&*()].*");
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "nombre=" + nombre +
                "correo=" + correo +
                "nombreDeUsuario='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                '}';
    }
}
