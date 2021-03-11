package com.mercurd.corsartreasures;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class testmain extends AppCompatActivity {

    int [] fructs = {
            R.drawable.sunduk,
            R.drawable.sunduk,
            R.drawable.sunduk,
            R.drawable.sunduk,
            R.drawable.sunduk,
            R.drawable.sunduk,
    };

    int witchOFblock, witchOFScreed, numberOfblock = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        witchOFScreed = displayMetrics.widthPixels;
        int heightOfScreen = displayMetrics.heightPixels;

        witchOFblock = witchOFScreed/numberOfblock;
        createBlockBoard();

    }

    private void createBlockBoard() {
        GridLayout gridLayout = findViewById(R.id.gridNemain);
        gridLayout.setColumnCount(numberOfblock);
        gridLayout.setRowCount(numberOfblock);
        gridLayout.getLayoutParams().height = witchOFScreed;
        gridLayout.getLayoutParams().width = witchOFScreed;

        for (int i=0; i< numberOfblock*numberOfblock; i++){
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(witchOFblock, witchOFblock));
            imageView.setMaxWidth(witchOFblock);
            imageView.setMaxHeight(witchOFblock);

            int randomBlock = (int) Math.floor(Math.random()*fructs.length);
            imageView.setImageResource(fructs[randomBlock]);
            gridLayout.addView(imageView);
        }

    }
}
