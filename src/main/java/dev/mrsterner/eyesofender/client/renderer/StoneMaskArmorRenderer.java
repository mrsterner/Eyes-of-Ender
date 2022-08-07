package dev.mrsterner.eyesofender.client.renderer;

import dev.mrsterner.eyesofender.client.model.armor.StoneMaskModel;
import dev.mrsterner.eyesofender.common.item.StoneMaskItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class StoneMaskArmorRenderer extends GeoArmorRenderer<StoneMaskItem> {
	public StoneMaskArmorRenderer() {
		super(new StoneMaskModel());
		this.headBone = "armorHead";
	}
}
