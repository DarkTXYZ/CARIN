import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestFetchAPI {
    public static void main(String[] args) throws IOException {
//        GET();
        POST();

//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("param1", "val");
//
//        con.setDoOutput(true);
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());
//        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//        out.flush();
//        out.close();
    }

    public static void GET() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:8080/employees");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(String.valueOf(result));
            JSONArray array = (JSONArray)obj;
            System.out.println(array);

            JSONObject obj2 = (JSONObject)array.get(1);
            System.out.println(obj2.get("role"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void POST() throws IOException {
        List<Integer> nums = new LinkedList<>();
        nums.add(1);
        nums.add(2);
        Map<String, Integer> mapped = new HashMap<>();
        mapped.put("haha",222);
        mapped.put("kkk" , 333);


        JSONObject obj = new JSONObject();
        obj.put("name" , "Jackson PP");
        obj.put("nums", nums);
        obj.put("role" , "student");
        obj.put("mapped" , mapped);

        String rawData = obj.toJSONString();
        System.out.println(rawData);

        URL url = new URL ("http://localhost:8080/employees");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = rawData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
}
