package com.textsdev.postsfetcher.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class CommentsModel : ArrayList<CommentsModel.CommentsModelItem>() {

    @Keep
    data class CommentsModelItem(
        @SerializedName("body")
        val body: String, // laudantium enim quasi est quidem magnam voluptate ipsam eostempora quo necessitatibusdolor quam autem quasireiciendis et nam sapiente accusantium
        @SerializedName("email")
        val email: String, // Eliseo@gardner.biz
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // id labore ex et quam laborum
        @SerializedName("postId")
        val postId: Int // 1
    )
}