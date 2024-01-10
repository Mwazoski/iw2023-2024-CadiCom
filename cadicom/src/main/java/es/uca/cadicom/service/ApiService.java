package es.uca.cadicom.service;
import es.uca.cadicom.entity.LineaCliente;
import es.uca.cadicom.entity.RegistroDatos;
import es.uca.cadicom.entity.RegistroLlamadas;

import es.uca.cadicom.entity.Usuario;
import org.apache.hc.core5.net.URIBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    UsuarioService usuarioService;
    public ApiService(RestTemplate restTemplate) { this.restTemplate = restTemplate; this.usuarioService = usuarioService; }

    public List<LineaCliente> getLineaClienteAll() throws URISyntaxException, IOException, InterruptedException, ParseException {
        List<LineaCliente> lineaClientes = new ArrayList<>();

        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/")
                .addParameter("carrier", "cadicom")
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.body());

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            generateUserfromLineaCliente(jsonObject);
            LineaCliente lineaCliente = new LineaCliente();
            lineaCliente.setId((String) jsonObject.get("id"));
            lineaCliente.setNombre((String) jsonObject.get("name"));
            lineaCliente.setApellidos((String) jsonObject.get("surname"));
            lineaCliente.setCompania((String) jsonObject.get("carrier"));
            lineaCliente.setNumero((String) jsonObject.get("phoneNumber"));

            lineaClientes.add(lineaCliente);
        }
        return lineaClientes;
    }

    public void generateUserfromLineaCliente(JSONObject jsonObject) {
        String name = jsonObject.get("name").toString();
        String surname = jsonObject.get("surname").toString();
        String email = name.toLowerCase() + "." + surname.charAt(0) + "@gmail.com";

        Usuario usuario = new Usuario(name, surname, email, "1234");
        usuarioService.createUser(usuario);
    }

    public void setLineaCliente(String name, String surname, String carrier, String phoneNumber) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/")
                .build();

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("surname", surname);
        json.put("carrier", carrier);
        json.put("phoneNumber", phoneNumber);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("accept", "application/hal+json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public LineaCliente getLineaCliente(String uuid) throws URISyntaxException, IOException, InterruptedException, ParseException {
        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid)
                .addParameter("carrier", "cadicom")
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());

        LineaCliente lineaCliente = new LineaCliente();
        lineaCliente.setId((String) jsonObject.get("id"));
        lineaCliente.setNombre((String) jsonObject.get("name"));
        lineaCliente.setApellidos((String) jsonObject.get("surname"));
        lineaCliente.setCompania((String) jsonObject.get("carrier"));
        lineaCliente.setNumero((String) jsonObject.get("phoneNumber"));

        return lineaCliente;
    }

    public LineaCliente getLineaClienteTelefono(String telefono) throws URISyntaxException, IOException, InterruptedException, ParseException {
        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/search/phoneNumber/" + telefono)
                .addParameter("carrier", "cadicom")
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());

        LineaCliente lineaCliente = new LineaCliente();
        lineaCliente.setId((String) jsonObject.get("id"));
        lineaCliente.setNombre((String) jsonObject.get("name"));
        lineaCliente.setApellidos((String) jsonObject.get("surname"));
        lineaCliente.setCompania((String) jsonObject.get("carrier"));
        lineaCliente.setNumero((String) jsonObject.get("phoneNumber"));

        return lineaCliente;
    }

    public LineaCliente updateLineaCliente(String uuid) throws URISyntaxException, IOException, InterruptedException, ParseException {
        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid)
                .build();

        JSONObject json = new JSONObject();
        json.put("name", "Pepito");
        json.put("surname", "Ruiz");
        json.put("carrier", "cadicom");
        json.put("phoneNumber", "654366555");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json.toString()))
                .header("accept", "application/hal+json")
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response.body());

        LineaCliente lineaCliente = new LineaCliente();
        lineaCliente.setId((String) jsonObject.get("id"));
        lineaCliente.setNombre((String) jsonObject.get("name"));
        lineaCliente.setApellidos((String) jsonObject.get("surname"));
        lineaCliente.setCompania((String) jsonObject.get("carrier"));
        lineaCliente.setNumero((String) jsonObject.get("phoneNumber"));

        return lineaCliente;
    }

    public void deleteLineaCliente(String uuid) throws URISyntaxException, IOException, InterruptedException {

        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid)
                .addParameter("carrier", "cadicom")
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .header("accept", "*/*")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<RegistroDatos> getRegistroDatos(String uuid, String startDate, String endDate) throws URISyntaxException, IOException, InterruptedException, ParseException {

        List<RegistroDatos> registroDatos = new ArrayList<>();

        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid + "/datausagerecords")
                .addParameter("carrier", "cadicom")
                .addParameter("startDate", startDate)
                .addParameter("endDate", endDate)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.body());

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            RegistroDatos registroDato = new RegistroDatos();
            registroDato.setDate((String) jsonObject.get("date"));
            registroDato.setMegaBytes(Math.toIntExact((Long) jsonObject.get("megaBytes")));

            registroDatos.add(registroDato);
        }
        return registroDatos;
    }

    public int getRegistroDatosSuma(String uuid, String startDate, String endDate) throws URISyntaxException, IOException, InterruptedException, ParseException {

        int total = 0;

        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid + "/datausagerecords")
                .addParameter("carrier", "cadicom")
                .addParameter("startDate", startDate)
                .addParameter("endDate", endDate)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.body());

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            total += Math.toIntExact((Long) jsonObject.get("megaBytes"));
        }
        return total;
    }

    public List<RegistroLlamadas> getRegistroLlamadas(String uuid, String startDate, String endDate) throws URISyntaxException, IOException, InterruptedException, ParseException {

        List<RegistroLlamadas> registroLlamadas = new ArrayList<>();

        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid + "/callrecords")
                .addParameter("carrier", "cadicom")
                .addParameter("startDate", startDate)
                .addParameter("endDate", endDate)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.body());

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            RegistroLlamadas registroLlamada = new RegistroLlamadas();
            registroLlamada.setNumeroDestino((String) jsonObject.get("destinationPhoneNumber"));
            registroLlamada.setFecha((String) jsonObject.get("date"));
            registroLlamada.setSegundos((Integer) jsonObject.get("seconds"));

            registroLlamadas.add(registroLlamada);
        }
        return registroLlamadas;
    }

    public double getRegistroLlamadasSuma(String uuid, String startDate, String endDate) throws URISyntaxException, IOException, InterruptedException, ParseException {

        double total = 0;

        URI uri = new URIBuilder("http://omr-simulator.us-east-1.elasticbeanstalk.com/" + uuid + "/callrecords")
                .addParameter("carrier", "cadicom")
                .addParameter("startDate", startDate)
                .addParameter("endDate", endDate)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("accept", "application/hal+json")
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.body());

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            total += ((Integer) jsonObject.get("seconds"));

        }
        return total;
    }

    public static String getMonthStartDate(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return startDate.format(formatter);
    }

    public static String getMonthEndDate(int year, int month) {
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return endDate.format(formatter);
    }

}