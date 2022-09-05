package de.m_marvin.openui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import de.m_marvin.render.GLStateManager;
import de.m_marvin.render.dispatch.RenderDispatcher;
import de.m_marvin.render.translation.Matrix3f;
import de.m_marvin.render.translation.Matrix4f;
import de.m_marvin.render.translation.MatrixStack;
import de.m_marvin.render.vertex.IBufferSource;
import de.m_marvin.render.vertex.IVertexConsumer;
import de.m_marvin.render.vertex.MultiBufferSource;
import de.m_marvin.render.vertex.RenderType;
import de.m_marvin.render.vertex.VertexBuilder;
import de.m_marvin.render.vertex.VertexFormat;
import de.m_marvin.render.vertex.VertexFormat.VertexElement;

public class OpenUI {
	
	long window;
	MultiBufferSource bufferSource;
	RenderDispatcher renderDispatcher;
	
	public static final VertexElement VERTEX = new VertexElement(VertexFormat.Format.FLOAT, VertexFormat.Usage.VERTEX, 3);
	public static final VertexElement NORMAL = new VertexElement(VertexFormat.Format.FLOAT, VertexFormat.Usage.NORMAL, 3);
	public static final VertexElement COLOR = new VertexElement(VertexFormat.Format.UBYTE, VertexFormat.Usage.COLOR, 4);
	public static final VertexElement UV = new VertexElement(VertexFormat.Format.FLOAT, VertexFormat.Usage.UV, 2);
	public static final VertexFormat TEST_FORMAT = new VertexFormat.Builder().put("vertex", VERTEX).put("normal", NORMAL).put("color", COLOR).put("texCoord", UV).build();
	public static final RenderType TEST_TYPE = new RenderType(VertexFormat.Mode.TRIANGLE, TEST_FORMAT);
	public static final RenderType TEST_TYPE_NONFIX = new RenderType(VertexFormat.Mode.TRIANGLE, TEST_FORMAT);
	
	public void start() {
		
		GLFW.glfwInit();		
		GLFW.glfwDefaultWindowHints();
		this.window = GLFW.glfwCreateWindow(1000, 600, "TEST", 0, 0);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		this.bufferSource = new MultiBufferSource(256, 64);
		this.bufferSource.addFixedBuffer(TEST_TYPE, new VertexBuilder(256, 64, true));
		
		this.renderDispatcher = new RenderDispatcher();
		this.renderDispatcher.init();
		
		while (!GLFW.glfwWindowShouldClose(window)) {
			
			GLStateManager.clearColor(1, 0, 1, 1);
			GLStateManager.clearBufferBit(GLStateManager.GL_COLOR_BUFFER_BIT | GLStateManager.GL_DEPTH_BUFFER_BIT);
			
			render();
			
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
		
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		
	}
	
	
	public void render() {
		
		MatrixStack stack = new MatrixStack(Matrix4f.identity(), Matrix3f.identity());
		
		testDraw(stack, this.bufferSource);
		
		this.bufferSource.endOfFrame();
		
		this.renderDispatcher.dispatchBufferBuilder(this.bufferSource.getFinishedBuffer(TEST_TYPE));
		this.renderDispatcher.dispatchBufferBuilder(this.bufferSource.getFinishedBuffer(TEST_TYPE_NONFIX));
		
	}
	
	public void testDraw(MatrixStack matrixStack, IBufferSource bufferSource) {
		
		IVertexConsumer vertexBuilder = bufferSource.getBuffer(TEST_TYPE);
		Matrix4f pose = matrixStack.last().pose();
		Matrix3f normal = matrixStack.last().normal();
		
		vertexBuilder.vertex(pose, -1, -1, 0).normal(normal, 0, 0, 1).color(1, 0, 0, 1).uv(0, 0).endVertex();
		vertexBuilder.vertex(pose, 1, -1, 0).normal(normal, 0, 0, 1).color(1, 0, 0, 1).uv(0, 0).endVertex();
		vertexBuilder.vertex(pose, 0, 1, 0).normal(normal, 0, 0, 1).color(1, 0, 0, 1).uv(0, 0).endVertex();
		
	}
	
}
