
package com.ExploradorPaises.model;

import java.util.List;

public class Country {

    private Names names;
    private Codes codes;
    private Flag flag;
    private String region;
    private String subregion;
    private List<Capital> capitals;
    private long population;
    private Area area;
    private List<Currency> currencies;
    private List<Language> languages;

    public String getName() {
        return names != null && names.common != null
                ? names.common : "";
    }

    public String getCode() {
        return codes != null && codes.alpha_2 != null
                ? codes.alpha_2 : "";
    }

    public String getFlagUrl() {
        return flag != null && flag.url_png != null
                ? flag.url_png : "";
    }

    public String getFlagEmoji() {
        return flag != null && flag.emoji != null
                ? flag.emoji : "";
    }

    public String getRegion() {
        return region != null ? region : "Não informado";
    }

    public String getSubregion() {
        return subregion != null ? subregion : "Não informado";
    }

    public String getCapital() {
        if (capitals == null || capitals.isEmpty()) {
            return "Não informada";
        }

        return capitals.get(0).name != null
                ? capitals.get(0).name : "Não informada";
    }

    public long getPopulation() {
        return population;
    }

    public double getArea() {
        return area != null ? area.kilometers : 0;
    }

    public String getCurrency() {
        if (currencies == null || currencies.isEmpty()) {
            return "Não informada";
        }

        Currency currency = currencies.get(0);

        String nome = currency.name != null
                ? currency.name : "";

        String codigo = currency.code != null
                ? currency.code : "";

        return nome + (codigo.isEmpty() ? "" : " (" + codigo + ")");
    }

    public String getLanguages() {
        if (languages == null || languages.isEmpty()) {
            return "Não informado";
        }

        StringBuilder resultado = new StringBuilder();

        for (Language idioma : languages) {
            if (idioma.name != null) {
                if (resultado.length() > 0) {
                    resultado.append(", ");
                }

                resultado.append(idioma.name);
            }
        }

        return resultado.length() > 0
                ? resultado.toString() : "Não informado";
    }

    public static class Names {
        private String common;
    }

    public static class Codes {
        private String alpha_2;
    }

    public static class Flag {
        private String url_png;
        private String emoji;
    }

    public static class Capital {
        private String name;
    }

    public static class Area {
        private double kilometers;
    }

    public static class Currency {
        private String code;
        private String name;
    }

    public static class Language {
        private String name;
    }
}
