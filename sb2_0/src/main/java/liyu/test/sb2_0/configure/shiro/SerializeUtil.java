package liyu.test.sb2_0.configure.shiro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
 
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }
 
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] in, Class<T> requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }
    
    public static ObjectInputStream deserialize(byte[] in) {
    	ByteArrayInputStream bis = null;
    	ObjectInputStream is = null;
    	try {
    		if (in != null) {
    			bis = new ByteArrayInputStream(in);
    			return new ObjectInputStream(bis);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		close(is);
    		close(bis);
    	}
		return is;
    }
 
    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
}