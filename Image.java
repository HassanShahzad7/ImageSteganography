import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public abstract class Image {

    protected
    int imageheight,imagewidth,red[],green[],blue[];
    private
    BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    Color c;

    public Image(File src) throws IOException
    {
        image=ImageIO.read(src);
    }
    protected void init()
    {
        imageheight=image.getHeight();
        imagewidth=image.getWidth();
        int i,j;
        red=new int[imageheight*imagewidth];
        green=new int[imageheight*imagewidth];
        blue=new int[imageheight*imagewidth];

        for(i=0;i<imageheight;i++)
        {
            for(j=0;j<imagewidth;j++)
            {
                c=new Color(image.getRGB(j, i),true);
                red[j+i*imagewidth]=c.getRed();
                green[j+i*imagewidth]=c.getGreen();
                blue[j+i*imagewidth]=c.getBlue();
            }
        }
    }
    public boolean writeImage(File des,int pixels[]) throws IOException
    {
        BufferedImage bim=new BufferedImage(imagewidth, imageheight, BufferedImage.TYPE_INT_RGB);
        bim.setRGB(0, 0, imagewidth, imageheight, pixels, 0,imagewidth);
        ImageIO.write(bim, "png", des);

        return true;
    }
    public abstract int[] constructPixels();
}
