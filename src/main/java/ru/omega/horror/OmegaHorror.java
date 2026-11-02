package ru.omega.horror;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(OmegaHorror.ID)
public class OmegaHorror {
    public static final String ID = "omegahorror";
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
    public static final RegistryObject<Item> SOCKS = ITEMS.register("femboy_socks", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SOUND = ITEMS.register("random_sounds", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Block> KRITA = BLOCKS.register("krita", () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
    public static final RegistryObject<Item> KRITA_ITEM = ITEMS.register("krita", () -> new BlockItem(KRITA.get(), new Item.Properties()));

    public OmegaHorror() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus); BLOCKS.register(bus);
        bus.addListener(this::creative);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void creative(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.INGREDIENTS) { e.accept(SOCKS); e.accept(SOUND); e.accept(KRITA_ITEM); }
    }
    private static Zombie monster(ServerLevel level, BlockPos pos, String tag, String name) {
        Zombie z = EntityType.ZOMBIE.create(level);
        if (z == null) return null;
        z.moveTo(pos.getX() + .5, pos.getY(), pos.getZ() + .5, 0, 0);
        z.addTag(tag); z.setCustomName(Component.literal(name)); z.setCustomNameVisible(true);
        z.setPersistenceRequired(); level.addFreshEntity(z); return z;
    }
    @SubscribeEvent public void chat(ServerChatEvent e) {
        String message = e.getRawText().toLowerCase(java.util.Locale.ROOT);
        if (message.contains("роблокс") || message.contains("roblox")) {
            e.getPlayer().sendSystemMessage(Component.literal("Шуточная история браузера: как напугать холодильник; 10 способов подружиться с кнопкой; коровы в космосе."));
        }
    }
    @SubscribeEvent public void place(BlockEvent.EntityPlaceEvent e) {
        if (!(e.getLevel() instanceof ServerLevel level) || !(e.getEntity() instanceof Player p) || !(e.getPlacedBlock().getBlock() instanceof ButtonBlock)) return;
        // Count nearby buttons, including the one just placed. Only one boss per area.
        BlockPos center = e.getPos(); int count = 0;
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-4,-4,-4), center.offset(4,4,4)))
            if (level.getBlockState(pos).getBlock() instanceof ButtonBlock) count++;
        if (count >= 5 && level.getEntitiesOfClass(Zombie.class, new AABB(center).inflate(32), z -> z.getTags().contains("omega_button")).isEmpty()) {
            Zombie z = monster(level, center.above(), "omega_button", "Великая злая кнопка");
            if (z != null) z.setTarget(p);
        }
    }
    @SubscribeEvent public void death(LivingDeathEvent e) {
        if (!(e.getEntity().level() instanceof ServerLevel level)) return;
        if (e.getEntity().getTags().contains("omega_button")) {
            for (int i = 0; i < 3; i++) e.getEntity().spawnAtLocation(new ItemStack(Items.OAK_BUTTON, 64));
            e.getEntity().spawnAtLocation(new ItemStack(Items.OAK_BUTTON, 48));
        }
        if (e.getEntity().getTags().contains("omega_soundpad")) e.getEntity().spawnAtLocation(SOUND.get());
    }
    private static void angerFish(ServerLevel level, Player player) {
        for (AbstractFish fish : level.getEntitiesOfClass(AbstractFish.class, player.getBoundingBox().inflate(16))) {
            fish.getPersistentData().putUUID("omega_target", player.getUUID());
        }
        player.sendSystemMessage(Component.literal("Рыбки запомнили это."));
    }
    @SubscribeEvent public void fishing(ItemFishedEvent e) {
        if (e.getEntity().level() instanceof ServerLevel level) angerFish(level, e.getEntity());
    }
    @SubscribeEvent public void hurt(LivingHurtEvent e) {
        if (!(e.getEntity() instanceof AbstractFish) || !(e.getSource().getEntity() instanceof Player player) || !(player.level() instanceof ServerLevel level)) return;
        angerFish(level, player);
    }
    private static SoundEvent randomSound(ServerLevel level) {
        SoundEvent[] sounds = { SoundEvents.AMBIENT_CAVE, SoundEvents.ENDERMAN_SCREAM, SoundEvents.GHAST_SCREAM, SoundEvents.WARDEN_HEARTBEAT, SoundEvents.CAT_AMBIENT };
        return sounds[level.random.nextInt(sounds.length)];
    }
    @SubscribeEvent public void use(PlayerInteractEvent.RightClickItem e) {
        if (!(e.getLevel() instanceof ServerLevel level)) return;
        if (e.getItemStack().is(SOUND.get())) {
            level.playSound(null, e.getEntity().blockPosition(), randomSound(level), SoundSource.PLAYERS, 1, 0.5f + level.random.nextFloat());
        }
    }
    @SubscribeEvent public void tick(TickEvent.LevelTickEvent e) {
        if (e.phase != TickEvent.Phase.END || !(e.level instanceof ServerLevel level) || level.getGameTime() % 40 != 0) return;
        for (ServerPlayer p : level.players()) {
            if (p.getInventory().contains(new ItemStack(SOCKS.get()))) {
                p.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 100, 249, true, false));
                p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 99, true, false));
            }
            if (level.random.nextInt(300) == 0) p.sendSystemMessage(Component.literal("[ШУТКА] Твой ПК взломан насосом Василием! Он в 250 метрах от тебя."));
            if (level.random.nextInt(500) == 0) {
                BlockPos at = p.blockPosition().offset(level.random.nextInt(17)-8, 0, level.random.nextInt(17)-8);
                String[] tags = {"omega_david", "omega_null", "omega_soundpad", "omega_mart"};
                String[] names = {"Давид", "NULL", "Soundpad", "Mart"};
                int n = level.random.nextInt(tags.length); monster(level, at, tags[n], names[n]);
            }
            for (AbstractFish fish : level.getEntitiesOfClass(AbstractFish.class, p.getBoundingBox().inflate(16), f -> f.getPersistentData().hasUUID("omega_target") && f.getPersistentData().getUUID("omega_target").equals(p.getUUID()))) {
                fish.getNavigation().moveTo(p, 1.3);
                if (fish.distanceToSqr(p) < 4) p.hurt(level.damageSources().mobAttack(fish), 2);
            }
            for (Zombie z : level.getEntitiesOfClass(Zombie.class, p.getBoundingBox().inflate(32), mob -> mob.getTags().contains("omega_button") || mob.getTags().contains("omega_david") || mob.getTags().contains("omega_soundpad"))) {
                if (z.getTags().contains("omega_button")) {
                    BlockPos pos = z.blockPosition();
                    if (level.isEmptyBlock(pos) && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), net.minecraft.core.Direction.UP)) level.setBlock(pos, Blocks.OAK_BUTTON.defaultBlockState().setValue(net.minecraft.world.level.block.ButtonBlock.FACE, net.minecraft.world.level.block.state.properties.AttachFace.FLOOR), 3);
                }
                if (z.getTags().contains("omega_david")) {
                    for (Cow cow : level.getEntitiesOfClass(Cow.class, z.getBoundingBox().inflate(3))) cow.discard();
                    if (z.distanceToSqr(p) < 16) {
                        for (int slot = 0; slot < p.getInventory().getContainerSize(); slot++) {
                            ItemStack stack = p.getInventory().getItem(slot);
                            if (stack.is(Items.MILK_BUCKET)) p.getInventory().setItem(slot, new ItemStack(Items.BUCKET));
                        }
                    }
                }
                if (z.getTags().contains("omega_soundpad") && level.random.nextInt(3) == 0) level.playSound(null, z.blockPosition(), randomSound(level), SoundSource.HOSTILE, 1, .5f + level.random.nextFloat());
            }
        }
    }
}
