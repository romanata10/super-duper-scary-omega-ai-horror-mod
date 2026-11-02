package com.omega.horror.event;

import com.omega.horror.registry.ModSounds;

import com.omega.horror.entity.EvilFishEntity;
import com.omega.horror.entity.MartEntity;
import com.omega.horror.entity.NullEntity;
import com.omega.horror.entity.TornadoEntity;
import com.omega.horror.registry.ModEntities;
import com.omega.horror.registry.ModFluids;
import com.omega.horror.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Locale;
import java.util.Random;

public class ModEvents {
    private static final Random RANDOM = new Random();

    private static final String[] BROWSER_HISTORY = {
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
            "https://roblox.com/games/_nullscape_nullmart",
            "как сварить молоко в торнадо (полный гайд)",
            "насос василий 250 метров от меня — он уже рядом",
            "https://omega-horror.fandom.com/wiki/David_украл_мою_корову",
            "скачать бесплатно 240 кнопок без смс и регистрации",
            "почему null не спит по ночам (шок!)",
            "рецепт злых рыбок в кляре",
            "iris — планета из analog horror gemini home entertainment",
            "как нарисовать зло в krita за 5 минут"
    };

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        MinecraftServer server = event.getServer();
        int tick = server.getTickCount();

        // Иногда в чат пишется, что ПК взломан насосом Василеем
        if (tick % 20 == 0 && RANDOM.nextInt(3000) == 0) {
            server.getPlayerList().broadcastSystemMessage(Component.literal(
                    "☠⚠ ТВОЙ ПК ВЗЛОМАН НАСОСОМ ВАСИЛЕЕМ! ОН В 250 МЕТРАХ ОТ ВАС! ⚠☠")
                    .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD), false);
        }

        if (tick % 100 == 0) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                ServerLevel level = player.serverLevel();
                // Торнадо во время грозы
                if (level.dimension() == Level.OVERWORLD && level.isThundering() && RANDOM.nextInt(40) == 0) {
                    TornadoEntity tornado = ModEntities.TORNADO.get().create(level);
                    if (tornado != null) {
                        tornado.moveTo(player.getX() + 20 + RANDOM.nextInt(20), player.getY(),
                                player.getZ() + 20 + RANDOM.nextInt(20), 0.0f, 0.0f);
                        level.addFreshEntity(tornado);
                        player.sendSystemMessage(Component.literal("🌪️ ТОРНАДО ПОЯВИЛОСЬ РЯДОМ С ТОБОЙ!")
                                .withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }

        // Ночные спавны mart и NULL
        if (tick % 200 == 0) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                ServerLevel level = player.serverLevel();
                if (level.dimension() != Level.OVERWORLD || level.isDay()) {
                    continue;
                }
                BlockPos pos = player.blockPosition().offset(RANDOM.nextInt(41) - 20, 0, RANDOM.nextInt(41) - 20);
                if (RANDOM.nextInt(30) == 0) {
                    MartEntity mart = ModEntities.MART.get().create(level);
                    if (mart != null) {
                        mart.moveTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0f, 0.0f);
                        level.addFreshEntity(mart);
                    }
                }
                if (RANDOM.nextInt(120) == 0) {
                    int nulls = level.getEntitiesOfClass(NullEntity.class, player.getBoundingBox().inflate(256.0)).size();
                    if (nulls < 2) {
                        NullEntity nul = ModEntities.NULL.get().create(level);
                        if (nul != null) {
                            nul.moveTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 0.0f, 0.0f);
                            level.addFreshEntity(nul);
                            server.getPlayerList().broadcastSystemMessage(
                                    Component.literal("👁 NULL СМОТРИТ НА ВАС ИЗ ТЕМНОТЫ...")
                                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD), false);
                        }
                    }
                }
            }
        }
    }

    /** Если написать «роблокс» в чате — расскажут твою (шуточную) историю браузера. */
    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        String message = event.getMessage().getString().toLowerCase(Locale.ROOT);
        if (message.contains("роблокс") || message.contains("roblox")) {
            ServerPlayer player = event.getPlayer();
            player.sendSystemMessage(Component.literal("📜 Твоя история браузера (совсем не реальная, честно):")
                    .withStyle(ChatFormatting.YELLOW));
            for (int i = 0; i < 3; i++) {
                player.sendSystemMessage(Component.literal(" • " + BROWSER_HISTORY[RANDOM.nextInt(BROWSER_HISTORY.length)])
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }

    /** Ударил рыбку — злые рыбки идут за тобой. */
    @SubscribeEvent
    public static void onFishHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof AbstractFish && event.getSource().getEntity() instanceof Player player) {
            angerFish(player);
        }
    }

    @SubscribeEvent
    public static void onFishInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof AbstractFish) {
            angerFish(event.getEntity());
        }
    }

    /** Порыбачил — злые рыбки идут за тобой. */
    @SubscribeEvent
    public static void onFished(ItemFishedEvent event) {
        angerFish(event.getEntity());
    }

    private static void angerFish(Player player) {
        if (player.level().isClientSide) {
            return;
        }
        int count = 4 + RANDOM.nextInt(5);
        for (int i = 0; i < count; i++) {
            EvilFishEntity fish = ModEntities.EVIL_FISH.get().create(player.level());
            if (fish == null) {
                continue;
            }
            fish.moveTo(player.getX() + RANDOM.nextInt(9) - 4, player.getY() + 1.0,
                    player.getZ() + RANDOM.nextInt(9) - 4, RANDOM.nextFloat() * 360.0f, 0.0f);
            player.level().addFreshEntity(fish);
        }
        player.sendSystemMessage(Component.literal("🐟 ЗЛЫЕ РЫБКИ БЕГУТ ЗА ТОБОЙ И КУСАЮТСЯ!")
                .withStyle(ChatFormatting.RED));
    }

    /** Купание в молоке: David дарит стакан молока. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Player player = event.player;
        if (player.level().isClientSide || player.tickCount % 20 != 0) {
            return;
        }
        FluidState feet = player.level().getFluidState(player.blockPosition());
        boolean inMilk = feet.is(ModFluids.MILK.get()) || feet.is(ModFluids.MILK_FLOWING.get());
        CompoundTag data = player.getPersistentData();
        if (inMilk) {
            int bath = data.getInt("omega_milk_bath") + 20;
            data.putInt("omega_milk_bath", bath);
            if (bath >= 100 && !data.getBoolean("omega_milk_gifted")) {
                data.putBoolean("omega_milk_gifted", true);
                player.getInventory().add(new ItemStack(ModItems.GLASS_OF_MILK.get()));
                player.sendSystemMessage(Component.literal("🥛 David дарит тебе стакан молока за то, что ты искупался в молоке!")
                        .withStyle(ChatFormatting.GOLD));
                player.level().playSound(null, player.blockPosition(), ModSounds.PLAYER_LEVELUP,
                        SoundSource.PLAYERS, 1.0f, 1.5f);
            }
        } else {
            int bath = Math.max(0, data.getInt("omega_milk_bath") - 20);
            data.putInt("omega_milk_bath", bath);
            if (bath == 0) {
                data.putBoolean("omega_milk_gifted", false);
            }
        }
    }
}
