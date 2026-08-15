package com.example.data.repoimpl

import android.provider.SyncStateContract
import android.util.Log
import com.android.identity.util.Constants
import com.example.composereview.mainascreen.Constants.PHONE_IDENTIFIER
import com.example.data.apiservices.AllUsersApi
import com.example.data.db.dao.AllUsersDao
import com.example.data.toDomain
import com.example.data.toEntity
import com.example.domain.recenttranscations.model.AllUsers
import com.example.domain.recenttranscations.repo.AllUsersRepo
import com.example.domain.recenttranscations.utils.Resource

class AllUsersRepoImpl(private val allUsersApi: AllUsersApi, private val allUsersDao: AllUsersDao) :
    AllUsersRepo {
    override suspend fun getAllUser(): Resource<List<AllUsers>> {
        return try {
            val remoteUsers = allUsersApi.getAllUsers()
                .map { it.toEntity() }
                .filter { user -> user.identifier != PHONE_IDENTIFIER }

            Log.e("TAG", "getAllUser: ${remoteUsers.size}")


            allUsersDao.insertAllUsers(remoteUsers)

            val localUsers = allUsersDao.getAllUsers().map { it.toDomain() }
            Resource.Success(localUsers)

        } catch (e: Exception) {
            Log.e("TAG", "getAllUser Network Error: ${e.localizedMessage}")

            try {
                val cachedUsers = allUsersDao.getAllUsers().map { it.toDomain() }

                if (cachedUsers.isNotEmpty()) {
                    Log.d("TAG", "getAllUser: Loaded from Cache successfully (${cachedUsers.size} users)")
                    Resource.Success(cachedUsers)
                } else {
                    Resource.Error("لا يوجد اتصال بالإنترنت ولا توجد بيانات مسجلة مسبقاً")
                }
            } catch (dbException: Exception) {
                Log.e("TAG", "getAllUser DB Error: ${dbException.localizedMessage}")
                Resource.Error("حدث خطأ أثناء قراءة البيانات المحلية")
            }
        }
    }
}