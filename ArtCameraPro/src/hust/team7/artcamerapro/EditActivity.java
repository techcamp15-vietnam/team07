package hust.team7.artcamerapro;

import hust.team7.actionfilter.ContourFilterAction;
import hust.team7.actionfilter.DiffuseFilterAction;
import hust.team7.actionfilter.DissolveFilterAction;
import hust.team7.actionfilter.EdgeFilterAction;
import hust.team7.actionfilter.EmbossFilterAction;
import hust.team7.actionfilter.GainFilterAction;
import hust.team7.actionfilter.GrayscaleFilterAction;
import hust.team7.actionfilter.InvertFilterAction;
import hust.team7.actionfilter.NoiseFilterAction;
import hust.team7.actionfilter.OilFilterAction;
import hust.team7.actionfilter.PointillizeFilterAction;
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
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class EditActivity extends Activity implements OnClickListener {

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
	private ImageView btThreshold1950;
	private ImageButton btContBright;
	private ImageButton btSave;
	private ImageButton btShare;

	private RelativeLayout svContBright;
	private ImageButton btSelectEffect;
	private HorizontalScrollView svListEffect;
	private HorizontalScrollView svListColor;
	private HorizontalScrollView svListFunny;
	private ImageButton btSelectFunny;

	private ImageView ivEditImage;
	private Bitmap bmpImage, bmpResult;
	private String file_name;
	private Boolean checkbtContBright = true;
	private int aCheckFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		aCheckFlag = 0;
		initView();
	}

	public Bitmap rotateBitmap(String fileName) {

		Bitmap bitmap = null;
		Bitmap rotatedBitmap = null;
		Uri uri = Uri.fromFile(new File(fileName));

		try {
			bitmap = BitmapFactory.decodeFile(fileName);
			if (bitmap == null) {
				return null;
			}

			ExifInterface ex = new ExifInterface(fileName);
			int orientation = ex.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);

			if (orientation == ExifInterface.ORIENTATION_UNDEFINED) {
				Cursor cursor = getContentResolver()
						.query(uri,
								new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
								null, null, null);

				try {
					if (cursor.moveToFirst()) {
						int deg = cursor
								.getInt(cursor
										.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION));
						if (deg == 90) {
							orientation = ExifInterface.ORIENTATION_ROTATE_90;
						} else if (deg == 180) {
							orientation = ExifInterface.ORIENTATION_ROTATE_180;
						} else if (deg == 270) {
							orientation = ExifInterface.ORIENTATION_ROTATE_270;
						}
					}

					cursor.close();
				} catch (Exception e) {

				}
			}

			int degree = 0;
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree += 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree += 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree += 90;
				break;
			case ExifInterface.ORIENTATION_TRANSPOSE:
				degree += 45;
				break;
			case ExifInterface.ORIENTATION_UNDEFINED:
				degree += 360;
				break;
			default:
				break;
			}

			if (degree > 0) {
				Matrix matrix = new Matrix();
				matrix.postRotate(degree);
				rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			} else {
				rotatedBitmap = bitmap;
			}

		} catch (Exception e) {
			// handle the exception(s)
		}

		return rotatedBitmap;
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
		btThreshold1950 = (ImageView) findViewById(R.id.btThreshold1950);
		btThreshold1950.setOnClickListener(this);

		btSave = (ImageButton) findViewById(R.id.btSave);
		btSave.setOnClickListener(this);
		btShare = (ImageButton) findViewById(R.id.btShare);
		btShare.setOnClickListener(this);
		btContBright = (ImageButton) findViewById(R.id.btContBright);
		btContBright.setOnClickListener(this);
		btSelectEffect = (ImageButton) findViewById(R.id.btSelectEffect);
		btSelectEffect.setOnClickListener(this);
		btSelectColor = (ImageButton) findViewById(R.id.btSelectColor);
		btSelectColor.setOnClickListener(this);
		btSelectFunny = (ImageButton) findViewById(R.id.btSelectFunny);
		btSelectFunny.setOnClickListener(this);
		
		svListEffect = (HorizontalScrollView) findViewById(R.id.svListEffect);
		svListColor = (HorizontalScrollView) findViewById(R.id.svListColor);
		svListFunny =(HorizontalScrollView) findViewById(R.id.svListFunny);
		ivEditImage = (ImageView) findViewById(R.id.ivImageEdit);

		svContBright = (RelativeLayout) findViewById(R.id.svListContBright);
		Intent i = getIntent();
		file_name = i.getStringExtra("image_name");
		//file_name = Environment.getExternalStorageDirectory() + "/image.jpg";
		bmpImage = BitmapFactory.decodeFile(file_name);
		//bmpImage = rotateBitmap(file_name);
		bmpResult = bmpImage;
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
		Animation menuDropDown = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.menu_drop_down);
		Animation menuDropUp = AnimationUtils.loadAnimation(getBaseContext(),
				R.anim.menu_drop_up);
		switch (v.getId()) {

		case R.id.btOriginalEffect:
			if (bmpImage != null) {
				ivEditImage.setImageBitmap(bmpImage);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btDissolve:
			if (bmpImage != null) {
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
				OilFilterAction oilFilter = new OilFilterAction(bmpImage, 2, 4);
				bmpResult = oilFilter.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		// case R.id.btGaussian:
		// if (bmpImage != null) {
		// GaussianFilterAction gaussAction = new GaussianFilterAction(
		// bmpImage);
		// bmpResult = gaussAction.action();
		// ivEditImage.setImageBitmap(bmpResult);
		// } else {
		// Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
		// .show();
		// }
		// break;

		case R.id.btOriginal:
			if (bmpImage != null) {
				ivEditImage.setImageBitmap(bmpImage);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btWarmVintage:
			if (bmpImage != null) {
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
				// GainFilterAction gainFilter = new GainFilterAction(bmpImage);
				btThreshold1950.setBackgroundResource(R.drawable.rounded_edittext);
				ThresholdFilterAction thresFilter1950 = new ThresholdFilterAction(
						bmpImage, 92, 150);
				bmpResult = thresFilter1950.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
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
		case R.id.btContBright:
			if (checkbtContBright) {
				svContBright.setAnimation(menuDropDown);
				svContBright.setVisibility(View.VISIBLE);
				checkbtContBright=false;
			} else {

				svContBright.setAnimation(menuDropUp);
				svContBright.setVisibility(View.INVISIBLE);
				checkbtContBright=true;
			}
			break;
		case R.id.btShare:
            //Save first
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
			//Now share
			//File file = new File(file_name);
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            sharingIntent.setType("image/jpeg");            

            startActivity(Intent.createChooser(sharingIntent , "Share to your friends by"));
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
				Toast.makeText(this, "Saved to "+sFileName, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Log.e("e", "exception");
			}
			break;
		}
	}
		
	/**
	create file for app (if not existed) and save image 
	@param 
	@author 7-B Nguyen Quoc Hung
	*/
	private String createFileName(){
		
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){   		
			File folder = new File(Environment.getExternalStorageDirectory() + "/ArtCameraPro");
			boolean success = true;
			if (!folder.exists()) {
			    success = folder.mkdir();
			} 
			String sFileName = Environment.getExternalStorageDirectory() + "/ArtCameraPro" + File.separator + System.currentTimeMillis() + ".jpg";
			return sFileName;
			
		}
		return "";
	}
}