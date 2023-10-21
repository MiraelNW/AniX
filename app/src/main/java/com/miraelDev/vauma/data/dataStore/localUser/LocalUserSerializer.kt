package com.miraelDev.vauma.data.dataStore.localUser

import androidx.datastore.core.Serializer
import com.miraelDev.vauma.data.dataStore.crypto.CryptoManager
import com.miraelDev.vauma.domain.models.user.LocalUser
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class LocalUserSerializer(
    private val cryptoManager: CryptoManager
) : Serializer<LocalUser> {

    override val defaultValue: LocalUser
        get() = LocalUser(email = "")

    override suspend fun readFrom(input: InputStream): LocalUser {
        val decryptedBytes = cryptoManager.decrypt(input)
        return try {
            Json.decodeFromString(
                deserializer = LocalUser.serializer(),
                string = decryptedBytes.decodeToString()
            )
        } catch(e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: LocalUser, output: OutputStream) {
        cryptoManager.encrypt(
            bytes = Json.encodeToString(
                serializer = LocalUser.serializer(),
                value = t
            ).encodeToByteArray(),
            outputStream = output
        )
    }
}