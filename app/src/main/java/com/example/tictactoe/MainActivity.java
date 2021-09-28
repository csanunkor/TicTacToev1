package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private TicTacToe tttGame;
    private ButtonGridAndTextView tttView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tttGame = new TicTacToe();
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / TicTacToe.SIDE;
        ButtonHandler bh = new ButtonHandler();
        tttView = new ButtonGridAndTextView(this, w, TicTacToe.SIDE, bh);
        tttView.setStatusText("PLAY!!");
        setContentView(tttView);
    }

    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.w( "MainActivity", "Inside onClick, v = " + v );
            for( int row = 0; row < TicTacToe.SIDE; row++ )
                for(int col = 0; col < TicTacToe.SIDE; col++ ) {
                    if (tttView.isButton((Button) v, row, col)) {
                        int play = tttGame.play( row, col);
                        if (play == 1) {
                            tttView.setButtonText(row, col, "X");
                        }
                        else if (play == 2) {
                            tttView.setButtonText(row, col, "O");
                        }
                        //when 1 player wins or tie
                        if (tttGame.isGameOver()) {
                            tttView.setStatusBackgroundColor(Color.RED);
                            tttView.enableButtons(false);
                            tttView.setStatusText(tttGame.result());
                            showNewGameDialog();
                        }
                    }
                }
        }
    }

    private class PlayDialog implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                tttGame.resetGame();
                tttView.enableButtons(true);
                tttView.resetButtons();
                tttView.setStatusBackgroundColor(Color.GREEN);
                tttView.setStatusText("PLAY!!");
            }
            else if (i == -2) {
                MainActivity.this.finish();
            }
        }
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
