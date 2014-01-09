package hust.team7.actionfilter;


import hust.team7.filter.EmbossFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class EmbossFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private float directionValue;
	private float elevationValue;
	private float bumpHeightValue;
	
	/**
	Constructor EmbossFilterAction
	@param bmp, directionValue, elevationValue, bumpHeightValue
	@author 7-A Bui Quang Tan
	*/
	public EmbossFilterAction(Bitmap bmp, float directionValue, float elevationValue, float bumpHeightValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.directionValue = directionValue;
		this.elevationValue = elevationValue;
		this.bumpHeightValue = bumpHeightValue;
	}
	
	/**
	Handler Emboss Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		EmbossFilter filter = new EmbossFilter();
		filter.setAzimuth(directionValue);
		filter.setElevation(elevationValue);
		filter.setBumpHeight(bumpHeightValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}

