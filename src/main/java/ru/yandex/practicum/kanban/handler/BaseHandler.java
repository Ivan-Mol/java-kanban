package ru.yandex.practicum.kanban.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.kanban.exceptions.IncorrectTaskException;
import ru.yandex.practicum.kanban.exceptions.TaskNotFoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class BaseHandler implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    protected final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            Object result = handleInner(httpExchange);
            writeResponse(httpExchange, result, 200);
        } catch (IOException e) {
            e.printStackTrace();
            writeResponse(httpExchange, "Unexpected Error", 500);
        } catch (TaskNotFoundException e) {
            writeResponse(httpExchange, e.getMessage(), 404);
        } catch (IncorrectTaskException e) {
            writeResponse(httpExchange, e.getMessage(), 400);
        }
    }

    protected abstract Object handleInner(HttpExchange exchange) throws IOException;

    private void writeResponse(HttpExchange exchange,
                               Object responseObj,
                               int responseCode) throws IOException {
        String responseString = gson.toJson(responseObj);
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }

    protected int getIdFromQuery(String query) {
        try {
            return Integer.parseInt(query.substring(3));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
