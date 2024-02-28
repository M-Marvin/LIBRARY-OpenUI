package de.m_marvin.openui.core.layout;

import java.util.List;

import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.renderengine.resources.IResourceProvider;
import de.m_marvin.univec.impl.Vec2i;

public class AlignmentLayout extends Layout<AlignmentLayout.AlignmentLayoutData> {
	
	public static enum Alignment {
		LEFT, RIGHT, TOP, BOTTOM, CENTERED
	}
	
	public static class AlignmentLayoutData extends Layout.LayoutData {
		public final Alignment alignment;
		public AlignmentLayoutData() {
			this.alignment = Alignment.CENTERED;
		}
		public AlignmentLayoutData(Alignment alignment) {
			this.alignment = alignment;
		}
	}
	
	public AlignmentLayout() {}
	
	@Override
	public Class<AlignmentLayoutData> getDataClass() {
		return AlignmentLayoutData.class;
	}
	
	protected Vec2i minSizeRequired = new Vec2i();
	protected Vec2i maxSizeRequired = new Vec2i();
	
	@Override
	public <R extends IResourceProvider<R>> void rearange(Compound<R> compound, List<Compound<R>> childComponents) {
		
		this.minSizeRequired = new Vec2i(0, 0);
		this.maxSizeRequired = childComponents.size() > 0 ? childComponents.get(0).getSizeMaxMargin() : new Vec2i(20, 20);
		
		for (Compound<R> c : childComponents) {
			Alignment alignment = c.getLayoutData(this).alignment;
			
			Vec2i size = new Vec2i(
					c.getSizeMaxMargin().x <= 0 ? compound.getSize().x : Math.min(compound.getSize().x, c.getSizeMaxMargin().x),
					c.getSizeMaxMargin().y <= 0 ? compound.getSize().y : Math.min(compound.getSize().y, c.getSizeMaxMargin().y)
					);
			c.setSizeMargin(size.max(c.getSizeMinMargin()));
			
			switch (alignment) {
			case LEFT: 		c.setOffsetMargin(new Vec2i(0, compound.getSize().y / 2 - c.getSize().y / 2)); break;
			case RIGHT: 	c.setOffsetMargin(new Vec2i(compound.getSize().x - c.getSizeMargin().x, compound.getSize().y / 2 - c.getSizeMargin().y / 2)); break;
			case TOP: 		c.setOffsetMargin(new Vec2i(compound.getSize().x / 2 - c.getSize().x / 2, 0)); break;
			case BOTTOM: 	c.setOffsetMargin(new Vec2i(compound.getSize().x / 2 - c.getSize().x / 2, compound.getSize().y - c.getSizeMargin().y)); break;
			default:
			case CENTERED:	c.setOffsetMargin(new Vec2i(compound.getSize().x / 2 - c.getSize().x / 2, compound.getSize().y / 2 - c.getSize().y / 2)); break;
			}
			
			this.minSizeRequired = this.minSizeRequired.max(c.getSizeMinMargin());
			this.maxSizeRequired = this.maxSizeRequired.min(c.getSizeMaxMargin());
		}
		
	}

	@Override
	public Vec2i getMinSizeRequired() {
		return this.minSizeRequired;
	}

	@Override
	public Vec2i getMaxSizeRequired() {
		return this.maxSizeRequired;
	}

}
