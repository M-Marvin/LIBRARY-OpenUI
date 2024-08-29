package de.m_marvin.openui;

import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.openui.core.layout.BorderLayout;
import de.m_marvin.openui.core.layout.BorderLayout.BorderSection;
import de.m_marvin.openui.holographic.WindowHolographic;
import de.m_marvin.openui.holographic.components.FrameGroupBox;
import de.m_marvin.openui.holographic.components.GrabBar;
import de.m_marvin.openui.holographic.components.ResizeFrame;
import de.m_marvin.univec.impl.Vec2i;

public class TestWindow extends WindowHolographic {

	public TestWindow() {
		super("Holographic UI Design Test");
	}

	@Override
	protected void initUI() {
		
		Compound<ResourceLocation> root = getRootComponent();
		root.setLayout(new BorderLayout());
		
		ResizeFrame gr = new ResizeFrame(this.getMainWindow());
		gr.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
		gr.setLayout(new BorderLayout());
		
		int grabBorder = gr.getGrabFrameWidth();

		GrabBar g = new GrabBar(this.getMainWindow(), this.getWindowName());
		g.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.TOP));
		g.setMargin(grabBorder, grabBorder, grabBorder, 4);
		gr.addComponent(g);
		
		FrameGroupBox cnt = new FrameGroupBox();
		cnt.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
		cnt.setSizeMin(new Vec2i(60, 40));
		cnt.setSizeMax(new Vec2i(-1, -1));
		cnt.setMargin(grabBorder, grabBorder, 0, grabBorder);
		gr.addComponent(cnt);
		
		gr.autoSetMaxAndMinSize();
		
		root.addComponent(gr);
		root.autoSetMaxAndMinSize();
		this.autoSetMinAndMaxSize();
		
	}

}
