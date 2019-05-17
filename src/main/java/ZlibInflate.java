import java.io.*;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class ZlibInflate {

    private final InputStream in;
    private final OutputStream out;
    private final int bufLen;

    ZlibInflate(InputStream in, OutputStream out, int bufLen) {
        this.in = in;
        this.out = out;
        this.bufLen = bufLen;
    }

    void inflate() throws IOException {
        final Inflater inflater = new Inflater();
        try (InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(out, inflater, bufLen)) {
            byte[] bytes = new byte[4096];
            int length;
            while ((length = in.read(bytes)) != -1) {
                inflaterOutputStream.write(bytes, 0, length);
            }
            inflaterOutputStream.flush();
        }
    }
}
