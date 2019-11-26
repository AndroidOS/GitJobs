package com.casa.azul.dogs.model

import com.casa.azul.gitjobs.model.GitJob
import io.reactivex.Single
import retrofit2.http.GET

interface GitApi {
    @GET("positions.json?description=python&location=new+york")
    fun getGitJobs(): Single<List<GitJob>>
}