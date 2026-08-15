package com.example.domain.recenttranscations.usecase

import com.example.domain.recenttranscations.model.AllUsers
import com.example.domain.recenttranscations.repo.AllUsersRepo
import com.example.domain.recenttranscations.utils.Resource

class AllUserUseCase(
    private val allUsersRepo: AllUsersRepo
) {
    suspend operator fun invoke(): Resource<List<AllUsers>> {
        return allUsersRepo.getAllUser()
    }
}