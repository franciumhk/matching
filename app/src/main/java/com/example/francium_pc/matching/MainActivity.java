package com.example.francium_pc.matching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    Button btnNDK, btnRestore;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("message");
        System.loadLibrary("imgCanny");
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRestore = (Button) this.findViewById(R.id.btnRestore);
        btnRestore.setOnClickListener(new ClickEvent());
        btnNDK = (Button) this.findViewById(R.id.btnNDK);
        btnNDK.setOnClickListener(new ClickEvent());
        imgView = (ImageView) this.findViewById(R.id.ImageView01);
        Bitmap img = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.flower)).getBitmap();
        imgView.setImageBitmap(img);
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI() + '\n' + getMsg());
    }

    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
            //btnRestore.setText(ImgFun());
            if (v == btnNDK) {
                long current = System.currentTimeMillis();
                Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.flower)).getBitmap();
                int w = img1.getWidth(), h = img1.getHeight();
                int[] pix = new int[w * h];
                img1.getPixels(pix, 0, w, 0, 0, w, h);
                int[] resultInt = getCannyImg(pix, w, h);
                Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
                long performance = System.currentTimeMillis() - current;
                imgView.setImageBitmap(resultImg);
            }else if (v == btnRestore){
                Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.flower)).getBitmap();
                imgView.setImageBitmap(img1);
            }
        }
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native String getMsg();
    public native int [] getCannyImg(int[] a, int b, int c);
}
