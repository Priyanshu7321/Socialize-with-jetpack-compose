package com.example.socialize.service

import com.example.socialize.entity.User
import com.example.socialize.entity.UserPassword
import com.example.socialize.pojo.GroupChat
import com.example.socialize.pojo.Message
import com.example.socialize.pojo.Post
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

import com.example.socialize.data.model.SocialSignInRequest
import com.example.socialize.data.model.SocialSignInResponse

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body user: UserPassword): Response<Map<String, String>>

    @POST("api/auth/signup")
    suspend fun signup(@Body user: UserPassword): Response<Map<String, String>>

    @PATCH("update_record")
    suspend fun updateUserInfo(@Body user: User): Response<Map<String,String>>

    @POST("/send/user")
    suspend fun sendMsgToUser(@Body message: Message): Response<Map<String,String>>

    @POST("/send/group")
    suspend fun sendMsgGroup(@Body message: Message): Response<Map<String,String>>

    @GET("getUserChat")
    suspend fun getUserChat(
        @Query("user1") user1: String,
        @Query("user2") user2: String
    ): Response<List<Message>>

    @GET("getGroupChat")
    suspend fun getGroupChat(
        @Query("groupId") groupId:String
    ): Response<List<Message>>

    @GET("getUserGroups")
    suspend fun getUserGroups(@Query("userId") userId: String): Response<List<GroupChat>>
    
    @POST("auth/social")
    suspend fun socialSignIn(@Body request: SocialSignInRequest): Response<Map<String, String>>

    @Multipart
    @POST("file/upload/post")
    suspend fun uploadPost(
        @Part file: MultipartBody.Part,
        @Body post: Post
    ): Response<Map<String, String>>

    @POST("createGroup")
    suspend fun createGrp(@Body grpChat: GroupChat):Response<String>

    @GET("allUsers")
    suspend fun getAllUsers():Response<List<User>>

    @GET("allMembers")
    suspend fun getAllMembers(): Response<List<User>>

    @POST("follow")
    suspend fun followUser(@Query("user1") user1:String,@Query("user2") user2:String):Response<String>

    @POST("unfollow")
    suspend fun unfollowUser(@Query("user1") user1:String,@Query("user2") user2:String):Response<String>
}
