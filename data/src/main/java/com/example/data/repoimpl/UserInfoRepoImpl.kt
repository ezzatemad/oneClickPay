package com.example.data.repoimpl

import com.example.data.apiservices.UserInfoApi
import com.example.data.toDomain
import com.example.domain.recenttranscations.model.UserInfo
import com.example.domain.recenttranscations.repo.UserInfoRepo
import com.example.domain.recenttranscations.utils.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException

class UserInfoRepoImpl(private val userInfoApi: UserInfoApi) : UserInfoRepo {

    override suspend fun getUserInfo(identifier: String): Resource<UserInfo> {

        return try {
            val response = userInfoApi.getUserInfoApi(identifier)
            Resource.Success(response.toDomain())
        } catch (e: IOException) {
            Resource.Error("لا يوجد اتصال بالإنترنت. يرجى التحقق من الشبكة.", e)
        } catch (e: ClientRequestException) {
            Resource.Error("خطأ في بيانات الطلب: ${e.response.status.value}", e)
        } catch (e: ServerResponseException) {
            Resource.Error("خطأ في الخادم: ${e.response.status.value}", e)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "حدث خطأ غير متوقع", e)
        }
    }
}