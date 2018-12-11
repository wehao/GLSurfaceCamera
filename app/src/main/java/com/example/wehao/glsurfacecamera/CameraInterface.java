package com.example.wehao.glsurfacecamera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import java.io.IOException;

/**
 * Created by wehao on 18-12-11.
 */

public class CameraInterface {
    private static CameraInterface mInst = null;
    private CameraInterface() {}

    private boolean mIsPreviewing = false;
    private Camera mCamera;

    public static CameraInterface getInstance() {
        synchronized (CameraInterface.class) {
            if (mInst == null) {
                mInst = new CameraInterface();
            }
            return mInst;
        }
    }

    public void doOpenCamera(String cameraId) {
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public void doStartPreview(SurfaceTexture surfaceTexture, float f) {
        if (!isCameraAvailable() || isPreviewing()) {
            return;
        }

        try {
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
            mIsPreviewing = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doStopPreview() {
        if (!isCameraAvailable() || !isPreviewing()) {
            return;
        }

        mCamera.stopPreview();
        mIsPreviewing = false;
    }

    public void doStopCamera() {
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }

    public boolean isPreviewing() {
        return mIsPreviewing;
    }

    private boolean isCameraAvailable() {
        return mCamera != null;
    }

}
