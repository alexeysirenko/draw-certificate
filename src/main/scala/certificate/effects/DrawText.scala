package certificate.effects

import certificate.ImageEffect

import java.awt.{Color, Font, Graphics, RenderingHints}
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


sealed trait Font {
  def name: String
  def size: Int
}
case class Arial(size: Int) extends Font {
  override def name = "Arial"
}
case class ComicSans(size: Int) extends Font {
  override def name = "Comic Sans"
}
case class CustomFont(name: String, size: Int) extends Font

case class DrawText(text: String, position: AreaRect, font: Font) extends ImageEffect {
  override def processImage(imageSrc: File): File = {
    val inputImage = ImageIO.read(imageSrc)
    val inputWidth = inputImage.getWidth
    val inputHeight = inputImage.getHeight
    val outputImage = new BufferedImage(inputWidth, inputHeight, BufferedImage.TYPE_INT_ARGB)
    val graphics = outputImage.createGraphics()
    graphics.drawImage(inputImage, 0, 0, null)

    val drawFont = new java.awt.Font(font.name, java.awt.Font.ITALIC, font.size)
    graphics.setFont(drawFont)
    graphics.setColor(Color.BLACK)
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

    /*
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
        RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_PURE);
     */

    val fm = graphics.getFontMetrics()
    val x = position.topLeftX + (position.bottomRightX - position.topLeftX) / 2 - fm.stringWidth(text) / 2
    val y = position.topLeftY + ((position.bottomRightY - position.topLeftY - fm.getHeight()) / 2) //+ fm.getAscent()

    graphics.drawString(text, x, y)
    graphics.dispose()

    val outputFile = File.createTempFile("drawn_text_img", ".png")
    ImageIO.write(outputImage, "png", outputFile)
    outputFile
  }
}
