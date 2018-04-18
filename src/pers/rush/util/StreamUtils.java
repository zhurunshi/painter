package pers.rush.util;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZhuRunShi on 2017/5/12.
 */
public class StreamUtils {

    /**
     * 序列化,List
     */
    public static <T> boolean writeObject(List<T> list, File file)
    {
        T[] array = (T[]) list.toArray();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(array);
            out.flush();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 反序列化,List
     */
    public static <E> List<E> readObjectForList(File file)
    {
        E[] object;
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file)))
        {
            object = (E[]) out.readObject();
            return Arrays.asList(object);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
