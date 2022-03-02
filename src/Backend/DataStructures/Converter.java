package Backend.DataStructures;

    public class Converter {

    public static String rightpad(String data)  {
        while (data.length()%8!=0)
            data+='0';
        return data;
    }
    public static String tobinaryString(int x) {
        if (x<0)
            x+=256;
        String out = "";
        while (x > 0) {
            if (x % 2 == 0)
                out = "0" + out;
            else {
                out = 1 + out;
            }
            x /= 2;
        }
        while (out.length() < 8) {
            out = "0" + out;
        }
        return out;
    }
    public static int strtoint(String s) {
        int b = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.length() == 8 && i == 0 && s.charAt(i) == '1') {
                b -= 128;
            }
            else {
                if (s.charAt(i) == '1')
                    b += Math.pow(2, s.length() - i - 1);
            }
        }
        return b;
    }
    public static byte[] binaryStringtobytes(String binaryString) {
        byte[] bytes = new byte[binaryString.length()/8];
        byte a = 0;
        int count = 0;
        for (int i = 0; i < binaryString.length(); i+=8) {
            bytes[count] = (byte) (strtoint(binaryString.substring(i, i + 8)));
            count++;
        }
        return bytes;
    }
}