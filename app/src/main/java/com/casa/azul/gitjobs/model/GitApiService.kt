package com.casa.azul.dogs.model

import com.casa.azul.gitjobs.model.GitJob
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GitApiService {

    private val BASE_URL = "https://jobs.github.com"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GitApi::class.java)

    fun getGitJobs(): Single<List<GitJob>>{
        return api.getGitJobs()
    }
}