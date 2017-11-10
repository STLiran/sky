package DataStructures;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Event {

    private static String classname = Event.class.getSimpleName();

    Integer msgId;
    String operation;
    String type;
    Double timestamp;
    String eventId;
    String category;
    String subCategory;
    String name;
    Double startTime;
    boolean displayed;
    boolean suspended;
    List<Market> marketList;

    ////////////////////////////
    //Constructors METHODS//////
    ////////////////////////////

    public Event() {
    }

    public Event(String[] wordArr) {
        this.msgId = Integer.valueOf(wordArr[1]);
        this.operation = wordArr[2];
        this.type = wordArr[3];
        this.timestamp = Double.valueOf(wordArr[4]);
        this.eventId = wordArr[5];
        this.category = wordArr[6];
        this.subCategory = wordArr[7];
        this.name = wordArr[8];
        this.startTime = Double.valueOf(wordArr[9]);
        this.displayed = Boolean.parseBoolean(wordArr[10]);
        this.suspended = Boolean.parseBoolean(wordArr[11]);
        this.marketList = new LinkedList<Market>();
    }

////////////////////////////
///////HELPER METHODS///////
////////////////////////////

    public boolean isEmpty() {
        String logPrefix = classname + "isEmpty";
        try {
            if (null == this.getMsgId() || "".equals(this.getMsgId())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(logPrefix + "Couldn't check if isnt Empty, err:" + e.getMessage());
            return true;
        }
    }

    public static JSONObject toJson(Event event) {
        String logPrefix = classname + "toJson";
        JSONObject obj = new JSONObject();
        try {
            obj.put("msgId",event.getMsgId());
            obj.put("operation",event.getOperation());
            obj.put("type",event.getType());
            obj.put("timestamp",event.getTimestamp());
            obj.put("eventId",event.getEventId());
            obj.put("category",event.getCategory());
            obj.put("subCategory",event.getSubCategory());
            obj.put("name",event.getName());
            obj.put("startTime",event.getStartTime());
            obj.put("displayed",event.isDisplayed());
            obj.put("suspended",event.isSuspended());
            obj.put("marketList",Market.createMarketsJsonArray(event.getMarketList()));
        } catch (JSONException e) {
            System.out.println(logPrefix+ "Error:" + e.getMessage());
        }
        return obj;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public List<Market> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<Market> marketList) {
        this.marketList = marketList;
    }


}
