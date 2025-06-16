package br.com.api.call.shared.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Cat(val id: String,
               val name: String,
               val data: JsonObject? = JsonObject( content = emptyMap())
)

