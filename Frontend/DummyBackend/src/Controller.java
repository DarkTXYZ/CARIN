import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    public static void main(String[] args) throws IOException, InterruptedException {
//        Run Every Second
//        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    sendGameData(m, n, state, shopState, currency, cost, posX, posY, hp, hpMax, type, objective, objectiveMax);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 1, TimeUnit.SECONDS);

//        while(true) {
//            int m = 6, n = 6;
//            int state = 1;
//            List<Integer> shopState = Arrays.asList(1, 1, 1);
//            int currency = 100;
//            List<Integer> cost = Arrays.asList(20,40,60);
//            List<Integer> posX = Arrays.asList(1,2, 3, 4, 5 ,3,5,2);
//            List<Integer> posY = Arrays.asList(4, 3, 4, 0, 2 , 0 , 5 ,1);
//            List<Integer> hp = Arrays.asList(10, 20, 30, 40, 10,6,10,4562);
//            List<Integer> hpMax = Arrays.asList(50, 30, 100, 80, 45,10,20,9999);
//            List<Integer> type = Arrays.asList(1,2,3,1,2,5,4,6);
//            int objective = 2 , objectiveMax = 10;
//
//            sendGameData(m, n, state, shopState, currency, cost, posX, posY, hp, hpMax, type, objective, objectiveMax);
//            Thread.sleep(10000);
//            shopState = Arrays.asList(1, 1, 1);
//            currency = 300;
//            cost = Arrays.asList(20,40,60);
//            posX = Arrays.asList(1	,5	,4	,4	,2	,2	,1);
//            posY = Arrays.asList(2	,2	,2	,0	,0	,5	,1);
//            hp = Arrays.asList(61,	97,	82,	20	,98,	72,	10);
//            hpMax = Arrays.asList(120,	760,	500	,70,	600,	350,	39);
//            type = Arrays.asList(2	,5,	1	,4	,4	,3	,1);
//            objective = 4 ;
//
//            sendGameData(m, n, state, shopState, currency, cost, posX, posY, hp, hpMax, type, objective, objectiveMax);
//            Thread.sleep(10000);
//        }

        int m = 6, n = 6;
        int state = 1;
        List<Integer> shopState = Arrays.asList(1, 1, 1);
        int currency = 100;
        List<Integer> cost = Arrays.asList(20,40,60);
        List<Integer> posX = Arrays.asList(1,2, 3, 4, 5 ,3,5,2);
        List<Integer> posY = Arrays.asList(4, 3, 4, 0, 2 , 0 , 5 ,1);
        List<Integer> hp = Arrays.asList(10, 20, 30, 40, 10,6,10,4562);
        List<Integer> hpMax = Arrays.asList(50, 30, 100, 80, 45,10,20,9999);
        List<Integer> type = Arrays.asList(1,2,3,1,2,5,4,6);
        int objective = 2 , objectiveMax = 10;

        sendGameData(m, n, state, shopState, currency, cost, posX, posY, hp, hpMax, type, objective, objectiveMax);



    }

    @SuppressWarnings("unchecked")
    public static void sendGameData(int m, int n, int state, List<Integer> shopState, int currency, List<Integer> cost, List<Integer> posX,
                                    List<Integer> posY, List<Integer> hp, List<Integer> hpMax, List<Integer> type, int objective, int objectiveMax) throws IOException {

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

        putData("http://localhost:8080/gamedata/put" , obj);

    }

    public static void putData(String link , JSONObject data) throws IOException {
        String rawData = data.toJSONString();
        System.out.println("Raw GameData PUT:");
        System.out.println(rawData);

        URL url = new URL(link);
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
