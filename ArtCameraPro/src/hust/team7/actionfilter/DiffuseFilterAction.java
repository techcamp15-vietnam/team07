package hust.team7.actionfilter;

import hust.team7.filter.DiffuseFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class DiffuseFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int scaleValue;

	/**
	 * Constructor DiffuseFilterAction
	 * @param bmp, scaleValue
	 * @author 7-A Bui Quang Tan
	 */
	public DiffuseFilterAction(Bitmap bmp, int scaleValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.scaleValue = scaleValue;
	}

	/**
	 * Handler Diffuse Effect
	 * @param no param
	 * @author 7-A Bui Quang Tan
	 */
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		DiffuseFilter filter = new DiffuseFilter();
		filter.setScale(scaleValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
