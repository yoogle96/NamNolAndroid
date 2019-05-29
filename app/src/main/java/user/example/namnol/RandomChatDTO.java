package user.example.namnol;

public class RandomChatDTO {
    private String chat;
    private int currCount;
    private String roomName;
    public RandomChatDTO() {
    }
    public String getChat() {
        return chat;
    }
    public void setChat(String chat) {
        this.chat = chat;
    }
    public int getCurrCount() {
        return currCount;
    }
    public void setCurrCount(int currCount) {
        this.currCount = currCount;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
