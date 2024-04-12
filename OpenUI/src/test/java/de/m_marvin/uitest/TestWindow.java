package de.m_marvin.uitest;

import java.awt.Color;

import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.openui.core.layout.BorderLayout;
import de.m_marvin.openui.core.layout.BorderLayout.BorderSection;
import de.m_marvin.openui.core.layout.BorderLayout.CornerStretch;
import de.m_marvin.openui.core.layout.GridLayout;
import de.m_marvin.openui.flatmono.Window;
import de.m_marvin.openui.flatmono.components.ButtonComponent;
import de.m_marvin.openui.flatmono.components.GraphComponent;
import de.m_marvin.openui.flatmono.components.GraphComponent.Graph;
import de.m_marvin.openui.flatmono.components.GroupBox;
import de.m_marvin.openui.flatmono.components.TextFieldComponent;
import de.m_marvin.openui.flatmono.components.ToggleButtonComponent;
import de.m_marvin.renderengine.resources.defimpl.ResourceLocation;
import de.m_marvin.univec.impl.Vec2d;
import de.m_marvin.univec.impl.Vec2i;

public class TestWindow extends Window {
	
	public TestWindow() {
		super("Test Window");
	}

	public static final int CHANNELS = 8;
	
	protected GraphComponent gr_readings;
	protected Graph[] graphs;
	
	protected TextFieldComponent tf_channelId;
	protected TextFieldComponent tf_channelName;
	protected ToggleButtonComponent tb_channelEnable;
	protected TextFieldComponent tf_minValue;
	protected ButtonComponent bt_calibrateMin;
	protected TextFieldComponent tf_maxValue;
	protected ButtonComponent bt_calibrateMax;
	protected TextFieldComponent tf_captureValue;
	protected ButtonComponent bt_capture;
	
	@Override
	protected void initUI() {
		

		Compound<ResourceLocation> bg = this.getRootComponent();
		bg.setLayout(new BorderLayout(CornerStretch.HORIZONTAL));
		
		GroupBox center = new GroupBox();
		center.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
		center.setLayout(new BorderLayout());

		this.gr_readings = new GraphComponent(0, 100, -4700, 4700);
		this.gr_readings.setScalaNumberScaleY(500);
		this.gr_readings.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
		this.gr_readings.resetSizeLimits();
		center.addComponent(gr_readings);
		
		this.graphs = new Graph[CHANNELS];
		for (int i = 0; i < CHANNELS; i++) {
			this.graphs[i] = new Graph("CHN " + (i + 1), Color.RED);
			this.gr_readings.addGraph(this.graphs[i]);
		}
		
		for (int i1 = 0; i1 < 10; i1++) {
			for (int i = 0; i < CHANNELS; i++) {
				this.graphs[i].getDataPoints().addLast(new Vec2d(i1 * 10, 0));
			}
		}
		
		center.setSizeMax(new Vec2i(-1, -1));
		center.autoSetMinSize();
		bg.addComponent(center);
		
		GroupBox bottom = new GroupBox();
		bottom.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.BOTTOM));
		bottom.setLayout(new GridLayout());
		
		this.tf_channelId = new TextFieldComponent();
		this.tf_channelId.setLayoutData(new GridLayout.GridLayoutData(0, 0));
		bottom.addComponent(tf_channelId);
		
		this.tf_channelName = new TextFieldComponent();
		this.tf_channelName.setLayoutData(new GridLayout.GridLayoutData(0, 1));
		bottom.addComponent(tf_channelName);
		
		this.tb_channelEnable = new ToggleButtonComponent("Channel Enbl");
		this.tb_channelEnable.setLayoutData(new GridLayout.GridLayoutData(0, 2));
		bottom.addComponent(tb_channelEnable);
		
		Compound<ResourceLocation> comp1 = new GroupBox();
		comp1.setLayoutData(new GridLayout.GridLayoutData(1, 0));
		this.tf_minValue = new TextFieldComponent();
		this.tf_minValue.setOffset(new Vec2i(0, 0));
