
package com.ExploradorPaises.model;

import java.util.List;

public class CountryResponse {

    private Data data;

    public List<Country> getCountries() {

        if (data == null || data.objects == null) {
            return List.of();
        }

        return data.objects;
    }

    public static class Data {

        private List<Country> objects;
    }
}
