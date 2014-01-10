package hust.team7.artcamerapro;

import hust.team7.actionfilter.ContourFilterAction;
import hust.team7.actionfilter.DiffuseFilterAction;
import hust.team7.actionfilter.DissolveFilterAction;
import hust.team7.actionfilter.EdgeFilterAction;
import hust.team7.actionfilter.EmbossFilterAction;
import hust.team7.actionfilter.GainFilterAction;
import hust.team7.actionfilter.GrayscaleFilterAction;
import hust.team7.actionfilter.InvertFilterAction;
import hust.team7.actionfilter.LevelsFilterAction;
import hust.team7.actionfilter.NoiseFilterAction;
import hust.team7.actionfilter.OilFilterAction;
import hust.team7.actionfilter.PointillizeFilterAction;
import hust.team7.actionfilter.PosterizeFilterAction;
import hust.team7.actionfilter.RGBAdjustFilterAction;
import hust.team7.actionfilter.ThresholdFilterAction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class EditActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private float dx = 0, dy = 0, x = 0, y = 0;
	private RelativeLayout reLayout;
	// private RelativeLayout.LayoutParams[] paramList;
	// private ArrayList<ImageView> trollFaceList = new ArrayList<ImageView>();
	// private ImageView[] trollFaceList;
	// private int index;
	private Bitmap tempBitmap;
	private ImageView btYunoFunny, btNoFunny, btMilkFunny, btSeriousFunny;
	private ImageView btSweatFunny, btIseeFunny, btOverjoyedFunny,
			btPerfectFunny, btHappyFunny;
	private ImageView btFtsFunny, btLolFunny, btPftFunny, btTrollFunny,
			btCryFunny, btFaFunny, btFapFunny;
	private ImageView btFuuFunny, btGustaMuchoFunny, btKiddingmeFunny,
			btOkayFunny, btSadtrollFunny;

	private ImageButton btOriginalEffect;
	private ImageButton btDissolve;
	private ImageButton btEmboss1;
	private ImageButton btEmboss2;
	private ImageButton btContour;
	private ImageButton btEdge;
	private ImageButton btDiffuse;
	private ImageButton btNoise;
	private ImageButton btPointillize;
	private ImageButton btOil;
	// private ImageButton btGaussian;
	// private ImageButton btBlur;

	private ImageButton btSelectColor;
	private ImageButton btOriginal;
	private ImageButton btWarmVintage;
	private ImageButton btColdVintage;
	private ImageButton btInvert;
	private ImageButton btGrayscale;
	private ImageButton btGain;
	private ImageButton btThreshold1950;
	private ImageButton btLevels;
	private ImageButton btPosterize;
	private ImageButton btContBright;

	private ImageButton btDone;
	private ImageButton btShare;
	private RelativeLayout svContBright;
	private ImageButton btSelectEffect;
	private HorizontalScrollView svListEffect;
	private HorizontalScrollView svListColor;
	private HorizontalScrollView svListFunny;
	private ImageButton btSelectFunny;
	private ImageButton btSave;
	private ImageButton btBack;

	private ImageView ivEditImage;
	private Bitmap bmpImage, bmpResult;
	private String file_name;
	private Boolean checkbtContBright = true;
	private int aCheckFlag;
	private int aCheckSelected_color = 0;
	private int aCheckSelected_effect = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		aCheckFlag = 0;
		initView();
	}

	/**
	 * initView
	 * 
	 * @param no
	 *            param
	 * @author 7-A Bui Quang Tan
	 */
	private void initView() {

		btOriginalEffect = (ImageButton) findViewById(R.id.btOriginalEffect);
		btOriginalEffect.setOnClickListener(this);
		btDissolve = (ImageButton) findViewById(R.id.btDissolve);
		btDissolve.setOnClickListener(this);
		btEmboss1 = (ImageButton) findViewById(R.id.btEmboss1);
		btEmboss1.setOnClickListener(this);
		btEmboss2 = (ImageButton) findViewById(R.id.btEmboss2);
		btEmboss2.setOnClickListener(this);
		btContour = (ImageButton) findViewById(R.id.btContour);
		btContour.setOnClickListener(this);
		btEdge = (ImageButton) findViewById(R.id.btEdge);
		btEdge.setOnClickListener(this);
		btDiffuse = (ImageButton) findViewById(R.id.btDiffuse);
		btDiffuse.setOnClickListener(this);
		btNoise = (ImageButton) findViewById(R.id.btNoise);
		btNoise.setOnClickListener(this);
		btPointillize = (ImageButton) findViewById(R.id.btPointillize);
		btPointillize.setOnClickListener(this);
		btOil = (ImageButton) findViewById(R.id.btOil);
		btOil.setOnClickListener(this);
		// btGaussian = (ImageButton) findViewById(R.id.btGaussian);
		// btGaussian.setOnClickListener(this);
		// btBlur = (ImageButton) findViewById(R.id.btBlur);
		// btBlur.setOnClickListener(this);

		btOriginal = (ImageButton) findViewById(R.id.btOriginal);
		btOriginal.setOnClickListener(this);
		btWarmVintage = (ImageButton) findViewById(R.id.btWarmVintage);
		btWarmVintage.setOnClickListener(this);
		btColdVintage = (ImageButton) findViewById(R.id.btColdVintage);
		btColdVintage.setOnClickListener(this);
		btInvert = (ImageButton) findViewById(R.id.btInvert);
		btInvert.setOnClickListener(this);
		btGrayscale = (ImageButton) findViewById(R.id.btGrayscale);
		btGrayscale.setOnClickListener(this);
		btGain = (ImageButton) findViewById(R.id.btGain);
		btGain.setOnClickListener(this);
		btThreshold1950 = (ImageButton) findViewById(R.id.btThreshold1950);
		btThreshold1950.setOnClickListener(this);
		btLevels = (ImageButton) findViewById(R.id.btLevels);
		btLevels.setOnClickListener(this);
		btPosterize = (ImageButton) findViewById(R.id.btPosterize);
		btPosterize.setOnClickListener(this);
		btBack = (ImageButton) findViewById(R.id.btBack);
		btBack.setOnClickListener(this);
		btSave = (ImageButton) findViewById(R.id.btSave);
		btSave.setOnClickListener(this);
		btShare = (ImageButton) findViewById(R.id.btShare);
		btShare.setOnClickListener(this);
		// Init variable for funny icon function
		// paramList = new RelativeLayout.LayoutParams[6];
		// index = 0;
		tempBitmap = null;
		reLayout = (RelativeLayout) findViewById(R.id.layoutImageEdit);
		btYunoFunny = (ImageView) findViewById(R.id.btYunoFunny);
		btYunoFunny.setOnClickListener(this);
		btNoFunny = (ImageView) findViewById(R.id.btNoFunny);
		btNoFunny.setOnClickListener(this);
		btMilkFunny = (ImageView) findViewById(R.id.btMilkFunny);
		btMilkFunny.setOnClickListener(this);
		btSeriousFunny = (ImageView) findViewById(R.id.btSeriousFunny);
		btSeriousFunny.setOnClickListener(this);
		btSweatFunny = (ImageView) findViewById(R.id.btSweatFunny);
		btSweatFunny.setOnClickListener(this);
		btIseeFunny = (ImageView) findViewById(R.id.btIseeFunny);
		btIseeFunny.setOnClickListener(this);
		btOverjoyedFunny = (ImageView) findViewById(R.id.btOverjoyedFunny);
		btOverjoyedFunny.setOnClickListener(this);
		btPerfectFunny = (ImageView) findViewById(R.id.btPerfectFunny);
		btPerfectFunny.setOnClickListener(this);
		btHappyFunny = (ImageView) findViewById(R.id.btHappyFunny);
		btHappyFunny.setOnClickListener(this);
		btFtsFunny = (ImageView) findViewById(R.id.btFtsFunny);
		btFtsFunny.setOnClickListener(this);
		btLolFunny = (ImageView) findViewById(R.id.btLolFunny);
		btLolFunny.setOnClickListener(this);
		btPftFunny = (ImageView) findViewById(R.id.btPftFunny);
		btPftFunny.setOnClickListener(this);
		btTrollFunny = (ImageView) findViewById(R.id.btTrollFunny);
		btTrollFunny.setOnClickListener(this);
		btCryFunny = (ImageView) findViewById(R.id.btCryFunny);
		btCryFunny.setOnClickListener(this);
		btFaFunny = (ImageView) findViewById(R.id.btFaFunny);
		btFaFunny.setOnClickListener(this);
		btFapFunny = (ImageView) findViewById(R.id.btFapFunny);
		btFapFunny.setOnClickListener(this);
		btFuuFunny = (ImageView) findViewById(R.id.btFuuFunny);
		btFuuFunny.setOnClickListener(this);
		btGustaMuchoFunny = (ImageView) findViewById(R.id.btGustaMuchoFunny);
		btGustaMuchoFunny.setOnClickListener(this);
		btKiddingmeFunny = (ImageView) findViewById(R.id.btKiddingmeFunny);
		btKiddingmeFunny.setOnClickListener(this);
		btOkayFunny = (ImageView) findViewById(R.id.btOkayFunny);
		btOkayFunny.setOnClickListener(this);
		btSadtrollFunny = (ImageView) findViewById(R.id.btSadtrollFunny);
		btSadtrollFunny.setOnClickListener(this);

		btDone = (ImageButton) findViewById(R.id.btDone);
		btDone.setEnabled(false);
		btDone.setOnClickListener(this);
		btSelectEffect = (ImageButton) findViewById(R.id.btSelectEffect);
		btSelectEffect.setOnClickListener(this);
		btSelectColor = (ImageButton) findViewById(R.id.btSelectColor);
		btSelectColor.setOnClickListener(this);
		btSelectFunny = (ImageButton) findViewById(R.id.btSelectFunny);
		btSelectFunny.setOnClickListener(this);

		svListEffect = (HorizontalScrollView) findViewById(R.id.svListEffect);
		svListColor = (HorizontalScrollView) findViewById(R.id.svListColor);
		svListFunny = (HorizontalScrollView) findViewById(R.id.svListFunny);
		ivEditImage = (ImageView) findViewById(R.id.ivImageEdit);

		svContBright = (RelativeLayout) findViewById(R.id.svListContBright);
		Intent i = getIntent();
		file_name = i.getStringExtra("image_name");
		//file_name = Environment.getExternalStorageDirectory() + "/image.jpg";
		// bmpImage = BitmapFactory.decodeFile(file_name);
		bmpImage = resizeBitMapImage1(file_name, 640, 480);
		bmpImage = fixOrientation(bmpImage);
		bmpResult = bmpImage;
		// bmpImage = rotateBitmap(file_name);
		// File tempPhoto = new File(file_name);
		// try {
		// if (!tempPhoto.exists()) {
		// tempPhoto.createNewFile();
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// to do check null
		if (bmpImage != null) {
			ivEditImage.setImageBitmap(bmpImage);
			// ivEditImage.setImageDrawable(bmp);
		}

		// Bitmap bmp = ((BitmapDrawable)imgView.getBackground()).getBitmap();
	}

	@Override
	/**
	Onlick check
	@param view 
	@author 7-A Nguyen Hai Duong
	 */
	/**
	Write all case except 3 case : R.id.btSelectEffect, R.id.btSelectColor, R.id.btContBright
	@param View v 
	@author 7-A Bui Quang Tan
	 */
	public void onClick(View v) {
		Animation bottomUp = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.bottom_up);
		Animation bottomDown = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.bottom_down);

		switch (v.getId()) {

		case R.id.btOriginalEffect:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;

				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 0;
					break;
				case 0:
					btOriginalEffect
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 0;
					break;
				default:
					break;
				}
				btOriginalEffect
						.setBackgroundResource(R.drawable.rounded_edittext);
				ivEditImage.setImageBitmap(bmpImage);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btDissolve:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 1;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				case 0:
					btOriginalEffect.setBackgroundResource(0);
					aCheckSelected_effect = 1;
					break;
				default:
					break;
				}
				btDissolve.setBackgroundResource(R.drawable.rounded_edittext);
				DissolveFilterAction dissolveFilter = new DissolveFilterAction(
						bmpImage, 0.8f, 0.15f);
				bmpResult = dissolveFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btEmboss1:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 2:
					btEmboss1
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 2;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 2;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 2;

					break;
				case 0:
					btOriginalEffect.setBackgroundResource(0);
					break;
				default:
					break;
				}
				btEmboss1.setBackgroundResource(R.drawable.rounded_edittext);
				EmbossFilterAction embossFilter = new EmbossFilterAction(
						bmpImage, 4.57f, 0.54f, 0.97f);
				bmpResult = embossFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btEmboss2:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 3:
					btEmboss2
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 3;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				case 0:
					btOriginalEffect.setBackgroundResource(0);
					aCheckSelected_effect = 3;
					break;
				default:
					break;
				}
				btEmboss2.setBackgroundResource(R.drawable.rounded_edittext);
				EmbossFilterAction embossFilter = new EmbossFilterAction(
						bmpImage, 3.78f, 0.44f, 0.0f);
				bmpResult = embossFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btContour:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 4:
					btContour
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 4;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				case 0:
					btOriginalEffect.setBackgroundResource(0);
					aCheckSelected_effect = 4;
					break;
				default:
					break;
				}
				btContour.setBackgroundResource(R.drawable.rounded_edittext);
				ContourFilterAction contourFilter = new ContourFilterAction(
						bmpImage, 8, 0.77f, 0.27f);
				bmpResult = contourFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btEdge:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 5:
					btEdge.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 5;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;

				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 5;
					break;
				case 0:
					btOriginalEffect
							.setBackgroundResource(R.drawable.rounded_edittext);
					break;
				default:
					break;
				}
				btEdge.setBackgroundResource(R.drawable.rounded_edittext);
				EdgeFilterAction edgeFilter = new EdgeFilterAction(bmpImage);
				bmpResult = edgeFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btDiffuse:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);

					aCheckSelected_effect = 6;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 6:
					btDiffuse
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 6;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 6;
					break;
				case 0:
					btOriginalEffect
							.setBackgroundResource(R.drawable.rounded_edittext);
					break;
				default:
					break;
				}
				btDiffuse.setBackgroundResource(R.drawable.rounded_edittext);
				DiffuseFilterAction diffuseFilter = new DiffuseFilterAction(
						bmpImage, 12);
				bmpResult = diffuseFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btNoise:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 7:
					btNoise.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 7;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 7;
					break;
				case 0:
					btOriginalEffect
							.setBackgroundResource(R.drawable.rounded_edittext);
					break;
				default:
					break;
				}
				btNoise.setBackgroundResource(R.drawable.rounded_edittext);
				NoiseFilterAction noiseFilter = new NoiseFilterAction(bmpImage,
						65, 54);
				bmpResult = noiseFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btPointillize:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 8:
					btPointillize
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 8;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				case 0:
					btOriginalEffect.setBackgroundResource(0);
					aCheckSelected_effect = 8;
					break;
				default:
					break;
				}
				btPointillize
						.setBackgroundResource(R.drawable.rounded_edittext);
				PointillizeFilterAction pointillizeFilter = new PointillizeFilterAction(
						bmpImage, 8, 0.2f, 0.17f);
				bmpResult = pointillizeFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btOil:
			if (bmpImage != null) {
				switch (aCheckSelected_effect) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;

				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_effect = 9;
					break;
				case 9:
					btOil.setBackgroundResource(R.drawable.rounded_edittext);
					break;
				case 0:
					btOriginalEffect
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_effect = 9;
					break;
				default:
					break;
				}
				btOil.setBackgroundResource(R.drawable.rounded_edittext);
				OilFilterAction oilFilter = new OilFilterAction(bmpImage, 2, 4);
				bmpResult = oilFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btOriginal:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btDissolve.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 2:
					btEmboss1.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 3:
					btEmboss2.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 4:
					btContour.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 5:
					btEdge.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 6:
					btDiffuse.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 7:
					btNoise.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 8:
					btPointillize.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 9:
					btOil.setBackgroundResource(0);
					aCheckSelected_color = 0;
					break;
				case 0:
					btOriginal
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 0;
					break;
				default:
					break;
				}

				btOriginal.setBackgroundResource(R.drawable.rounded_edittext);
				ivEditImage.setImageBitmap(bmpImage);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btWarmVintage:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btWarmVintage
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 1;
					break;
				case 2:
					btColdVintage.setBackgroundResource(0);
					aCheckSelected_color = 1;
					break;
				case 3:
					btInvert.setBackgroundResource(0);
					aCheckSelected_color = 1;
					break;
				case 4:
					btGrayscale.setBackgroundResource(0);
					aCheckSelected_color = 1;
					break;
				case 5:
					btGain.setBackgroundResource(0);
					aCheckSelected_color = 1;
					break;
				case 6:
					btThreshold1950.setBackgroundResource(0);
					aCheckSelected_color = 1;
					break;

				case 0:
					btOriginal.setBackgroundResource(0);
					aCheckSelected_color = 1;
					break;
				default:
					break;
				}
				btWarmVintage
						.setBackgroundResource(R.drawable.rounded_edittext);
				RGBAdjustFilterAction warmFilter = new RGBAdjustFilterAction(
						bmpImage, 0.83f, 0.18f, -0.03f);
				bmpResult = warmFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btColdVintage:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btWarmVintage.setBackgroundResource(0);
					aCheckSelected_color = 2;
					break;
				case 2:
					btColdVintage
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 2;
					break;

				case 3:
					btInvert.setBackgroundResource(0);
					aCheckSelected_color = 2;
					break;
				case 4:
					btGrayscale.setBackgroundResource(0);
					aCheckSelected_color = 2;
					break;
				case 5:
					btGain.setBackgroundResource(0);
					aCheckSelected_color = 2;
					break;
				case 6:
					btThreshold1950.setBackgroundResource(0);
					aCheckSelected_color = 2;
					break;
				case 0:
					btOriginal.setBackgroundResource(0);
					aCheckSelected_color = 2;
					break;
				default:
					break;
				}
				btColdVintage
						.setBackgroundResource(R.drawable.rounded_edittext);
				RGBAdjustFilterAction coldFilter = new RGBAdjustFilterAction(
						bmpImage, -0.09f, 0.06f, 0.35f);
				bmpResult = coldFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btInvert:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btWarmVintage.setBackgroundResource(0);
					aCheckSelected_color = 3;
					break;
				case 2:
					btColdVintage.setBackgroundResource(0);
					aCheckSelected_color = 3;
					break;

				case 3:
					btInvert.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 3;
					break;
				case 4:
					btGrayscale.setBackgroundResource(0);
					aCheckSelected_color = 3;
					break;
				case 5:
					btGain.setBackgroundResource(0);
					aCheckSelected_color = 3;
					break;
				case 6:
					btThreshold1950.setBackgroundResource(0);
					aCheckSelected_color = 3;
					break;

				case 0:
					btOriginal.setBackgroundResource(0);
					aCheckSelected_color = 3;
					break;
				default:
					break;
				}
				btInvert.setBackgroundResource(R.drawable.rounded_edittext);
				InvertFilterAction invertFilter = new InvertFilterAction(
						bmpImage);
				bmpResult = invertFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btGrayscale:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btWarmVintage.setBackgroundResource(0);
					aCheckSelected_color = 4;
					break;
				case 2:
					btColdVintage.setBackgroundResource(0);
					aCheckSelected_color = 4;
					break;
				case 3:
					btInvert.setBackgroundResource(0);
					aCheckSelected_color = 4;
					break;
				case 4:
					btGrayscale
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 4;
					break;
				case 5:
					btGain.setBackgroundResource(0);
					aCheckSelected_color = 4;
					break;
				case 6:
					btThreshold1950.setBackgroundResource(0);
					break;
				case 0:
					btOriginal.setBackgroundResource(0);
					aCheckSelected_color = 4;
					break;
				default:
					break;
				}
				btGrayscale.setBackgroundResource(R.drawable.rounded_edittext);
				GrayscaleFilterAction grayscaleFilter = new GrayscaleFilterAction(
						bmpImage);
				bmpResult = grayscaleFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btGain:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btWarmVintage.setBackgroundResource(0);
					aCheckSelected_color = 5;
					break;
				case 2:
					btColdVintage.setBackgroundResource(0);
					aCheckSelected_color = 5;
					break;
				case 3:
					btInvert.setBackgroundResource(0);
					aCheckSelected_color = 5;
					break;
				case 4:
					btGrayscale.setBackgroundResource(0);
					aCheckSelected_color = 5;
					break;
				case 5:
					btGain.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 5;
					break;
				case 6:
					btThreshold1950.setBackgroundResource(0);
					aCheckSelected_color = 5;
					break;
				case 0:
					btOriginal.setBackgroundResource(0);
					aCheckSelected_color = 5;
					break;
				default:
					break;
				}
				btGain.setBackgroundResource(R.drawable.rounded_edittext);
				GainFilterAction gainFilter = new GainFilterAction(bmpImage);
				bmpResult = gainFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btThreshold1950:
			if (bmpImage != null) {
				switch (aCheckSelected_color) {
				case 1:
					btWarmVintage.setBackgroundResource(0);
					aCheckSelected_color = 6;
					break;
				case 2:
					btColdVintage.setBackgroundResource(0);
					aCheckSelected_color = 6;
					break;
				case 3:
					btInvert.setBackgroundResource(0);
					aCheckSelected_color = 6;
					break;
				case 4:
					btGrayscale.setBackgroundResource(0);
					aCheckSelected_color = 6;
					break;
				case 5:
					btGain.setBackgroundResource(0);
					aCheckSelected_color = 6;
					break;
				case 6:
					btThreshold1950
							.setBackgroundResource(R.drawable.rounded_edittext);
					aCheckSelected_color = 6;
					break;
				case 0:
					btOriginal.setBackgroundResource(0);
					aCheckSelected_color = 6;
					break;
				default:
					break;
				}

				btThreshold1950
						.setBackgroundResource(R.drawable.rounded_edittext);
				ThresholdFilterAction thresFilter1950 = new ThresholdFilterAction(
						bmpImage, 92, 150);
				bmpResult = thresFilter1950.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btLevels:
			if (bmpImage != null) {
				LevelsFilterAction levelsFilter = new LevelsFilterAction(
						bmpImage, 0.25f, 0.79f, 0.0f, 1.0f);
				bmpResult = levelsFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btPosterize:
			if (bmpImage != null) {
				PosterizeFilterAction postFilter = new PosterizeFilterAction(
						bmpImage, 10);
				bmpResult = postFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btYunoFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btYunoFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			// index++;
			break;

		case R.id.btNoFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btNoFunny.getDrawable()).getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			// index++;
			break;

		case R.id.btMilkFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btMilkFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			// index++;
			break;

		case R.id.btSeriousFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btSeriousFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btSweatFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btSweatFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btIseeFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btIseeFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btOverjoyedFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btOverjoyedFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btPerfectFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btPerfectFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btHappyFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btHappyFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btFtsFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btFtsFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btLolFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btLolFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btPftFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btPftFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btTrollFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btTrollFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btCryFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btCryFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btFaFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btFaFunny.getDrawable()).getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btFapFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btFapFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btFuuFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btFuuFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btGustaMuchoFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btGustaMuchoFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btKiddingmeFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btKiddingmeFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btOkayFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btOkayFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btSadtrollFunny:
			btDone.setEnabled(true);
			tempBitmap = ((BitmapDrawable) btSadtrollFunny.getDrawable())
					.getBitmap();
			HandleTouch(tempBitmap);
			tempBitmap = null;
			break;

		case R.id.btSelectEffect:

			switch (aCheckFlag) {

			case 0:
				svListEffect.setAnimation(bottomUp);
				svListEffect.setVisibility(View.VISIBLE);
				aCheckFlag = 2;
				break;
			case 1:
				svListColor.setAnimation(bottomDown);
				svListColor.setVisibility(View.INVISIBLE);
				svListEffect.setAnimation(bottomUp);
				svListEffect.setVisibility(View.VISIBLE);
				aCheckFlag = 2;
				break;
			case 2:
				svListEffect.setAnimation(bottomDown);
				svListEffect.setVisibility(View.INVISIBLE);
				aCheckFlag = 0;
				break;
			case 3:
				svListFunny.setAnimation(bottomDown);
				svListFunny.setVisibility(View.INVISIBLE);
				svListEffect.setAnimation(bottomUp);
				svListEffect.setVisibility(View.VISIBLE);
				aCheckFlag = 2;
				break;
			}
			break;
		case R.id.btSelectColor:
			switch (aCheckFlag) {

			case 0:
				svListColor.setAnimation(bottomUp);
				svListColor.setVisibility(View.VISIBLE);
				aCheckFlag = 1;

				break;
			case 1:
				svListColor.setAnimation(bottomDown);
				svListColor.setVisibility(View.INVISIBLE);

				aCheckFlag = 0;
				break;
			case 2:
				svListEffect.setAnimation(bottomDown);
				svListEffect.setVisibility(View.INVISIBLE);
				svListColor.setAnimation(bottomUp);
				svListColor.setVisibility(View.VISIBLE);
				aCheckFlag = 1;
				break;
			case 3:
				svListFunny.setAnimation(bottomDown);
				svListFunny.setVisibility(View.INVISIBLE);
				svListColor.setAnimation(bottomUp);
				svListColor.setVisibility(View.VISIBLE);
				aCheckFlag = 1;
				break;

			}
			break;

		case R.id.btSelectFunny:
			Log.e("da vao ", " dc log");
			Toast.makeText(this, "Swipe left to remove icon",
					Toast.LENGTH_SHORT).show();
			switch (aCheckFlag) {

			case 0:
				svListFunny.setAnimation(bottomUp);
				svListFunny.setVisibility(View.VISIBLE);
				aCheckFlag = 3;
				break;
			case 1:
				svListColor.setAnimation(bottomDown);
				svListColor.setVisibility(View.INVISIBLE);
				svListFunny.setAnimation(bottomUp);
				svListFunny.setVisibility(View.VISIBLE);
				aCheckFlag = 3;
				break;
			case 2:
				svListEffect.setAnimation(bottomDown);
				svListEffect.setVisibility(View.INVISIBLE);
				svListFunny.setAnimation(bottomUp);
				svListFunny.setVisibility(View.VISIBLE);
				aCheckFlag = 3;
				break;
			case 3:
				svListFunny.setAnimation(bottomDown);
				svListFunny.setVisibility(View.INVISIBLE);
				aCheckFlag = 0;
				break;

			}
			break;

		case R.id.btDone:
			bmpResult = Bitmap.createBitmap(reLayout.getMeasuredWidth(),
					reLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bmpResult);
			reLayout.draw(canvas);
			ivEditImage.setImageBitmap(bmpResult);
			Toast.makeText(this, "Add icon done", Toast.LENGTH_SHORT).show();
			break;

		case R.id.btShare:
			// Save first
			String temp = createFileName();

			ByteArrayOutputStream b = new ByteArrayOutputStream();
			bmpResult.compress(Bitmap.CompressFormat.JPEG, 85, b);

			File file = new File(temp);
			try {
				file.createNewFile();
				FileOutputStream fo = new FileOutputStream(file);
				fo.write(b.toByteArray());
				fo.close();
			} catch (Exception e) {
				Log.e("e", "exception");
			}
			// Now share
			// File file = new File(file_name);
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			sharingIntent.setType("image/jpeg");

			startActivity(Intent.createChooser(sharingIntent,
					"Share to your friends by"));
			break;

		case R.id.btSave:

			String sFileName = createFileName();
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bmpResult.compress(Bitmap.CompressFormat.JPEG, 80, bytes);

			File f = new File(sFileName);
			try {
				f.createNewFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(bytes.toByteArray());
				fo.close();
				Toast.makeText(this, "Saved to " + sFileName,
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("e", "exception");
			}
			break;

		case R.id.btBack:

			finish();
			break;
		}
	}

	/**
	 * Override onTouch Method
	 * 
	 * @param v
	 *            , event
	 * @author 7-A Bui Quang Tan
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (!(v instanceof ImageView)) {
			return false;
		}
		LayoutParams param = (LayoutParams) v.getLayoutParams();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dx = event.getRawX() - param.leftMargin;
			dy = event.getRawY() - param.topMargin;
			break;
		case MotionEvent.ACTION_MOVE:
			x = event.getRawX();
			y = event.getRawY();

			param.leftMargin = (int) (x - dx);
			param.topMargin = (int) (y - dy);
			v.setLayoutParams(param);
			if (param.leftMargin < 0) {
				reLayout.removeView(v);
				Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
			}
			break;
		case MotionEvent.ACTION_UP:

		}
		return true;
	}

	/**
	 * Handler Touch Icon Funny
	 * 
	 * @param temp
	 * @author 7-A Bui Quang Tan
	 */
	private void HandleTouch(Bitmap temp) {
		ImageView imgView = new ImageView(EditActivity.this);
		imgView.setImageBitmap(temp);
		String tag = Long.toString(System.currentTimeMillis());
		imgView.setTag(tag);

		// Add imgView to RelativeLayout and list ImageView
		reLayout.addView(imgView);
		// trollFaceList.add(imgView);

		LayoutParams param = (LayoutParams) imgView.getLayoutParams();
		param.leftMargin = 350;
		param.topMargin = 150;
		imgView.setLayoutParams(param);
		imgView.setOnTouchListener(this);
	}

	/**
	 * create file for app (if not existed) and save image
	 * 
	 * @param
	 * @author 7-B Nguyen Quoc Hung
	 */
	private String createFileName() {

		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File folder = new File(Environment.getExternalStorageDirectory()
					+ "/ArtCameraPro");
			boolean success = true;
			if (!folder.exists()) {
				success = folder.mkdir();
			}
			String sFileName = Environment.getExternalStorageDirectory()
					+ "/ArtCameraPro" + File.separator
					+ System.currentTimeMillis() + ".jpg";
			return sFileName;

		}
		return "";
	}

	public static Bitmap resizeBitMapImage1(String filePath, int targetWidth,
			int targetHeight) {
		Bitmap bitMapImage = null;
		// First, get the dimensions of the image
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		double sampleSize = 0;
		// Only scale if we need to
		// (16384 buffer for img processing)
		Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math
				.abs(options.outWidth - targetWidth);

		if (options.outHeight * options.outWidth * 2 >= 1638) {
			// Load, scaling to smallest power of 2 that'll get it <= desired
			// dimensions
			sampleSize = scaleByHeight ? options.outHeight / targetHeight
					: options.outWidth / targetWidth;
			sampleSize = (int) Math.pow(2d,
					Math.floor(Math.log(sampleSize) / Math.log(2d)));
		}

		// Do the actual decoding
		options.inJustDecodeBounds = false;
		options.inTempStorage = new byte[128];
		while (true) {
			try {
				options.inSampleSize = (int) sampleSize;
				bitMapImage = BitmapFactory.decodeFile(filePath, options);

				break;
			} catch (Exception ex) {
				try {
					sampleSize = sampleSize * 2;
				} catch (Exception ex1) {

				}
			}
		}

		return bitMapImage;
	}

	public Bitmap fixOrientation(Bitmap mBitmap) {
		if (mBitmap.getWidth() > mBitmap.getHeight()) {
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
					mBitmap.getHeight(), matrix, true);
		}
		return mBitmap;
	}

}