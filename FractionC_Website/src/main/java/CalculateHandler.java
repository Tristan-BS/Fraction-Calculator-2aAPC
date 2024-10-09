import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculateHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Read the request body
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(body);

            // Extract the values from JSON
            int L1 = Integer.parseInt(json.getString("L1"));
            int L2 = Integer.parseInt(json.getString("L2"));
            int R1 = Integer.parseInt(json.getString("R1"));
            int R2 = Integer.parseInt(json.getString("R2"));
            String operator = json.getString("operation");

            Fraction b1 = new Fraction(L1, L2);
            Fraction b2 = new Fraction(R1, R2);
            Fraction result = null;
            String title = "Calculation Result";
            String message = "";

            try {
                switch (operator) {
                    case "add":
                        result = b1.addieren(b2);
                        message = "Addition successful!";
                        break;
                    case "subtract":
                        result = b1.subtrahieren(b2);
                        message = "Subtraction successful!";
                        break;
                    case "multiply":
                        result = b1.multiplizieren(b2);
                        message = "Multiplication successful!";
                        break;
                    case "divide":
                        result = b1.dividieren(b2);
                        message = "Division successful!";
                        break;
                }
                if (result == null || !result.isValid()) {
                    throw new ArithmeticException("Invalid operation");
                }
            }catch(ArithmeticException e) {
                    title = "Error";
                    message = e.getMessage(); // Get the exception message from ArithmeticException
                }

            // Create JSON response
            JSONObject responseJson = new JSONObject();
            responseJson.put("result1", result != null ? result.Zähler : ""); // If result is null, return empty string
            responseJson.put("result2", result != null ? result.Nenner : "");
            responseJson.put("title", title);
            responseJson.put("message", message);

            // Send response
            String response = responseJson.toString();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}