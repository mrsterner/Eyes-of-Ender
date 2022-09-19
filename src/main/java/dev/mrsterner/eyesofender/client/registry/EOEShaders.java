package dev.mrsterner.eyesofender.client.registry;

import com.mojang.blaze3d.vertex.VertexFormats;
import com.mojang.datafixers.util.Pair;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.shader.EOEShaderProgram;
import dev.mrsterner.eyesofender.client.shader.ShaderInstance;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EOEShaders {

    public static List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaderList;
    public static final ShaderInstance DISTORTED_TEXTURE = new ShaderInstance("Speed", "TimeOffset", "Amplitude", "FreqX", "FreqY", "UV");
    public static final ShaderInstance HAMON = new ShaderInstance("Speed", "Color");

    public static void init(ResourceManager manager) throws IOException {
        shaderList = new ArrayList<>();
        registerShader(EOEShaderProgram.createShaderInstance(DISTORTED_TEXTURE, manager, EyesOfEnder.id("eoe__distortion"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(EOEShaderProgram.createShaderInstance(HAMON, manager, EyesOfEnder.id("eoe__hamon"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));


    }

    public static void registerShader(EOEShaderProgram EOEShaderProgramInstance) {
        shaderList.add(Pair.of(EOEShaderProgramInstance, (shader) -> ((EOEShaderProgram) shader).getHolder().setInstance(shader)));
    }
}
