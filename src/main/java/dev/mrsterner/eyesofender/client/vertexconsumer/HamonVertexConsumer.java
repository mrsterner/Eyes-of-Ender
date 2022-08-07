package dev.mrsterner.eyesofender.client.vertexconsumer;

import com.mojang.blaze3d.vertex.FixedColorVertexConsumer;
import com.mojang.blaze3d.vertex.VertexConsumer;

public final class HamonVertexConsumer extends FixedColorVertexConsumer {
	private final VertexConsumer delegate;
	private double x;
	private double y;
	private double z;
	private float u;
	private float v;

	HamonVertexConsumer(VertexConsumer delegate, int red, int green, int blue, int alpha) {
		this.delegate = delegate;
		super.fixColor(red, green, blue, alpha);
	}

	@Override
	public void fixColor(int red, int green, int blue, int alpha) {
	}

	@Override
	public void unfixColor() {
	}

	@Override
	public VertexConsumer vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public VertexConsumer color(int red, int green, int blue, int alpha) {
		return this;
	}

	@Override
	public VertexConsumer uv(float u, float v) {
		this.u = u;
		this.v = v;
		return this;
	}

	@Override
	public VertexConsumer overlay(int u, int v) {
		return this;
	}

	@Override
	public VertexConsumer light(int u, int v) {
		return this;
	}

	@Override
	public VertexConsumer normal(float x, float y, float z) {
		return this;
	}

	@Override
	public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {
		this.delegate.vertex(x, y, z).color(this.fixedRed, this.fixedGreen, this.fixedBlue, this.fixedAlpha).uv(u, v).next();
	}

	@Override
	public void next() {
		this.delegate.vertex(this.x, this.y, this.z).color(this.fixedRed, this.fixedGreen, this.fixedBlue, this.fixedAlpha).uv(this.u, this.v).next();
	}
}
