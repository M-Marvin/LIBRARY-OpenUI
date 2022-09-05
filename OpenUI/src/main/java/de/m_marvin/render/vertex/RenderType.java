package de.m_marvin.render.vertex;

import java.util.function.BiConsumer;

public record RenderType(
		VertexFormat.Mode mode, 
		VertexFormat format
	) {}
