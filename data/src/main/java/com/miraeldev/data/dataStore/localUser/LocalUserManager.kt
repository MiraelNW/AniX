package com.miraeldev.data.dataStore.localUser

import android.content.Context
import androidx.datastore.dataStore
import com.miraeldev.data.dataStore.crypto.CryptoManager
import com.miraeldev.domain.models.userDataModels.LocalUserDataModel
import com.miraeldev.user.LocalUser
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.userDataStore by dataStore(
    fileName = "local_user_info",
    serializer = LocalUserSerializer(CryptoManager())
)

internal class LocalUserManager @Inject constructor(
    context: Context
) : LocalUserStoreApi {

    private val dataStore = context.userDataStore
    override suspend fun updateUser(localUser: LocalUserDataModel) {
        dataStore.updateData {
            localUser
        }
    }

    override suspend fun getUser(): LocalUserDataModel {
        return dataStore.data.first()
    }


}