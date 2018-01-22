import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

public class ZlibDeflate {

    private final InputStream in;
    private final OutputStream out;

    ZlibDeflate(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    void deflate() throws IOException {
        try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out)) {
            byte[] bytes = new byte[4096];
            int length;
            while ((length = in.read(bytes)) != -1) {
                deflaterOutputStream.write(bytes, 0, length);
            }
        }
    }
}
