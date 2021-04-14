package com.gilbertparreno.cartrack.core.networking.serializers

import com.google.gson.*
import java.lang.reflect.Type

class StringDeserializer : JsonDeserializer<String> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String? {
        if (json?.asString?.isEmpty() == true) {
            return null
        } else {
            return json?.asString
        }
    }
}