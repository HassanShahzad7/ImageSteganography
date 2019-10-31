public class UnHidingTools {

    public void convertIntToBinary(int x,StringBuilder ItoB)
    {
        StringBuilder sb=new StringBuilder();
        sb.append(Integer.toBinaryString(0x100 | x).substring(1));
        // System.out.println(sb+" "+sb.length());
        ItoB.append(sb);
    }
    public char extractLsb(StringBuilder sb)
    {
        return sb.charAt(sb.length()-1);
    }

    public void convertBinaryToString(StringBuilder output,StringBuilder BtoS)
    {
        StringBuilder sb=new StringBuilder();
        for(int i = 0; i <= output.length()-8; i += 8) //this is a little tricky.  we want [0, 7], [9, 16], etc (increment index by 9 if bytes are space-delimited)
        {

            //   System.out.println(output.substring(i, i+8));
            sb.append((char)Integer.parseInt(output.substring(i, i+8), 2));

        }
        BtoS.append(sb);
    }
}
