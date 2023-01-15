package ru.yandex.practicum.kanban;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVClient {
    private final String kvServerUrl;
    private final HttpClient client;

    private final String token;

    public KVClient(String URL) throws IOException, URISyntaxException, InterruptedException {
        kvServerUrl = URL;
        client = HttpClient.newHttpClient();
        token = getToken();
    }

    public void put(String key, String json) throws IOException, InterruptedException, URISyntaxException {
        //должен сохранять состояние менеджера задач через запрос POST /save/<ключ>?API_TOKEN=.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(kvServerUrl + "/save" + key + "?" + getToken()))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        client.send(request, HttpResponse.BodyHandlers.discarding());
    }

    public String load(String key) throws IOException, URISyntaxException, InterruptedException {
        //должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_TOKEN=.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(kvServerUrl + "/load" + key + "?" + getToken()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private String getToken() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(kvServerUrl + "/register"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return token;
    }
}
