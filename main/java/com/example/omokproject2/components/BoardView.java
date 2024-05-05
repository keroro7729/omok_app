package com.example.omokproject2.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class BoardView extends View {
    private Context context;

    private int[][] board;
    private List<Spot> spots;

    private int touchX, touchY;
    private boolean touchPermission;
    private MyListener listener;

    private boolean showProhibition, showNumbering;

    public void init(){
        // default setting
        board = new int[15][15];
        board[7][7] = 1;
        showProhibition = false;
        showNumbering = false;

        touchX = -1; touchY = -1;
        touchPermission = true;
        spots = new ArrayList<>();
    }
    public BoardView(Context context){
        super(context);
        this.context = context;
        init();
    }
    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context= context;
        init();
    }
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context= context;
        init();
    }
    public void setListener(MyListener listener){
        this.listener = listener;
    }

    private float len, l, stoneSize, textSize, lineup;
    private Paint line, black, white, redText, blackText, whiteText;
    private Paint greenPoint, gray;
    private boolean setting = false;
    public float getStoneSize(){ return stoneSize; }
    public void setPaints(int width){
        setting = true;
        len = (float)width;
        l = len/16;
        stoneSize = l/2 - 3;
        textSize = (float) (stoneSize * 1.5);
        lineup = (float) (textSize*0.4);

        line = new Paint();
        line.setColor(Color.BLACK);
        line.setStrokeWidth(3);

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        redText = new Paint();
        redText.setColor(Color.RED);
        redText.setTextSize(textSize+10);
        redText.setTextAlign(Paint.Align.CENTER);

        blackText = new Paint();
        blackText.setColor(Color.BLACK);
        blackText.setTextSize(textSize);
        blackText.setTextAlign(Paint.Align.CENTER);

        whiteText = new Paint();
        whiteText.setColor(Color.WHITE);
        whiteText.setTextSize(textSize);
        whiteText.setTextAlign(Paint.Align.CENTER);

        greenPoint = new Paint();
        greenPoint.setColor(Color.GREEN);
        greenPoint.setStyle(Paint.Style.STROKE);
        greenPoint.setStrokeWidth(5);

        gray = new Paint();
        gray.setColor(Color.GRAY);
        gray.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(!setting) setPaints(this.getWidth());
        drawBackGround(canvas);
        drawBoard(canvas);
        drawTouchCoor(canvas);
        drawSpots(canvas);
    }

    private void drawBackGround(Canvas canvas){
        canvas.drawRect(0, 0, len, len, gray);
        canvas.drawLine(1, 1, 1, len-1, line);
        canvas.drawLine(1, 1, len-1, 1, line);
        canvas.drawLine(len-1, len-1, 1, len-1, line);
        canvas.drawLine(len-1, len-1, len-1, 1, line);
        for(int i=1; i<=15; i++){
            canvas.drawLine(l*i, l, l*i, l*15, line);
            canvas.drawLine(l, l*i, l*15, l*i, line);
        }
    }

    private void drawBoard(Canvas canvas){
        for(int i=0; i<15; i++){
            for(int j=0; j<15; j++){
                float x = l*(i+1), y = l*(j+1);
                if(board[i][j] == 0) continue;
                else if(board[i][j] < 0){
                    if(!showProhibition) continue;
                    if(board[i][j] == Const.SPHB)
                        canvas.drawText("6", x, y+lineup, redText);
                    else if(board[i][j] == Const.FFPHB)
                        canvas.drawText("4", x, y+lineup, redText);
                    else if(board[i][j] == Const.TTPHB)
                        canvas.drawText("3", x, y+lineup, redText);
                }
                else if(board[i][j]%2 == 1){
                    canvas.drawCircle(x, y, stoneSize, black);
                    if(showNumbering)
                        canvas.drawText(String.valueOf(board[i][j]), x, y+lineup, whiteText);
                }
                else{
                    canvas.drawCircle(x, y, stoneSize, white);
                    if(showNumbering)
                        canvas.drawText(String.valueOf(board[i][j]), x, y+lineup, blackText);
                }
            }
        }
    }

    private void drawTouchCoor(Canvas canvas){
        if(touchX != -1 && touchY != -1){
            canvas.drawCircle(l*(touchX+1), l*(touchY+1), stoneSize/3*2, greenPoint);
        }
    }

    private void drawSpots(Canvas canvas){
        for(Spot s : spots){
            canvas.drawCircle(l*(s.x+1), l*(s.y+1), s.size, s.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(!touchPermission) return false;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX(), y = event.getY();
            if(x < l/2 || y < l/2 || x > l*15.5 || y > l*15.5)
                return false;
            for(int i=1; i<=15; i++){
                if(x < l/2 + l*i){
                    touchX = i-1;
                    break;
                }
            }
            for(int i=1; i<=15; i++){
                if(y < l/2 + l*i){
                    touchY = i-1;
                    break;
                }
            }
            listener.onTouchEvent(touchX, touchY);
            invalidate();
        }
        return false;
    }

    public void refresh(int[][] board, boolean showProhibition, List<Spot> spots){
        this.board = board;
        this.showProhibition = showProhibition;
        this.spots = spots;
        invalidate();
    }
    public void refresh(int[][] board, boolean showProhibition){
        this.board = board;
        this.showProhibition = showProhibition;
        invalidate();
    }
    public void clearSpots(){
        spots.clear();
    }
    public void addSpot(Spot spot){
        spots.add(spot);
    }
    public void turnNumbering(){ showNumbering = !showNumbering; }
}
