package certificate.effects

import certificate.ImageEffect

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.Graphics2D

case class ScaleTo(maxWidth: Int, maxHeight: Int) extends ImageEffect {
  override def processImage(imageSrc: File): File = {
    val inputImage = ImageIO.read(imageSrc)
    val inputWidth = inputImage.getWidth
    val inputHeight = inputImage.getHeight

    val (outputWidth, outputHeight) = if (inputWidth > inputHeight) {
      (maxWidth, (1.0 * maxWidth / inputWidth * inputHeight).toInt)
    } else {
      ((1.0 * maxHeight / inputHeight * inputWidth).toInt, maxHeight)
    }

    val outputImage = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_ARGB)

    val g2d = outputImage.createGraphics
    g2d.drawImage(inputImage, 0, 0, outputWidth, outputHeight, null)
    g2d.dispose()

    val outputFile = File.createTempFile("scaled_img", ".png")
    ImageIO.write(outputImage, "png", outputFile)
    outputFile
  }
}