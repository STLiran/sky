package DataStructures;

public enum Type {
    EVENT("event"),
    MARKET("market"),
    OUTCOME("outcome");

    private static String classname = Event.class.getSimpleName();

    private String type;

    Type(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Type getTypeByVal(String op) {
        String logPrefix = classname + "main";
        if (op.equals("event")) {
            return Type.EVENT;
        }
        if (op.equals("outcome")) {
            return Type.OUTCOME;
        }
        if (op.equals("market")) {
            return Type.MARKET;
        }
        System.out.println(logPrefix + "Unknown type");
        return null;
    }
}
