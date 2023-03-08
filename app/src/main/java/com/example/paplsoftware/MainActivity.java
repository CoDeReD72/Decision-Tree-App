package com.example.paplsoftware;

import static android.graphics.Color.rgb;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    NodeMap nodeMap;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream in_s = getCSVRes();
        nodeMap = new NodeMap(in_s);

        TextView questionField = findViewById(R.id.questionBox);
        questionField.setText(NodeMap.currentNode().getQuestion());

    }

    protected InputStream getCSVRes(){
        Resources res = getResources();
        return res.openRawResource(R.raw.data);
    }

    /*
        ================Shortcut Functions==============
     */
    public TextView getUIQuestion(){return findViewById(R.id.questionBox); }

    public boolean noDecision(){return NodeMap.currentNode().getYesNode() == NodeMap.currentNode().getNoNode();}

    public void checkOptions(){if(noDecision()){changeOptions();}}

    /*
    ================HANDLERS==============
     */
    public void yesClickHandler(){
        TextView questionField = getUIQuestion();
        NodeMap.decision(1);
        questionField.setText(NodeMap.currentNode().getQuestion());
        checkOptions();

    }

    public void noClickHandler(){
        TextView questionField = getUIQuestion();
        NodeMap.decision(2);
        questionField.setText(NodeMap.currentNode().getQuestion());
        checkOptions();
    }

    public void continueHandler(){
        TextView questionField = getUIQuestion();
        NodeMap.decision(1); //Decision(1 & 2) should be the same
        changeOptions();
        questionField.setText(NodeMap.currentNode().getQuestion());
        checkOptions();
    }

    public void changeOptions(){ //Change from Yes/No Buttons to a continue/restart button
        Button yesButton = findViewById(R.id.yesBtn);
        Button noButton = findViewById(R.id.noBtn);
        Button continueButton = findViewById(R.id.continueBtn);

        if(continueButton.getVisibility() == View.INVISIBLE) {
            yesButton.setVisibility(View.INVISIBLE);
            noButton.setVisibility(View.INVISIBLE);
            continueButton.setVisibility(View.VISIBLE);
            continueSwap(continueButton);
        }else if(continueButton.getVisibility() == View.VISIBLE){
            yesButton.setVisibility(View.VISIBLE);
            noButton.setVisibility(View.VISIBLE);
            continueButton.setVisibility(View.INVISIBLE);
        }

    }

    public void continueSwap(Button continueButton){
        if(continueButton.getText() != "Continue")
            continueButton.setText("Continue");
        continueButton.setBackgroundColor(rgb(103,58,183));
        continueButton.setTextColor(rgb(255,255,255));
        if(NodeMap.isEndNode()){
            continueButton.setText("Restart");
            continueButton.setBackgroundColor(-256);
            continueButton.setTextColor(rgb(0,0,0));
        }
    }

}