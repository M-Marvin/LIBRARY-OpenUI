package de.m_marvin.univec.api;

/*
 * Declares all mathematical methods for the 2d vectors
 */
@SuppressWarnings("unchecked")
public interface IVector2Math<N extends Number, VO extends IVector2<N>, VI extends IVector2<? extends Number>> extends IVector2<N> {
	
	/* Basic math */
	
	public VO setI(N x, N y);
	public VO setI(VI vec);
	
	public IVector2Math<N, VO, VI> copy();
	
	public VO add(VI vec);
	public VO add(N x, N y);
	default public VO addI(VI vec) { return this.setI((VI) this.add(vec)); }
	default public VO addI(N x, N y) { return this.setI((VI) this.add(x, y)); }
	
	public VO sub(VI vec);
	public VO sub(N x, N y);
	default public VO subI(VI vec) { return this.setI((VI) this.sub(vec)); }
	default public VO subI(N x, N y) { return this.setI((VI) this.sub(x, y)); }
	
	public VO mul(VI vec);
	public VO mul(N x, N y);
	public VO mul(N n);
	default public VO mulI(VI vec) { return this.setI((VI) this.mul(vec)); }
	default public VO mulI(N x, N y) { return this.setI((VI) this.mul(x, y)); }
	default public VO mulI(N n) { return this.setI((VI) this.mul(n)); }
	
	public VO div(VI vec);
	public VO div(N x, N y);
	public VO div(N n);
	default public VO divI(VI vec) { return this.setI((VI) this.div(vec)); }
	default public VO divI(N x, N y) { return this.setI((VI) this.div(x, y)); }
	default public VO divI(N n) { return this.setI((VI) this.div(n)); }
	
	public VO clamp(VI min, VI max);
	public VO clamp(N min, N max);
	default public VO clampI(VI min, VI max) { return this.setI((VI) this.clamp(min, max)); }
	default public VO clampI(N min, N max) { return this.setI((VI) this.clamp(min, max)); }
	
	/* Vector math */

	public double angle(VI vec);
	public N cross(VI vec);
	public N dot(VI vec);
	public VO lerp(VI vec, N delta);
		
	public VO module(N m);
	default public VO moduleI(N m) { return this.setI((VI) this.module(m)); }
	
	public VO normalize();
	default public VO normalizeI() { return this.setI((VI) this.normalize()); }
	
}
