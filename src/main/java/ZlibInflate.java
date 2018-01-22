import java.io.*;
import java.util.zip.InflaterOutputStream;

public class ZlibInflate {

    private final InputStream in;
    private final OutputStream out;

    ZlibInflate(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    void inflate() throws IOException {
        try (InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(out)) {
            byte[] bytes = new byte[4096];
            int length;
            while ((length = in.read(bytes)) != -1) {
                inflaterOutputStream.write(bytes, 0, length);
                inflaterOutputStream.flush();
            }
        }
    }
}
