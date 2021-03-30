package com.zxb.secai.ui.publish;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.camera.core.UseCase;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.VideoCaptureConfig;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.zxb.libcommon.utils.MediaFile;
import com.zxb.secai.R;
import com.zxb.secai.databinding.ActivityLayoutCaptureBinding;
import com.zxb.secai.view.RecordView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CaptureActivity extends AppCompatActivity {
    public static final int REQ_CAPTURE = 10001;
    public static final int CHOOSE_PHOTO = 10008;
    private ActivityLayoutCaptureBinding mBinding;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private static final int PERMISSION_CODE = 1000;
    private ArrayList<String> deniedPermission = new ArrayList<>();
    private CameraX.LensFacing mLensFacing = CameraX.LensFacing.BACK;
    private int rotation = Surface.ROTATION_0;
    private Size resolution = new Size(1280, 720);
    private Rational rational = new Rational(9, 16);
    private Preview preview;
    private ImageCapture imageCapture;
    private VideoCapture videoCapture;
    private TextureView textureView;
    private boolean takingPicture,phtotoImag;
    private String outputFilePath,outputFilePath1;
    public static final String RESULT_FILE_PATH = "file_path";
    public static final String RESULT_FILE_WIDTH = "file_width";
    public static final String RESULT_FILE_HEIGHT = "file_height";
    public static final String RESULT_FILE_TYPE = "file_type";
    private String imagepath;

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, REQ_CAPTURE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_layout_capture);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_CODE);
        mBinding.recordView.setOnRecordListener(new RecordView.onRecordListener() {
            @Override
            public void onClick() {
                takingPicture = true;
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis() + ".jpeg");
                mBinding.captureTips.setVisibility(View.INVISIBLE);
                imageCapture.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        onFileSaved(file);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        showErrorToast(message);
                    }
                });
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onLongClick() {
                takingPicture = false;
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), System.currentTimeMillis() + ".mp4");
                videoCapture.startRecording(file, new VideoCapture.OnVideoSavedListener() {
                    @Override
                    public void onVideoSaved(File file) {
                        onFileSaved(file);
                    }

                    @Override
                    public void onError(VideoCapture.UseCaseError useCaseError, String message, @Nullable Throwable cause) {
                        showErrorToast(message);
                    }
                });
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFinish() {
                videoCapture.stopRecording();
            }
        });

        mBinding.captureAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                intent.putExtra(RESULT_FILE_PATH, outputFilePath);
