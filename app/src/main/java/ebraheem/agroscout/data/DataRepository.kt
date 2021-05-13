package ebraheem.agroscout.data

import android.content.Context
import ebraheem.agroscout.data.model.Post
import ebraheem.agroscout.data.network.Routes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ebraheem.agroscout.data.result.Result
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

interface DataRepository {

    fun homePosts() : Flow<Result<List<Post>>>
    fun post(postId: String) : Flow<Result<Post>>
}

class DataRepositoryImpl(var context: Context, var apiService: Routes) : DataRepository{

    private var postsHashMap: ConcurrentHashMap<String,Post> = ConcurrentHashMap()

    override fun homePosts(): Flow<Result<List<Post>>> =  flow {
        emit(Result.Loading)

        val response = apiService.homePosts()
        if(response.isSuccessful) {
            response.body()?.let {
                val posts =it.data.children.map { data ->
                    data.data
                }
                saveToHashmap(posts)
                emit(Result.Success(posts))
            }
        } else {
            emit(Result.Error(Exception("some error occurred")))// TODO
        }
    }

    private fun saveToHashmap(posts: List<Post>) {
        posts.forEach {
            postsHashMap[it.id] = it
        }
    }

    override fun post(postId: String): Flow<Result<Post>> =  flow {
        emit(Result.Loading)

        val post = postsHashMap[postId]
        if( post != null) {
//            apiService.getPost("${post.permalink}.json") // TODO
            emit(Result.Success(post))
        } else {
            emit(Result.Error(Exception("error")))
        }
    }

}