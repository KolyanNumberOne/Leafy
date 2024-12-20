package com.example.leafy.data.models

import kotlinx.serialization.Serializable
@Serializable
data class ChatRequest(
    val model: String,
    val stream: Boolean,
    val update_interval: Int,
    val messages: List<Message>
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class ChatResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: AssistantMessage
)

@Serializable
data class AssistantMessage(
    val content: String,
    val role: String
)