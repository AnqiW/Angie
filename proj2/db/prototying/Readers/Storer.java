package db.prototying.Readers;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by angie on 2017/3/3.
 */
public class Storer {

    private FileWriter w;

    public Storer(String fileName) {
        try {
           w = new FileWriter(fileName + ".tbl");
        } catch (Exception e) {
            System.out.println("ERROR!: named file exists but is a directory rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason");
        }
    }


    public void write(String str){
        try{
            w.write(str);
        } catch(Exception e){
            System.out.println("The file has been closed");
        }

    }

    public void flush(){
        try {
            w.flush();
        }catch(Exception e){
            System.out.println("Error! in flush");
        }
    }

    public void close(){
        try{
            w.close();
        }catch(Exception e){
            System.out.println("ERROR! in close");
        }
    }





}
