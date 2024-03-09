package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TicTacToe tttGame;
    private Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tttGame = new TicTacToe();
        buildGuiByCode();
    }

    public void buildGuiByCode() {
        // Get width of the screen
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / TicTacToe.SIDE;

        //Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE + 1);

        //Create the buttons and add them to gridLayout
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        ButtonHandler bh = new ButtonHandler();
        for (int row = 0; row < TicTacToe.SIDE; row++) {
            for (int col = 0; col < TicTacToe.SIDE; col++) {
                buttons[row][col] = new Button(this);
                buttons[row][col].setTextSize((int) (w * .2));
                buttons[row][col].setOnClickListener( bh );
                gridLayout.addView(buttons[row][col], w, w);
            }
        }
        //Set up layout
        status = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE, 1);
        GridLayout.Spec columnSpec = GridLayout.spec(0, TicTacToe.SIDE);
        GridLayout.LayoutParams lpStatus = new GridLayout.LayoutParams(rowSpec, columnSpec);
        status.setLayoutParams(lpStatus);
        //set up status
        status.setWidth( TicTacToe.SIDE * w);
        status.setHight(w);
        status.setGravity(Gravity.CENTER);
        status.SetBackgroundColor(Color.GREEN);
        status.setTextSize((int)(w * 15));
        status.setText( tttGame.result());

        gridLayout.addView(status);
        //Set gridLayout
        setContentView(gridLayout);
    }
    public void update(int row, int col){
        int play = tttGame.play(row, col);
        if( play == 1)
            buttons[row][col].setText("X");
        else if(play == 2)
            buttons[row][col].setText("O");
        if(tttGame.isGameOver()) //game over
            stutus.setBackgroundCorlor(Color.RED);
            enableButtons(false);
            status.setText(tttGame.result());
    }
    public void enableButtons(boolean enabled){
        for (int row = 0; row < TicTacToe.SIDE; row++)
            for( int col = 0; col <TicTacToe.SIDE; col++)
                buttons[row][col].setEnabled(enabled);
    }
    private class ButtonHandler implements View.OnClickListener{
        public void onClick(View v){
            Log.w("MainActivity", "Inside onClick, v = " + v );
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int column = 0; column < TicTacToe.SIDE; column++)
                    if (v == buttons[row][column])
                        update(row, column);
        }
    }
}