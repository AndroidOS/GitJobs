package com.casa.azul.gitjobs.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.casa.azul.dogs.viewmodel.ListViewModel

import com.casa.azul.gitjobs.R
import kotlinx.android.synthetic.main.fragment_list.*


private const val TAG = "ListFragment"
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val gitListAdapter = GitjobsListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        gitList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = gitListAdapter
            observeViewModel()
        }


    }

    fun observeViewModel() {
        viewModel.gitJobs.observe(this, Observer { jobs ->
            jobs?.let {
                gitList.visibility = View.VISIBLE
                gitListAdapter.updateGitlist(jobs)
                Log.d(TAG,"Job count = ${jobs.size}")
            }
        })

    }
}
