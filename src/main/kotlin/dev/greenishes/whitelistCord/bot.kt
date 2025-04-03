package dev.greenishes.whitelistCord




import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.events.onCommand
import dev.minn.jda.ktx.jdabuilder.intents
import dev.minn.jda.ktx.jdabuilder.light
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime


fun bot(botToken: String, plugin: JavaPlugin) {
    val jda = light(botToken, enableCoroutines=true) {
        intents += listOf(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_PRESENCES)
    }


    jda.onCommand("ping") {event ->
        println("[WhitelistCord] ping command invoked")
        val time = measureTime {
            event.reply("Pong!").await()
        }.inWholeMilliseconds

        event.hook.editOriginal("Pong: $time ms").queue()
    }
    jda.onCommand("whitelist") {event ->
        println("[WhitelistCord] whitelist command invoked")
        var username = event.getOption("username")?.asString
        try{
            if (username != null) {
                whitelist(username, plugin)
                event.reply("Whitelisted $username").queue()
            }
        } catch (e: Exception){
            event.reply("Error whitelisting player, check console")
            println("[WhitelistCord] " + e.message)
        }



    }


    suspend fun registerCommands(jda: JDA) {
        jda.updateCommands()
            .addCommands(
                Commands.slash("ping", "gives the current ping"),
                Commands.slash("whitelist", "Whitelists yourself").addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "username", "Your Minecraft username", true)).await()
    }
    runBlocking { registerCommands(jda) }

}