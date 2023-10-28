package com.miraeldev.data.dataStore.localUser

import androidx.datastore.core.Serializer
import com.miraeldev.data.dataStore.crypto.CryptoManager
import com.miraeldev.domain.models.userDataModels.LocalUserDataModel
import com.miraeldev.user.LocalUser
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal class LocalUserSerializer(
    private val cryptoManager: CryptoManager
) : Serializer<LocalUserDataModel> {

    override val defaultValue: LocalUserDataModel
        get() = LocalUserDataModel(email = "")

    override suspend fun readFrom(input: InputStream): LocalUserDataModel {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = LocalUserDataModel.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: LocalUserDataModel, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = LocalUserDataModel.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}