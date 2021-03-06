package com.casa.azul.gitjobs.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.casa.azul.dogs.viewmodel.ListViewModel
import com.casa.azul.gitjobs.R
import com.casa.azul.gitjobs.model.GitJob
import kotlinx.android.synthetic.main.fragment_detail.*

private const val TAG = "DetailFragment"
class DetailFragment : Fragment() {

    private var gitJobNumber = 0
    private lateinit var viewModel: ListViewModel
    private lateinit var listener: Listener
    private lateinit var gitJob: GitJob

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

        viewModel = activity?.run {
            ViewModelProviders.of(this)[ListViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        gitJob = viewModel.getDetailJobInfo(gitJobNumber)!!
        webView.loadData(gitJob.description.toString(), "text/html; charset=UTF-8", null)

        observeViewModel()
        listener.unHideFAB(true)
    }

    private fun observeViewModel() {

        viewModel.menu_email.observe(this, Observer { isEmail ->
            isEmail?.let {

                if (it) {
                    sendEmail()
                    //Toast.makeText(activity, "Menu selection", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun sendEmail() {

        val body = gitJob.description
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/html"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, gitJob.title)
        //shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body))
        startActivity(Intent.createChooser(shareIntent, "GitJobs"))
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement Listener")

        }
    }

    override fun onDetach() {
        super.onDetach()
        listener.unHideFAB(false)
    }


}

internal interface Listener {
    fun unHideFAB(b: Boolean)
}


