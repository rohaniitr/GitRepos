package com.appstreet.myapplication.repoList.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.myapplication.R
import com.appstreet.myapplication.repoList.model.data.GitRepo
import com.appstreet.myapplication.util.imageCache.ImageUtil
import kotlinx.android.synthetic.main.item_repo_list.view.*
import javax.inject.Inject
import kotlin.reflect.KFunction1

class RepoListAdapter @Inject constructor(
    private val repos: List<GitRepo>,
    private val onClick: KFunction1<@ParameterName(name = "repo") GitRepo, Unit>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_repo_list, parent, false)
        return RepoHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepoHolder)
            holder.bindData(repos[position], onClick)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    class RepoHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(
            repo: GitRepo,
            onClick: KFunction1<@ParameterName(name = "repo") GitRepo, Unit>
        ) {

            with(itemView) {
                author_name.text = repo.author
                project_name.text = repo.name

                Log.i("ROHAN", repo.avatar)

                ImageUtil.loadImage(context, profile_pic, repo.avatar)

                setOnClickListener {
                    onClick(repo)
                }
            }
        }
    }
}