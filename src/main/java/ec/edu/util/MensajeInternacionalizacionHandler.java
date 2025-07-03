package ec.edu.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MensajeInternacionalizacionHandler {
    private static MensajeInternacionalizacionHandler instance;
    private ResourceBundle bundle;
    private Locale locale;

    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }

    public String get(String key) {
        return bundle.getString(key);
    }

    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("messages", locale);
    }

    public Locale getLocale() {
        return locale;
    }
    public ResourceBundle getBundle() {
        return bundle;
    }
    public void setLocale(Locale newLocale, String baseName) {
        this.locale = newLocale;
        this.bundle = ResourceBundle.getBundle(baseName, newLocale);
    }

}

