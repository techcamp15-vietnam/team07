package hust.team7.actionfilter;

import hust.team7.filter.OilFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class OilFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int levelValue;
	private int rangeValue;
	
	/**
	Constructor OilFilterAction
	@param bmp, levelValue, rangeValue
	@author 7-A Bui Quang Tan
	*/
	public OilFilterAction(Bitmap bmp, int levelValue, int rangeValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.levelValue = levelValue;
		this.rangeValue = rangeValue;
	}
	
	/**
	Handler Oil Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		OilFilter filter = new OilFilter();
		filter.setLevels(levelValue);
		filter.setRange(rangeValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}
}

