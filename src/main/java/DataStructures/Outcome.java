package DataStructures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Outcome {

    private static String classname = Outcome.class.getSimpleName();

    Integer msgId;
    String operation;
    String type;
    Double timestamp;
    String marketId;
    String name;
    String outcomeId;
    String price;
    Boolean displayed;
    Boolean suspended;

    ////////////////////////////
    //Constructors METHODS//////

    public Outcome(String[] wordArr) {
        this.msgId= Integer.valueOf(wordArr[1]);
        this.operation=wordArr[2];
        this.type=wordArr[3];
        this.timestamp= Double.valueOf(wordArr[4]);
        this.marketId=wordArr[5];
        this.name=wordArr[6];
        this.outcomeId=wordArr[7];
        this.price=wordArr[8];
        this.displayed= Boolean.valueOf(wordArr[9]);
        this.suspended= Boolean.valueOf(wordArr[10]);
    }

    ////////////////////////////
    ///////HELPER METHODS///////
    ////////////////////////////


    public boolean isEmpty() {
        String logPrefix = classname + "isEmpty";
        try{
            if (null==this.getMsgId() || "".equals(this.getMsgId())){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(logPrefix + "Couldn't check if isnt Empty, err:" + e.getMessage());
            return true;
        }
    }

    public static JSONArray createOutcomesJsonArray(List<Outcome> outcomeList) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < outcomeList.size(); i++) {
            jsonArray.put(Outcome.createOutcomeJson(outcomeList.get(i)));
        }
        return jsonArray;
    }

    private static JSONObject createOutcomeJson(Outcome outcome) {
        String logPrefix = classname + "createOutcomeJson";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msgId",outcome.getMsgId());
            jsonObject.put("operation",outcome.getOperation());
            jsonObject.put("type",outcome.getType());
            jsonObject.put("timestamp",outcome.getTimestamp());
            jsonObject.put("marketId",outcome.getMarketId());
            jsonObject.put("name",outcome.getName());
            jsonObject.put("outcomeId",outcome.getOutcomeId());
            jsonObject.put("price",outcome.getPrice());
            jsonObject.put("displayed",outcome.getDisplayed());
            jsonObject.put("suspended",outcome.getSuspended());
        } catch (JSONException e) {
            System.out.println(logPrefix+ "Error:" + e.getMessage());
        }
        return jsonObject;
    }

    ////////////////////////////
    /////Getters And Setters////
    ////////////////////////////


    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public void setOutcomeId(String outcomeId) {
        this.outcomeId = outcomeId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getDisplayed() {
        return displayed;
    }

    public void setDisplayed(Boolean displayed) {
        this.displayed = displayed;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

}
