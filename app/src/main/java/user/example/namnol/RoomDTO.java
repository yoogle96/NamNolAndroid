package user.example.namnol;

public class RoomDTO {
    private String title;
    private String kind;
    private String curr;
    private String key;

    RoomDTO(String title, String kind, String curr, String key){
        this.title = title;
        this.kind = kind;
        this.curr = curr;
        this.key = key;
    }

    public String getTitle(){
        return title;
    }

    public String getKind(){
        return kind;
    }

    public String getCurr(){
        return curr;
    }

    public String getKey(){
        return key;
    }
}
