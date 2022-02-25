package certificate

import java.io.File

trait ImageEffect {
  def processImage(imageSrc: File): File
}

class Drawer(imageSrc: File) {
  def draw(effects: List[ImageEffect]): File = {
    effects.zipWithIndex.foldLeft(imageSrc)({ case (file, (effect, effectNo)) =>
      val output = effect.processImage(file)
      if (effectNo > 0) {
        file.delete()
      }
      output
    })
  }
}
