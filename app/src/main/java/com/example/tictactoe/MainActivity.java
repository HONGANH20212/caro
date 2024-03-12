    package com.example.tictactoe;

    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import android.graphics.Point;
    import android.util.Log;
    import android.view.View;
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.GridLayout;
    import android.graphics.Color;
    import android.view.Gravity;
    import android.widget.TextView;
    import android.content.DialogInterface;


    import java.util.Objects;

    public class MainActivity extends AppCompatActivity {
        private TicTacToe tttGame;
        private Button[][] buttons;
        private TextView status;

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
            Log.w("Width of screen: -- ", String.valueOf(size.x));
            Log.w("Width of Grid Cell: -- ", String.valueOf(w));

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
            status.setHeight(w);
            status.setGravity(Gravity.CENTER);
            status.setBackgroundColor(Color.GREEN);
            status.setTextSize(3 * 15);
            status.setText(tttGame.result());

            gridLayout.addView(status);

            //Set gridLayout
            setContentView(gridLayout);
        }

        public void update(int row, int col){
            int play = tttGame.play(row, col);
            if( play == 1) {
                buttons[row][col].setText("X");
            } else if(play == 2) {
                buttons[row][col].setText("O");
            }

            if(tttGame.isGameOver()) {
                //game over
                status.setBackgroundColor(Color.RED);
                status.setText(tttGame.result());
                enableButtons(false);

                showNewGameDialog();  //offer to play again
            }

        }
        public void enableButtons(boolean enabled){
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for( int col = 0; col <TicTacToe.SIDE; col++)
                    buttons[row][col].setEnabled(enabled);
        }

        public void resetButton(){
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int col = 0; col < TicTacToe.SIDE; col++)
                    buttons[row][col].setText(" ");
        }

        public void showNewGameDialog(){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("This is fun");
            alert.setMessage("Play again?\n \n 02-Võ Thị Hồng Ánh-N20DCVT002");
            PlayDialog playAgain = new PlayDialog();
            alert.setPositiveButton("YES", playAgain);
            alert.setNegativeButton("NO", playAgain);
            AlertDialog alertDialog = alert.create();
            alertDialog.getWindow().setGravity(Gravity.TOP);
            alertDialog.show();
        }

        private class ButtonHandler implements View.OnClickListener {
            public void onClick(View v){
                Log.w("MainActivity", "Inside onClick, v = " + v );
                for (int row = 0; row < TicTacToe.SIDE; row++)
                    for (int column = 0; column < TicTacToe.SIDE; column++)
                        if (v == buttons[row][column])
                            update(row, column);
            }
        }

        private class PlayDialog implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialog, int id){
                if (id == -1){
                    tttGame.resetGame();
                    enableButtons(true);
                    resetButton();
                    status.setBackgroundColor(Color.GREEN);// yes button
                    status.setText(tttGame.result());
                }else if (id == -2) { //No button
                    MainActivity.this.finish();
                }
            }
        }

    }
