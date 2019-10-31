import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DCTSteganography extends Image {

    private
    HidingTools htools=new HidingTools();
    UnHidingTools uhtools=new UnHidingTools();
    double dctcoefficents[][];
    int color[][];
    final int m=8; final int n=8;
    public DCTSteganography(File src) throws IOException
    {
        super(src);
        dctcoefficents=new double[m][n];
        super.init();
    }

    private void paddingImage()
    {
        int wbuffer=8-(imagewidth%8==0?8:imagewidth%8);
        int hbuffer=8-(imageheight%8==0?8:imageheight%8);
        BufferedImage img=super.getImage();
        BufferedImage newimg = new BufferedImage(imagewidth+wbuffer , imageheight+hbuffer , BufferedImage.TYPE_INT_RGB);

        for (int i = 0 ; i < newimg.getHeight() ; i++)
        {

            for (int j = 0 ; j < newimg.getWidth() ; j++)
            {
                if(i<imageheight&&j<imagewidth)
                {
                    newimg.setRGB(j, i, img.getRGB(j, i));
                }
                else if(i<imageheight&&j>=imagewidth)
                {
                    newimg.setRGB(j, i, img.getRGB(j-wbuffer, i));
                }
                else if(i>=imageheight)
                {
                    newimg.setRGB(j, i, newimg.getRGB(j, i-hbuffer));
                }
            }
        }
        super.setImage(newimg);
        super.init();

    }

    private void calculatedct(int component[][]) throws IOException
    {
        // dct will store the discrete cosine transform

        double ci, cj, dct1, sum;
        int i,j,k,l;

        for (i = 0; i < m; i++)
        {
            for (j = 0; j < n; j++)
            {
                // ci and cj depends on frequency as well as
                // number of row and columns of specified matrix
                if (i == 0)
                    ci = 1 / Math.sqrt(m);
                else
                    ci = Math.sqrt(2) / Math.sqrt(m);

                if (j == 0)
                    cj = 1 / Math.sqrt(n);
                else
                    cj = Math.sqrt(2) / Math.sqrt(n);

                // sum will temporarily store the sum of 
                // cosine signals
                sum = 0;
                for (k = 0; k < m; k++)
                {
                    for (l = 0; l < n; l++)
                    {
                        dct1 = component[k][l] *
                                Math.cos(((2 * k + 1) * i * Math.PI) / (2 * m)) *
                                Math.cos(((2 * l + 1) * j * Math.PI )/ (2 * n));
                        sum = sum + dct1;
                    }
                }
                dctcoefficents[i][j] = ci * cj * sum;
            }
        }

    }

    public void hide(String message,ObjectOutputStream Objos) throws IOException
    {
        StringBuilder binarymsg= new StringBuilder();
        htools.stringToBinary(message,binarymsg);
        StringBuilder rgb=new StringBuilder();
        int colorbuffer[][]=new int[m][n];
        int i,j,k,l,pos;
        int len=binarymsg.length();
        paddingImage();
        twoDColor();
        boolean exit=false;
        pos=0;

        for(i=0;i<imageheight-8;i+=8)
        {
            for(j=0;j<imagewidth-8;j+=8)
            {
                for(k=0;k<m;k++)
                {
                    for(l=0;l<n;l++)
                    {
                        colorbuffer[k][l]=color[i+k][j+l];
                    }
                    calculatedct(colorbuffer);
                }

                for(k=0;k<m;k++)
                {
                    for(l=0;l<n;l++)
                    {
                        if(dctcoefficents[k][l]<0)
                        {  rgb.delete(0, rgb.length());
                            htools.convertIntToBinary(color[i+k][j+l],rgb);
                            if(pos>=len){ exit =true; break; }
                            htools.replaceLSB(rgb, binarymsg.charAt(pos));
                            color[i+k][j+l]=htools.stringToInteger(rgb);
                            pos++;
                            Objos.writeBoolean(true);
                        }
                        else
                            Objos.writeBoolean(false);
                    }
                    if(exit) break;
                }
                if(exit) break;
            }
            if(exit) break;
        }
        oneDColor();
    }

    public void unHide(ObjectInputStream Objins,StringBuilder Stringmsg) throws IOException
    {
        StringBuilder message=new StringBuilder();
        StringBuilder rgb=new StringBuilder();
        int i,j,k,l;
        twoDColor();
        boolean exit=false;
        for(i=0;i<imageheight-8;i+=8)
        {
            for(j=0;j<imagewidth-8;j+=8)
            {
                for(k=0;k<m;k++)
                {
                    for(l=0;l<n;l++)
                    {
                        try
                        {
                            if(Objins.readBoolean())
                            {
                                uhtools.convertIntToBinary(color[i+k][j+l],rgb);
                                message.append(uhtools.extractLsb(rgb));
                            }
                        }
                        catch(IOException e)
                        {
                            exit =true;
                            break;
                        }
                    }
                    if(exit) break;
                }
                if(exit) break;
            }
            if(exit) break;
        }
        uhtools.convertBinaryToString(message,Stringmsg);

    }

    private void twoDColor()
    {
        Color c;
        color=new int[imageheight][imagewidth];
        BufferedImage img=super.getImage();

        for(int i=0;i<imageheight;i++)
        {
            for(int j=0;j<imagewidth;j++)
            {
                c=new Color(img.getRGB(j, i));
                color[i][j]=c.getRed();
            }
        }

    }
    private void oneDColor()
    {
        for(int i=0;i<imageheight;i++)
        {
            System.arraycopy(color[i], 0, red, i*imagewidth, imagewidth);
        }
    }

    @Override
    public int[] constructPixels() {
        int pixels[]=new int[imageheight*imagewidth];
        int i,length=imagewidth*imageheight;
        Color c;

        for(i=0;i<length;i++)
        {
            c=new Color(red[i],green[i],blue[i]);
            pixels[i]=c.getRGB();
        }

        return pixels;
    }
}