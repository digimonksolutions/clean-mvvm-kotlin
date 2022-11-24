package com.cleanarchitecture.data.source.remote.api

import com.cleanarchitecture.data.BuildConfig
import com.cleanarchitecture.data.source.remote.auth.AuthenticationInterceptor
import com.cleanarchitecture.data.source.remote.service.APIEndPoints
import com.cleanarchitecture.data.source.remote.settings.Setting.Companion.BASE_URL
import com.cleanarchitecture.data.source.remote.settings.Setting.Companion.HEADER
import com.cleanarchitecture.data.source.remote.settings.Setting.Companion.IS_HEADER_REQUIRED
import com.cleanarchitecture.domain.response.Response
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class RestClient() {

    /*
    * Get
    * */
    suspend fun <T> get(clazz: Class<T> , url: String): Response<T> {
        if (IS_HEADER_REQUIRED) {
            val response = create(APIEndPoints::class.java).httpGet(url, HEADER)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T, response.code())
                } else {
                    return Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ), response.code()
                    )
                }
            } else {
                return Response.error(response.message(), response.code())
            }
        }else {
            val response = create(APIEndPoints::class.java).httpGet(url)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }
        }
    }

    /*
    * Post
    * */
    suspend fun <T> post(
        clazz: Class<T>,
        url: String,
        keyValues: HashMap<String, String>,
    ): Response<T> {
        if (IS_HEADER_REQUIRED) {
            val response = create(APIEndPoints::class.java).httpPost(url, HEADER, keyValues)

            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }

        } else {
            val response = create(APIEndPoints::class.java).httpPost(url, keyValues)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }
        }
    }

    /*
    * Put
    * */
    suspend fun <T> put(
        clazz: Class<T>,
        url: String,
        keyValues: HashMap<String, String>,
    ): Response<T> {
        if (IS_HEADER_REQUIRED) {
            val response = create(APIEndPoints::class.java).httpPut(url, HEADER, keyValues)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }
        }
        else {
            val response = create(APIEndPoints::class.java).httpPut(url, keyValues)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }
        }
    }

    /*
    * Multipart
    * */
    suspend fun <T> postMultipart(
        clazz: Class<T>,
        url: String,
        file: HashMap<String, String>,
        keyValues: HashMap<String, String>,
    ): Response<T> {
        val list: ArrayList<MultipartBody.Part> = ArrayList()
        val arrays: HashMap<String, RequestBody> = HashMap()
        for (entry: Map.Entry<String, String> in file.entries) {
            list.add(createRequestBody(entry.key, File(entry.value)))
        }
        for (entry: Map.Entry<String, String> in keyValues.entries) {
            arrays.put(entry.key, createRequestBody(entry.value))
        }
        if (IS_HEADER_REQUIRED) {
            val response = create(APIEndPoints::class.java).postMutlipart(url, HEADER, arrays, list)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }

        }
        else {
            val response = create(APIEndPoints::class.java).postMutlipart(url, arrays, list)
            if (response.isSuccessful) {

                if (clazz == String::class.java) {
                    return Response.success(response.body() as T,response.code())
                } else {
                    return  Response.success(
                        Gson().fromJson(
                            response.body(),
                            clazz
                        ) , response.code())
                }
            }else{
                return Response.error(response.message(),response.code())
            }
        }
    }

    private fun createRequestBody(data: String): RequestBody {
        return data.toRequestBody(MultipartBody.FORM)
    }

    private fun createRequestBody(partName: String, file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("*/*".toMediaType())
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile)
    }

    /*
    * Create Client
    * */
    private fun <T> create(classObject: Class<T>): T {
        val retrofitClient = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofitClient.create(classObject)
    }

    private fun makeHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor())
            .hostnameVerifier(HostnameVerifier { hostname, session ->  true})
            .addInterceptor(AuthenticationInterceptor())
//            .addInterceptor(ConnectivityInterceptor(context))
            .build()
    }

    private fun logInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

}