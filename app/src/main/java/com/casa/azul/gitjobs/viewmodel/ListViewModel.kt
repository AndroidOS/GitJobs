package com.casa.azul.dogs.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.casa.azul.dogs.model.GitApiService
import com.casa.azul.gitjobs.model.GitJob
import com.casa.azul.gitjobs.model.GitJobDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

private const val TAG = "ListViewModel"
class ListViewModel(application: Application): BaseViewModel(application) {


    private val gitService = GitApiService()
    private val disposable = CompositeDisposable()

    val gitJobs = MutableLiveData<List<GitJob>>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {


            fetchFromRemote()

    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            gitService.getGitJobs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<GitJob>>(){
                    override fun onSuccess(jobList: List<GitJob>) {
                        Log.d(TAG, "List size =  ${jobList.size}")
                        Toast.makeText(getApplication(), "Jobs retrieved from endpoint", Toast.LENGTH_SHORT).show()
                        gitJobs.value = jobList
                        storeGitJobsLocally(jobList)

                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, " RxJava error")
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun storeGitJobsLocally(list: List<GitJob>) {
        launch {
            val dao = GitJobDatabase(getApplication()).gitjobDao()
            dao.deleteAllGitJobs()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            Log.d(TAG, "Room Database result = $result")
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            //dogsRetrieved(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}