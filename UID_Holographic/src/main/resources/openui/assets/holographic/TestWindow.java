package openui.assets.holographic;

import de.m_marvin.gframe.resources.defimpl.ResourceLocation;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.openui.core.layout.BorderLayout;
import de.m_marvin.openui.core.layout.BorderLayout.BorderSection;
import de.m_marvin.openui.holographic.WindowHolographic;
import de.m_marvin.openui.holographic.components.GrabBar;
import de.m_marvin.openui.holographic.components.ResizableGroupBox;

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

		GrabBar g = new GrabBar(this.getMainWindow());
		gr.addComponent(g);
		
		
		root.addComponent(gr);
		
	}

}
