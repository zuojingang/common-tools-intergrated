package pers.zuo.entity.pojo;

import java.io.*;

/**
 * @author zuojingang
 * @Title: BasicPOJO
 * @Description: basic POJO
 * @date 2020/6/3 17:21
 */
public class BasicPOJO<T> implements Serializable, Cloneable {

    private static final long serialVersionUID = -638482828402957716L;

    @Override
    public T clone() {
        T clone = null;
        try {
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objectOS = new ObjectOutputStream(byteOS);
            objectOS.writeObject(this);
            ByteArrayInputStream byteIS = new ByteArrayInputStream(byteOS.toByteArray());
            ObjectInputStream objectIS = new ObjectInputStream(byteIS);
            clone = (T) objectIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
