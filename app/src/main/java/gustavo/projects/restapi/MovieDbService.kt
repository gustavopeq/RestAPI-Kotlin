package gustavo.projects.restapi

import retrofit2.Call
import retrofit2.http.GET

interface MovieDbService {


    @GET("movie/155?api_key=1d1b00098614494b1604f69cca5ec62d")
    fun getMovieById(): Call<Any>


}