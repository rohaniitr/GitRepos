package com.appstreet.myapplication.repoDetail.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.appstreet.myapplication.R
import com.appstreet.myapplication.repoList.model.data.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_collaborator.view.*
import kotlin.reflect.KFunction1

class CollaboratorAdapter(
    private val collaborators: List<User>,
    private val onClick: KFunction1<@ParameterName(name = "collaborator") User, Unit>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborator, parent, false)
        return CollaboratorHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CollaboratorHolder)
            holder.bindData(collaborators[position], onClick)
    }

    override fun getItemCount(): Int {
        return collaborators.size
    }

    class CollaboratorHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(
            collaborator: User,
            onClick: KFunction1<@ParameterName(name = "collaborator") User, Unit>
        ) {

            with(itemView) {
                collaborator_name.text = collaborator.userName

                Glide.with(context)
                    .load(collaborator.avatar)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .into(collaborator_image)

                setOnClickListener {
                    onClick(collaborator)
                }
            }
        }
    }
}