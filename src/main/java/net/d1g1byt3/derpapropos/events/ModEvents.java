package net.d1g1byt3.derpapropos.events;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.command.ReturnHomeCommand;
import net.d1g1byt3.derpapropos.command.SetHomeCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = DerpAproposMod.MOD_ID)
public class ModEvents {


@SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event){
        new SetHomeCommand(event.getDispatcher());
        new ReturnHomeCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
    event.getEntity().getPersistentData().putIntArray("derpapropos.homepos",
            event.getOriginal().getPersistentData().getIntArray("derpapropos.homepos"));
    }



}
