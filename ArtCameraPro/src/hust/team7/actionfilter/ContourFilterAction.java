package hust.team7.actionfilter;

import hust.team7.filter.ContourFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class ContourFilterAction {
	private Bitmap bmp;
	private float levelValue = 15f;
	private float offsetValue = 0.78f;
	private float scaleValue = 0.24f;
	private int width, height;
	private int[] colors;
	public ContourFilterAction(Bitmap bmp) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.colors = AndroidUtils.bitmapToIntArray(this.bmp);
	}
	
	public Bitmap action() {
		ContourFilter filter = new ContourFilter();
		filter.setLevels(levelValue);
		filter.setScale(scaleValue);
		filter.setOffset(offsetValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;
	}
}
