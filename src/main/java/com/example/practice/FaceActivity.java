package com.example.practice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.util.List;

public class FaceActivity extends Activity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_face);
        mContext=this;

        final RelativeLayout RelativeLayout_main = findViewById(R.id.RelativeLayout_main);

        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();

        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.face);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(highAccuracyOpts);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        // Task completed successfully
                                        // ...

                                        Log.d("Faces",faces.toString());

                                        //위치 찾기 위함
                                        Point p = new Point();
                                        Display display = getWindowManager().getDefaultDisplay();
                                        display.getSize(p);


                                        //사진이 여러개일때를 위해 for문
                                        for (FirebaseVisionFace face : faces) {

                                            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                            // nose available):
                                            FirebaseVisionFaceLandmark leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                                            //눈의 위치를 찾아줌
                                            //Landmark.getPosition() : 각 랜드마크의 위치가 있음
                                            float lex = leftEye.getPosition().getX();
                                            float ley = leftEye.getPosition().getY();

                                            FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                            float lcx = leftCheek.getPosition().getX();
                                            float lcy = leftCheek.getPosition().getY();

                                            FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                            float rcx = rightCheek.getPosition().getX();
                                            float rcy = rightCheek.getPosition().getY();



                                            //xml에 imageview 로 만들어도 되지만 추가될때마다 만들어줘야해서
                                            //class 에서 임시로 만들어 붙여줌

                                            ImageView imageLE=new ImageView(mContext);
                                            imageLE.setImageResource(R.drawable.mung);
                                            imageLE.setX(p.x*lex/bitmap.getWidth()-100);
                                            imageLE.setY(p.y*ley/bitmap.getHeight());

                                            imageLE.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
                                            RelativeLayout_main.addView(imageLE);

                                            ImageView imageLC=new ImageView(mContext);
                                            imageLC.setImageResource(R.drawable.left);
                                            imageLC.setX(p.x*lcx/bitmap.getWidth()-100);
                                            imageLC.setY(p.y*lcy/bitmap.getHeight()-100);

                                            imageLC.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
                                            RelativeLayout_main.addView(imageLC);

                                            ImageView imageRC=new ImageView(mContext);
                                            imageRC.setImageResource(R.drawable.right);
                                            imageRC.setX(p.x*rcx/bitmap.getWidth()-100);
                                            imageRC.setY(p.y*rcy/bitmap.getHeight()-100);

                                            imageRC.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
                                            RelativeLayout_main.addView(imageRC);
                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });

    }
}