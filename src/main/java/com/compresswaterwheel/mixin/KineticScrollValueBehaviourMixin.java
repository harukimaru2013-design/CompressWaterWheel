package com.compresswaterwheel.mixin;

import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.infrastructure.config.AllConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(KineticScrollValueBehaviour.class)
public class KineticScrollValueBehaviourMixin {

    /**
     * createBoard() 内でハードコードされている 256 を、
     * Create本家のserver config (maxRotationSpeed) の値に差し替える。
     * これにより Issue #6609 (RSCのGUI上限が256固定になるバグ) を解消する。
     */
    @ModifyConstant(method = "createBoard", constant = @Constant(intValue = 256))
    private int compresswaterwheel$useConfiguredMaxRotationSpeed(int original) {
        return AllConfigs.server().kinetics.maxRotationSpeed.get();
    }
}