package ebraheem.agroscout.ui.post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import ebraheem.agroscout.R
import ebraheem.agroscout.data.model.Post
import ebraheem.agroscout.data.result.Result
import ebraheem.agroscout.databinding.PostFragmentBinding
import ebraheem.agroscout.ui.base.BaseFragment


const val ARG_POST_ID = "post_id"

@AndroidEntryPoint
class PostFragment : BaseFragment() {

    private lateinit var viewModel: PostViewModel

    private lateinit var binding: PostFragmentBinding

    private var listener: PostFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is PostFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PostFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        viewModel.postLiveData.observe(viewLifecycleOwner){
            when (it) {
                is Result.Success -> {
                    binding.swipeToRefresh.isRefreshing = false
                    bindPostWithViews(it.data)
                }
                is Result.Error -> {
                    binding.swipeToRefresh.isRefreshing = false
                }
                is Result.Loading -> {
                    binding.swipeToRefresh.isRefreshing = true
                }
            }
        }
        arguments?.getString(ARG_POST_ID)?.let { postId ->
            viewModel.loadPost(postId)
        }

        initViews()
    }

    private fun bindPostWithViews(post: Post) {
        binding.titleTextView.text = post.title

        binding.authorName.text = post.author

        Glide.with(binding.postImage).load(post.url).into(binding.postImage)

        Glide.with(binding.authorIcon)
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.default_profile_icon))
            .load(post.icon_url)
            .into(binding.authorIcon)

        binding.numberOfComments.text = "${post.num_comments}"

        binding.postLayout.visibility = View.VISIBLE
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            listener?.onPostFragmentBackClick()
        }
        binding.swipeToRefresh.setOnRefreshListener {
            arguments?.getString(ARG_POST_ID)?.let { postId ->
                viewModel.loadPost(postId)
            }

        }
    }

    companion object {
        fun newInstance(postId: String) = PostFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_POST_ID,postId)
            }
        }
    }
}

interface PostFragmentListener {
    fun onPostFragmentBackClick()
}