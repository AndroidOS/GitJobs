package com.casa.azul.gitjobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GitJob(
    val id: String?,
    val type: String?,
    val url: String?,
    val created_at: String?,
    val company: String?,
    val company_url: String?,
    val location: String?,
    val title: String?,
    val description: String?,
    val how_to_apply: String?,
    val company_logo: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}