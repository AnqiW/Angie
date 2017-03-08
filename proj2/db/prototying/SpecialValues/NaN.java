package db.prototying.SpecialValues;
import db.prototying.ClassChecker.*;

/**
 * Created by angie on 2017/3/7.
 */
public class NaN {

    private String value;
    private String type;

    public NaN(String type){
        this.type = type;
        if(type.equals("int") || type.equals("Integer")){
            value = "infinity";
        } else if(type.equals("string") || type.equals("String")){
            value = "NaN";
        } else if(type.equals("Float")||type.equals("float")){
            value = "infinity";
        }
    }

    public String toString(){
      return "NaN";
    }

    public int compareTo(){
        return 1;
    }

    public String getType(){
        return type;
    }

    public String getValue(){
        return value;
    }


}