//		this.tf_minValue.setSize(new Vec2i());
		this.tf_maxValue = new TextFieldComponent();
		this.tf_maxValue.setOffset(new Vec2i(0, 0));
		comp1.autoSetMaxAndMinSize();
		bottom.addComponent(comp1);
		
		Compound<ResourceLocation> comp2 = new GroupBox();
		comp2.setLayoutData(new GridLayout.GridLayoutData(1, 1));
		this.bt_calibrateMin = new ButtonComponent("Calib. Min");
		this.bt_calibrateMin.setOffset(new Vec2i(0, 0));
//		this.tf_minValue.setSize(new Vec2i());
		this.bt_calibrateMax = new ButtonComponent("Calib. Max");
		this.bt_calibrateMax.setOffset(new Vec2i(0, 0));
		comp2.autoSetMaxAndMinSize();
		bottom.addComponent(comp2);
		
		System.out.println("comp2 " + comp2.getSizeMax());
		
		Compound<ResourceLocation> comp3 = new Compound<>();
		comp3.setLayoutData(new GridLayout.GridLayoutData(1, 2));
		bottom.addComponent(comp3);
		
		bottom.autoSetMaxAndMinSize();
		bg.addComponent(bottom);
		
		bg.autoSetMaxAndMinSize();
		this.autoSetMinAndMaxSize();
		
		
