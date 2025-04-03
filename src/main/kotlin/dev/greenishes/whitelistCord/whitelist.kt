package dev.greenishes.whitelistCord


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

@Serializable
data class WhitelistEntry(val uuid: String, val name: String)
val whitelist = mutableListOf<WhitelistEntry>()


suspend fun whitelist(username: String, plugin: JavaPlugin): String {
    var UUID = convertNameToUuid(username)
    UUID = trimmedToUuid(UUID.toString())
    val properName = getProperCasing(username)
    val serverFolder = Bukkit.getServer().worldContainer
    val whitelistFile = File(serverFolder, "whitelist.json")

    if (whitelist.any { it.uuid.toString() == UUID.toString() }) {
        return "exists"
    } else {
        whitelist.add(WhitelistEntry(UUID.toString(), username))
        whitelistFile.writeText(Json.encodeToString(whitelist))

        // Execute the command on the main thread
        Bukkit.getScheduler().runTask(plugin, Runnable {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "whitelist reload")
        })
    }
    return "done"
}








