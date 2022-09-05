package de.m_marvin.openui;

import de.m_marvin.render.vertex.VertexFormat;
import de.m_marvin.render.vertex.VertexFormat.Format;
import de.m_marvin.render.vertex.VertexFormat.Usage;
import de.m_marvin.render.vertex.VertexFormat.VertexElement;

public class VertexFormats {
	
	public static final VertexElement VERTEX = new VertexElement(Format.FLOAT, Usage.VERTEX, 3);
	public static final VertexElement NORMAL = new VertexElement(Format.FLOAT, Usage.NORMAL, 3);
	public static final VertexElement COLOR = new VertexElement(Format.UBYTE, Usage.COLOR, 4);
	public static final VertexElement UV = new VertexElement(Format.FLOAT, Usage.UV, 2);
	
	public static final VertexFormat UI_DEFAULT = new VertexFormat.Builder().put("Vertex", VERTEX).put("Color", COLOR).put("UB", UV).build();
	
}
