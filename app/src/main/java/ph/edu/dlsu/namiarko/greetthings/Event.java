package ph.edu.dlsu.namiarko.greetthings;

public class Event {

    public static final String TABLE_NAME = "events_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_RECIPIENT = "recipient";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_REPEAT = "repeat";

    private long id;
    private String title;
    private String date;
    private String time;
    private String recipient;   // may instance kasi na yung number is may +63 so String??
    private String message;
    private String repeat;      // daily, monthly, yearly, none



    public Event() { }

    public Event(String title, String date, String time, String recipient,
                 String message, String repeat) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.recipient = recipient;
        this.message = message;
        this.repeat = repeat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

}
