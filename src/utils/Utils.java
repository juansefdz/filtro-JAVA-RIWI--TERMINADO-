package utils;

import java.util.List;

public class Utils {
    public static <genericList>genericList[] listToArray (List<genericList>list){
        genericList[] array = (genericList[]) new Object[list.size()];
        int i=0;
        for(genericList temp:list){
            array[i++]=temp;
        }
        return array;

    }


}