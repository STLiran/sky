import DataStructures.Event;
import DataStructures.Market;
import DataStructures.Outcome;
import com.mongodb.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

class UnitT {
    private final static String classname = UnitT.class.getSimpleName();

    @Test
    void addEmptyEventTest() {
        String logPrefix = classname + ":addEmptyEventTest:";
        try {
            assert SkyBetSol.saveEventToMongo(new Event(), SkyBetSol.mongoHost, SkyBetSol.mongoPort);
        } catch (NullPointerException e) {
            System.out.println(logPrefix + "Success");
        }
    }

    @Test
    void addNonEmptyEventTest() {
        Event ev = new Event();
        Market market = new Market();
        market.getOutcomeList().add(new Outcome());
        ev.getMarketList().add(market);

        assert SkyBetSol.saveEventToMongo(ev, SkyBetSol.mongoHost, SkyBetSol.mongoPort);
    }

    @Test
    void jsonCreationTest() {
        String logPrefix = classname + ":jsonCreationTest:";
        try {
            JSONObject json1 = Event.toJson(createTestEvent());
            JSONObject json2 = new JSONObject(initTestingJson());
            assert compareJsonEvents(json1, json2);
        } catch (JSONException e) {
            System.out.println(logPrefix + "Error:" + e.getMessage());
        }
    }


    @Test
    void saveEventToMongoTest() {
        String logPrefix = classname + ":saveEventToMongoTest:";
        assert SkyBetSol.saveEventToMongo(createTestEvent(), SkyBetSol.mongoHost, SkyBetSol.mongoPort);
        try {
            Mongo mongo = new Mongo(SkyBetSol.mongoHost, SkyBetSol.mongoPort);
            DB db = mongo.getDB(SkyBetSol.dbName);
            DBCollection collection = db.getCollection(SkyBetSol.collectionName);

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("msgId", 77);
            DBCursor cursor = collection.find(whereQuery);
            while (cursor.hasNext()) {
                try {
                    JSONObject curr = new JSONObject(cursor.next().toString());
                    assert compareJsonEvents(curr, Event.toJson(createTestEvent()));
                } catch (JSONException e) {
                    System.out.println(logPrefix + "Error:" + e.getMessage());
                }
            }
        } catch (UnknownHostException e) {
            System.out.println(logPrefix + "Error:" + e.getMessage());
        }
    }

    ////////////////////////////
    ///////HELPER METHODS///////
    ////////////////////////////

    private Event createTestEvent() {
        Event ev = new Event();
        ev.setMsgId(77);
        ev.setOperation("create");
        ev.setType("event");
        ev.setTimestamp(1.510395118988E12);
        ev.setEventId("fee9a40d-ce66-4e80-af4d-ed7e56f1fa74");
        ev.setCategory("Football");
        ev.setSubCategory("Premier League");
        ev.setName("Watford vs Manchester Utd");
        ev.setStartTime(1.510393763899E12);
        ev.setDisplayed(false);
        ev.setSuspended(false);
        return ev;
    }

    private boolean compareJsonEvents(JSONObject json1, JSONObject json2) {
        String logPrefix = classname + ":compareJsonEvents:";
        try {
            boolean res = json1.get("msgId").equals(json2.get("msgId"));
            res &= json1.get("operation").equals(json2.get("operation"));
            res &= json1.get("type").equals(json2.get("type"));
            res &= json1.get("timestamp").equals(json2.get("timestamp"));
            res &= json1.get("eventId").equals(json2.get("eventId"));
            res &= json1.get("category").equals(json2.get("category"));
            res &= json1.get("subCategory").equals(json2.get("subCategory"));
            res &= json1.get("name").equals(json2.get("name"));
            res &= json1.get("startTime").equals(json2.get("startTime"));
            res &= json1.get("displayed").equals(json2.get("displayed"));
            res &= json1.get("suspended").equals(json2.get("suspended"));
            return res;
        } catch (JSONException e) {
            System.out.println(logPrefix + "Error:" + e.getMessage());
        }
        return false;
    }

