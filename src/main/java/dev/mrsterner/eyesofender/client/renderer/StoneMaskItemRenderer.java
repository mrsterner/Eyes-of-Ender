package dev.mrsterner.eyesofender.client.renderer;

import dev.mrsterner.eyesofender.client.model.armor.StoneMaskModel;
import dev.mrsterner.eyesofender.common.item.StoneMaskItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class StoneMaskItemRenderer extends GeoItemRenderer<StoneMaskItem> {
	public StoneMaskItemRenderer() {
		super(new StoneMaskModel());
	}
}
