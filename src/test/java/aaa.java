import com.google.gson.Gson;

import java.util.Map;

public class aaa {
    public static void main(String[] args) {
        String str = "{\"SSBM_ID\":\"01090004\",\"BM_ID\":\"\",\"01090004\":\"01049010\",\"YFID\":{\"01090004\":\"080011\"}}";
        Gson gson = new Gson();
        Map<String, Object> bmksmap = gson.fromJson(str, Map.class);
        System.out.println(bmksmap.containsKey("YFID"));
        System.out.println(bmksmap.containsKey("01090004"));
        System.out.println(bmksmap.get("01090004").toString());
    }
}
