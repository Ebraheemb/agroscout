package ebraheem.agroscout.data.network

import ebraheem.agroscout.data.model.Data
import ebraheem.agroscout.data.model.HomeData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface Routes {

    @GET("/.json")
    suspend fun homePosts(): Response<Data<HomeData>>

    @GET
    suspend fun getPost(@Url url: String): Response<ResponseBody>
}