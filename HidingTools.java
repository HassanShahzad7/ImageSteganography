public class HidingTools {

    public void convertIntToBinary(int x,StringBuilder ItoB)
    {
        StringBuilder sb=new StringBuilder();
        sb.append(Integer.toBinaryString(0x100 | x).substring(1));
        // System.out.println(sb+" "+sb.length());
        ItoB.append(sb);
    }

    public void replaceLSB(StringBuilder integer,char msg)
    {
        if(msg=='1')
            integer.replace(integer.length()-1,integer.length(), "1");
        else
            integer.replace(integer.length()-1,integer.length(), "0");

    }

    public int stringToInteger(StringBuilder sb)
    {
        return Integer.parseInt(sb.substring(0,sb.length()), 2);
    }

    public void stringToBinary(String msg,StringBuilder sb)
    {
        byte[] bytes = msg.getBytes();
        StringBuilder stringbinary = new StringBuilder();

        for (byte x : bytes)
        {
            int val = x;
            for (int i = 0; i < 8; i++)
            {
                stringbinary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        sb.append(stringbinary);
    }
}
