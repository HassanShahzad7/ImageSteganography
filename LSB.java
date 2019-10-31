import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class LSB extends Image{

    private
    HidingTools htools=new HidingTools();
    UnHidingTools uhtools=new UnHidingTools();
    public LSB(File src) throws IOException {
        super(src);
        super.init();
    }

    public void Hide(String msg)
    {
        StringBuilder message=new StringBuilder();
        htools.stringToBinary(msg,message);
        StringBuilder rgb=new StringBuilder();
        rgb.append("");
        int i,j,r,g,b;

        int length=message.length();
        StringBuilder msglen=new StringBuilder();
        htools.convertIntToBinary(length,msglen);
        System.out.println(length+" "+msglen);
        i=-1; j=0;
        while(true)
        {
            if(++i>=8) break;
            htools.convertIntToBinary(red[j],rgb);
            htools.replaceLSB(rgb, msglen.charAt(i));
            red[j]=htools.stringToInteger(rgb);
            rgb.delete(0, rgb.length());


            if(++i>=8) break;
            htools.convertIntToBinary(green[j],rgb);
            htools.replaceLSB(rgb, msglen.charAt(i));
            green[j]=htools.stringToInteger(rgb);
            rgb.delete(0, rgb.length());

            if(++i>=8) break;
            htools.convertIntToBinary(blue[j],rgb);
            htools.replaceLSB(rgb, msglen.charAt(i));
            blue[j]=htools.stringToInteger(rgb);
            rgb.delete(0, rgb.length());

            j++;
        }

        rgb.delete(0, rgb.length());
        i=-1;  j=3; length=message.length();
        while(true)
        {
            htools.convertIntToBinary(red[j],rgb);
            if(++i>=length) break;
            htools.replaceLSB(rgb, message.charAt(i));
            red[j]=htools.stringToInteger(rgb);
            rgb.delete(0, rgb.length());

            htools.convertIntToBinary(green[j],rgb);
            if(++i>=length) break;
            htools.replaceLSB(rgb,  message.charAt(i));
            green[j]=htools.stringToInteger(rgb);
            rgb.delete(0, rgb.length());

            htools.convertIntToBinary(blue[j],rgb);
            if(++i>=length) break;
            htools.replaceLSB(rgb, message.charAt(i));
            blue[j]=htools.stringToInteger(rgb);
            rgb.delete(0, rgb.length());

            j++;
        }
    }

    @Override
    public int[] constructPixels() {
        int pixels[]=new int[imageheight*imagewidth];
        int i,length=imagewidth*imageheight;
        Color c;

        for(i=0;i<length;i++)
        {
            try{
                c=new Color(red[i],green[i],blue[i]);
                pixels[i]=c.getRGB();
            }
            catch(Exception e)
            {
                System.err.println(red[i]+" "+green[i]+" "+blue[i]+" "+i);
            }
        }

        return pixels;
    }

    public void unHide(StringBuilder msg)
    {
        StringBuilder length=new StringBuilder();
        StringBuilder rgb=new StringBuilder();
        StringBuilder message=new StringBuilder();

        int len;
        int i=0;int j=0;
        System.out.println("\n");
        while(true)
        {
            if(j>=8) break;
            uhtools.convertIntToBinary(red[i],rgb);
            System.out.println(rgb+" "+red[i]);
            length.append(uhtools.extractLsb(rgb));
            j++;
            rgb.delete(0, rgb.length());

            if(j>=8) break;
            uhtools.convertIntToBinary(green[i],rgb);
            System.out.println(rgb+" "+green[i]);
            length.append(uhtools.extractLsb(rgb));
            j++;
            rgb.delete(0, rgb.length());

            if(j>=8) break;
            uhtools.convertIntToBinary(blue[i],rgb);
            System.out.println(rgb+" "+blue[i]);
            length.append(uhtools.extractLsb(rgb));
            j++;
            rgb.delete(0, rgb.length());

            i++;
        }
        System.out.println(length);
        len=htools.stringToInteger(length);
        System.out.println(len);
        i=3;  j=0;  rgb.delete(0, rgb.length());
        while(true)
        {
            if(j>len) break;
            uhtools.convertIntToBinary(red[i],rgb);
            message.append(uhtools.extractLsb(rgb)); j++;
            rgb.delete(0, rgb.length());

            if(j>len) break;
            uhtools.convertIntToBinary(green[i],rgb);;
            message.append(uhtools.extractLsb(rgb)); j++;
            rgb.delete(0, rgb.length());

            if(j>len) break;
            uhtools.convertIntToBinary(blue[i],rgb);
            message.append(uhtools.extractLsb(rgb)); j++;
            rgb.delete(0, rgb.length());

            i++;
        }
        uhtools.convertBinaryToString(message,msg);

    }
}
