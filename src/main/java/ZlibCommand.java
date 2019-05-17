import java.io.IOException;

public class ZlibCommand {
    private static int DEFAULT_BUFFER_SIZE = 8192;

    private final String[] args;
    private boolean compression = false;
    private boolean decompression = false;
    private int bufferSize = DEFAULT_BUFFER_SIZE;

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
            new ZlibDeflate(System.in, System.out, bufferSize).deflate();
        } else {
            new ZlibInflate(System.in, System.out, bufferSize).inflate();
        }
    }

    private boolean checkArguments() {
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            switch (s) {
                case "-b":
                case "--buffer-size":
                    i++;
                    try {
                        bufferSize = Integer.parseInt(args[i]);
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
                        usage();
                        return true;
                    }
                    break;
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
                "  -b, --buffer-size specify buffer size in bytes." + LF +
                "                    (default: 8192)" + LF +
                "  -c, --compress    compresses the input" + LF +
                "  -d, --decompress  decompresses the input" + LF +
                "  -h, --help        display this help" + LF +
                "  -v, --version     display program version";
        System.err.println(usage);
    }
}
