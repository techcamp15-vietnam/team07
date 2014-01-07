package hust.team7.actionfilter;

import android.graphics.Bitmap;
import hust.team7.filter.BlurFilter;
import hust.team7.filter.GaussianFilter;
import hust.team7.filter.util.*;

public class GaussianFilterAction {
	private Bitmap bmp;

	public GaussianFilterAction(Bitmap bmp) {
		this.bmp = bmp;
	}

	public Bitmap action() {
		int height = bmp.getHeight();
		int width = bmp.getWidth();

		int[] colors = AndroidUtils.bitmapToIntArray(bmp);
		GaussianFilter gaussFilter = new GaussianFilter();
		
		gaussFilter.setRadius(5);
		
		colors = gaussFilter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;
	}
}
