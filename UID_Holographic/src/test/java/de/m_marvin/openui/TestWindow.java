package de.m_marvin.openui;

import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.openui.core.layout.BorderLayout;
import de.m_marvin.openui.core.layout.BorderLayout.BorderSection;
import de.m_marvin.openui.flatmono.components.GroupBox;
import de.m_marvin.openui.holographic.WindowHolographic;
import de.m_marvin.openui.holographic.components.GrabBar;
import de.m_marvin.openui.holographic.components.ResizableGroupBox;
import de.m_marvin.univec.impl.Vec2i;

public class TestWindow extends WindowHolographic {

	public TestWindow() {
		super("Holographic UI Design Test");
	}

	@Override
	protected void initUI() {
		
		Compound<ResourceLocation> root = getRootComponent();
		root.setLayout(new BorderLayout());
		
		ResizableGroupBox gr = new ResizableGroupBox(this.getMainWindow());
		gr.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
		gr.setLayout(new BorderLayout());

		GrabBar g = new GrabBar(this.getMainWindow());
		g.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.TOP));
		g.setSizeMin(new Vec2i(60, 20));
		g.setSizeMax(new Vec2i(-1, 20));
		
		GroupBox cnt = new GroupBox();
		cnt.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
		cnt.setSizeMin(new Vec2i(60, 40));
		cnt.setSizeMax(new Vec2i(-1, -1));
		cnt.setMargin(5, 5, 5, 5);
		gr.addComponent(cnt);
		
		gr.addComponent(g);
		gr.autoSetMaxAndMinSize();
		
		root.addComponent(gr);
		root.autoSetMaxAndMinSize();
		this.autoSetMinAndMaxSize();
		
	}

}
