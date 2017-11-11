package DataStructures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Outcome {

    private static String classname = Outcome.class.getSimpleName();

    private Integer msgId;
    private String operation;
    private String type;
    private Double timestamp;
    private String marketId;
    private String name;
    private String outcomeId;
    private String price;
    private Boolean displayed;
    private Boolean suspended;

    ////////////////////////////
    ///////  Constructors //////
    ////////////////////////////
    public Outcome() {
    }

    public Outcome(String[] wordArr) {
        this.msgId = Integer.valueOf(wordArr[1]);
        this.operation = wordArr[2];
        this.type = wordArr[3];
        this.timestamp = Double.valueOf(wordArr[4]);
        this.marketId = wordArr[5];
        this.name = wordArr[6];
        this.outcomeId = wordArr[7];
        this.price = wordArr[8];
        this.displayed = Boolean.valueOf(wordArr[9]);
        this.suspended = Boolean.valueOf(wordArr[10]);
    }

    ////////////////////////////
    ///////HELPER METHODS///////
    ////////////////////////////

    public boolean isEmpty() {
        String logPrefix = classname + ":isEmpty:";
        try {
            return null == this.getMsgId();
        } catch (Exception e) {
            System.out.println(logPrefix + "Couldn't check if isn't Empty, err:" + e.getMessage());
            return true;
        }
    }

    static JSONArray createOutcomesJsonArray(List<Outcome> outcomeList) {
        JSONArray jsonArray = new JSONArray();
        outcomeList.forEach(outcome -> jsonArray.put(Outcome.createOutcomeJson(outcome)));
        return jsonArray;
    }

    private static JSONObject createOutcomeJson(Outcome outcome) {
        String logPrefix = classname + ":createOutcomeJson:";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msgId", outcome.getMsgId());
            jsonObject.put("operation", outcome.getOperation());
            jsonObject.put("type", outcome.getType());
            jsonObject.put("timestamp", outcome.getTimestamp());
            jsonObject.put("marketId", outcome.getMarketId());
            jsonObject.put("name", outcome.getName());
            jsonObject.put("outcomeId", outcome.getOutcomeId());
            jsonObject.put("price", outcome.getPrice());
            jsonObject.put("displayed", outcome.getDisplayed());
            jsonObject.put("suspended", outcome.getSuspended());
        } catch (JSONException e) {
            System.out.println(logPrefix + "Error:" + e.getMessage());
        }
        return jsonObject;
    }

    ////////////////////////////
    /////Getters And Setters////
    ////////////////////////////


    private Integer getMsgId() {
        return msgId;
    }


    private String getOperation() {
        return operation;
    }


    private String getType() {
        return type;
    }


    private Double getTimestamp() {
        return timestamp;
    }


    private String getMarketId() {
        return marketId;
    }


    private String getName() {
        return name;
    }


    private String getOutcomeId() {
        return outcomeId;
    }


    private String getPrice() {
        return price;
    }


    private Boolean getDisplayed() {
        return displayed;
    }


    private Boolean getSuspended() {
        return suspended;
    }


}
