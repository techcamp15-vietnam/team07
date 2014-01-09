package hust.team7.actionfilter;

import hust.team7.filter.GrayscaleFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class GrayscaleFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	
	/**
	Constructor GrayscaleFilterAction
	@param bmp
	@author 7-A Bui Quang Tan
	*/
	public GrayscaleFilterAction(Bitmap bmp) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
	}
	
	/**
	Handler Grayscale Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		GrayscaleFilter filter = new GrayscaleFilter();
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}
}
