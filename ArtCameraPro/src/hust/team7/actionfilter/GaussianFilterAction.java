package hust.team7.actionfilter;

import hust.team7.filter.GaussianFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class GaussianFilterAction {
	private Bitmap bmp;
	private int height;
	private int width;
	
	/**
	Constructor GaussianFilterAction
	@param bmp
	@author 7-A Bui Quang Tan
	*/
	public GaussianFilterAction(Bitmap bmp) {
		this.bmp = bmp;
		this.height = bmp.getHeight();
		this.width = bmp.getWidth(); 

	}
	
	/**
	Handler Gaussian Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {

		int[] colors = AndroidUtils.bitmapToIntArray(bmp);
		GaussianFilter gaussFilter = new GaussianFilter();

		gaussFilter.setRadius(5);

		colors = gaussFilter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;
	}
}