//                //当设备处于竖屏情况时，宽高的值 需要互换，横屏不需要
//                intent.putExtra(RESULT_FILE_WIDTH, resolution.getHeight());
//                intent.putExtra(RESULT_FILE_HEIGHT, resolution.getWidth());
//                intent.putExtra(RESULT_FILE_TYPE, !takingPicture);
                startActivityForResult(intent, CHOOSE_PHOTO);

            }
        });
    }

    private void showErrorToast(@NonNull String message) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(CaptureActivity.this, message, Toast.LENGTH_SHORT).show();
        } else {
            runOnUiThread(() -> Toast.makeText(CaptureActivity.this, message, Toast.LENGTH_SHORT).show());
        }
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==CHOOSE_PHOTO && resultCode == RESULT_OK) {
            if(Build.VERSION.SDK_INT >= 19){    //在sdk版本大于19时采用这种方法获取图片
                handleImageOnKitKat(data);
                Intent intent = new Intent();
                intent.putExtra(RESULT_FILE_PATH, imagepath);
                //当设备处于竖屏情况时，宽高的值 需要互换，横屏不需要
                intent.putExtra(RESULT_FILE_WIDTH, resolution.getHeight());
                intent.putExtra(RESULT_FILE_HEIGHT, resolution.getWidth());
                intent.putExtra(RESULT_FILE_TYPE, phtotoImag);
                setResult(RESULT_OK, intent);
                finish();
            }
            else{
                handleImageBeforeKitKat( data );   //低版本则使用此方法（这两个都需要自己创建）
                Intent intent = new Intent();
                intent.putExtra(RESULT_FILE_PATH, imagepath);
                //当设备处于竖屏情况时，宽高的值 需要互换，横屏不需要
                intent.putExtra(RESULT_FILE_WIDTH, resolution.getHeight());
                intent.putExtra(RESULT_FILE_HEIGHT, resolution.getWidth());
                intent.putExtra(RESULT_FILE_TYPE, phtotoImag);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        if (requestCode == PreviewActivity.REQ_PREVIEW && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_FILE_PATH, outputFilePath);
            //当设备处于竖屏情况时，宽高的值 需要互换，横屏不需要
            intent.putExtra(RESULT_FILE_WIDTH, resolution.getHeight());
            intent.putExtra(RESULT_FILE_HEIGHT, resolution.getWidth());
            intent.putExtra(RESULT_FILE_TYPE, !takingPicture);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    //4.4版本之前可以直接获取uri
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagepath = getImagePath( uri, null );
        displayImage2(imagepath);
    }

//    4.4之后的版本，需要对uri进行处理
    @TargetApi( 19 )
    private void handleImageOnKitKat(Intent data) {
        imagepath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri( this, uri )){
            String docId = DocumentsContract.getDocumentId( uri );
            //document类型处理方法-media文件，通过douument id处理
            if("com.android.providers.media.documents".equals( uri.getAuthority() )){
                String id = docId.split( ":" )[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagepath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }
            else if("com.android.providers.downloads.documents".equals( uri.getAuthority() )){
                Uri contentUri = ContentUris.withAppendedId( Uri.parse( "content://downloads/public_downloads" ), Long.valueOf( docId ) );
                imagepath = getImagePath(contentUri, null);
            }
        }
        //content类型处理方法，用普通方法处理
        else if("content".equalsIgnoreCase( uri.getScheme() )){
            imagepath = getImagePath(uri, null);
        }
        //file类型处理方法，直接获取图片路径
        else if("file".equalsIgnoreCase( uri.getScheme() )){
            imagepath = uri.getPath();
        }
        //将图片显示
        displayImage2(imagepath);
    }


    private void displayImage2(String imagePath) {
        if(imagePath != null){
            if(MediaFile.isImageFileType(imagePath)){
                phtotoImag=false;
//                PreviewActivity.startActivityForResult(this, imagePath, phtotoImag, "完成");
            }
            if (MediaFile.isVideoFileType(imagePath)){
                phtotoImag=true;
//                PreviewActivity.startActivityForResult(this, imagePath, phtotoImag, "完成");
            }
        }
        else Toast.makeText( this, "faild to get image", Toast.LENGTH_SHORT ).show();
    }
    private void onFileSaved(File file) {
        outputFilePath = file.getAbsolutePath();
        String mimeType = takingPicture ? "image/jpeg" : "video/mp4";
        MediaScannerConnection.scanFile(this, new String[]{outputFilePath}, new String[]{mimeType}, null);
        PreviewActivity.startActivityForResult(this, outputFilePath, !takingPicture, "完成");
    }

    //检查权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            deniedPermission.clear();
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int result = grantResults[i];
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedPermission.add(permission);
                }
            }

            if (deniedPermission.isEmpty()) {
                bindCameraX();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.capture_permission_message))
                        .setNegativeButton(getString(R.string.capture_permission_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                CaptureActivity.this.finish();
                            }
                        })
                        .setPositiveButton(getString(R.string.capture_permission_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] denied = new String[deniedPermission.size()];
                                ActivityCompat.requestPermissions(CaptureActivity.this, deniedPermission.toArray(denied), PERMISSION_CODE);
                            }
                        }).create().show();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void bindCameraX() {
        CameraX.unbindAll();

        //查询一下当前要使用的设备摄像头(比如后置摄像头)是否存在
        boolean hasAvailableCameraId = false;
        try {
            hasAvailableCameraId = CameraX.hasCameraWithLensFacing(mLensFacing);
        } catch (CameraInfoUnavailableException e) {
            e.printStackTrace();
        }

        if (!hasAvailableCameraId) {
            showErrorToast("无可用的设备cameraId!,请检查设备的相机是否被占用");
            finish();
            return;
        }

        //查询一下是否存在可用的cameraId.形式如：后置："0"，前置："1"
        String cameraIdForLensFacing = null;
        try {
            cameraIdForLensFacing = CameraX.getCameraFactory().cameraIdForLensFacing(mLensFacing);
        } catch (CameraInfoUnavailableException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(cameraIdForLensFacing)) {
            showErrorToast("无可用的设备cameraId!,请检查设备的相机是否被占用");
            finish();
            return;
        }

        PreviewConfig config = new PreviewConfig.Builder()
                //前后摄像头
                .setLensFacing(mLensFacing)
                //旋转角度
                .setTargetRotation(rotation)
                //分辨率
                .setTargetResolution(resolution)
                //宽高比
                .setTargetAspectRatio(rational)
                .build();
        preview = new Preview(config);

        imageCapture = new ImageCapture(new ImageCaptureConfig.Builder()
                .setTargetAspectRatio(rational)
                .setTargetResolution(resolution)
                .setLensFacing(mLensFacing)
                .setTargetRotation(rotation).build());

        videoCapture = new VideoCapture(new VideoCaptureConfig.Builder()
                .setTargetRotation(rotation)
                .setLensFacing(mLensFacing)
                .setTargetResolution(resolution)
                .setTargetAspectRatio(rational)
                //视频帧率
                .setVideoFrameRate(25)
                //bit率
                .setBitRate(3 * 1024 * 1024).build());
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {

                textureView = mBinding.textureView;
                ViewGroup parent = (ViewGroup) textureView.getParent();
                parent.removeView(textureView);
                parent.addView(textureView, 0);

                textureView.setSurfaceTexture(output.getSurfaceTexture());
            }
        });


        //上面配置的都是期望的分辨率
        List<UseCase> newUseList = new ArrayList<>();
        newUseList.add(preview);
        newUseList.add(imageCapture);
        newUseList.add(videoCapture);
        //下面我们要查询一下 当前设备它所支持的分辨率有哪些，然后再更新一下 所配置的几个usecase
        Map<UseCase, Size> resolutions = CameraX.getSurfaceManager().getSuggestedResolutions(cameraIdForLensFacing, null, newUseList);
        Iterator<Map.Entry<UseCase, Size>> iterator = resolutions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UseCase, Size> next = iterator.next();
            UseCase useCase = next.getKey();
            Size value = next.getValue();
            Map<String, Size> update = new HashMap<>();
            update.put(cameraIdForLensFacing, value);
            useCase.updateSuggestedResolution(update);
        }
        CameraX.bindToLifecycle(this, preview, imageCapture, videoCapture);
    }

    @Override
    protected void onDestroy() {
        CameraX.unbindAll();
        super.onDestroy();
    }
}

