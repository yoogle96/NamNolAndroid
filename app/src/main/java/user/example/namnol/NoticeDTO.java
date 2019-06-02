package user.example.namnol;

public class NoticeDTO {
    private String title;
    private String writer;
    private String pub_date;
    private String img;
    private String content;

    NoticeDTO(String title, String writer, String pub_date, String img, String content){
        this.title = title;
        this.writer = writer;
        this.pub_date = pub_date;
        this.img = img;
        this.content = content;
    }

    NoticeDTO(String title, String writer, String pub_date){
        this.title = title;
        this.writer = writer;
        this.pub_date = pub_date;
    }

    public String getTitle(){
        return title;
    }

    public String getWriter(){
        return writer;
    }

    public String getPub_date(){
        return pub_date;
    }

    public String getImg(){
        return img;
    }

    public String getContent(){
        return content;
    }
}
