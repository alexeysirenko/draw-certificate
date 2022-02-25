package certificate.effects

import certificate.ImageEffect

import java.awt.image.BufferedImage
import java.awt.{Color, RenderingHints}
import java.io.File
import javax.imageio.ImageIO

case class DrawImage(image: File, x: Int, y: Int, width: Int, height: Int) extends ImageEffect {
  override def processImage(imageSrc: File): File = {
    val inputImage = ImageIO.read(imageSrc)
    val inputWidth = inputImage.getWidth
    val inputHeight = inputImage.getHeight
    val outputImage = new BufferedImage(inputWidth, inputHeight, BufferedImage.TYPE_INT_ARGB)
    val graphics = outputImage.createGraphics()
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    graphics.drawImage(inputImage, 0, 0, null)

    val stickerImage = ImageIO.read(image)

    graphics.drawImage(stickerImage, x, y, width, height, null)
    graphics.dispose()

    val outputFile = File.createTempFile("drawn_sticker_img", ".png")
    ImageIO.write(outputImage, "png", outputFile)
    outputFile
  }
}
