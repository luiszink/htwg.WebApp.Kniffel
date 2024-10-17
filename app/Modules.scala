package modules

import com.google.inject.AbstractModule
import de.htwg.se.kniffel.controller.ControllerInterface // Importiere die Implementierung
import de.htwg.se.kniffel.controller.controllerImpl.Controller

class MyModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).to(classOf[Controller]) // Stelle sicher, dass du die Implementierung bindest
  }
}