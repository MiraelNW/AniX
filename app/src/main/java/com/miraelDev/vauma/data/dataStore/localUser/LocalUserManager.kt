package com.miraelDev.vauma.data.dataStore.localUser

import android.content.Context
import androidx.datastore.dataStore
import com.miraelDev.vauma.data.dataStore.crypto.CryptoManager
import com.miraelDev.vauma.domain.models.user.LocalUser
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.userDataStore by dataStore(
    fileName = "local_user_info",
    serializer = LocalUserSerializer(CryptoManager())
)

class LocalUserManager @Inject constructor(
    context: Context
) : LocalUserStoreApi {

    private val dataStore = context.userDataStore
    override suspend fun updateUser(localUser: LocalUser) {
        dataStore.updateData {
            localUser
        }
    }

    override suspend fun getUser(): LocalUser {
        return dataStore.data.first()
    }


}