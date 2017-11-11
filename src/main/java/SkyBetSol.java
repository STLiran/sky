import DataStructures.Event;
import DataStructures.Market;
import DataStructures.Outcome;
import DataStructures.Type;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SkyBetSol {

    private final static String classname = SkyBetSol.class.getSimpleName();
    private static final String host = "localhost";
    private static final int port = 8282;
    static final int mongoPort = 27017;
    static final String mongoHost = "localhost";
    static final String collectionName = "myCollection";
    static final String dbName = "admin";


    public static void main(String[] args) {
        String logPrefix = classname + ":main:";
        System.out.println(logPrefix + "Starting");
        SkyBetSol sky = new SkyBetSol();
        sky.scanToMongo(runServer(host, port));
        System.out.println(logPrefix + "Finished");
    }

    private static Scanner runServer(String host, int port) {
        String logPrefix = classname + ":runServer:";
        System.out.println(logPrefix + "Starting");
        try {
            Socket socket = new Socket(host, port);
            return new Scanner(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(logPrefix + "Couldn't get the data stream, err:" + e.getMessage());
        }
        return null;
    }

    private void scanToMongo(Scanner scan) {
        String logPrefix = classname + ":scanToMongo:";

        //init the data temporary structures:
        Event eventObj = new Event();
        Market marketObj = new Market();
        boolean isFirstTime = true; //Because every item is added in the next iteration
        // isFirstTime prevents the first iteration from adding items.

        //read the input stream:
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            line = line.replace("\\|", "");
            String[] wordArr = line.split("\\|");
//            System.out.println(logPrefix + "currently handling fixture with type: " + wordArr[3]);
            switch (Type.getTypeByVal(wordArr[3])) {
                case EVENT:
                    isFirstTime = false;
                    if (marketObj.isNotEmpty()) { //only insert full objects
                        eventObj.getMarketList().add(marketObj);
                    }
                    if (!eventObj.isEmpty()) { //prevent saving of the first object
                        saveEventToMongo(eventObj, mongoHost, mongoPort); //save previous event
                    }
                    eventObj = new Event(wordArr);
                    break;
                case MARKET:
                    if (isFirstTime) {
                        break;
                    }
                    if (marketObj.isNotEmpty()) { //only insert full objects
                        eventObj.getMarketList().add(marketObj); //save previous market
                    }
                    marketObj = new Market(wordArr);
                    break;
                case OUTCOME:
                    if (isFirstTime) {
                        break;
                    }
                    Outcome outcomeObj = new Outcome(wordArr);
                    marketObj.getOutcomeList().add(outcomeObj);
                    break;
                default:
                    System.out.println(logPrefix + "Wrong format type: " + wordArr[3]);
                    break;
            }
        }
    }

    static boolean saveEventToMongo(Event eventObj, String host, int port) {
        String logPrefix = classname + ":saveEventToMongo:";
        Mongo mongo;
        try {
            mongo = new Mongo(host, port);
            DB db = mongo.getDB(dbName);
            DBCollection collection = db.getCollection(collectionName);
            DBObject dbObject = (DBObject) JSON.parse(Event.toJson(eventObj).toString());
            collection.insert(dbObject);
            return true;
        } catch (UnknownHostException e) {
            System.out.println(logPrefix + "Error:" + e.getMessage());
            return false;
        }
    }

}

