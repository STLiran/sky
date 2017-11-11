package DataStructures;

public enum Type {
    EVENT("event"),
    MARKET("market"),
    OUTCOME("outcome");

    private final static String classname = Event.class.getSimpleName();

    private String type;

    ////////////////////////////
    ///////  Constructors //////
    ////////////////////////////

    Type(String type) {
        this.type = type;
    }

    ////////////////////////////
    ///////HELPER METHODS///////
    ////////////////////////////

    public static Type getTypeByVal(String op) {
        String logPrefix = classname + ":getTypeByVal:";
        switch (op) {
            case "event":
                return Type.EVENT;
            case "outcome":
                return Type.OUTCOME;
            case "market":
                return Type.MARKET;
            default:
                System.out.println(logPrefix + "Unknown type");
                break;
        }
        return null;
    }

    ////////////////////////////
    /////Getters And Setters////
    ////////////////////////////

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
