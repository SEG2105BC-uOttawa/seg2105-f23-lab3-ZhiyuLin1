package com.example.simplecalculator;

/*
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
*/

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private enum Operator {none, add, sub, mul, div, eq}
    private double data01=0, data02 = 0;
    private Operator opp = Operator.none;
    private boolean hasDot = false;
    private boolean requiresCleaning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Function called every time a number button is pressed
    public void onClickNumericalButton(View view) {
        //Getting ID of pressed Button
        int pressID = view.getId();

        //Getting Text object where we display the current number value
        TextView curText = (TextView)findViewById(R.id.resultText);

        //If we had an equal sign pressed last, standard operation is to clean
        if (opp == Operator.eq) {
            opp = Operator.none;
            curText.setText("");
        }

        if (requiresCleaning) {
            requiresCleaning = false;
            curText.setText("");
        }

        //Figuring out which button was pressed and updating the represented text field object
        if (pressID == R.id.button00) {
            curText.setText(curText.getText() + "0");
        } else if (pressID == R.id.button01) {
            curText.setText(curText.getText() + "1");
        } else if (pressID == R.id.button02) {
            curText.setText(curText.getText() + "2");
        } else if (pressID == R.id.button03) {
            curText.setText(curText.getText() + "3");
        } else if (pressID == R.id.button04) {
            curText.setText(curText.getText() + "4");
        } else if (pressID == R.id.button05) {
            curText.setText(curText.getText() + "5");
        } else if (pressID == R.id.button06) {
            curText.setText(curText.getText() + "6");
        } else if (pressID == R.id.button07) {
            curText.setText(curText.getText() + "7");
        } else if (pressID == R.id.button08) {
            curText.setText(curText.getText() + "8");
        } else if (pressID == R.id.button09) {
            curText.setText(curText.getText() + "9");
        } else if (pressID == R.id.buttonDot) {
            if (!hasDot) {
                curText.setText(curText.getText() + ".");
                hasDot = true;
            } else {
                //TODO Decide if we will implement a special case for when we already have a decimal point.
                //For now we follow android guidelines and ignore
            }
        } else {
            curText.setText("ERROR");
            Log.d("Error", "Error: Unknown Button pressed!");
        }
    }

    public void onClickFunctionButton(View view) {
        //Getting ID of pressed Button
        int pressID = view.getId();

        //Getting Text object where we display the current number value
        TextView curText = (TextView)findViewById(R.id.resultText);

        //CE clears all regardless of context
        if (pressID == R.id.buttonCE) {
            opp = Operator.none;
            curText.setText("");
            data01 = 0;
            data02 = 0;
            requiresCleaning = false;
            return;
        }

        String dataText = curText.getText().toString();
        double numberVal = dataText.length() > 0 ? Double.parseDouble(dataText) : 0;

        //Checking if we have "prior data" aka something stored in data1 that we should use
        //Having data1 !≃ 0 is not enough, we need to know of a previous operator, hence this
        if (opp == Operator.none) {
            data01 = numberVal;
            requiresCleaning = true;
            if (pressID == R.id.buttonEq) {//TODO Decide if we will implement a special function for the no data equal operation
                opp = Operator.eq;
                data01 = 0;
            } else if (pressID == R.id.buttonAdd) {
                opp = Operator.add;
            } else if (pressID == R.id.buttonSub) {
                opp = Operator.sub;
            } else if (pressID == R.id.buttonDiv) {
                opp = Operator.div;
            } else if (pressID == R.id.buttonMult) {
                opp = Operator.mul;
            } else if (pressID == R.id.buttonCE) {
                opp = Operator.none;
            }
        } else {
            double result = 0;
            data02 = numberVal;

            switch (opp) {
                case eq:
                    //TODO: Technically this doesn't do anything and shouldn't accur
                    break;

                case none:
                    //TODO: Technically this can't do anything and will never occur
                    break;

                case add:
                    result = data01 + data02;
                    break;

                case sub:
                    result = data01 - data02;
                    break;

                case div:
                    result = data01 / data02;
                    break;

                case mul:
                    result = data01 * data02;
                    break;
            }
            data01 = result;
            opp = Operator.none;
            if ( (result - (int)result) != 0) {
                curText.setText(String.valueOf(result));
            } else {
                curText.setText(String.valueOf((int)result));
            }
        }
    }


}