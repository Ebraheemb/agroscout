package ebraheem.agroscout.ui.main

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
class MainViewModel @Inject constructor(private var dataRepository: DataRepository) : ViewModel() {

    val homePosts: MutableLiveData<Result<List<Post>>> = MutableLiveData()

    fun loadHomePosts() {
        viewModelScope.launch {
            dataRepository.homePosts().collect {
                homePosts.postValue(it)
            }
        }

    }
}