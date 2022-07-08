package dev.igalaxy.nochatreports

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket
import net.minecraft.network.protocol.game.ServerboundChatPacket
import net.minecraft.network.protocol.login.ClientboundHelloPacket
import net.minecraft.server.MinecraftServer
import org.bukkit.plugin.java.JavaPlugin

class NoChatReports : JavaPlugin() {
    lateinit var protocolManager: ProtocolManager

    override fun onEnable() {
        super.onEnable()


        MinecraftServer.getServer().enforceSecureProfile()

        protocolManager = ProtocolLibrary.getProtocolManager()!!

        protocolManager.addPacketListener(object: PacketAdapter(
            this,
            ListenerPriority.HIGHEST,
            PacketType.Play.Server.CHAT
        ) {
            override fun onPacketSending(event: PacketEvent) {
                event.packet.chatTypes.writeSafely(0, EnumWrappers.ChatType.SYSTEM)
            }
        })
    }

    override fun onDisable() {
        super.onDisable()
    }
}