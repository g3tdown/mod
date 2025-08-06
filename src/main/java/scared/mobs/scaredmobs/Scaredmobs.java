package scared.mobs.scaredmobs;

import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("scaredanimals")
public class ScaredAnimalsMod {

    public ScaredAnimalsMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getWorld() instanceof ServerWorld)) return;

        if (event.getEntity() instanceof AnimalEntity) {
            AnimalEntity animal = (AnimalEntity) event.getEntity();

            // Удалим старые похожие цели (опционально)
            animal.goalSelector.goals.removeIf(goal ->
                    goal.getGoal() instanceof AvoidEntityGoal);

            // Добавим поведение избегания игрока
            animal.goalSelector.addGoal(1,
                    new AvoidEntityGoal<>(animal, PlayerEntity.class, 8.0F, 1.6D, 1.4D));
        }
    }
}