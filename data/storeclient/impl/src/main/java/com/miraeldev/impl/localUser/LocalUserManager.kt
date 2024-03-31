package com.miraeldev.impl.localUser

import android.content.Context
import androidx.datastore.dataStore
import com.miraeldev.api.LocalUserStoreApi
import com.miraeldev.api.models.LocalUserEmailDataModel
import com.miraeldev.impl.crypto.CryptoManager
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

private val Context.userDataStore by dataStore(
    fileName = "local_user_info",
    serializer = LocalUserSerializer(CryptoManager())
)

@Inject
class LocalUserManager(context: Context) : LocalUserStoreApi {

    private val dataStore = context.userDataStore
    override suspend fun updateUser(localUser: LocalUserEmailDataModel) {
        dataStore.updateData {
            localUser
        }
    }

    override suspend fun getUserEmail(): LocalUserEmailDataModel {
        return dataStore.data.first()
    }


}