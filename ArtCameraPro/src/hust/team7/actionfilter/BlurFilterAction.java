package hust.team7.actionfilter;

import android.graphics.Bitmap;
import hust.team7.filter.BlurFilter;
import hust.team7.filter.util.*;
public class BlurFilterAction {
	private Bitmap bmp;
	
	public BlurFilterAction(Bitmap bmp) {
		this.bmp = bmp;
	}
	
	public Bitmap action() {
		int height = bmp.getHeight();
		int width = bmp.getWidth();
		
		int[] colors = AndroidUtils.bitmapToIntArray(bmp);
		BlurFilter blurFilter = new BlurFilter();
		colors = blurFilter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height, Bitmap.Config.ARGB_8888);
		return bmp;
	}
}
