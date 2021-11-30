package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static int nb_example=1;

    public static void main(String[] args) {
        try {
            File myObj = new File("wedstrijd_invoer.txt");
            Scanner myReader = new Scanner(myObj);
            int nbExamples=Integer.parseInt(myReader.nextLine());
            for(int n=0;n<nbExamples;n++){
                int nb_rows=Integer.parseInt(myReader.nextLine());
                int nb_cols=Integer.parseInt(myReader.nextLine());
                char[][] characters = new char[nb_rows][nb_cols];
                for(int r=0;r<nb_rows;r++){
                    String line=myReader.nextLine();
                    for(int c=0;c<nb_cols;c++){
                        characters[r][c]=line.charAt(c);
                    }

                }

                solve(nb_rows,nb_cols,characters);
            }


            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void solve(int nb_rows, int nb_cols, char[][] characters) {
        boolean[][]visited=new boolean[nb_rows][nb_cols];
        ArrayList<Eiland>eilanden=new ArrayList<>();
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
        System.out.print(nb_example+" ");
        for(Integer key:sortedKeys){
            System.out.print(key+" "+map.get(key).size()+" ");
        }
        nb_example++;
        System.out.println();



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