package com.jude.mazeyo;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jude.mazeyo.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Stack;

public class GameView extends View {

    private FireBaseServices fbs;
    Context contextView;
    Dialog dialog;
    private enum Direction{
        UP, DOWN, LEFT, RIGHT
    }
    private Cell[] [] cells;
    private Cell player, exit;
    private static int Cols , Rows ;
    private static final float WALL_THICKNESS = 4;
    private float cellsSize, hMargin, vMargin;
    private final Paint wallPaint;
    private final Paint playerPaint;
    private final Paint exitPaint;
    private final Random random;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        fbs = FireBaseServices.getInstance();

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.RED);

        exitPaint = new Paint();
        exitPaint.setColor(getResources().getColor(R.color.Tangerine)); // set Color by Hex (int) without the # Symbol.

        random = new Random();

        // this if is to get the right difficulty...
        if (fbs.getDifficulty().equals("Easy")){
            Cols = 10; Rows = 10;
        } else {
            if (fbs.getDifficulty().equals("Medium")) {
                Cols = 20;
                Rows = 20;
            } else {
                if (fbs.getDifficulty().equals("Hard")) {
                    Cols = 30;
                    Rows = 30;
                } else {
                    if (fbs.getDifficulty().equals("DailyPlay")) {
                        int r = random.nextInt(20) + 20;
                        Cols = r;
                        Rows = r;
                    } else {Cols = 2; Rows = 2;}
                }
            }
        }

        contextView = context;

        dialog = new Dialog(contextView);
        dialog.setContentView(R.layout.winner_dialog_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        dialog.setCancelable(false);

         createMaze();
    }

    private Cell getNeighbour(Cell cell){
        ArrayList<Cell> neighbours = new ArrayList<>();

        // left neighbour
        if (cell.col > 0)
            if (!cells[cell.col-1][cell.row].visited)
                neighbours.add(cells[cell.col-1][cell.row]);

        // right neighbour
        if (cell.col < Cols-1)
            if (!cells[cell.col+1][cell.row].visited)
                neighbours.add(cells[cell.col+1][cell.row]);

        // top neighbour
        if (cell.row > 0)
            if (!cells[cell.col][cell.row-1].visited)
                neighbours.add(cells[cell.col][cell.row-1]);

        // bottom neighbour
        if (cell.row < Rows-1)
            if (!cells[cell.col][cell.row+1].visited)
                neighbours.add(cells[cell.col][cell.row+1]);

        if (neighbours.size() > 0){
            int index = random.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        return null;
    }

    private void removeWall(Cell current, Cell next){
        if (current.col == next.col && current.row == next.row+1){
            current.topwall = false;
            next.bottomwall = false;
        }

        if (current.col == next.col && current.row == next.row-1){
            current.bottomwall = false;
            next.topwall = false;
        }

        if (current.col == next.col+1 && current.row == next.row){
            current.leftwall = false;
            next.rightwall = false;
        }

        if (current.col == next.col-1 && current.row == next.row){
            current.rightwall = false;
            next.leftwall = false;
        }

    }

    private void createMaze(){

        Stack<Cell> stack = new Stack<>();
        Cell current, next;

        cells = new Cell[Cols] [Rows];

        for(int i=0; i<Cols ; i++){
            for(int j=0; j<Rows ; j++){
                cells [i] [j] = new Cell(i, j);
            }
        }

        player = cells[0][0];
        exit = cells[Cols-1][Rows-1];

        current = cells[0][0];
        current.visited = true;
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else
                current = stack.pop();
        }while (!stack.empty());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        int width = getWidth();
        int height = getHeight();

        if(width/height < Cols/Rows) {
            cellsSize = width / (Cols + 1);
        } else
            cellsSize = height/(Rows+1);

        hMargin = (width-Cols*cellsSize)/2;
        vMargin = (height-Rows*cellsSize)/2;

        canvas.translate(hMargin, vMargin);

        for(int i=0; i<Cols ; i++){
            for(int j=0; j<Rows ; j++){
                if(cells[i][j].topwall)
                    canvas.drawLine(
                            i*cellsSize,
                            j*cellsSize,
                            (i+1)*cellsSize,
                            j*cellsSize,
                            wallPaint);

                if(cells[i][j].leftwall)
                    canvas.drawLine(
                            i*cellsSize,
                            j*cellsSize,
                            i*cellsSize,
                            (j+1)*cellsSize,
                            wallPaint);

                if(cells[i][j].bottomwall)
                    canvas.drawLine(
                            i*cellsSize,
                            (j+1)*cellsSize,
                            (i+1)*cellsSize,
                            (j+1)*cellsSize,
                            wallPaint);

                if(cells[i][j].rightwall)
                    canvas.drawLine(
                            (i+1)*cellsSize,
                            j*cellsSize,
                            (i+1)*cellsSize,
                            (j+1)*cellsSize,
                            wallPaint);
            }
        }

        float margin = cellsSize/10;

        canvas.drawRect(
                player.col*cellsSize+margin,
                player.row*cellsSize+margin,
                (player.col+1)*cellsSize-margin,
                (player.row+1)*cellsSize-margin,
                playerPaint);

        canvas.drawRect(
                exit.col*cellsSize+margin,
                exit.row*cellsSize+margin,
                (exit.col+1)*cellsSize-margin,
                (exit.row+1)*cellsSize-margin,
                exitPaint);
    }

    private void movePlayer(Direction direction){
        switch (direction){
            case UP:
                if (!player.topwall)
                    player = cells[player.col][player.row-1];
                break;
            case DOWN:
                if (!player.bottomwall)
                    player = cells[player.col][player.row+1];
                break;
            case LEFT:
                if (!player.leftwall)
                    player = cells[player.col-1][player.row];
                break;
            case RIGHT:
                if (!player.rightwall)
                    player = cells[player.col+1][player.row];

        }

        checkExit();
        invalidate();
    }

    private void checkExit(){

        if (player == exit)
        {
            // add one Game Played to the current Difficulty! and Increase Coins Count.

            if(!dialog.isShowing()) dialog.show();

            Button cont = dialog.findViewById(R.id.btnContinueWinner);
            Button exit = dialog.findViewById(R.id.btnExitWinner);
            final boolean[] bool = {false};

            User user = fbs.getUser();

            if (fbs.getDifficulty().equals("DailyPlay")){
                user.setCoin(user.getCoin() + 20 + user.getDailyCount() * 5);
                user.setDailyCount(user.getDailyCount() + 1);
                user.setDidDaily(true);
                user.setDatePlay(Calendar.getInstance().getTime());
                Toast.makeText(contextView, "You Got 20 Mazeyo Coins!", Toast.LENGTH_SHORT).show();

                // go to the home
                fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        fbs.setUser(user);

                        fbs.setDifficulty("NoGame");

                        FragmentManager fm = ((MainActivity) contextView).getSupportFragmentManager();

                        BottomNavigationView bnv = ((MainActivity) contextView).getBottomNavigationView();
                        bnv.setVisibility(View.VISIBLE);

                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
                        ft.commit();

                        dialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(contextView, "Couldn't Update Stats, Try Again Later", Toast.LENGTH_LONG).show();
                    }
                });

            }
            else {

                createMaze();

                cont.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!bool[0]) {
                            if (user != null) {

                                // this if to set the right amount of MCoins and difficulty to the user
                                if (fbs.getDifficulty().equals("Easy")) {
                                    user.setCoin(user.getCoin() + 1);
                                    user.setEasy(user.getEasy() + 1);
                                    Toast.makeText(contextView, "You Got 1 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (fbs.getDifficulty().equals("Medium")) {
                                        user.setCoin(user.getCoin() + 5);
                                        user.setMedium(user.getMedium() + 1);
                                        Toast.makeText(contextView, "You Got 5 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (fbs.getDifficulty().equals("Hard")) {
                                            user.setCoin(user.getCoin() + 10);
                                            user.setHard(user.getHard() + 1);
                                            Toast.makeText(contextView, "You Got 10 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            user.setCoin(user.getCoin() + 20 + user.getDailyCount() * 5);
                                            user.setDailyCount(user.getDailyCount() + 1);
                                            user.setDidDaily(true);
                                            user.setDatePlay(Calendar.getInstance().getTime());
                                            Toast.makeText(contextView, "You Got 20 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        fbs.setUser(user);

                                        dialog.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(contextView, "Couldn't Update Stats, Try Again Later", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }

                            bool[0] = true;

                        }
                    }
                });

                exit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!bool[0]) {
                            if (user != null) {

                                // this if to set the right amount of MCoins and difficulty to the user
                                if (fbs.getDifficulty().equals("Easy")) {
                                    user.setCoin(user.getCoin() + 1);
                                    user.setEasy(user.getEasy() + 1);
                                    Toast.makeText(contextView, "You Got 1 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (fbs.getDifficulty().equals("Medium")) {
                                        user.setCoin(user.getCoin() + 5);
                                        user.setMedium(user.getMedium() + 1);
                                        Toast.makeText(contextView, "You Got 5 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (fbs.getDifficulty().equals("Hard")) {
                                            user.setCoin(user.getCoin() + 10);
                                            user.setHard(user.getHard() + 1);
                                            Toast.makeText(contextView, "You Got 10 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            user.setCoin(user.getCoin() + 20 + user.getDailyCount() * 5);
                                            user.setDailyCount(user.getDailyCount() + 1);
                                            user.setDidDaily(true);
                                            user.setDatePlay(Calendar.getInstance().getTime());
                                            Toast.makeText(contextView, "You Got 20 Mazeyo Coins!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                fbs.getFirestore().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        fbs.setUser(user);

                                        fbs.setDifficulty("NoGame");

                                        FragmentManager fm = ((MainActivity) contextView).getSupportFragmentManager();

                                        BottomNavigationView bnv = ((MainActivity) contextView).getBottomNavigationView();
                                        bnv.setVisibility(View.VISIBLE);

                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.FrameLayoutMain, new HomeFragment());
                                        ft.commit();

                                        dialog.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(contextView, "Couldn't Update Stats, Try Again Later", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }

                            bool[0] = true;

                        }
                    }
                });
            }

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            return true;

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();

            float playerCenterX = hMargin + (player.col+0.5f)*cellsSize;
            float playerCenterY = vMargin + (player.row+0.5f)*cellsSize;

            float dx = x - playerCenterX;
            float dy = y - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > cellsSize || absDy > cellsSize){

                if (absDx > absDy){
                    //move in x-direction
                    if (dx > 0)
                        movePlayer(Direction.RIGHT);
                    else
                        movePlayer(Direction.LEFT);
                }
                else {
                    //move in y-direction
                    if (dy > 0)
                        movePlayer(Direction.DOWN);
                    else
                        movePlayer(Direction.UP);
                }
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    private static class Cell{
        boolean topwall = true;
        boolean leftwall = true;
        boolean bottomwall = true;
        boolean rightwall = true;
        boolean visited = false;
        int col, row;
        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

}
