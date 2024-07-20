package de.m_marvin.openui.core.layout;

import java.util.List;

import de.m_marvin.gframe.resources.IResourceProvider;
import de.m_marvin.openui.core.components.Compound;
import de.m_marvin.univec.impl.Vec2i;

public abstract class Layout<T extends Layout.LayoutData> {
	
	public static class LayoutData {}

	public abstract Vec2i getMinSizeRequired();
	public abstract Vec2i getMaxSizeRequired();
	public abstract <R extends IResourceProvider<R>> void rearange(Compound<R> compound, List<Compound<R>> childComponents);

	public abstract Class<T> getDataClass();
	
	public static int[] fitSizes(int totalSize, int[] ... sizeMinAndMax)  {

		int min = 0, max = 0;
		int[] variations = new int[sizeMinAndMax.length];
		for (int i = 0; i < sizeMinAndMax.length; i++) {
			if (sizeMinAndMax[i] == null) {
				variations[i] = 0;
				continue;
			}
			variations[i] = sizeMinAndMax[i][1] == -1 ? -1 : sizeMinAndMax[i][1] - (sizeMinAndMax[i][0] >= 0 ? sizeMinAndMax[i][0] : 0);
			min += Math.max(sizeMinAndMax[i][0], 0);
			max += Math.max(sizeMinAndMax[i][1], Math.max(sizeMinAndMax[i][0], 0));
		}
		
		float variationFactor = max > 0 && min < max ? Math.min((totalSize - min) / (float) (max - min), 1.0F) : 1.0F;
		
		int[] sizes = new int[variations.length];
		int totalSetSize = 0;
		int numNonSet = 0;
		for (int i = 0; i < variations.length; i++) {
			if (sizeMinAndMax[i] == null) {
				sizes[i] = 0;
			} else if (variations[i] >= 0) {
				sizes[i] = Math.round(variations[i] * variationFactor + sizeMinAndMax[i][0]);
				totalSetSize += sizes[i];
			} else {
				sizes[i] = -1;
				totalSetSize += Math.max(sizeMinAndMax[i][0], 0);
				numNonSet++;
			}
		}
		
		int sizeRemaining = Math.max(totalSize - totalSetSize, 0);
		
		for (int i = 0; i < sizes.length; i++) {
			if (sizes[i] == -1) {
				sizes[i] = sizeMinAndMax[i][0] + Math.round(sizeRemaining / (float) numNonSet);
			}
		}
		
		return sizes;
	}
	
	public static int[] widthMinMax(Compound<?> component) {
		return component == null ? null : new int[] {component.getSizeMinMargin().x, component.getSizeMaxMargin().x};
	}

	public static int[] heightMinMax(Compound<?> component) {
		return component == null ? null : new int[] {component.getSizeMinMargin().y, component.getSizeMaxMargin().y};
	}
	
	public static int[] totalMinAndMax(int[] ... sizeMinAndMax) {
		if (sizeMinAndMax.length == 0) return null;
		int min = -2;
		int max = -2;
		for (int i = 0; i < sizeMinAndMax.length; i++) {
			if (sizeMinAndMax[i] == null) continue;
			if (sizeMinAndMax[i][0] > min) min = sizeMinAndMax[i][0];
			if ((sizeMinAndMax[i][1] < max || max <= -1) && (sizeMinAndMax[i][1] >= 0 || max < -1)) max = sizeMinAndMax[i][1];
		}
		if (min < -1 || max < -1) return null;
		if (min > max && max != -1) return new int[] {min, min};
		return new int[] {min, max};
	}
	
	public static int minSizeRequired(int[]... sizeMinAndMax) {
		if (sizeMinAndMax.length == 0) return -1;
		int min = -1;
		for (int[] minAndMax : sizeMinAndMax) {
			if (minAndMax == null) continue;
			if (minAndMax[0] != -1) min += minAndMax[0];
		}
		return min;
	}

	public static int maxSizeRequired(int[]... sizeMinAndMax) {
		if (sizeMinAndMax.length == 0) return -1;
		int max = -1;
		for (int[] minAndMax : sizeMinAndMax) {
			if (minAndMax == null) continue;
			if (minAndMax[1] == -1) return -1;
			max += minAndMax[1];
		}
		return max;
	}
	
}
