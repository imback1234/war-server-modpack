package dev.hans.warvehiclefix;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * Adds back the static Superb Warfare 0.8.9 functions AshVehicle 4.6 calls. Mixin does not allow a mixin class to add
 * a public static method, so this plugin adds each one directly to the class while it loads. Every added method only
 * forwards its arguments to the method with the same name and parameters in {@link LegacyApi}.
 */
public final class WarVehicleFixMixinPlugin implements IMixinConfigPlugin {
    private static final String LEGACY_API = "dev/hans/warvehiclefix/LegacyApi";
    private static final String THROWABLE = "Lnet/minecraft/world/entity/projectile/ThrowableItemProjectile;";

    /** Class being loaded -> the static methods (name, descriptor) it must have. */
    private static final Map<String, List<String[]>> BRIDGES = Map.of(
            "com.atsuishio.superbwarfare.tools.ProjectileTool", List.of(
                    new String[] {"causeCustomExplode", "(" + THROWABLE + "FFF)V"},
                    new String[] {"causeCustomExplode", "(" + THROWABLE + "Lnet/minecraft/world/damagesource/DamageSource;"
                            + "Lnet/minecraft/world/entity/Entity;FF)V"}),
            "com.atsuishio.superbwarfare.client.RenderHelper", List.<String[]>of(
                    new String[] {"isInGui", "()Z"}));

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        for (String[] bridge : BRIDGES.getOrDefault(targetClassName, List.of())) {
            addForwarder(targetClass, bridge[0], bridge[1]);
        }
    }

    /** Adds {@code public static <name><desc>} that calls LegacyApi's method of the same name and descriptor. */
    private static void addForwarder(ClassNode target, String name, String desc) {
        for (MethodNode existing : target.methods) {
            if (existing.name.equals(name) && existing.desc.equals(desc)) return; // a newer Superb Warfare has it again
        }
        MethodNode method = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, name, desc, null, null);
        int slot = 0;
        for (Type arg : Type.getArgumentTypes(desc)) {
            method.instructions.add(new VarInsnNode(arg.getOpcode(Opcodes.ILOAD), slot));
            slot += arg.getSize();
        }
        Type returns = Type.getReturnType(desc);
        method.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, LEGACY_API, name, desc, false));
        method.instructions.add(new InsnNode(returns.getOpcode(Opcodes.IRETURN)));
        method.maxLocals = slot;
        method.maxStack = Math.max(slot, returns.getSize());
        target.methods.add(method);
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
