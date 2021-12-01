package test;

import com.company.Eiland;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMain {
    public static int nb_example=1;
    @Test
    public void test(){
        try {
            File input = new File("voorbeeld_invoer.txt");
            Scanner readerInput = new Scanner(input);
            File output = new File("voorbeeld_uitvoer.txt");
            Scanner readerOutput = new Scanner(output);
            int nbExamples=Integer.parseInt(readerInput.nextLine());
            for(int n=0;n<nbExamples;n++){
                int nb_rows=Integer.parseInt(readerInput.nextLine());
                int nb_cols=Integer.parseInt(readerInput.nextLine());
                char[][] characters = new char[nb_rows][nb_cols];
                for(int r=0;r<nb_rows;r++){
                    String line=readerInput.nextLine();
                    for(int c=0;c<nb_cols;c++){
                        characters[r][c]=line.charAt(c);
                    }

                }

                String expected=solve(nb_rows,nb_cols,characters);
                assertEquals(expected, readerOutput.nextLine());

            }


            readerInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String solve(int nb_rows, int nb_cols, char[][] characters) {
        boolean[][]visited=new boolean[nb_rows][nb_cols];
        ArrayList<Eiland> eilanden=new ArrayList<>();
        for(int r=0;r<nb_rows;r++) {
            for (int c = 0; c < nb_cols; c++) {
                if(!visited[r][c]&&characters[r][c]=='+'){
                    Eiland eiland=new Eiland();
                    recursion(eiland,r,c,nb_rows,nb_cols,visited,characters);
                    eilanden.add(eiland);
                }


            }
        }

        Map<Integer, List<Eiland>> map = eilanden.stream()
                .collect(Collectors.groupingBy(o -> o.grootte));
        ArrayList<Integer> sortedKeys=new ArrayList(map.keySet());
        Collections.sort(sortedKeys);
        String output=nb_example+" ";
        StringBuffer sb=new StringBuffer();
        for(Integer key:sortedKeys){
            sb.append(key + " " + map.get(key).size() + " ");
        }
        output=output+sb.toString();

        nb_example++;
       return output.trim();



    }

    private static void recursion(Eiland eiland, int r, int c, int nb_rows, int nb_cols, boolean[][] visited, char[][] characters) {
        if(r<0||c<0||r>=nb_rows||c>=nb_cols||characters[r][c]=='.'||visited[r][c]){
            return;
        }
        visited[r][c]=true;
        eiland.grootte++;
        recursion(eiland,r-1,c,nb_rows,nb_cols,visited, characters);//upwards
        recursion(eiland,r+1,c,nb_rows,nb_cols,visited, characters);//downwards
        recursion(eiland,r,c-1,nb_rows,nb_cols,visited, characters);//left
        recursion(eiland,r,c+1,nb_rows,nb_cols,visited, characters);//right



    }
}
