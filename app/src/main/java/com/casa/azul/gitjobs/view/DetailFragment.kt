package com.casa.azul.gitjobs.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.casa.azul.dogs.viewmodel.ListViewModel
import com.casa.azul.gitjobs.R
import kotlinx.android.synthetic.main.fragment_detail.*

private const val TAG = "DetailFragment"
class DetailFragment : Fragment() {

    private var gitJobNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            gitJobNumber = DetailFragmentArgs.fromBundle(it).uuid
        }

        val viewModel = activity?.run {
            ViewModelProviders.of(this)[ListViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        val gitJob = viewModel.getDetailJobInfo(gitJobNumber)
        webView.loadData(gitJob?.description.toString(), "text/html; charset=UTF-8", null)

    }


}
