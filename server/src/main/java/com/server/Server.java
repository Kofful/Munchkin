package com.server;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Server {
    private static ArrayList<String> offers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);
        HttpContext context = server.createContext("/", new EchoHandler());
        context.setAuthenticator(new Auth());

        server.setExecutor(null);
        server.start();
        System.out.println("HTTP Server started on port 8765");
    }

    public static String parseQuery(String query) {
        String userName = "";
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] data = pair.split("=");
            if (data[0].equals("userName")) {
                userName = data[1];
            }
        }
        return userName;
    }

    public static HashMap<String, String> parsePost(String query) {
        HashMap<String, String> map = new HashMap<>();
        String[] pairs = query.replaceAll("[}{\"\\s]", "").split(",");
        String key;
        String value;
        for(String pair: pairs)
            System.out.println(pair);
        for (int i = 0; i < pairs.length; i++) {
            String[] strs = pairs[i].split(":");
            key = strs[0];
            value = strs[1];
            map.put(key, value);
        }
        return map;
    }

    public static void sendResponse(String response, HttpExchange exchange) {
        try {
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (Exception ex) {

        }
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
                    break;
                case "/register":
                    registerUser(exchange);
                    break;
                case "/login":
                    checkUser(exchange);
            }
        }

        private void registerUser(HttpExchange exchange) throws IOException {
            InputStream scanner = exchange.getRequestBody();
            byte[] array = new byte[scanner.available()];
            scanner.read(array);
            String str = new String(array, StandardCharsets.UTF_8);
            try {
                HashMap<String, String> data = parsePost(str);

                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/munchkindb?useUnicode=true&serverTimezone=UTC", "root", "");
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery("select id from `users` where nickname ='"
                        + data.get("nickname") + "'");
                if (result.next()) {
                    sendResponse("{\"answer\":\"111\"}", exchange);
                    return;
                }

                result = statement.executeQuery("select id from `users` where email ='"
                        + data.get("email") + "'");
                if (result.next()) {
                    sendResponse("{\"answer\":\"112\"}", exchange);
                    return;
                }

                statement.execute("insert into `users` (nickname, password, email) value ('"
                        + data.get("nickname") + "', '"
                        + data.get("passwordHash") + "', '"
                        + data.get("email") + "')");
                result = statement.executeQuery("select id from `users` where nickname = '" + data.get("nickname") + "'");
                result.next();
                String response = "{ \"userId\": \"" + result.getInt("id")
                        + "\", \"passwordHash\" : \"" + data.get("passwordHash")
                        + "\", \"nickname\": \"" + data.get("nickname")
                        + "\", \"email\": \"" + data.get("email")
                        + "}";
                sendResponse(response, exchange);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


        private void checkUser(HttpExchange exchange) throws IOException {
            InputStream scanner = exchange.getRequestBody();
            byte[] array = new byte[scanner.available()];
            scanner.read(array);
            String str = new String(array, StandardCharsets.UTF_8);
            try {
                HashMap<String, String> data = parsePost(str);
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/munchkindb?useUnicode=true&serverTimezone=UTC", "root", "");
                Statement statement = con.createStatement();
                ResultSet result = statement.executeQuery("select id from `users` where email ='"
                        + data.get("email") + "'");
                if (!result.next()) {
                    sendResponse("{\"answer\":\"211\"}", exchange);
                    return;
                }
                result = statement.executeQuery("select * from `users` where password ='"
                        + data.get("passwordHash") + "'");
                if (!result.next()) {
                    sendResponse("{\"answer\":\"212\"}", exchange);
                    return;
                }
                int userId = result.getInt("id");
                String userNickname = result.getString("nickname");
                result = statement.executeQuery("select COUNT(*) from `subscribers` where objectId = " + userId);
                result.next();
                int subscribers = result.getInt(1);
                String response = "{ \"userId\": \"" + userId
                        + "\", \"nickname\": \"" + userNickname
                        + "\", \"passwordHash\" : \"" + data.get("passwordHash")
                        + "\", \"email\": \"" + data.get("email")
                        + "\", \"subscribers\": \"" + subscribers
                        + "\"}";
                sendResponse(response, exchange);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

        }

        private void createOffer(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            String userName = parseQuery(exchange.getRequestURI().getQuery());
            builder.append("{ userId: '1', userName: '" + userName + "'}");
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
            builder.append("{ userId: '1', userName: '" + userName + "', offers: [");
            for (String offer : offers) {
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
            for (String offer : offers) {
                if (offer.equals(parseQuery(exchange.getRequestURI().getQuery()))) {
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
                return new Failure(403);
            } else {
                return new Success(new HttpPrincipal("c0nst", "realm"));
            }
        }
    }
}