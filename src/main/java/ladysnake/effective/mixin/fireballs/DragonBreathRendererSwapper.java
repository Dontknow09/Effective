package ladysnake.effective.mixin.fireballs;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import ladysnake.effective.Effective;
import ladysnake.effective.EffectiveConfig;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.awt.*;

@Mixin(AreaEffectCloudEntity.class)
public abstract class DragonBreathRendererSwapper extends Entity {
	public DragonBreathRendererSwapper(EntityType<?> type, World world) {
		super(type, world);
	}

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addImportantParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	private void effective$swapDragonBreathParticles(World world, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> voidOperation) {
		if (EffectiveConfig.improvedDragonFireballsAndBreath && parameters == ParticleTypes.DRAGON_BREATH) {
			ParticleBuilders.create(Effective.DRAGON_BREATH)
				.setSpin((float) (world.random.nextGaussian() / 5f))
				.setScale(1f, 0f)
				.setScaleEasing(Easing.CIRC_OUT)
				.setAlpha(0.2f)
				.setColor(new Color(0xD21EFF), new Color(0x7800FF))
				.setColorEasing(Easing.CIRC_OUT)
				.enableNoClip()
				.setLifetime(40)
				.setMotion((float) velocityX, (float) velocityY + 0.05f, (float) velocityZ)
				.spawn(world, x, y, z);
		} else {
			voidOperation.call(world, parameters, x, y, z, velocityX, velocityY, velocityZ);
		}
	}
}
