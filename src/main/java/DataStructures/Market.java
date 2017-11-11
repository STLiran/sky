package DataStructures;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Market {

    private static String classname = Market.class.getSimpleName();

    private Integer msgId;
    private String operation;
    private String type;
    private Double timestamp;
    private String eventId;
    private String marketId;
    private String name;
    private Boolean displayed;
    private Boolean suspended;
    private List<Outcome> outcomeList;

    ////////////////////////////
    ///////  Constructors //////
    ////////////////////////////

    public Market() {
        this.outcomeList= new LinkedList<>();
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
        this.outcomeList= new LinkedList<>();
    }

    ////////////////////////////
///////HELPER METHODS///////
////////////////////////////


    public boolean isEmpty() {
        String logPrefix = classname + ":isEmpty:";
        try{
            return null == this.getMsgId();
        }catch (Exception e){
            System.out.println(logPrefix + "Couldn't check if isnt Empty, err:" + e.getMessage());
            return true;
        }
    }

    static JSONArray createMarketsJsonArray(List<Market> marketList) {
        JSONArray jsonArray = new JSONArray();
        marketList.forEach(market->jsonArray.put(Market.createMarketJson(market)));
        return jsonArray;
    }

    private static JSONObject createMarketJson(Market market) {
        String logPrefix = classname + ":createMarketJson:";
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

    private String getEventId() {
        return eventId;
    }

    private String getMarketId() {
        return marketId;
    }

    private String getName() {
        return name;
    }

    private Boolean getDisplayed() {
        return displayed;
    }

    private Boolean getSuspended() {
        return suspended;
    }

    public List<Outcome> getOutcomeList() {
        return outcomeList;
    }

}
