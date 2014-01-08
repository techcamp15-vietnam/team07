package hust.team7.actionfilter;

import hust.team7.filter.ThresholdFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class ThresholdFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int lowerValue;
	private int upperValue;

	public ThresholdFilterAction(Bitmap bmp, int lowerValue, int upperValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}

	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		ThresholdFilter filter = new ThresholdFilter();
		filter.setLowerThreshold(lowerValue);
		filter.setUpperThreshold(upperValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
