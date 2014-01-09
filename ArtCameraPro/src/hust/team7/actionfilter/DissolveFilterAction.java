package hust.team7.actionfilter;

import hust.team7.filter.DissolveFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class DissolveFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private float densityValue;
	private float softnessValue;
	
	/**
	Constructor DissolveFilterAction
	@param bmp, densityValue, softnessValue
	@author 7-A Bui Quang Tan
	*/
	public DissolveFilterAction(Bitmap bmp, float densityValue, float softnessValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.densityValue = densityValue;
		this.softnessValue = softnessValue;
	}
	
	/**
	Handler Dissolve Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		DissolveFilter filter = new DissolveFilter();
		filter.setDensity(densityValue);
		filter.setSoftness(softnessValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}
