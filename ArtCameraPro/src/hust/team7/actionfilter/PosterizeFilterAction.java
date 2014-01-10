package hust.team7.actionfilter;

import hust.team7.filter.GainFilter;
import hust.team7.filter.LevelsFilter;
import hust.team7.filter.PointillizeFilter;
import hust.team7.filter.PosterizeFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class PosterizeFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int levels;

	/**
	Constructor PosterizeFilterAction
	@param bmp
	@author 7-A Bui Quang Tan
	*/
	public PosterizeFilterAction(Bitmap bmp, int levels) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.levels = levels;
	}

	/**
	Handler Posterize Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		PosterizeFilter filter = new PosterizeFilter();
		filter.setNumLevels(levels);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}


