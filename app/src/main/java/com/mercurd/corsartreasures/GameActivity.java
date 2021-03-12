package com.mercurd.corsartreasures;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    int [] candies = {
            R.drawable.crown,
            R.drawable.diamont,
            R.drawable.diamont_sunduk,
            R.drawable.kazan,
            R.drawable.mishok,
            R.drawable.sunduk
    };

    TextView time_txt;
    CountDownTimer countDownTimer;

    int widthofblock, numberOfBlock = 8, witchofScreen;

    ArrayList<ImageView> candy = new ArrayList<>();

    int candyToBeDragged, candyToBeReplaced;
    int notCandy = R.drawable.transparent;

    Handler mHandler;
    int interval = 100;

    TextView scoreResult;
    ImageView timeimage;

    public int score = 0;

    long animationDuration = 1000;






    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreResult = findViewById(R.id.score);
        time_txt = findViewById(R.id.timer);
        timeimage = findViewById(R.id.time_img);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        witchofScreen = displayMetrics.widthPixels;
        int heightOfScreen = displayMetrics.heightPixels;
        widthofblock = witchofScreen / numberOfBlock;
        createBoard();

        //timer
        countDownTimer = new CountDownTimer(120000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {


                time_txt.setText(millisUntilFinished/1000 + " s");

            }

            @Override
            public void onFinish() {
                time_txt.setText("finish");
                Toast.makeText(GameActivity.this, "finish", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(GameActivity.this, GameOver.class);
                intent.putExtra("score", score);
                startActivity(intent);



            }
        }.start();



        for (ImageView imageView: candy){
            imageView.setOnTouchListener(new OnSwipeListener(this){
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged -1;
                    candyInterchange();



                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged +1;
                    candyInterchange();



                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - numberOfBlock;
                    candyInterchange();

                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + numberOfBlock;
                    candyInterchange();

                }
            });
        }




        mHandler = new Handler();
        startRepeat();
    }
    private void checkRowForTree(){
        for (int i = 0; i<62; i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i)){
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank
                        && (int) candy.get(x++).getTag() == chosedCandy
                        && (int) candy.get(x).getTag() == chosedCandy)
                {
                    score = score + 3;
                    scoreResult.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);

                }

            }



        }
        moveDownCandies();
    }
    private void checkColumnForTree(){
        for (int i = 0; i<47; i++){
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
                int x = i;
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank
                        && (int) candy.get(x+ numberOfBlock).getTag() == chosedCandy
                        && (int) candy.get(x+2* numberOfBlock).getTag() == chosedCandy) {

                    score = score + 3;
                    scoreResult.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x= x + numberOfBlock;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x= x + numberOfBlock;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);



                }

            }
        moveDownCandies();


    }

    Runnable repeatCheker = new Runnable() {
        @Override
        public void run() {
            try {
                checkRowForTree();
                checkColumnForTree();
                moveDownCandies();




            }
            finally {
                mHandler.postDelayed(repeatCheker, interval);

            }

        }
    };

    void startRepeat(){
        repeatCheker.run();
    }

    private void candyInterchange(){
        int background = (int) candy.get(candyToBeReplaced).getTag();
        int background1 = (int) candy.get(candyToBeDragged).getTag();
        candy.get(candyToBeDragged).setImageResource(background);
        candy.get(candyToBeReplaced).setImageResource(background1);
        candy.get(candyToBeDragged).setTag(background);
        candy.get(candyToBeReplaced).setTag(background1);



    }

    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.board);
        gridLayout.setRowCount(numberOfBlock);
        gridLayout.setColumnCount(numberOfBlock);
        //square
        gridLayout.getLayoutParams().width = witchofScreen;
        gridLayout.getLayoutParams().height = witchofScreen;

        for (int i = 0; i< numberOfBlock * numberOfBlock; i++){
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(widthofblock, widthofblock));
            imageView.setMaxHeight(widthofblock);
            imageView.setMaxWidth(widthofblock);

            int randomCandy =(int) Math.floor(Math.random()*candies.length);//this generate randomValue from array

            imageView.setImageResource(candies[randomCandy]);
            imageView.setTag(candies[randomCandy]);

            candy.add(imageView);

            gridLayout.addView(imageView);

        }

    }

    private void moveDownCandies() {
        Integer[] firstRow = {1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(firstRow);
        for (int i = 55; i >= 0; i--)
        {
            if ((int) candy.get(i + numberOfBlock).getTag() == notCandy)
            {
                candy.get(i + numberOfBlock).setImageResource((int) candy.get(i).getTag());
                candy.get(i + numberOfBlock).setTag(candy.get(i).getTag());
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);

                if (list.contains(i) && (int) candy.get(i).getTag() == notCandy)
                {
                    int randomColor = (int) Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomColor]);
                    candy.get(i).setTag(candies[randomColor]);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((int) candy.get(i).getTag() == notCandy)
            {
                int randomColor = (int) Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomColor]);
                candy.get(i).setTag(candies[randomColor]);

            }
        }
    }

   public void handleAnimation(View view) {
        //ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "x", 420f);
        /*ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "y", 420f);
        animatorY.setDuration(animationDuration);

        //ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(imageView, View.ALPHA, )

        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(timeimage, "rotation" , 0f, 360f);
        rotateAnimation.setDuration(animationDuration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimation);
        animatorSet.start();*/

    }


}