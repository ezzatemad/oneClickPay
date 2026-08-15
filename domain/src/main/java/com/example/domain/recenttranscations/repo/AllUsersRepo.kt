package com.example.domain.recenttranscations.repo

import com.example.domain.recenttranscations.model.AllUsers
import com.example.domain.recenttranscations.utils.Resource

interface AllUsersRepo {

    suspend fun getAllUser(): Resource<List<AllUsers>>
}