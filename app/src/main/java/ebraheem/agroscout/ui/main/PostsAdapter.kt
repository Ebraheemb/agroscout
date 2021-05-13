package ebraheem.agroscout.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ebraheem.agroscout.R
import ebraheem.agroscout.data.model.Post
import ebraheem.agroscout.databinding.VhItemPostBinding

class PostsAdapter(var onPostClick: (Post) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {

    var dataList: List<Post> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data,onPostClick)
    }

    override fun getItemCount(): Int = dataList.size
}


class PostViewHolder(var binding: VhItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Post, onPostClick: (Post) -> Unit) {
        binding.titleTextView.text = data.title

        binding.authorName.text = data.author

        Glide.with(binding.authorIcon)
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.default_profile_icon))
            .load(data.icon_url)
            .into(binding.authorIcon)

        Glide.with(binding.postImage).load(data.url).into(binding.postImage)

        itemView.setOnClickListener {
            onPostClick(data)
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            PostViewHolder(VhItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}
