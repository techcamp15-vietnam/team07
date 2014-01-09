package hust.team7.artcamerapro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {
	private Preview mPreview;
	private Camera mCamera;
	private int numberOfCameras;
	private int cameraCurrentlyLocked;
	private SeekBar mSeekBar;
	private Boolean checkToogle;
	public static Handler mHandler;
	private RelativeLayout mSeekbarLayout;
	// The first rear facing camera
	int defaultCameraId;
	private ImageButton mBtnTakePicture;
	private ImageButton mBtnSwitchCamera;
	private ImageButton btChoose;
	private HorizontalScrollView honView;

	private static final int PICK_IMAGE = 1;

	private ImageView test;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkToogle = true;
		FrameLayout previewLayout = (FrameLayout) findViewById(R.id.preview);
		mPreview = new Preview(MainActivity.this);
		previewLayout.addView(mPreview);
		// Find the total number of cameras available
		numberOfCameras = Camera.getNumberOfCameras();

		// Find the ID of the default camera
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
				defaultCameraId = i;
			}
		}

		initButtons();

		/**
		 * send data to intent and start new activity
		 * 
		 * @param
		 * @author 7-B Nguyen Quoc Hung
		 */
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// chuyen activity khac
				if (msg.what == 1) {
					String sFileName = (String) msg.obj;
					Log.e("Got 1231231", sFileName);
					Log.e("Got 12", sFileName);

					Intent intent = new Intent(MainActivity.this,
							EditActivity.class);
					Log.e("Got 13", sFileName);
					try {
						Log.e("Got 14", sFileName);
						intent.putExtra("image_name", sFileName);
						Log.e("Got 15", sFileName);
						startActivity(intent);
					} catch (Exception e) {
						Log.e("bi loi ", e.getMessage());
						Toast.makeText(MainActivity.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
					}

					// finish();
				}

			}

		};
	}

	private void initButtons() {
		mBtnTakePicture = (ImageButton) findViewById(R.id.snapBtn);
		mBtnSwitchCamera = (ImageButton) findViewById(R.id.switchCamera);
		btChoose = (ImageButton) findViewById(R.id.btChoose);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
		honView = (HorizontalScrollView) findViewById(R.id.scrollView);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar1);

		btChoose = (ImageButton) findViewById(R.id.btChoose);
		btChoose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (checkToogle) {
					Animation bottomUp = AnimationUtils.loadAnimation(
							getBaseContext(), R.anim.bottom_up);

					honView.setAnimation(bottomUp);
					honView.setVisibility(View.VISIBLE);
					checkToogle = false;
				} else {

					Animation bottomDown = AnimationUtils.loadAnimation(
							getBaseContext(), R.anim.bottom_down);

					honView.setAnimation(bottomDown);
					honView.setVisibility(View.INVISIBLE);
					checkToogle = true;
				}
			}
		});

		// mSeekbarLayout = (RelativeLayout) findViewById(R.id.middleMenu);
		// mSeekbarLayout.setOnTouchListener(this) ;

		// View view = findViewById(R.id.touch_view);
		// view.setOnTouchListener(this);

		mSeekbarLayout = (RelativeLayout) findViewById(R.id.middleMenu);
		mSeekbarLayout.setOnTouchListener(this);

		// View view = findViewById(R.id.touch_view);
		// view.setOnTouchListener(this);

		mBtnTakePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPreview.takePicture();
			}
		});

		mBtnSwitchCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// check for availability of multiple cameras
				if (numberOfCameras == 1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setMessage(getString(R.string.camera_alert))
							.setNeutralButton("Close", null);
					AlertDialog alert = builder.create();
					alert.show();
				}

				// OK, we have multiple cameras.
				// Release this camera -> cameraCurrentlyLocked
				if (mCamera != null) {
					mCamera.stopPreview();
					mPreview.setCamera(null);
					mCamera.release();
					mCamera = null;
				}

				// Acquire the next camera and request Preview to reconfigure
				// parameters.
				mCamera = Camera.open((cameraCurrentlyLocked + 1)
						% numberOfCameras);
				cameraCurrentlyLocked = (cameraCurrentlyLocked + 1)
						% numberOfCameras;
				mPreview.switchCamera(mCamera);

				// Start the preview
				mCamera.startPreview();
			}
		});

		btChoose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						PICK_IMAGE);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera != null) {
			mPreview.setCamera(null);
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Open the default i.e. the first rear facing camera.
		mCamera = Camera.open();
		cameraCurrentlyLocked = defaultCameraId;
		mPreview.setCamera(mCamera);

		initZoomControl();
	}

	/**
	 * zoom camera by seekbar
	 * 
	 * @param
	 * @author 7-B Nguyen Quoc Hung
	 */
	private void initZoomControl() {
		if (mCamera == null) {
			return;
		}
		Camera.Parameters camParam = mCamera.getParameters();
		if (camParam != null) {
			if (camParam.isZoomSupported()) {
				mSeekBar.setVisibility(View.VISIBLE);
				final int maxZoomLevel = camParam.getMaxZoom();

				mSeekBar.incrementProgressBy(1);
				mSeekBar.setMax(maxZoomLevel);
				mSeekBar.setProgress(0);

				mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						mPreview.setCameraZoom(progress);
					}
				});

			} else {
				mSeekBar.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.middleMenu) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mSeekbarLayout.setVisibility(View.VISIBLE);
				mSeekBar.setVisibility(View.VISIBLE);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				mSeekbarLayout.setVisibility(View.INVISIBLE);
				mSeekBar.setVisibility(View.INVISIBLE);
			}
			// return true;
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
			Uri _uri = data.getData();

			// User had pick an image.
			Cursor cursor = getContentResolver()
					.query(_uri,
							new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
							null, null, null);
			cursor.moveToFirst();

			// Link to the image
			final String imageFilePath = cursor.getString(0);
			cursor.close();

			Intent intent = new Intent(MainActivity.this, EditActivity.class);
			try {
				intent.putExtra("image_name", imageFilePath);
				startActivity(intent);
			} catch (Exception e) {
				Toast.makeText(MainActivity.this, e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}