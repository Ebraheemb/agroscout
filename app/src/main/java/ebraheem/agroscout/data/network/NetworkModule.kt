package ebraheem.agroscout.data.network

import android.content.Context
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ebraheem.agroscout.app.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@Named("ChuckInterceptor") chuckInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(chuckInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()



    @Provides
    @Named("ChuckInterceptor")
    fun provideChuckInterceptor(@ApplicationContext context: Context): Interceptor =
        ChuckInterceptor(context)


    @Singleton
    @Provides
    fun getClient(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Routes = retrofit.create(Routes::class.java)
}