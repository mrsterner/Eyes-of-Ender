package dev.mrsterner.eyesofender.client.vertexconsumer;

import com.mojang.blaze3d.vertex.VertexConsumer;

public final class EmptyVertexConsumer implements VertexConsumer {
	@Override
	public VertexConsumer vertex(double x, double y, double z) {
		return this;
	}

	@Override
	public VertexConsumer color(int red, int green, int blue, int alpha) {
		return this;
	}

	@Override
	public VertexConsumer uv(float u, float v) {
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
	public void next() {
	}

	@Override
	public void fixColor(int red, int green, int blue, int alpha) {
	}

	@Override
	public void unfixColor() {
	}
}
