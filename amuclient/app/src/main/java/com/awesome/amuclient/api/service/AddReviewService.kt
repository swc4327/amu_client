
import com.awesome.amuclient.api.response.DefaultResponse
import com.awesome.amuclient.model.Review
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddReviewService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/addReview")
    fun addReview(@Body params: Review) : Call<DefaultResponse>
}