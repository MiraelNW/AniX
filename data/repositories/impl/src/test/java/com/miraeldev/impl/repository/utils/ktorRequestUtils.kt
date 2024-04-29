package com.miraeldev.impl.repository.utils

import com.miraeldev.anime.AnimeDetailInfo
import io.ktor.client.call.HttpClientCall
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.HttpResponseData
import io.ktor.client.statement.DefaultHttpResponse
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpMethod
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.content.OutgoingContent
import io.ktor.util.Attributes
import io.ktor.util.InternalAPI
import io.ktor.util.date.GMTDate
import io.ktor.util.reflect.typeInfo
import io.ktor.utils.io.ByteReadChannel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


@OptIn(InternalAPI::class)
fun getDefaultHttpResponse(statusCode: Int) = DefaultHttpResponse(call, getHttpResponseData(statusCode))

@InternalAPI
private val call = mockk<HttpClientCall> {
    every { client } returns mockk {}
    coEvery { body(typeInfo<String>()) } returns ""
    every { coroutineContext } returns EmptyCoroutineContext
    every { attributes } returns Attributes()
    every { request } returns object : HttpRequest {
        override val call: HttpClientCall = this@mockk
        override val attributes: Attributes = Attributes()
        override val content: OutgoingContent = object : OutgoingContent.NoContent() {}
        override val headers: Headers = Headers.Empty
        override val method: HttpMethod = HttpMethod.Get
        override val url: Url = Url("/")
    }

    every { response } returns object : HttpResponse() {
        override val call: HttpClientCall = this@mockk
        override val content: ByteReadChannel = ByteReadChannel("body")
        override val coroutineContext: CoroutineContext = EmptyCoroutineContext
        override val headers: Headers = Headers.Empty
        override val requestTime: GMTDate = GMTDate.START
        override val responseTime: GMTDate = GMTDate.START
        override val status: HttpStatusCode = HttpStatusCode.OK
        override val version: HttpProtocolVersion = HttpProtocolVersion.HTTP_1_1
    }
}

private fun getHttpResponseData(statusCode: Int): HttpResponseData {
    return HttpResponseData(
            statusCode = HttpStatusCode(statusCode,"ok"),
            requestTime = GMTDate(),
            headers = Headers.Empty,
            version = HttpProtocolVersion.HTTP_1_1,
            body = "body",
            callContext = EmptyCoroutineContext
    )
}