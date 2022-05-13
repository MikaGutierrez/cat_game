package com.example.catgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int[] elements = {
            R.drawable.purplecandy,
            R.drawable.bluecandy,
            R.drawable.yellowcandy,
            R.drawable.redcandy,
            R.drawable.graycandy
    };
    int [] cats = {
            R.drawable.slime_cat,
            R.drawable.fire_cat,
            R.drawable.water_cat,
            R.drawable.stone_cat,
            R.drawable.electro_cat
    };
    int widthOfBlock, noOfBlocks = 8, widthOfScreen;
    ArrayList<ImageView> candy = new ArrayList<>();
    int candyToBeDragget, candyToBeReplaced;
    int notCandy = R.drawable.none;
    Handler mHandker;
    TextView Health;
    TextView LevelCount;
    ImageView Cats;
    int interval = 100;
    int score = 0;
    int fire_score = 0;
    int ice_score = 0;
    int electro_score = 0;
    int stone_score = 0;
    int wood_score = 0;
    int health;
    int level = 1;
    int randomCat = (int) Math.floor(Math.random() * cats.length);
    int old_cat = cats.length + 1;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*scoreResult = findViewById(R.id.score);*/
        Health = findViewById(R.id.health);
        LevelCount = findViewById(R.id.level);
        Cats= findViewById(R.id.cat);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthOfScreen = displayMetrics.widthPixels;
        int heightOfScreen = displayMetrics.heightPixels;
        widthOfBlock = widthOfScreen / noOfBlocks;
        randomCat = 0;
        Cats.setImageResource(cats[randomCat]);
        createBoard();
        health = 10 * level;
        Health.setText(String.valueOf(health));
        for (ImageView imageView : candy)
        {
            imageView.setOnTouchListener(new OnSwipeListener(this)
            {
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    candyToBeDragget = imageView.getId();
                    if (candyToBeDragget%noOfBlocks != 0) {
                        Log.d("", candyToBeDragget + "");
                        candyToBeReplaced = candyToBeDragget - 1;
                        candyInterchanged();
                    }
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    candyToBeDragget = imageView.getId();
                    Log.d("", candyToBeDragget + "");
                    if (candyToBeDragget%noOfBlocks != noOfBlocks-1) {
                        candyToBeReplaced = candyToBeDragget + 1;
                        candyInterchanged();
                    }
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragget = imageView.getId();
                    Log.d("", candyToBeDragget + "");
                    if (candyToBeDragget >= noOfBlocks) {
                        candyToBeReplaced = candyToBeDragget - noOfBlocks;
                        candyInterchanged();
                    }
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragget = imageView.getId();
                    Log.d("", candyToBeDragget + "");
                    if (candyToBeDragget < (noOfBlocks*noOfBlocks) - noOfBlocks) {
                        candyToBeReplaced = candyToBeDragget + noOfBlocks;
                        candyInterchanged();
                    }
                }
            });
        }
        mHandker = new Handler();
        startRepeat();
    }
    /*Check*/

    /* Three */
    private  void  checkRowForThree()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() == chosedCandy)
                {
                    Log.d("Item Type:", candy.get(x).getTag() +"");
                    if ((int) candy.get(x).getTag() == R.drawable.purplecandy){
                        wood_score = wood_score + 3;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.redcandy){
                        fire_score = fire_score + 3;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.graycandy){
                        stone_score = stone_score + 3;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.yellowcandy){
                        electro_score = electro_score + 3;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.bluecandy){
                        ice_score = ice_score + 3;
                    }
                    score = score+3;
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
        movedDownCandyes();
    }

    private  void  checkColumnForThree()
    {
        for (int i = 0; i < 48; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            int x = i;
            if ((int) candy.get(x).getTag() == chosedCandy && !isBlank &&
                    (int) candy.get(x+noOfBlocks).getTag() == chosedCandy &&
                    (int) candy.get(x+2*noOfBlocks).getTag() == chosedCandy)
            {
                Log.d("Item Type:", candy.get(x).getTag() +"");
                if ((int) candy.get(x).getTag() == R.drawable.purplecandy){
                    wood_score = wood_score + 3;
                }
                if ((int) candy.get(x).getTag() == R.drawable.redcandy){
                    fire_score = fire_score + 3;
                }
                if ((int) candy.get(x).getTag() == R.drawable.graycandy){
                    stone_score = stone_score + 3;
                }
                if ((int) candy.get(x).getTag() == R.drawable.yellowcandy){
                    electro_score = electro_score + 3;
                }
                if ((int) candy.get(x).getTag() == R.drawable.bluecandy){
                    ice_score = ice_score + 3;
                }
                score = score+3;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlocks;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
            }
        }
        movedDownCandyes();
    }

    /* Fore */
    private  void  checkRowForFore()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() == chosedCandy)
                {

                    if ((int) candy.get(x).getTag() == R.drawable.purplecandy){
                        wood_score = wood_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.redcandy){
                        fire_score = fire_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.graycandy){
                        stone_score = stone_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.yellowcandy){
                        electro_score = electro_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.bluecandy){
                        ice_score = ice_score + 4;
                    }
                    score = score+4;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
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
        movedDownCandyes();
    }

    private  void  checkColumnForFore()
    {
        for (int i = 0; i < 48; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {63, 62, 61, 60, 59, 58, 57, 56};
            List<Integer> list = Arrays.asList(notValid);
            int x = i;
            if (!list.contains(i)) {
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank && !list.contains(x) &&
                        (int) candy.get(x + noOfBlocks).getTag() == chosedCandy && !list.contains(x + noOfBlocks) &&
                        (int) candy.get(x + 2 * noOfBlocks).getTag() == chosedCandy && !list.contains(x + 2 * noOfBlocks) &&
                        (int) candy.get(x + 3 * noOfBlocks).getTag() == chosedCandy && !list.contains(x + 3 * noOfBlocks)) {
                    if ((int) candy.get(x).getTag() == R.drawable.purplecandy){
                        wood_score = wood_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.redcandy){
                        fire_score = fire_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.graycandy){
                        stone_score = stone_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.yellowcandy){
                        electro_score = electro_score + 4;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.bluecandy){
                        ice_score = ice_score + 4;
                    }
                    score = score + 4;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                }
            }
        }
        movedDownCandyes();
    }

    /* Five */
    private  void  checkRowForFive()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x++).getTag() == chosedCandy&&
                        (int) candy.get(x).getTag() == chosedCandy)
                {
                    if ((int) candy.get(x).getTag() == R.drawable.purplecandy){
                        wood_score = wood_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.redcandy){
                        fire_score = fire_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.graycandy){
                        stone_score = stone_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.yellowcandy){
                        electro_score = electro_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.bluecandy){
                        ice_score = ice_score + 5;
                    }
                    score = score+5;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
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
        movedDownCandyes();
    }

    private  void  checkColumnForFive()
    {
        for (int i = 0; i < 48; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48};
            List<Integer> list = Arrays.asList(notValid);
            int x = i;
            if (!list.contains(i)) {
                if ((int) candy.get(x).getTag() == chosedCandy && !isBlank && !list.contains(x) &&
                        (int) candy.get(x + noOfBlocks).getTag() == chosedCandy && !list.contains(x + noOfBlocks) &&
                        (int) candy.get(x + 2 * noOfBlocks).getTag() == chosedCandy && !list.contains(x + 2 * noOfBlocks) &&
                        (int) candy.get(x + 3 * noOfBlocks).getTag() == chosedCandy && !list.contains(x + 3 * noOfBlocks) &&
                        (int) candy.get(x + 4 * noOfBlocks).getTag() == chosedCandy && !list.contains(x + 4 * noOfBlocks))
                {
                    if ((int) candy.get(x).getTag() == R.drawable.purplecandy){
                        wood_score = wood_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.redcandy){
                        fire_score = fire_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.graycandy){
                        stone_score = stone_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.yellowcandy){
                        electro_score = electro_score + 5;
                    }
                    if ((int) candy.get(x).getTag() == R.drawable.bluecandy){
                        ice_score = ice_score + 5;
                    }
                    score = score + 5;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x = x + noOfBlocks;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                }
            }
        }
        movedDownCandyes();
    }

    /* Combinetions */
    private  void  checkForThreeXThree()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() == chosedCandy)
                {
                    checkColumnForThree();
                    checkColumnForFore();
                    checkColumnForFive();
                    score = score+2;
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
        movedDownCandyes();
    }

    private  void  checkForForeXThree()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() == chosedCandy)
                {

                    checkColumnForThree();
                    score = score+3;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
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
        movedDownCandyes();
    }

    private  void  checkForThreeXFore()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() == chosedCandy)
                {
                    checkColumnForFore();
                    score = score+2;
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
        movedDownCandyes();
    }

    private  void  checkForThreeXFive()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x).getTag() == chosedCandy)
                {
                    checkColumnForFive();
                    score = score+2;
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
        movedDownCandyes();
    }

    private  void  checkForFiveXThree()
    {
        for (int i = 0; i < 62; i++)
        {
            int chosedCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6,7,14,15,22,23,30,31,38,39,46,47,54,55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i))
            {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosedCandy && !isBlank &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x++).getTag() == chosedCandy &&
                        (int) candy.get(x++).getTag() == chosedCandy&&
                        (int) candy.get(x).getTag() == chosedCandy)
                {

                    checkColumnForThree();
                    score = score+4;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
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
        movedDownCandyes();
    }

    private void movedDownCandyes()
        {
            Integer[] firstRow = {0, 1, 2, 3, 4, 5, 6, 7};
            List<Integer> list = Arrays.asList(firstRow);
            for (int i =55; i>= 0;i--)
            {
                if ((int) candy.get(i + noOfBlocks).getTag() == notCandy)
                {
                    candy.get(i+noOfBlocks).setImageResource((int) candy.get(i).getTag());
                    candy.get(i+noOfBlocks).setTag(candy.get(i).getTag());
                    candy.get(i).setImageResource(notCandy);
                    candy.get(i).setTag(notCandy);
                    if (list.contains(i) && (int) candy.get(i).getTag() == notCandy)
                    {
                        int randomColor = (int) Math.floor(Math.random() * elements.length);
                        candy.get(i).setImageResource(elements[randomColor]);
                        candy.get(i).setTag(elements[randomColor]);
                    }
                }
            }
            for (int i = 0; i< 8;i++)
            {
                if ((int) candy.get(i).getTag() == notCandy)
                {
                    int randomColor = (int) Math.floor(Math.random() * elements.length);
                    candy.get(i).setImageResource(elements[randomColor]);
                    candy.get(i).setTag(elements[randomColor]);
                }
            }
        }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                /*checkForThreeXThree();*/
                /*checkForForeXThree();*/
                /*checkForFiveXThree();*/
                /*checkForThreeXFive();*/
                checkRowForFive();
                checkColumnForFive();
                checkRowForFore();
                checkColumnForFore();
                /*checkForThreeXFore();*/
                checkRowForThree();
                checkColumnForThree();
                movedDownCandyes();
                Hit();
            }
            finally {
                mHandker.postDelayed(repeatChecker, interval);
            }
        }
    };
    void startRepeat()
    {
        repeatChecker.run();
    }
    private  void candyInterchanged()
    {
        int background = (int) candy.get(candyToBeReplaced).getTag();
        int background1 = (int) candy.get(candyToBeDragget).getTag();
        candy.get(candyToBeDragget).setImageResource(background);
        candy.get(candyToBeReplaced).setImageResource(background1);
        candy.get(candyToBeDragget).setTag(background);
        candy.get(candyToBeReplaced).setTag(background1);
    }
    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.board);
        gridLayout.setRowCount(noOfBlocks);
        gridLayout.setColumnCount(noOfBlocks);
        gridLayout.getLayoutParams().width = widthOfScreen;
        gridLayout.getLayoutParams().height = widthOfScreen;
        for (int i = 0; i< noOfBlocks * noOfBlocks ; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new
                    android.view.ViewGroup.LayoutParams(widthOfBlock, widthOfBlock));
            imageView.setMaxHeight(widthOfBlock);
            imageView.setMaxWidth(widthOfBlock);
            int randomCandy = (int) Math.floor(Math.random() * elements.length);
            imageView.setImageResource(elements[randomCandy]);
            imageView.setTag(elements[randomCandy]);
            candy.add(imageView);
            gridLayout.addView(imageView);
        }

    }
    private void createCats(){
        randomCat = (int) Math.floor(Math.random() * cats.length);
        Cats.setImageResource(cats[randomCat]);
    }


    private void Hit(){
        if (health > 0){
            if (randomCat == 0){
                health = health - wood_score;
                health = health - fire_score;
                health = health - stone_score;
                health = health - electro_score;
                health = health - ice_score;
            }
            else if (randomCat == 1){
                health = health + wood_score;
                health = health + 2*fire_score;
                health = health - stone_score;
                health = health - electro_score;
                health = health - 2*ice_score;
            }
            else if (randomCat == 2){
                health = health - wood_score;
                health = health - 2*fire_score;
                health = health - stone_score;
                health = health - electro_score;
                health = health + 2*ice_score;
            }
            else if (randomCat == 3){
                health = health - wood_score;
                health = health - fire_score;
                health = health + 2*stone_score;
                health = health - electro_score;
                health = health - ice_score;
            }
            else if (randomCat == 4){
                health = health - wood_score;
                health = health - fire_score;
                health = health - stone_score;
                health = health + 2*electro_score;
                health = health + ice_score;
            }
            Health.setText(String.valueOf(health));
            wood_score = 0;
            fire_score = 0;
            ice_score = 0;
            stone_score = 0;
            electro_score = 0;

        }
        else {
            level ++;
            if (level < 4){
                randomCat = 0;
                Cats.setImageResource(cats[randomCat]);
            }
            else {
                Log.d("Hi","");
                createCats();
            }
            health = 10 * level;
            Health.setText(String.valueOf(health));
            LevelCount.setText(String.valueOf(level));
        }
    }
}