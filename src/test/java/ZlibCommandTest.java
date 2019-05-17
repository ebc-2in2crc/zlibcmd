import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ZlibCommandTest {

    private static final String LF = System.getProperty("line.separator");

    public static class CompressOption {

        private ByteArrayOutputStream baos;

        @Before
        public void setUp() throws Exception {
            InputStream in = getClass().getResourceAsStream("decompressed.dat");
            System.setIn(in);

            baos = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(baos);
            System.setOut(out);
        }

        @Test
        public void testShortCompressionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"-c"});

            sut.exec();

            assertThat(baos.toByteArray(), is(compressed()));
        }

        private byte[] compressed() throws IOException, URISyntaxException {
            URI uri = getClass().getResource("compressed.dat").toURI();
            Path path = Paths.get(uri);
            return Files.readAllBytes(path);
        }

        @Test
        public void testLongCompressionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"--compress"});

            sut.exec();

            assertThat(baos.toByteArray(), is(compressed()));
        }
    }

    public static class DecompressOption {

        private ByteArrayOutputStream baos;

        @Before
        public void setUp() {
            InputStream in = getClass().getResourceAsStream("compressed.dat");
            System.setIn(in);
            baos = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(baos);
            System.setOut(out);
        }

        @Test
        public void testShortDecompressionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"-d"});

            sut.exec();

            assertThat(baos.toByteArray(), is(decompressed()));
        }

        private byte[] decompressed() throws IOException, URISyntaxException {
            URI uri = getClass().getResource("decompressed.dat").toURI();
            Path path = Paths.get(uri);
            return Files.readAllBytes(path);
        }

        @Test
        public void testLongDecompressionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"--decompress"});

            sut.exec();

            assertThat(baos.toByteArray(), is(decompressed()));
        }
    }

    public static class OtherOption {

        private ByteArrayOutputStream baos;

        @Before
        public void setUp() {
            baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos);
            System.setErr(printStream);
        }

        @Test
        public void testShortVersionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"-v"});

            sut.exec();

            assertThat(baos.toString(), is("zlib version 1.1" + LF));
        }

        @Test
        public void testLongVersionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"--version"});

            sut.exec();

            assertThat(baos.toString(), is("zlib version 1.1" + LF));
        }

        @Test
        public void testShortHelpOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"-h"});

            sut.exec();

            assertThat(baos.toString(), is(getUsage()));
        }

        @Test
        public void testLongHelpOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"--help"});

            sut.exec();

            assertThat(baos.toString(), is(getUsage()));
        }
    }

    private static String getUsage() {
        String usage = "zlib is row zlib compression commandline program." + LF +
                "" + LF +
                "usage: zlib [options]" + LF +
                "" + LF +
                "  -b, --buffer-size specify buffer size in bytes." + LF +
                "                    (default: 8192)" + LF +
                "  -c, --compress    compresses the input" + LF +
                "  -d, --decompress  decompresses the input" + LF +
                "  -h, --help        display this help" + LF +
                "  -v, --version     display program version" + LF;

        return usage;
    }

    public static class InvalidOption {

        private ByteArrayOutputStream baos;

        @Before
        public void setUp() {
            baos = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(baos);
            System.setErr(printStream);
        }

        @Test
        public void testInvalidOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"-D"});

            sut.exec();

            assertThat(baos.toString(), is(getUsage()));
        }

        @Test
        public void testBothCompressionAndDecompressionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{"-c", "-d"});

            sut.exec();

            assertThat(baos.toString(), is(getUsage()));
        }

        @Test
        public void testMissingBothCompressionAndDecompressionOption() throws Exception {
            ZlibCommand sut = new ZlibCommand(new String[]{});

            sut.exec();

            assertThat(baos.toString(), is(getUsage()));
        }
    }
}