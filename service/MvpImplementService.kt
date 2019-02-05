package mvp.implement.kotlin.service

import mvp.implement.kotlin.Model.Claimed
import mvp.implement.kotlin.Model.Experience
import mvp.implement.kotlin.Model.UserExperience
import mvp.implement.kotlin.common.Global
import mvp.implement.kotlin.service.DTO.*
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.Response
import org.json.JSONObject
import retrofit2.http.*

interface MvpImplementService {

    @Headers("Content-Type: application/json")
    @POST("auth/")
    fun getAuth(@Body login: LoginDTO) : Observable<AuthDTO>

    @GET("api/app/testeCars/")
    fun getCar(@Query("id_user") id: String, @Query("profile_type") profile: String) : Observable<CarDTO>
}
