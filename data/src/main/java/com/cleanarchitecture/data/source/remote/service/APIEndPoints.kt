package com.cleanarchitecture.data.source.remote.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

public interface APIEndPoints {

    /*
    * GET
    * */
    @GET
    suspend fun httpGet(@Url url: String): Response<String>

    @GET
    suspend fun httpGet(@Url url: String, @HeaderMap headers: HashMap<String, String>?): Response<String>

    /*
    * POST
    * */
    @FormUrlEncoded
    @POST
    suspend fun httpPost(@Url url: String, @FieldMap keyValues: HashMap<String, String>): Response<String>

    @FormUrlEncoded
    @POST
    suspend fun httpPost(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>?,
        @FieldMap keyValues: HashMap<String, String>,
    ):Response<String>

    /*
    * PUT
    * */
    @PUT
    suspend fun httpPut(@Url url: String, @Body keyValues: HashMap<String, String>): Response<String>

    @PUT
    suspend fun httpPut(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>?,
        @Body keyValues: HashMap<String, String>
    ): Response<String>

    /*
    * DELETE
    * */
    @DELETE
    fun httpDelete(@Url url: String)

    @DELETE
    fun httpDelete(@Url url: String, @HeaderMap headers: HashMap<String, String>?)

    /*
    * MULTIPART
    * */
    @Multipart
    @POST
    fun getMultipart(@Url url: String, @FieldMap hash: HashMap<String, String>): Call<String>

    @Multipart
    @POST
    fun getMultipart(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>?,
        @FieldMap hash: HashMap<String, String>
    ): Call<String>

    @Multipart
    @POST
    suspend fun postMutlipart(
        @PartMap url: String,
        @PartMap hash: Map<String, RequestBody>,
        @Part file: List<MultipartBody.Part>
    ): Response<String>

    @Multipart
    @POST
    suspend fun postMutlipart(
        @PartMap url: String,
        @HeaderMap headers: HashMap<String, String>?,
        @PartMap hash: Map<String, RequestBody>,
        @Part file: List<MultipartBody.Part>
    ): Response<String>
}