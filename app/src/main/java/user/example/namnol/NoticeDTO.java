package user.example.namnol;

public class NoticeDTO {
    private String title;
    private String writer;
    private String date;


    NoticeDTO(String title, String writer, String date){
        this.title = title;
        this.writer = writer;
        this.date = date;
    }

    public String getTitle(){
        return title;
    }

    public String getWriter(){
        return writer;
    }

    public String getPub_date(){
        return date;
    }

}