    private String initTestingJson() {
        return "{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\"," +
                "\"subCategory\":\"Premier League\",\"name\":\"Watford vs Manchester Utd\"," +
                "\"msgId\":77,\"startTime\":1.510393763899E12,\"type\":\"event\",\"category\":\"Football\"," +
                "\"operation\":\"create\",\"suspended\":false,\"marketList\"" +
                ":[{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\"," +
                "\"name\":\"Full Time Result\",\"outcomeList\":[{\"displayed\":false,\"price\":\"5/1\",\"name\":" +
                "\"ba6f7204-15ef-4239-b87d-8d9975bff311\",\"msgId\":79,\"outcomeId\":\"Watford\",\"type\":\"outcome\"," +
                "\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12,\"marketId\":" +
                "\"8a0ba31a-b03e-47d8-bb83-3a4ff420b9b4\"},{\"displayed\":false,\"price\":\"7/4\",\"name\":" +
                "\"f29b84a3-2948-4a08-acd7-44b4323d8af3\",\"msgId\":80,\"outcomeId\":\"Draw\",\"type\":\"outcome\"," +
                "\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12,\"marketId\":" +
                "\"8a0ba31a-b03e-47d8-bb83-3a4ff420b9b4\"},{\"displayed\":false,\"price\":\"4/1\",\"name\":" +
                "\"a9f45503-02d1-40db-82d2-f51f6001984d\",\"msgId\":81,\"outcomeId\":\"Manchester Utd\",\"type\":" +
                "\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12,\"marketId\":\"8a0ba31a-b03e-47d8-bb83-3a4ff420b9b4\"}]," +
                "\"msgId\":78,\"type\":\"market\",\"operation\":\"create\",\"suspended\":false," +
                "\"timestamp\":1.510395118989E12,\"marketId\":\"8a0ba31a-b03e-47d8-bb83-3a4ff420b9b4\"}," +
                "{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\",\"name\":\"Both Teams To Score\"," +
                "\"outcomeList\":[{\"displayed\":false,\"price\":\"2/7\",\"name\":\"d1e577e4-2478-48d8-90d2-a934703b28d2\"," +
                "\"msgId\":83,\"outcomeId\":\"Yes\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12," +
                "\"marketId\":\"2f30a8f4-c5c6-46f0-8b55-fc18ad042e41\"},{\"displayed\":false,\"price\":\"20/1\",\"name\":\"1e1a3686-1952-47eb-b7e3-62550490a290\"," +
                "\"msgId\":84,\"outcomeId\":\"No\",\"type\":\"outcome\",\"operation\":\"create\"," +
                "\"suspended\":false,\"timestamp\":1.510395118989E12,\"marketId\":" +
                "\"2f30a8f4-c5c6-46f0-8b55-fc18ad042e41\"}],\"msgId\":82,\"type\":\"market\"," +
                "\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12," +
                "\"marketId\":\"2f30a8f4-c5c6-46f0-8b55-fc18ad042e41\"},{\"displayed\":false," +
                "\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\",\"name\":\"Half Time Result\"," +
                "\"outcomeList\":[{\"displayed\":false,\"price\":\"2/1\"," +
                "\"name\":\"8688ea1c-e090-4bb0-a3f5-3fdd515c4129\",\"msgId\":86,\"outcomeId\":\"Watford\"," +
                "\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false," +
                "\"timestamp\":1.510395118989E12,\"marketId\":\"0af96f47-0a75-4236-9bb7-0cf28cf1dc97\"}," +
                "{\"displayed\":false,\"price\":\"4/11\",\"name\":\"f591df8d-9743-4fd4-a2fa-65e69b75cb5d\"," +
                "\"msgId\":87,\"outcomeId\":\"Draw\",\"type\":\"outcome\",\"operation\":\"create\"," +
                "\"suspended\":false,\"timestamp\":1.510395118989E12,\"marketId\":\"0af96f47-0a75-4236-9bb7-0cf28cf1dc97\"}," +
                "{\"displayed\":false,\"price\":\"1/1\",\"name\":\"0b2d7be5-b361-4fa2-bf6d-040bb612efd9\",\"msgId\":88," +
                "\"outcomeId\":\"Manchester Utd\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false," +
                "\"timestamp\":1.510395118989E12,\"marketId\":\"0af96f47-0a75-4236-9bb7-0cf28cf1dc97\"}],\"msgId\":85," +
                "\"type\":\"market\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12," +
                "\"marketId\":\"0af96f47-0a75-4236-9bb7-0cf28cf1dc97\"},{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\"," +
                "\"name\":\"Correct Score\",\"outcomeList\":[{\"displayed\":false,\"price\":\"1/4\",\"name\":\"44eb2812-03b0-44bd-8d60-ed7653c0959a\"," +
                "\"msgId\":90,\"outcomeId\":\"Watford 1-0\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12," +
                "\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"price\":\"2/7\",\"name\":\"56190873-871e-4327-a3fb-8397c59aee37\"," +
                "\"msgId\":91,\"outcomeId\":\"Draw 0-0\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":" +
                "\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"price\":\"13/8\",\"name\":\"db3bf631-de66-4b9c-a829-3bb3165c4377\",\"msgId\":92,\"outcomeId\":" +
                "\"Manchester Utd 0-1\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"}" +
                ",{\"displayed\":false,\"price\":\"33/1\",\"name\":\"2b57897b-c052-48ff-8bd4-5686c75d4152\",\"msgId\":93,\"outcomeId\":\"Watford 2-0\",\"type\":\"outcome\",\"operation\"" +
                ":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"price\":\"6/5\",\"name\":" +
                "\"0f1cd26c-75eb-41cf-ad7a-fe4398df3487\",\"msgId\":94,\"outcomeId\":\"Draw 1-1\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\"" +
                ":1.51039511899E12,\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"price\":\"8/13\",\"name\":\"eade4f71-918e-4b7a-9408-4af10d68ec8e\"," +
                "\"msgId\":95,\"outcomeId\":\"Manchester Utd 0-2\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":" +
                "\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"price\":\"1/4\",\"name\":\"2e7533f0-fad2-436e-b9eb-e263cdb935d5\",\"msgId\":96,\"outcomeId\":" +
                "\"Watford 3-0\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"}," +
                "{\"displayed\":false,\"price\":\"1/6\",\"name\":\"33117adf-dda7-47e5-98d2-445fff12a041\",\"msgId\":97,\"outcomeId\":\"Draw 2-2\",\"type\":\"outcome\",\"operation\":\"create\"," +
                "\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"price\":\"1/6\",\"name\":\"fc412fdc-7f62-4768-abf6-0e1791fbd23b\"," +
                "\"msgId\":98,\"outcomeId\":\"Manchester Utd 0-3\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"}]," +
                "\"msgId\":89,\"type\":\"market\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.510395118989E12," +
                "\"marketId\":\"cbce430e-0d11-48f0-b5cf-5ebb60cbe236\"},{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\",\"name\":\"Goal Handicap (-1)\"," +
                "\"outcomeList\":[{\"displayed\":false,\"price\":\"1/12\",\"name\":\"3dd83e56-3b8a-4724-a806-466a9ce95961\",\"msgId\":100,\"outcomeId\":\"Watford -1\",\"type\":\"outcome\"," +
                "\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"0da5d2df-9827-4578-abde-a436e428f5e8\"},{\"displayed\":false,\"price\":\"9/4\",\"name\"" +
                ":\"6791b6f1-9a10-41c1-bf56-8f506bb591c7\",\"msgId\":101,\"outcomeId\":\"Handicap Draw -1\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12," +
                "\"marketId\":\"0da5d2df-9827-4578-abde-a436e428f5e8\"},{\"displayed\":false,\"price\":\"11/4\",\"name\":\"befcfa1a-0451-4406-9187-45e37be34501\",\"msgId\":102,\"outcomeId\":\"Manchester Utd +1\"," +
                "\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":" +
                "\"0da5d2df-9827-4578-abde-a436e428f5e8\"}],\"msgId\":99,\"type\":\"market\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":" +
                "\"0da5d2df-9827-4578-abde-a436e428f5e8\"},{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\",\"name\":\"Goal Handicap (-2)\",\"outcomeList\":" +
                "[{\"displayed\":false,\"price\":\"16/1\",\"name\":\"c1186bf2-fd5c-4eb5-8487-ef1f3b15c896\",\"msgId\":104,\"outcomeId\":\"Watford -2\",\"type\":\"outcome\",\"operation\"" +
                ":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"0a32ca11-f7f4-487f-a193-483d1acc4048\"},{\"displayed\":false,\"price\":\"1/500\",\"name\"" +
                ":\"e1e4df52-33c5-42c8-b3c6-6637afbd3603\",\"msgId\":105,\"outcomeId\":\"Handicap Draw -2\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\"" +
                ":1.51039511899E12,\"marketId\":\"0a32ca11-f7f4-487f-a193-483d1acc4048\"},{\"displayed\":false,\"price\":\"1/4\",\"name\":\"43c95b17-3390-4c8a-a055-6a59224361e3\",\"msgId\":106," +
                "\"outcomeId\":\"Manchester Utd +2\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"0a32ca11-f7f4-487f-a193-483d1acc4048\"}]," +
                "\"msgId\":103,\"type\":\"market\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"0a32ca11-f7f4-487f-a193-483d1acc4048\"},{\"displayed\":false,\"eventId\":" +
                "\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\",\"name\":\"Goal Handicap (+1)\",\"outcomeList\":[{\"displayed\":false,\"price\":\"10/3\",\"name\":\"6c2d6104-c2bf-4cfa-a92f-022963e27d00\"," +
                "\"msgId\":108,\"outcomeId\":\"Watford +1\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"b4859d38-0fec-4d10-a981-b744c2d4aea3\"" +
                "},{\"displayed\":false,\"price\":\"1/33\",\"name\":\"a81776f5-7b98-4eb6-b12b-41a62d438c25\",\"msgId\":109,\"outcomeId\":\"Handicap Draw +1\",\"type\":\"outcome\",\"operation\":\"create\"," +
                "\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"b4859d38-0fec-4d10-a981-b744c2d4aea3\"},{\"displayed\":false,\"price\":\"1/8\",\"name\":\"4315d91e-1e09-4eb4-8f04-f793c13856e8\"," +
                "\"msgId\":110,\"outcomeId\":\"Manchester Utd -1\",\"type\":\"outcome\",\"operation\":\"create\"," +
                "\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"b4859d38-0fec-4d10-a981-b744c2d4aea3\"}]," +
                "\"msgId\":107,\"type\":\"market\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12," +
                "\"marketId\":\"b4859d38-0fec-4d10-a981-b744c2d4aea3\"},{\"displayed\":false,\"eventId\":\"fee9a40d-ce66-4e80-af4d-ed7e56f1fa74\"," +
                "\"name\":\"Goal Handicap (+2)\",\"outcomeList\":[{\"displayed\":false,\"price\":\"1/10\",\"name\":\"542f36cd-d776-4694-ac18-0bfb1fb82162\"," +
                "\"msgId\":112,\"outcomeId\":\"Watford +2\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12," +
                "\"marketId\":\"94638c09-e11a-41e1-bbb0-56e7fe5808f6\"},{\"displayed\":false,\"price\":\"20/1\",\"name\":\"9b9d485e-4ed7-47a6-8dd7-22d4090aaacf\"," +
                "\"msgId\":113,\"outcomeId\":\"Handicap Draw +2\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12," +
                "\"marketId\":\"94638c09-e11a-41e1-bbb0-56e7fe5808f6\"},{\"displayed\":false,\"price\":\"1/10\",\"name\":\"5f558a74-fbb1-4314-b650-10cd967b486d\"," +
                "\"msgId\":114,\"outcomeId\":\"Manchester Utd -2\",\"type\":\"outcome\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12," +
                "\"marketId\":\"94638c09-e11a-41e1-bbb0-56e7fe5808f6\"}],\"msgId\":111,\"type\":\"market\",\"operation\":\"create\",\"suspended\":false,\"timestamp\":1.51039511899E12,\"marketId\":\"" +
                "94638c09-e11a-41e1-bbb0-56e7fe5808f6\"}],\"timestamp\":1.510395118988E12}";
    }
}
