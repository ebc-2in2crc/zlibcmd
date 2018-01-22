import java.io.IOException;

public class ZlibCommand {
    private final String[] args;
    private boolean compression = false;
    private boolean decompression = false;

    public static void main(String[] args) throws IOException {
        new ZlibCommand(args).exec();
    }

    public ZlibCommand(String[] args) {
        this.args = args;
    }

    void exec() throws IOException {
        boolean err = checkArguments();
        if (err) {
            return;
        }

        if (compression) {
            new ZlibDeflate(System.in, System.out).deflate();
        } else {
            new ZlibInflate(System.in, System.out).inflate();
        }
    }

    private boolean checkArguments() {
        for (String s : args) {
            switch (s) {
                case "-c":
                case "--compress":
                    compression = true;
                    break;
                case "-v":
                case "--version":
                    System.err.println("zlib version 1.0");
                    return true;
                case "-d":
                case "--decompress":
                    decompression = true;
                    break;
                default:
                    usage();
                    return true;
            }
        }

        if (compression == decompression) {
            usage();
            return true;
        }

        return false;
    }

    private void usage() {
        final String LF = System.getProperty("line.separator");
        String usage = "zlib is row zlib compression commandline program." + LF +
                "" + LF +
                "usage: zlib [options]" + LF +
                "" + LF +
                "  -c, --compress    compresses the input" + LF +
                "  -d, --decompress  decompresses the input" + LF +
                "  -h, --help        display this help" + LF +
                "  -v, --version     display program version";
        System.err.println(usage);
    }
}
