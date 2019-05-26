package user.example.namnol;

public class UserDTO {
    public String username;
    public String email;
    public String password1;
    public String password2;
    public String password;

    // 회원가입 DTO
    public UserDTO(String username, String email, String password1, String password2){
        this.username = username;
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
    }

    // 로그인 DTO
    public UserDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}
