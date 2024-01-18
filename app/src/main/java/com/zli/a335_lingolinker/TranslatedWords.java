package com.zli.a335_lingolinker;

public class TranslatedWords {

    private String original;

    private String translated;

    private String language;

    public TranslatedWords(String original, String translated, String language) {
        this.original = original;
        this.translated = translated;
        this.language = language;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOriginal() {
        return original;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
