import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class ZlibDeflate {

    private final InputStream in;
    private final OutputStream out;
    private final int bufLen;

    ZlibDeflate(InputStream in, OutputStream out, int bufLen) {
        this.in = in;
        this.out = out;
        this.bufLen = bufLen;
    }

    void deflate() throws IOException {
        final Deflater deflater = new Deflater();
        try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out, deflater, bufLen)) {
            byte[] bytes = new byte[4096];
            int length;
            while ((length = in.read(bytes)) != -1) {
                deflaterOutputStream.write(bytes, 0, length);
            }
            deflaterOutputStream.flush();
        }
    }
}
