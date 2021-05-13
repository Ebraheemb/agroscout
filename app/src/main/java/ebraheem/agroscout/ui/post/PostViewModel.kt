package ebraheem.agroscout.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ebraheem.agroscout.data.DataRepository
import ebraheem.agroscout.data.model.Post
import ebraheem.agroscout.data.result.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private var dataRepository: DataRepository) : ViewModel() {

    val postLiveData : MutableLiveData<Result<Post>> = MutableLiveData()

    fun loadPost(postId: String) {
        viewModelScope.launch {
            dataRepository.post(postId).collect {
                postLiveData.postValue(it)
            }
        }
    }

}