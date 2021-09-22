package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TicTacToe tttGame;
    public Button[][] buttons;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tttGame = new TicTacToe();
        status = new TextView(this);
        buildGUIByCode();
    }

    public void buildGUIByCode() {
        //Get Width and assign to w
        Point size = new Point();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        display.getSize(size);

        int w = size.x/TicTacToe.SIDE;

        // Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setRowCount(TicTacToe.SIDE + 1);
        gridLayout.setColumnCount(TicTacToe.SIDE);

        //instantiate buttonHandler
        buttonHandler handler = new buttonHandler();

        // Create the buttons and add them to gridLayout
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for(int col = 0; col < TicTacToe.SIDE; col++) {
                buttons[row][col] = new Button(this);
                buttons[row][col].setTextSize((float) (w * 0.2));
                buttons[row][col].setOnClickListener(handler);
                gridLayout.addView(buttons[row][col], w, w);
            }
        }
        GridLayout.Spec rowSpec = GridLayout.spec(3,1);
        GridLayout.Spec columnSpec = GridLayout.spec(0,3);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowSpec, columnSpec);

        status.setLayoutParams(lp);
        status.setBackgroundColor(Color.GREEN);
        status.setText("PLAY!!");
        status.setTextSize((float) (w * .1));
        status.setHeight(w);
        status.setWidth(size.x);
        status.setGravity(Gravity.CENTER);

        gridLayout.addView(status, lp);

        // Set gridLayout as the View of this Activity
        setContentView(gridLayout);
    }

    public void update(int row, int col) {
        Log.w("MainActivity", "Inside update: " + row + ", " + col);
        int player = tttGame.play(row, col);

        if (player == 1) {
            buttons[row][col].setText("X");
        }
        else if (player == 2) {
            buttons[row][col].setText("O");
        }

        if (tttGame.isGameOver()) {
            enableButtons(false);
            status.setBackgroundColor(Color.RED);
            if (tttGame.canNotPlay()) {
                status.setText("GAME OVER!");
            }
            else {
                status.setText("PLAYER " + tttGame.whoWon() + " WON");
            }
            showNewGameDialog();
        }
    }

    public void enableButtons(boolean enabled) {
        for(int row = 0; row < TicTacToe.SIDE; row++)
            for(int col = 0; col < TicTacToe.SIDE; col++)
                buttons[row][col].setEnabled(enabled);
    }

    private class buttonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.w( "MainActivity", "Inside onClick, v = " + v );
            for( int row = 0; row < TicTacToe.SIDE; row++ )
                for(int col = 0; col < TicTacToe.SIDE; col++ ) {
                    if (v == buttons[row][col]) {
                        update(row, col);
                    }
                }
        }
    }

    private class PlayDialog implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                tttGame.resetGame();
                enableButtons(true);
                resetButtons();
                status.setBackgroundColor(Color.GREEN);
                status.setText("PLAY!!");
            }
            else if (i == -2) {
                MainActivity.this.finish();
            }
        }
    }

    public void resetButtons() {
        for(int row = 0; row < TicTacToe.SIDE; row++)
            for(int col = 0; col < TicTacToe.SIDE; col++)
                buttons[row][col].setText("");
    }

    public void showNewGameDialog() {
        //Play again
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("That Was Fun!");
        alert.setMessage("Play Again?");

        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

}