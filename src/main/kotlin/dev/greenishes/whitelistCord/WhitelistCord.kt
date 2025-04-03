package dev.greenishes.whitelistCord

import org.bukkit.plugin.java.JavaPlugin


class WhitelistCord : JavaPlugin() {

    override fun onEnable() {
        logger.info("Starting WhitelistCord!")
        saveDefaultConfig()
        val config = config
        if (config.getString("botToken") == "Change me!") {
            logger.warning("<!> Please change the bot token in WhitelistCord's configuration file. <!>")
        } else {
            config.getString("botToken")?.let { bot(
                it,
                plugin = this
            ) }
        }
    }

    override fun onDisable() {
        logger.info("Stopping WhitelistCord!")
    }
}
