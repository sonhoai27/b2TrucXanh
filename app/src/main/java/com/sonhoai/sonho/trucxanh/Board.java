package com.sonhoai.sonho.trucxanh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by sonho on 3/28/2018.
 */

public class Board {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Context context;
    private int bitmapWidth, bitmapHeight, colQty,rowQty;
    private List<Line> lines;
    private int[][] board;
    private List<Bitmap> listBitmap;
    private List<Integer> source;
    int flag = 0;
    int temp = 0;

    public Board() {
    }

    public Board(Context context, int bitmapWidth, int bitmapHeight, int colQty, int rowQty) {
        this.context = context;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.colQty = colQty;
        this.rowQty = rowQty;
    }

    public void init(){
        makeSource();
        board = Board.createBoard(colQty,rowQty);
        lines = new ArrayList<>();
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setStrokeWidth(2);
        int celWidth = bitmapWidth/colQty;
        int celHeight = bitmapHeight/rowQty;
        for(int i = 0; i <= colQty; i++){
            lines.add(new Line(celWidth*i, 0, celWidth*i, bitmapHeight));
        }
        for(int i = 0; i <= rowQty; i++){
            lines.add(new Line(0, i*celHeight, bitmapWidth, i*celHeight));
        }
    }
    public static int[][] createBoard(int row, int col){
        Random  random = new Random();
        int mang[] = new int[col*row];
        int board[][] = new int[col][row];
        for(int i = 0; i< row*col;i++){
            mang[i] = i;
        }
        //thay doi thu tu mang
        for(int dem = row*col - 1; dem > 0; --dem){
            int i = random.nextInt(dem);
            int temp = mang[dem];
            mang[dem] = mang[i];
            mang[i] = temp;
        }

        //tạo mảng 2 chiều có trùng. do có 16 phần từ, mà mỗi
        //phần tử trùng 1 nên còn 8, vì vậy cái nào lớn hơn 7 thì - 8 đi
        for(int i = 0; i < row*col;i++){
            board[i/4][i%4] = mang[i] > 7 ? mang[i] - 8 : mang[i];
        }
        return board;
    }

    public Bitmap drawBoard(){
        for(int i = 0; i < lines.size(); i++){
            canvas.drawLine(
                    lines.get(i).getX1(),
                    lines.get(i).getY1(),
                    lines.get(i).getX2(),
                    lines.get(i).getY2(),
                    paint
            );
        }
        return bitmap;
    }

    public boolean onTouch(final View view, MotionEvent motionEvent){
        int cellWidth = bitmapWidth / colQty;
        int cellHeight = bitmapHeight / rowQty;
        int colIndex = (int) (motionEvent.getX() / (view.getWidth() / colQty));
        int rowIndex = (int) (motionEvent.getY() / (view.getHeight() / rowQty));
        Log.i("DOO", String.valueOf(board[colIndex][rowIndex]));
        onDrawBoard(colIndex,rowIndex,cellWidth,cellHeight);
        view.invalidate();
        return true;
    }

    private void onDrawBoard(int colIndex, int rowIndex, int cellWidth, int cellHeight){
        int padding = 0;
        checkWin(colIndex,rowIndex);
        canvas.drawBitmap(
                createBitmap(source.get(board[colIndex][rowIndex])),
            new Rect(0,0,createBitmap(source.get(board[colIndex][rowIndex])).getWidth(), createBitmap(source.get(board[colIndex][rowIndex])).getHeight()),
            new Rect(colIndex*cellWidth+padding,rowIndex*cellHeight+padding,(colIndex+1)*cellWidth -padding, (rowIndex+1)*cellHeight -padding),
            paint);
    }


    private Bitmap createBitmap(int x){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.output);
        int numImg = rowQty*colQty/2;
        int width = bitmap.getWidth()/numImg;
        int height = bitmap.getHeight();
        Bitmap temp = Bitmap.createBitmap(bitmap,x,0, width,height);
        return temp;
    }
    private void makeSource(){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.output);
        int numImg = rowQty*colQty/2;
        int x = 0;
        int width = bitmap.getWidth()/numImg;
        source = new ArrayList<>();
        for(int i = 0; i < numImg; i++ ){
            source.add(x);
            x +=width;
        }
    }
    private void checkWin(int col, int row){
        if(temp == 0){
            temp = board[col][row];
        }else if(temp != 0){
            if(temp == board[col][row]){
                Log.i("THONG_BAO", "THANG");
                temp = 0;
            }else {
                Log.i("THONG_BAO", "SAI");
                temp = 0;
            }
        }
    }
}
