package hust.team7.actionfilter;

import hust.team7.filter.GainFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class GainFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private float gainValue = 0.34f;
	private float biasValue = 0.44f;

	public GainFilterAction(Bitmap bmp) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
	}

	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		GainFilter filter = new GainFilter();
		filter.setGain(gainValue);
		filter.setBias(biasValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
