package ec.edu.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MensajeInternacionalizacionHandler {
    private static MensajeInternacionalizacionHandler instance;
    private ResourceBundle bundle;
    private Locale locale;
    /**
     * Clase encargada de gestionar la internacionalización (i18n) de la aplicación.
     *
     * Utiliza {@link ResourceBundle} para cargar mensajes en distintos idiomas
     * según la configuración regional definida por el usuario o el sistema.
     *
     * Permite cambiar dinámicamente el idioma y recuperar textos según claves
     * definidas en archivos `messages.properties`.
     *
     * @author Valeria
     * @version 1.0
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }
    /**
     * Retorna el mensaje traducido asociado a la clave especificada.
     *
     * @param key clave definida en el archivo de propiedades
     * @return texto traducido correspondiente
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }

    public Locale getLocale() {
        return this.locale;
    }
    public ResourceBundle getBundle() {
        return bundle;
    }
    public void setLocale(Locale newLocale, String baseName) {
        this.locale = newLocale;
        this.bundle = ResourceBundle.getBundle(baseName, newLocale);
    }

}

