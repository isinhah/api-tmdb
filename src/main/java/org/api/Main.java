package org.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String movieTitle = "harry potter";
        movieTitle = movieTitle.replace(" ", "%");

        try {
            // abrindo a conexao
            URL url = new URL("https://api.themoviedb.org/3/search/movie?query=" + movieTitle + "&language=pt-BR");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // atributos obrigatorios
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNzY5Mjk2ZDg4ZDY4NGNiNTc2YjI4ZWFkYjgzYzNlNiIsInN1YiI6IjY2NzFmNTMyZDdhYjUwMTQyNDM1M2ZkOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.v7tEf-vudBZC9VK6oYrV-3mlC07lsUWDsCWB2ZuTjU4");

            // armazenando as informacoes da conexao
            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            // leitura de cada linha
            while ((line = bf.readLine()) != null) {
                response.append(line);
            }

            bf.close();

            // manipulando o json
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            System.out.println(json);

            JSONArray resultados = (JSONArray) json.get("results");
            for (Object resultado : resultados) {
                JSONObject filme = (JSONObject) resultado;
                System.out.println("************************");
                System.out.println(filme.get("title"));
                System.out.println("Overview: " + filme.get("overview"));
                System.out.println("Popularity: " + filme.get("popularity"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}