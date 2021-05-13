package ebraheem.agroscout.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ebraheem.agroscout.R
import ebraheem.agroscout.app.App
import ebraheem.agroscout.helpers.withTransaction
import ebraheem.agroscout.ui.base.BaseActivity
import ebraheem.agroscout.ui.main.MainFragment
import ebraheem.agroscout.ui.main.MainFragmentListener
import ebraheem.agroscout.ui.post.PostFragment
import ebraheem.agroscout.ui.post.PostFragmentListener

@AndroidEntryPoint
class MainActivity : BaseActivity(), PostFragmentListener, MainFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        withTransaction {
            replace(R.id.container, MainFragment.newInstance())
        }
    }

    override fun onPostFragmentBackClick() {
        onBackPressed()
    }

    override fun onMainFragmentPostClick(postId: String) {
        withTransaction {
            add(R.id.container, PostFragment.newInstance(postId))
                .addToBackStack("PostFragment")
        }
    }
}