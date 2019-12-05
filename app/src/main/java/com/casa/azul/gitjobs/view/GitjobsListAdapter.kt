package com.casa.azul.gitjobs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.casa.azul.gitjobs.R


import com.casa.azul.gitjobs.model.GitJob
import kotlinx.android.synthetic.main.gitjobs_item.view.*


class GitjobsListAdapter(val gitJobList: ArrayList<GitJob>): RecyclerView.Adapter<GitjobsListAdapter.GitJobsViewHolder>() {

    fun updateGitlist(newGitJobsList: List<GitJob>){
        gitJobList.clear()
        gitJobList.addAll(newGitJobsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitJobsViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(
            R.layout.gitjobs_item
            , parent, false)
        return GitJobsViewHolder(view)
    }

    override fun getItemCount() = gitJobList.size

    override fun onBindViewHolder(holder: GitJobsViewHolder, position: Int) {
        holder.view.txt_title.text = gitJobList[position].title
        holder.view.txt_company.text = gitJobList[position].company

        val uuid = position
        holder.view.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(uuid))
        }

    }

    class GitJobsViewHolder(var view: View): RecyclerView.ViewHolder(view)
}