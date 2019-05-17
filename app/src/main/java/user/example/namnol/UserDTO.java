package user.example.namnol;

public class UserDTO {
    public String id;
    public String password;
    public String passwordConfirm;

    public UserDTO(String id, String password, String passwordConfirm){
        this.id = id;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
