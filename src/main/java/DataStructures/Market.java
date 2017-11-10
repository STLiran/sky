package DataStructures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Market {

    private static String classname = Market.class.getSimpleName();

    Integer msgId;
    String operation;
    String type;
    Double timestamp;
    String eventId;
    String marketId;
    String name;
    Boolean displayed;
    Boolean suspended;
    List<Outcome> outcomeList;

    ////////////////////////////
    //Constructors METHODS//////

    public Market() {
    }

    public Market(String[] wordArr) {
        this.msgId= Integer.valueOf(wordArr[1]);
        this.operation=wordArr[2];
        this.type=wordArr[3];
        this.timestamp= Double.valueOf(wordArr[4]);
        this.eventId=wordArr[5];
        this.marketId=wordArr[6];
        this.name=wordArr[7];
        this.displayed= Boolean.valueOf(wordArr[8]);
        this.suspended= Boolean.valueOf(wordArr[9]);
        this.outcomeList= new LinkedList<Outcome>();
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

    public static JSONArray createMarketsJsonArray(List<Market> marketList) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < marketList.size(); i++) {
            jsonArray.put(Market.createMarketJson(marketList.get(i)));
        }
        return jsonArray;
    }

    private static JSONObject createMarketJson(Market market) {
        String logPrefix = classname + "createMarketJson";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msgId",market.getMsgId());
            jsonObject.put("operation",market.getOperation());
            jsonObject.put("type",market.getType());
            jsonObject.put("timestamp",market.getTimestamp());
            jsonObject.put("eventId",market.getEventId());
            jsonObject.put("marketId",market.getMarketId());
            jsonObject.put("name",market.getName());
            jsonObject.put("displayed",market.getDisplayed());
            jsonObject.put("suspended",market.getSuspended());
            jsonObject.put("outcomeList",Outcome.createOutcomesJsonArray(market.getOutcomeList()));
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public List<Outcome> getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(List<Outcome> outcomeList) {
        this.outcomeList = outcomeList;
    }

}
