package com.cpluscplus.pawan.sudokusolver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//adding a temporary comment
public class MainActivity extends AppCompatActivity {
    public Button solve, reset;
    private int[][] grid = new int[9][9];
    private EditText[][] edit = new EditText[9][9];
    private TextView m;
    public MainActivity MainActivity;
    public MainActivity() {}
    public static boolean isSafe(int[][] grid,int u,int v,int n)
    {
        for(int i=0;i<9;i++){
            if(grid[i][v]==n||grid[u][i]==n){
                return false;
            }
        }
        int uf = u/3,vf = v/3;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(grid[i+uf*3][j+vf*3]==n){
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] findUnassigned(int[][] grid)
    {
        int[] res = new int[2];
        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
        }
        return res;
    }
    public static boolean sudoku(int[][] grid){
        int flag=0;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(grid[i][j]==0){
                    flag=1;
                    break;
                }
            }
        }
        if(flag==0){
            return true;
        }
        else{
            int u,v;
            int[] unassigned = findUnassigned(grid);
            u = unassigned[0];
            v = unassigned[1];
            for(int i=1;i<=9;i++){
                if(isSafe(grid,u,v,i)){
                    grid[u][v]=i;
                    if(sudoku(grid)==true){
                        return true;
                    }
                    grid[u][v]=0;
                }
            }
        }
        return false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m = (TextView)findViewById(R.id.message);
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                String buttonID = "editText" + Integer.toString(i) + Integer.toString(j);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                edit[i][j] = ((EditText) findViewById(resID));
            }
        }
        solve = (Button)findViewById(R.id.button);
        reset = (Button)findViewById(R.id.reset);
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<9; i++) {
                    for(int j=0; j<9; j++) {
                       // String buttonID = "editText" + i + "-" + j;
                        //int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                        String temp = (edit[i][j].getText()).toString();
                        if(temp.isEmpty())
                            grid[i][j] = 0;
                        else
                            grid[i][j] = Integer.parseInt(temp);
                    }
                }
                if(sudoku(grid)==true){
                    for (int i=0;i<9;i++){
                        for (int j=0;j<9;j++){
                            edit[i][j].setText(grid[i][j]+"");
                        }
                    }
                    m.setText("Correct");
                }
                else{
                    m.setText("Wrong Input");
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<9;i++){
                    for (int j=0;j<9;j++){
                        edit[i][j].setText(""+"");
                    }
                }
            }
        });
    }
}

