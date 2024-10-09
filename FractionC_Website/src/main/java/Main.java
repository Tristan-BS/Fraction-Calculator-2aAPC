import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
        server.createContext("/", new MyHandler());
        server.createContext("/src/main/Resources/", new StaticFileHandler());
        server.createContext("/calculate", new CalculateHandler());
        server.setExecutor(null);
        server.start();

        logger.info("Server started at http://localhost:3000");

        String url = "http://localhost:3000";
        try {
            new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", url).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to open browser, please visit " + url + " manually.");
        }
    }

    // Handler for default context and opens the FCalculator.html file as Localhost Website
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            String path = "src/main/Resources/FCalcuator.html";
            logger.info("Requested file path: " + path + "\n");
            byte[] response;

            try {
                response = Files.readAllBytes(Paths.get(path));

                logger.info("File read successfully, size: " + response.length + " bytes \n");

                t.getResponseHeaders().set("Content-Type", "text/html");
                t.sendResponseHeaders(200, response.length);

            } catch (IOException e) {
                String errorMessage = "File not found or unable to read file. \n";

                logger.log(Level.SEVERE, "Error: " + errorMessage);

                response = errorMessage.getBytes();
                t.sendResponseHeaders(404, response.length);
            }
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }

    // Handler for serving static files like CSS
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath().substring(1); // remove leading slash
            logger.info("Requested static file path: " + path + "\n");
            byte[] response;

            try {
                response = Files.readAllBytes(Paths.get(path));
                String contentType = Files.probeContentType(Paths.get(path));
                t.getResponseHeaders().set("Content-Type", contentType);
                t.sendResponseHeaders(200, response.length);
            } catch (IOException e) {
                String errorMessage = "File not found or unable to read file. \n";
                logger.log(Level.SEVERE, "Error: " + errorMessage);
                response = errorMessage.getBytes();
                t.sendResponseHeaders(404, response.length);
            }
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}