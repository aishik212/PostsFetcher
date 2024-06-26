package com.textsdev.postsfetcher.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PostsModel(
    @SerializedName("body")
    val body: String, // quia et suscipitsuscipit recusandae consequuntur expedita et cumreprehenderit molestiae ut ut quas totamnostrum rerum est autem sunt rem eveniet architecto
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("title")
    val title: String, // sunt aut facere repellat provident occaecati excepturi optio reprehenderit
    @SerializedName("userId")
    val userId: Int // 1
)