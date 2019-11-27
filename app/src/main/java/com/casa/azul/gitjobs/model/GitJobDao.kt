package com.casa.azul.gitjobs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GitJobDao {

    @Insert
    suspend fun insertAll(vararg dogs: GitJob): List<Long>

    @Query("SELECT * FROM gitjob")
    suspend fun getAllGitJobs(): List<GitJob>

    @Query("SELECT * FROM gitjob WHERE uuid = :dogId")
    suspend fun getGitJob(dogId: Int): GitJob

    @Query("DELETE FROM gitjob")
    suspend fun deleteAllGitJobs()
}