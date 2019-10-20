package com.example.server;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Server {
    private static ArrayList<String> offers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);
        HttpContext context = server.createContext("/", new EchoHandler());
        context.setAuthenticator(new Auth());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8765");
    }

    public static String parseQuery(String query) {
        String userName = "";
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] data = pair.split("=");
            if(data[0].equals("userName")) {
                userName = data[1];
            }
        }
        return userName;
    }

    static class EchoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().getPath();
            System.out.println(uri);
            switch (uri) {
                case "/offers/create":
                    createOffer(exchange);
                    break;
                case "/offers/find":
                    findOffer(exchange);
                    break;
                case "/disconnect":
                    disconnect(exchange);
            }
        }

        private void createOffer(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            String userName = parseQuery(exchange.getRequestURI().getQuery());
            builder.append("{ userId: '1', userName: '" + userName+ "'}");
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            offers.add(userName);
            OutputStream os = exchange.getResponseBody();
            System.out.println("Creator with id 1 connected");
            os.write(bytes);
            os.close();
        }

        private void findOffer(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            String userName = parseQuery(exchange.getRequestURI().getQuery());
            builder.append("{ userId: '1', userName: '" + userName+ "', offers: [");
            for(String offer: offers) {
                builder.append('\'' + offer + "',");
            }
            builder.append("]}");
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            System.out.println("Finder with id 1 connected");
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }

        private void disconnect(HttpExchange exchange) {
            for(String offer : offers) {
                if(offer.equals(parseQuery(exchange.getRequestURI().getQuery()))) {
                    offers.remove(offer);
                }
            }
            System.out.println("User with id 1 disconnected");
        }
    }

    static class Auth extends Authenticator {
        @Override
        public Result authenticate(HttpExchange httpExchange) {
            if ("/forbidden".equals(httpExchange.getRequestURI().toString())) {
                System.out.println("Some error");
                return new Failure(403);
            } else {
                return new Success(new HttpPrincipal("c0nst", "realm"));
            }
        }
    }
}