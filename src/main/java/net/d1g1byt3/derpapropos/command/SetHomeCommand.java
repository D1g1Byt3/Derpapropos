package net.d1g1byt3.derpapropos.command;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class SetHomeCommand {
    public SetHomeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /home set
        dispatcher.register(Commands.literal("home").then(Commands.literal("set")
                .executes(this::execute)));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        BlockPos playerPos = player.blockPosition();
        String positionString = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")"; //(X, Y, Z)

        player.getPersistentData().putIntArray("derpapropos.homepos",
                new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ()});

        context.getSource().sendSuccess(()-> Component.literal("Set home at " + positionString), false);
        return 1;
    }
}
