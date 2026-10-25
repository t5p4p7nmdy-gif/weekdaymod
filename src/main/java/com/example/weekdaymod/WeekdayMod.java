package com.example.weekdaymod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Mod(WeekdayMod.MOD_ID)
public class WeekdayMod {
    public static final String MOD_ID = "weekdaymod";

    public WeekdayMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("weekday")
                .executes(context -> {
                    String today = LocalDate.now().getDayOfWeek()
                            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    context.getSource().sendSuccess(
                            () -> Component.literal("Today is " + today), false);
                    return 1;
                })
                .then(Commands.literal("all")
                    .executes(context -> {
                        StringBuilder sb = new StringBuilder("Days of the week: ");
                        DayOfWeek[] days = DayOfWeek.values();
                        for (int i = 0; i < days.length; i++) {
                            sb.append(days[i].getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                            if (i < days.length - 1) sb.append(", ");
                        }
                        context.getSource().sendSuccess(
                                () -> Component.literal(sb.toString()), false);
                        return 1;
                    })
                )
        );
    }
}
