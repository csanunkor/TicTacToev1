package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    TicTacToe tttGame;
    public Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tttGame = new TicTacToe();
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
        gridLayout.setRowCount(TicTacToe.SIDE);
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

}