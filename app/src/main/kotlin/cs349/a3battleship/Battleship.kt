package cs349.a3battleship

import cs349.a3battleship.model.Game
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class Battleship : Application() {
    var model = Model()
    var pane1 = StackPane()
    var fleet = PlayerFleetView(model)

    override fun start(stage: Stage) {
        var game = Game(10, true)
        var computer = AI(game)
		// var player = ...

        pane1.children.add(fleet)
        val scene = Scene(pane1, 875.0, 375.0)
        with (stage) {
            isResizable = false
            this.scene = scene
            title = "A3 Battleship (hy5chen)"
            this.show()
        }
    }
}