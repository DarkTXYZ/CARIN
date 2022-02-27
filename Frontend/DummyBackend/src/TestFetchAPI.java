import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TestFetchAPI {
    public static void main(String[] args) throws IOException, InterruptedException {
//        GETGameData();
//        POST();
//        while(true) {
            PUTGameData();
//        setGameState(1);
//        setGameState(1);
//            Thread.sleep(3000);
//            PUTGameData2();
//            Thread.sleep(3000);
//        }


//        int cnt = 0;
//        while (true) {
//            GETInput();
//            PUTGameData(cnt++);
//            Thread.sleep(3000);
//        }
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
            System.out.println("InputData GET: ");
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

    public static void setGameState(int state) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("state", state);


        String rawData = obj.toJSONString();
        System.out.println("Raw GameData PUT:");
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

    @SuppressWarnings("unchecked")
    public static void PUTGameData() throws IOException {

//        private int m,n;
//        private int state;
//        private List<Integer> shopState;
//        private int currency;
//        private List<Integer> posX,posY,type,hp,hpMax;
//        private int objective , objectiveMax;

        int m = 6, n = 6;
        int state = 1;
        List<Integer> shopState = Arrays.asList(0, 1, 1);
        int currency = 100;
        List<Integer> cost = Arrays.asList(20,40,60);
        List<Integer> posX = Arrays.asList(1,2, 3, 4, 5);
        List<Integer> posY = Arrays.asList(4, 3, 4, 0, 2);
        List<Integer> hp = Arrays.asList(10, 20, 30, 40, 10);
        List<Integer> hpMax = Arrays.asList(50, 30, 100, 80, 45);
        List<Integer> type = Arrays.asList(1,2,3,1,2);
        int objective = 2 , objectiveMax = 10;

        JSONObject obj = new JSONObject();
        obj.put("m", m);
        obj.put("n", n);
        obj.put("state", state);
        obj.put("shopState", shopState);
        obj.put("currency", currency);
        obj.put("cost",cost);
        obj.put("posX", posX);
        obj.put("posY", posY);
        obj.put("hp", hp);
        obj.put("hpMax", hpMax);
        obj.put("type", type);
        obj.put("objective", objective);
        obj.put("objectiveMax", objectiveMax);

        String rawData = obj.toJSONString();
        System.out.println("Raw GameData PUT:");
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

    @SuppressWarnings("unchecked")
    public static void PUTGameData2() throws IOException {

//        private int m,n;
//        private int state;
//        private List<Integer> shopState;
//        private int currency;
//        private List<Integer> posX,posY,type,hp,hpMax;
//        private int objective , objectiveMax;

        int m = 6, n = 10;
        int state = 1;
        List<Integer> shopState = Arrays.asList(1, 1, 1);
        int currency = 200;
        List<Integer> cost = Arrays.asList(20,40,60);
        List<Integer> posX = Arrays.asList(1, 2, 4, 5, 3);
        List<Integer> posY = Arrays.asList(9, 8, 2, 6, 1);
        List<Integer> hp = Arrays.asList(20, 25, 50, 60, 30);
        List<Integer> hpMax = Arrays.asList(50, 30, 100, 80, 45);
        List<String> type = Arrays.asList("atbd1","atbd2","atbd3","atbd1","atbd2");
        int objective = 4 , objectiveMax = 10;

        JSONObject obj = new JSONObject();
        obj.put("m", m);
        obj.put("n", n);
        obj.put("state", state);
        obj.put("shopState", shopState);
        obj.put("currency", currency);
        obj.put("cost",cost);
        obj.put("posX", posX);
        obj.put("posY", posY);
        obj.put("hp", hp);
        obj.put("hpMax", hpMax);
        obj.put("type", type);
        obj.put("objective", objective);
        obj.put("objectiveMax", objectiveMax);

        String rawData = obj.toJSONString();
        System.out.println("Raw GameData PUT:");
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
