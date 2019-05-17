package user.example.namnol;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/rest-auth/registration/")
    Call<UserDTO> createUser(@Body UserDTO userDTO);

}
