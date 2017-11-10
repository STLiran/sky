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

public class Main {

    private static String classname = Main.class.getSimpleName();

    public static void main(String[] args) {
        String logPrefix = classname + "main";
        System.out.println(logPrefix + "Starting");
        try {
            Socket socket = new Socket("localhost", 8282);
            Scanner scan = new Scanner(socket.getInputStream());
            scanToMongo(scan);
        } catch (Exception e) {
            System.out.println(logPrefix + "Couldn't get the data stream, err:" + e.getMessage());
        }
        System.out.println(logPrefix + "Finished");
    }

    private static void scanToMongo(Scanner scan) {
        String logPrefix = classname + "scanToMongo";

        //init the data temporary structures:
        Event eventObj = new Event();
        Market marketObj = new Market();
        boolean isFirstTime = true;

        //read the input stream:
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            line = line.replace("\\|", "");
            String[] wordArr = line.split("\\|");
            System.out.println(logPrefix + "currently handling fixture with type: " + wordArr[3]);
            switch (Type.getTypeByVal(wordArr[3])) {
                case EVENT:
                    isFirstTime = false;
                    if (!marketObj.isEmpty()) { //only insert full objects
                        eventObj.getMarketList().add(marketObj);
                    }
                    if (!eventObj.isEmpty()) { //prevent saving of the first object
                        saveEventToMongo(eventObj); //save previous event
                    }
                    eventObj = new Event(wordArr);
                    break;
                case MARKET:
                    if (isFirstTime) {
                        break;
                    }
                    if (!marketObj.isEmpty()) { //only insert full objects
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

    private static void saveEventToMongo(Event eventObj) {
        String logPrefix = classname + "saveEventToMongo";
        Mongo mongo = null;
        try {
            mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("admin");
            DBCollection collection = db.getCollection("myCollection");
            DBObject dbObject = (DBObject) JSON.parse(Event.toJson(eventObj).toString());
            collection.insert(dbObject);
        } catch (UnknownHostException e) {
            System.out.println(logPrefix + "Error:" + e.getMessage());
        }
    }
}

