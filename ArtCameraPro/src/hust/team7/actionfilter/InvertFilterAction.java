package hust.team7.actionfilter;

import hust.team7.filter.InvertFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class InvertFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;

	/**
	Constructor InvertFilterAction
	@param bmp
	@author 7-A Bui Quang Tan
	*/
	public InvertFilterAction(Bitmap bmp) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
	}

	/**
	Handler Invert Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		InvertFilter filter = new InvertFilter();
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
