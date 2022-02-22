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
import java.util.*;

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

    public static void PUTGameData() throws IOException {

//        private int state;
//        private List<Integer> posX,posY,type,shopState;
//        private int objective , objectiveMax;
//        private int m,n;

        Integer[] a = { 1, 3, 1, 3, 2};
        List<Integer> posX = Arrays.asList(a);

        a = new Integer[]{2, 2, 3, 0, 3};
        List<Integer> posY = Arrays.asList(a);

        a = new Integer[]{2, 1, 1, 3, 2};
        List<Integer> type = Arrays.asList(a);

        a = new Integer[]{1, 0, 1};
        List<Integer> shopState = Arrays.asList(a);

        JSONObject obj = new JSONObject();
        obj.put("state", 2);
        obj.put("posX", posX);
        obj.put("posY", posY);
        obj.put("type", type);
        obj.put("objective", 2);
        obj.put("objectiveMax", 10);
        obj.put("shopState", shopState);
        obj.put("m", 10);
        obj.put("n", 10);

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
