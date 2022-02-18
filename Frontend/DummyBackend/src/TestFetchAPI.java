import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestFetchAPI {
    public static void main(String[] args) throws IOException, InterruptedException {
//        GETGameData();
//        POST();
        PUTGameData();
//        int cnt = 0;
//        while (true) {
//            GETInput();
//            PUTGameData(cnt++);
//            Thread.sleep(3000);
//        }
    }

    public static void GETGameData() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:8080/gamedata");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(String.valueOf(result));

            JSONObject obj2 = (JSONObject) obj;
            System.out.println("Data GET: ");
            System.out.println(obj2.toString());
            System.out.println("---------------------");
//            System.out.println(obj2.get("state"));
//            List<Integer> a = (List<Integer>) obj2.get("pos");
//            Map<String , Integer> b = (Map<String, Integer>) obj2.get("bindings");
//            if(a != null)
//                System.out.println(a.get(1));
//            if(b != null)
//                System.out.println(b.get("sss"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void GETInput() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:8080/input");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(String.valueOf(result));

            JSONObject obj2 = (JSONObject) obj;
            System.out.println("Data GET: ");
            System.out.println(obj2.toString());
            System.out.println("---------------------");
//            System.out.println(obj2.get("state"));
//            List<Integer> a = (List<Integer>) obj2.get("pos");
//            Map<String , Integer> b = (Map<String, Integer>) obj2.get("bindings");
//            if(a != null)
//                System.out.println(a.get(1));
//            if(b != null)
//                System.out.println(b.get("sss"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void POST() throws IOException {
        List<Integer> nums = new LinkedList<>();
        nums.add(1);
        nums.add(2);
        Map<String, Integer> mapped = new HashMap<>();
        mapped.put("haha", 222);
        mapped.put("kkk", 333);


        JSONObject obj = new JSONObject();
        obj.put("name", "Jackson PP");
        obj.put("nums", nums);
        obj.put("role", "student");
        obj.put("mapped", mapped);

        String rawData = obj.toJSONString();
        System.out.println(rawData);

        URL url = new URL("http://localhost:8080/employees");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = rawData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }
    }

    public static void PUTGameData() throws IOException {

        List<Integer> pos = new LinkedList<>();
        pos.add(42);
        pos.add(8668);
        Map<String, Integer> bindings = new HashMap<>();
        bindings.put("sss", 2);
        bindings.put("bbb", 555);
        List<List<Integer>> order = new LinkedList<>();
        order.add(pos);
        order.add(pos);

        JSONObject obj = new JSONObject();
        obj.put("state", 3);
        obj.put("pos", pos);
        obj.put("order", order);
        obj.put("bindings", bindings);

        String rawData = obj.toJSONString();
        System.out.println("Raw Data PUT:");
        System.out.println(rawData);

        URL url = new URL("http://localhost:8080/gamedata/put");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = rawData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        System.out.println("Response:");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }
        System.out.println("---------------------");
    }
}
