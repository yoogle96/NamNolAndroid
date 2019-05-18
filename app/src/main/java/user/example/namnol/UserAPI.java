package user.example.namnol;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    // 회원가입
    @POST("/rest-auth/registration/")
    Call<UserDTO> createUser(@Body UserDTO userDTO);

    // 로그인
    @POST("/rest-auth/login/")
    Call<UserDTO> signinUser(@Body UserDTO userDTO);

}
