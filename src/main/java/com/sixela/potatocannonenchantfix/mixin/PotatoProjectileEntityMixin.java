package com.sixela.potatocannonenchantfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PotatoProjectileEntity.class)
public abstract class PotatoProjectileEntityMixin extends AbstractHurtingProjectile implements IEntityWithComplexSpawn {
    protected PotatoProjectileEntityMixin(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    protected boolean create_potato_cannon_early_fix$additionalFlame = false;

    @Shadow
    protected float additionalDamageMult;
    @Shadow
    protected float additionalKnockback;


    @Inject(method = "setEnchantmentEffectsFromCannon", at = @At("TAIL"))
    private void loadAdditionalDamageFromEnchants(ItemStack cannon, CallbackInfo ci, @Local(name = "enchantmentRegistry") Registry<Enchantment> enchantmentRegistry) {
        int power = cannon.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(Enchantments.POWER));
        int knockback = cannon.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(Enchantments.PUNCH));
        int flame = cannon.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(Enchantments.FLAME));

        additionalDamageMult = 1 + power * 0.2f;
        additionalKnockback = knockback * 0.5f;
        create_potato_cannon_early_fix$additionalFlame = flame > 0;
    }

    @ModifyReturnValue(method = "shouldBurn", at = @At("RETURN"))
    private boolean replaceShouldBurn(boolean original) {
        return create_potato_cannon_early_fix$additionalFlame;
    }


}
