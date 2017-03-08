package db.prototying.SpecialValues;

/**
 * Created by angie on 2017/3/7.
 */
public class NOVALUE {

    private String value;
    private String type;

    public NOVALUE(String type){
        this.type = type;
        if(type.equals("int")){
            value = "0";
        } else if(type.equals("float")) {
            value = "0.0";
        } else if(type.equals("string")){
            value = "";
        }

    }

    public int compareTo(){
        return -1;
    }


}
