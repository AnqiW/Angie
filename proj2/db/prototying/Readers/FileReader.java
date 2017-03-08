package db.prototying.Readers;
import db.prototying.LinkedListDeque;
import db.prototying.List;
import edu.princeton.cs.algs4.In;


/**
 * Created by angie on 2017/2/28.
 */
public class FileReader {
    private In in;
    public FileReader(String fileName){
        in = new In(fileName + ".tbl");
    }

    public int readInt(){
        return in.readInt();
    }

    public double readDouble(){
        return in.readDouble();
    }

    public String readString(){
        return in.readString();
    }

    public boolean exsit(){
        return in.exists();
    }

    public boolean isEmpty(){
        return in.isEmpty();
    }

    public String[] readColumnNames() {
        String firstLine = in.readLine();
        ReaderClass rd = new ReaderClass();
        if (!rd.malformedFlah(firstLine)) {
            String[] a = new String[1];
            int b = 1/0;
            a[0]= " ";
            return a;
        } else {
            List<String> columnNames = new LinkedListDeque<>();
            int current = 0;
            int prev = 0;
            for (int i = 0; i < firstLine.length(); i++) {
                if (firstLine.charAt(i) == ' ' || firstLine.charAt(i) == ',') {
                    if (firstLine.charAt(i - 1) == ',' || firstLine.charAt(i - 1) == ' ') {
                        prev = i + 1;
                    } else {
                        current = i;
                        columnNames.addLast(firstLine.substring(prev, current));
                        prev = i + 1;
                    }
                } else if (i == firstLine.length() - 1) {
                    columnNames.addLast(firstLine.substring(prev));
                }
            }
            String[] result = new String[columnNames.length() / 2];
            int k = 0;
            while (!columnNames.isEmpty()) {
                String a = columnNames.removeFirst();
                String b = columnNames.removeFirst();
                result[k] = a + " " + b;
                k++;
            }
            return result;
        }
    }



    public List readElements(){
        List a = new LinkedListDeque();
        while(in.hasNextLine()){
            a.addLast(in.readLine());
        }
        return a;
    }

    public String readTableLine(){
        return in.readLine();

    }

    public boolean hasNextLine(){
        return in.hasNextLine();
    }

}
