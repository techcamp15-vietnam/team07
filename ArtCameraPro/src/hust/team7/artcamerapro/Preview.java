package hust.team7.artcamerapro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback{
	private Camera mCamera;
	private SurfaceHolder mHolder;
	Size mPreviewSize;
    List<Size> mSupportedPreviewSizes;
	private final String TAG = "PreviewCamera";
	
	public Preview(Context context){
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }
	}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if(mCamera == null){
			return;
		}
		if(mPreviewSize != null){
			Camera.Parameters parameters = mCamera.getParameters();
//			List<Size> sizes = parameters.getSupportedPictureSizes();
//			Size optimalSize = getOptimalPreviewSize(sizes, width, height);
//			if (optimalSize != null && !parameters.getPictureSize().equals(optimalSize))
			
			parameters.setPictureSize(640, 480);
	        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
	        requestLayout();

	        mCamera.setParameters(parameters);
		}
		
        
        mCamera.startPreview();
        //mCamera.startFaceDetection();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
            mCamera.stopPreview();
        }
	}

    private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) 
            	continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        
        if(optimalSize == null){
        	return sizes.get(sizes.size()/2);
        }
        return optimalSize;
    }
    
    public void switchCamera(Camera camera) {
    	if(camera == null){
    		return;
    	}
        setCamera(camera);
        try {
            camera.setPreviewDisplay(mHolder);
        } catch (IOException exception) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
        if(mPreviewSize != null){
        	Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            parameters.setPictureSize(640, 480);
            camera.setParameters(parameters);
            requestLayout();
        }
        
        
     }
    
    public void setCamera(Camera camera) {
        mCamera = camera;
        mPreviewSize = null;
        requestLayout();
        if (mCamera != null) {
        	mCamera.setDisplayOrientation(90);
            mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        }
    }
    
    public void setCameraZoom(int zoomLevel){
    	if(mCamera == null){
    		return;
    	}
    	
    	try{
    		Parameters param = mCamera.getParameters();
    		param.setZoom(zoomLevel);
    		mCamera.setParameters(param);
    	}catch(Exception e){
    		Log.e(TAG, "" + e.getMessage());
    	}
    }
    
    PictureCallback pictureCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			String sFileName = createFileName();
			if(sFileName.equals("")){
				return;
			}
			
			FileOutputStream outStream = null;
			try{
				outStream = new FileOutputStream(sFileName);
				outStream.write(data);
				outStream.close();
				//camera.startPreview();
				if(mCamera != null){
					mCamera.release();
					mCamera = null;
				}
				//camera.startPreview();
				Message msg = new Message();
				msg.obj = sFileName;
				msg.what = 1;
				MainActivity.mHandler.sendMessage(msg);
			}catch(Exception e){
				Log.e(TAG, "" + e.getMessage());
			}
		}
	};
	
	PictureCallback rawCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.v(TAG, "onPictureTaken");
		}
	};
	
	ShutterCallback shutterCallback = new ShutterCallback() {
		
		@Override
		public void onShutter() {
			Log.i(TAG, "onShutter");
		}
	};
    public void takePicture(){
    	if(mCamera == null){
    		return;
    	}
    	
    	mCamera.takePicture(shutterCallback, rawCallback, pictureCallback);
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