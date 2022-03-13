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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Controller {

    static JSONObject input;

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

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

//        sendGameData(m, n, state, shopState, currency, cost, posX, posY, hp, hpMax, type, objective, objectiveMax);

//        getInput();
//        int selectedX = getInputData("selectedX");
//        int move = getInputData("moveState");
//        System.out.println(selectedX);
//        System.out.println(move);

        sendGameState(0);
    }

    @SuppressWarnings("unchecked")
    public static void sendGameData(int m, int n, int state, List<Boolean> shopState, int currency, List<Integer> cost, List<Integer> posX,
                                    List<Integer> posY, List<Integer> hp, List<Integer> hpMax, List<Integer> type, int objective, int objectiveMax){

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

    public static void sendGameState(int state){

        JSONObject obj = new JSONObject();
        obj.put("state", state);

        putData("http://localhost:8080/gamedata/put" , obj);

    }

    public static void sendPlaceState(int state){

        JSONObject obj = new JSONObject();
        obj.put("placeState", state);

        putData("http://localhost:8080/input/put/placestate" , obj);

    }

    public static void putData(String link , JSONObject data) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exit Program");
            System.exit(0);
        }

    }

    public static void getInput(){
        try {
            input = getData("http://localhost:8080/input");
        } catch(Exception e) {
            System.out.println("Can't get data");
            e.printStackTrace();
            System.out.println("Exit Program");
            System.exit(0);
        }
    }

    public static int getInputData(String key) {
        return ((Long) input.get(key)).intValue();
    }

    public static JSONObject getData(String link) throws IOException, ParseException {
        URL url = new URL(link);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responsecode = conn.getResponseCode();

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }

            //Close the scanner
            scanner.close();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();

            return (JSONObject) parse.parse(inline.toString());
//            //Get the required object from the above created object
//            JSONObject obj = (JSONObject) data_obj.get("Global");
//
//            //Get the required data using its key
//            System.out.println(obj.get("TotalRecovered"));
//
//            JSONArray arr = (JSONArray) data_obj.get("Countries");
//
//            for (int i = 0; i < arr.size(); i++) {
//
//                JSONObject new_obj = (JSONObject) arr.get(i);
//
//                if (new_obj.get("Slug").equals("albania")) {
//                    System.out.println("Total Recovered: " + new_obj.get("TotalRecovered"));
//                    break;
//                }
//            }
        }
    }
}
