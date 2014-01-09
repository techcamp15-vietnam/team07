package hust.team7.actionfilter;

import hust.team7.filter.RGBAdjustFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class RGBAdjustFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private float rfactorValue;
	private float gfactorValue;
	private float bfactorValue;
	private int[] colors;

	/**
	Constructor RGBAdjustFilterAction
	@param bmp, rfactorValue, gfactorValue, bfactorValue
	@author 7-A Bui Quang Tan
	*/
	public RGBAdjustFilterAction(Bitmap bmp, float rfactorValue,
			float gfactorValue, float bfactorValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.rfactorValue = rfactorValue;
		this.gfactorValue = gfactorValue;
		this.bfactorValue = bfactorValue;
	}

	/**
	Handler RGBAdjust Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		RGBAdjustFilter filter = new RGBAdjustFilter();
		filter.setRFactor(rfactorValue);
		filter.setGFactor(gfactorValue);
		filter.setBFactor(bfactorValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
