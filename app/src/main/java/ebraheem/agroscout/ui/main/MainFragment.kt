package ebraheem.agroscout.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ebraheem.agroscout.data.model.Post
import ebraheem.agroscout.data.result.Result
import ebraheem.agroscout.databinding.MainFragmentBinding
import ebraheem.agroscout.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding

    private var postsAdapter = PostsAdapter(::onPostClick)

    private var listener: MainFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun onPostClick(post: Post) {
        listener?.onMainFragmentPostClick(postId = post.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.homePosts.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.swipeToRefresh.isRefreshing = false
                    postsAdapter.dataList = it.data
                }
                is Result.Error -> {
                    binding.swipeToRefresh.isRefreshing = false
                    Timber.e(it.exception)
                }
                is Result.Loading -> {
                    binding.swipeToRefresh.isRefreshing = true
                }
            }

        }
        viewModel.loadHomePosts()
        initViews()
    }

    private fun initViews() {
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.loadHomePosts()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postsAdapter
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

interface MainFragmentListener {
    fun onMainFragmentPostClick(postId: String)
}