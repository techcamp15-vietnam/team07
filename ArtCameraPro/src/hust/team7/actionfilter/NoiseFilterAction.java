package hust.team7.actionfilter;

import hust.team7.filter.NoiseFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class NoiseFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int amountValue;
	private int densityValue;

	/**
	 * Constructor NoiseFilterAction
	 * @param bmp, amountValue, densityValue
	 * @author 7-A Bui Quang Tan
	 */
	public NoiseFilterAction(Bitmap bmp, int amountValue, int densityValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.amountValue = amountValue;
		this.densityValue = densityValue;
	}

	/**
	 * Handler Noise Effect
	 * @param no param
	 * @author 7-A Bui Quang Tan
	 */
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		NoiseFilter filter = new NoiseFilter();
		filter.setAmount(amountValue);
		filter.setDensity(densityValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
