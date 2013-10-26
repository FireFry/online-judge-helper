import java.io.*;

public class ProblemTemplate {

    private final class Solution {

        public void solve() throws Exception {

        }

    }

    //Here goes some helping code:
    private final BufferedReader bufferedReader;
    private final StreamTokenizer in;
    private final PrintWriter out;

    public ProblemTemplate() {
        this(System.in, System.out);
    }

    public ProblemTemplate(InputStream inputStream, OutputStream outputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        in = new StreamTokenizer(bufferedReader);
        out = new PrintWriter(new OutputStreamWriter(outputStream));
    }

    public static void main(String[] args) throws IOException {
        new ProblemTemplate().solve();
    }

    public void solve() {
        try {
            new Solution().solve();
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int readInt() throws IOException {
        return (int) readDouble();
    }

    public long readLong() throws IOException {
        return (long) readDouble();
    }

    public float readFloat() throws IOException {
        return (float) readDouble();
    }

    public double readDouble() throws IOException {
        int nextToken = in.nextToken();
        if (nextToken == StreamTokenizer.TT_NUMBER) {
            return in.nval;
        }
        throw new IllegalStateException("Number expected. Found: " + nextToken);
    }

    public String readWord() throws IOException {
        int nextToken = in.nextToken();
        if (nextToken == StreamTokenizer.TT_WORD) {
            return in.sval;
        }
        throw new IllegalStateException("Word expected. Found: " + nextToken);
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }
}