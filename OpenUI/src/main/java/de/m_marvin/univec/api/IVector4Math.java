package de.m_marvin.univec.api;

/*
 * Declares all mathematical methods for the 3d vectors
 */
@SuppressWarnings("unchecked")
public interface IVector4Math<N extends Number, VO extends IVector4<N>, VI extends IVector4<? extends Number>> extends IVector4<N> {
	
	/* Basic math */
	
	public VO setI(N x, N y, N z, N w);
	public VO setI(VI vec);
	
	public IVector4Math<N, VO, VI> copy();
	
	public VO add(VI vec);
	public VO add(N x, N y, N z, N w);
	default public VO addI(VI vec) { return this.setI((VI) this.add(vec)); }
	default public VO addI(N x, N y, N z, N w) { return this.setI((VI) this.add(x, y, z, w)); }
	
	public VO sub(VI vec);
	public VO sub(N x, N y, N z, N w);
	default public VO subI(VI vec) { return this.setI((VI) this.sub(vec)); }
	default public VO subI(N x, N y, N z, N w) { return this.setI((VI) this.sub(x, y, z, w)); }
	
	public VO mul(VI vec);
	public VO mul(N x, N y, N z, N w);
	public VO mul(N n);
	default public VO mulI(VI vec) { return this.setI((VI) this.mul(vec)); }
	default public VO mulI(N x, N y, N z, N w) { return this.setI((VI) this.mul(x, y, z, w)); }
	default public VO mulI(N n) { return this.setI((VI) this.mul(n)); }
	
	public VO div(VI vec);
	public VO div(N x, N y, N z, N w);
	public VO div(N n);
	default public VO divI(VI vec) { return this.setI((VI) this.div(vec)); }
	default public VO divI(N x, N y, N z, N w) { return this.setI((VI) this.div(x, y, z, w)); }
	default public VO divI(N n) { return this.setI((VI) this.div(n)); }
	
	public VO clamp(VI min, VI max);
	public VO clamp(N min, N max);
	default public VO clampI(VI min, VI max) { return this.setI((VI) this.clamp(min, max)); }
	default public VO clampI(N min, N max) { return this.setI((VI) this.clamp(min, max)); }
	
	/* Vector math */
	
	public double angle(VI vec);
	public N dot(VI vec);
	public VO cross(VI vec);
	public VO lerp(VI vec, N delta);
	
	public VO module(N m);
	default public VO moduleI(N m) { return this.setI((VI) this.module(m)); }
	
	public VO normalize();
	default public VO normalizeI() { return this.setI((VI) this.normalize()); }
	
}
