package com.example.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class ButtonGridAndTextView extends GridLayout {
    private int side;
    private Button[][] buttons;
    private TextView status;

    public ButtonGridAndTextView(Context context, int width, int newSide,
                                 OnClickListener listener) {
        super(context);
        side = newSide;
        // Set # of rows and columns of this GridLayout
        setColumnCount(side);
        setRowCount(side + 1);

        // Create the layout manager as a GridLayout
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setRowCount(TicTacToe.SIDE);
        gridLayout.setColumnCount(TicTacToe.SIDE);


        // Create the buttons and add them to gridLayout
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for(int col = 0; col < TicTacToe.SIDE; col++) {
                buttons[row][col] = new Button(context);
                buttons[row][col].setTextSize((float) (width * 0.2));
                buttons[row][col].setOnClickListener(listener);
                addView(buttons[row][col], width, width);
            }
        }
        // set up layout parameters of 4th row of gridLayout
        GridLayout.Spec rowSpec = GridLayout.spec(3,1);
        GridLayout.Spec columnSpec = GridLayout.spec(0,3);
        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowSpec, columnSpec);

        status = new TextView(context);
        status.setLayoutParams(lp);
        status.setBackgroundColor(Color.GREEN);
        status.setText("PLAY!!");
        status.setTextSize((float) (width * .1));
        status.setHeight(width);
        status.setWidth(width*3);
        status.setGravity(Gravity.CENTER);

        addView(status, lp);
    }

    public void setStatusText(String text) {
        status.setText(text);
    }

    public void setStatusBackgroundColor(int color) {
        status.setBackgroundColor(color);
    }

    public void setButtonText(int row, int column, String text) {
        buttons[row][column].setText(text);
    }

    public boolean isButton(Button b, int row, int column) {
        return (b == buttons[row][column]);
    }

    public void resetButtons() {
        for(int row = 0; row < side; row++)
            for(int col = 0; col < side; col++)
                buttons[row][col].setText("");
    }

    public void enableButtons(boolean enabled) {
        for(int row = 0; row < side; row++)
            for(int col = 0; col < side; col++)
                buttons[row][col].setEnabled(enabled);
    }

}
