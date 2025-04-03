package dev.greenishes.whitelistCord

import com.google.gson.JsonParser
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun trimmedToUuid(trimmedUuid: String): UUID {
    val formattedUuid = trimmedUuid.replaceFirst(
        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(),
        "$1-$2-$3-$4-$5"
    )
    return UUID.fromString(formattedUuid)
}


fun convertNameToUuid(name: String): UUID? {
    val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    return if (connection.responseCode == 200) {
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        val trimmedUuid = JsonParser.parseString(response).asJsonObject["id"].asString
        return trimmedToUuid(trimmedUuid)
    } else {
        null
    }
}

fun getProperCasing(name: String): String? {
    val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"

    return if (connection.responseCode == 200) {
        val response = connection.inputStream.bufferedReader().use { it.readText() }
        JsonParser.parseString(response).asJsonObject["name"].asString
    } else {
        null
    }
}
