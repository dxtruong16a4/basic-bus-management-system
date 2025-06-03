package utility;

import java.util.Locale;
import java.util.ResourceBundle;

public class AppTranslator {
    ResourceBundle bundle;
    private static AppTranslator instance;

    private AppTranslator(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    /**
     * Singleton instance of AppTranslator.
     * @param locale the locale for which translations are needed
     * @return the singleton instance of AppTranslator
     */
    public static AppTranslator getInstance(Locale locale) {
        if (instance == null) {
            instance = new AppTranslator(locale);
        }
        return instance;
    }

    public String translate(String key) {
        return bundle.getString(key);
    }

    // Usage example:
    /*
     * AppTranslator translator = AppTranslator.getInstance(new Locale("vi"));
     * String translatedText = translator.translate("signup.username.invalid1");
     */    
}