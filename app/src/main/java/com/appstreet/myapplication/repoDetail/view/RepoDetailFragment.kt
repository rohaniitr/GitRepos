package com.appstreet.myapplication.repoDetail.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.appstreet.myapplication.R
import com.appstreet.myapplication.base.BaseFragment
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.repoList.model.data.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailFragment : BaseFragment() {
    companion object {
        private const val BUNDLE_GIT_REPO = "bundle_git_repo"

        fun getBundle(gitRepo: GitRepo) = Bundle().apply {
            putSerializable(BUNDLE_GIT_REPO, gitRepo)
        }
    }

    private lateinit var repo: GitRepo

    override fun getLayoutId() = R.layout.fragment_repo_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = arguments!!.getSerializable(BUNDLE_GIT_REPO) as GitRepo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeader()
        setUi()
    }

    private fun setHeader() {
        title.text = repo.name
        Glide.with(context!!)
            .load(repo.avatar)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(title_image)

        val clickListener = View.OnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(repo.repoUrl))
            ContextCompat.startActivity(it.context, browserIntent, null)
        }
        title_image.setOnClickListener(clickListener)
        title.setOnClickListener(clickListener)
    }

    private fun setUi() {
        author_name.text = getString(R.string.author_name, repo.author)

        if (repo.description.isNotEmpty()) {
            description.text = getString(R.string.quoted_text, repo.description)
            description.visibility = View.VISIBLE
        } else {
            description.visibility = View.GONE
        }

        if (repo.language.isNotEmpty()) {
            language.text = getString(R.string.language_name, repo.language)
            language.visibility = View.VISIBLE
        } else {
            language.visibility = View.GONE
        }

        if (repo.forks.isNotEmpty()) {
            fork_count.text = getString(R.string.forks_count, repo.forks)
            fork_count.visibility = View.VISIBLE
        } else {
            fork_count.visibility = View.GONE
        }

        if (repo.stars.isNotEmpty()) {
            star_count.text = getString(R.string.stars_count, repo.stars)
            star_count.visibility = View.VISIBLE
        } else {
            star_count.visibility = View.GONE
        }

        if (repo.collaborators.isNullOrEmpty()) {
            collaborator_text.visibility = View.GONE
            recycler_view.visibility = View.GONE
        } else {
            collaborator_text.visibility = View.VISIBLE
            recycler_view.visibility = View.VISIBLE

            val adapter = CollaboratorAdapter(repo.collaborators!!, ::onCollaboratorClick)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = adapter

            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            recycler_view.addItemDecoration(decoration)
        }
    }

    private fun onCollaboratorClick(collaborator: User) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(collaborator.userProfile))
        ContextCompat.startActivity(context!!, browserIntent, null)
    }
}