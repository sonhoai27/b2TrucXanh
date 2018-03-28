package com.sonhoai.sonho.trucxanh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imgBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        final Board board = new Board(getApplicationContext(), 300,300, 4,4);
        board.init();
        imgBitmap.setImageBitmap( board.drawBoard());
        imgBitmap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return board.onTouch(view, motionEvent);
            }
        });
    }

    private void init(){
        imgBitmap = findViewById(R.id.imgBitmap);
    }
}