////		this.getRootComponent().setLayout(new GridLayout());
////		
////		GroupBox leftBox = new GroupBox();
////		leftBox.setSizeMin(new Vec2i(500, 600));
////		leftBox.setSizeMax(new Vec2i(500, 600));
////		leftBox.setLayoutData(new GridLayout.GridLayoutData(0, 0));
////		this.getRootComponent().addComponent(leftBox);
////
////		GroupBox rightBox = new GroupBox();
////		rightBox.setSizeMin(new Vec2i(500, 600));
////		rightBox.setSizeMax(new Vec2i(600, 700));
////		rightBox.setLayoutData(new GridLayout.GridLayoutData(1, 0));
////		this.getRootComponent().addComponent(rightBox);
////		
////		this.autoSetMinSize();
//		
//		this.getRootComponent().setLayout(new BorderLayout(CornerStretch.VERTICAL));
//		
//		GroupBox b = new GroupBox();
//		b.setSizeMin(new Vec2i(100, 100));
//		b.setSizeMax(new Vec2i(150, -1));
//		b.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.LEFT));
//		
//		ScrollBarComponent scrollbar1 = new ScrollBarComponent(false, 200, 60);
//		scrollbar1.setOffset(new Vec2i(40, 40));
//		b.addComponent(scrollbar1);
//		
//		GroupBox b1 = new GroupBox();
//		b1.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.TOP));
//		b1.setLayout(new GridLayout());
//
//		String[] titles = new String[] {"Test", "Button", "12345", "!?\\@Ä", "ABCDEFGHIJKLMNOP"};
//		Random r = new Random();
//		
//		for (int i = 0; i < 4; i++) {
//			for (int t = 0; t < 2; t++) {
//				
//				TextFieldComponent b6 = new TextFieldComponent();
//				b6.setText(titles[r.nextInt(titles.length)]);
//				b6.setLayoutData(new GridLayout.GridLayoutData(i, t + 10));
//				b1.addComponent(b6);
//				
//			}
//		}
//		
//		GraphComponent gc = new GraphComponent(0, 100, -50, 50);
//		gc.setLayoutData(new GridLayout.GridLayoutData(4, 0));
//		gc.setSize(new Vec2i(600, 300));
//		gc.fixSize();
//		b1.addComponent(gc);
//		
//		Graph graph = new GraphComponent.Graph("Test", new Color(0, 255, 0, 255));
//		gc.addGraph(graph);
//		graph.getDataPoints().add(new Vec2d(0, 25));
//		graph.getDataPoints().add(new Vec2d(40, -25));
//		graph.getDataPoints().add(new Vec2d(60, -40));
//		graph.getDataPoints().add(new Vec2d(80, 40));
//		graph.getDataPoints().add(new Vec2d(100, 45));
//
//		Graph graph2 = new GraphComponent.Graph("Test", new Color(255, 0, 0, 255));
//		gc.addGraph(graph2);
//		graph2.getDataPoints().add(new Vec2d(0, 20));
//		graph2.getDataPoints().add(new Vec2d(20, -20));
//		graph2.getDataPoints().add(new Vec2d(40, -30));
//		graph2.getDataPoints().add(new Vec2d(65, 00));
//		graph2.getDataPoints().add(new Vec2d(70, 23));
//		
//		b1.autoSetMaxAndMinSize();
//		b1.getSizeMax().setX(0);
//		
//		GroupBox b2 = new GroupBox();
//		b2.setSize(new Vec2i(100, -1));
//		b2.fixSize();
//		b2.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.RIGHT));
//		
//		BarComponent bar2 = new BarComponent(false);
//		bar2.setValue(70);
//		bar2.setSize(new Vec2i(20, 300));
//		bar2.setOffset(new Vec2i(30, 40));
//		b2.addComponent(bar2);
//		
//		GroupBox b3 = new GroupBox();
//		b3.setSizeMin(new Vec2i(100, 100));
//		b3.setSizeMax(new Vec2i(0, 150));
//		b3.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.BOTTOM));
//
//		ScrollBarComponent scrollbar2 = new ScrollBarComponent(true, 200, 60);
//		scrollbar2.setOffset(new Vec2i(40, 40));
//		b3.addComponent(scrollbar2);
//		
//		BarComponent bar1 = new BarComponent();
//		bar1.setValue(50);
//		bar1.setSize(new Vec2i(300, 20));
//		bar1.setOffset(new Vec2i(240, 40));
//		b3.addComponent(bar1);
//		
//		GroupBox b5 = new GroupBox();
//		b5.setLayoutData(new BorderLayout.BorderLayoutData(BorderSection.CENTERED));
//		b5.setLayout(new GridLayout());
//		b5.setSizeMin(new Vec2i(10 * 60, 10 * 20));
//		
//		for (int i = 0; i < 10; i++) {
//			for (int t = 0; t < 10; t++) {
//				
//				Color color = Color.WHITE;
//				if (!(i == 0 || i == 9 || t == 0 || t == 9)) color = Color.RED;
//				
//				ButtonComponent b6 = new ButtonComponent(titles[r.nextInt(titles.length)], color);
//				b6.setLayoutData(new GridLayout.GridLayoutData(i, t));
//				int a = i;
//				int c = t;
//				b6.setAction(() -> System.out.println("Clicked on " + a + "/" + c));
//				b5.addComponent(b6);
//				
//				b6.setAction(() -> gc.stepValueLimit(5, 0));
//				
//			}
//		}
//		
//		for (int i = 0; i < 10; i++) {
//			for (int t = 0; t < 2; t++) {
//				
//				ToggleButtonComponent b6 = new ToggleButtonComponent(titles[r.nextInt(titles.length)]);
//				b6.setLayoutData(new GridLayout.GridLayoutData(i, t + 10));
//				int a = i;
//				int c = t;
//				b6.setAction((state) -> {
//					System.out.println("ToggleX " + (state ? "on" : "off") + " " + a + "/" + c);
//					b.setVisible(state);
//				});
//				b5.addComponent(b6);
//				
//			}
//		}
//		
//		
//		b5.autoSetMinSize();
//		
//		this.getRootComponent().addComponent(b);
//		this.getRootComponent().addComponent(b1);
//		this.getRootComponent().addComponent(b2);
//		this.getRootComponent().addComponent(b3);
//		this.getRootComponent().addComponent(b5);
//		this.getRootComponent().autoSetMaxAndMinSize();
//		this.autoSetMinAndMaxSize();
////		this.setAdjustMaxScale(true);
		
	}
	
}
