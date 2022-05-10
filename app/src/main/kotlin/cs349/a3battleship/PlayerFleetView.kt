package cs349.a3battleship

import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font


class PlayerFleetView(
    private val model: Model
) : Pane(), IView {

    var destroyer = Rectangle(360.0,40.0, 20.0, 50.0)
    var cruiser = Rectangle(390.0,40.0, 20.0, 80.0)
    var submarine = Rectangle(420.0,40.0, 20.0, 80.0)
    var battleship = Rectangle(450.0,40.0, 20.0, 110.0)
    var carrier = Rectangle(480.0,40.0, 20.0, 140.0)
    var start = Button("Start Game")
    var exit = Button("Exit Game")

    override fun updateView() {
        if (model.game.getShipsPlacedCount(Player.Human) == 5 && !model.gameLoop) {
            start.isDisable = false
        }
    }

    init {
        // My formation
        var header1 = Label("My Formation")
        with (header1) {
            layoutX = 120.0
            layoutY = 0.0
            textFill = Color.BLACK
            font = Font.font(16.0)
            style = "-fx-font-weight: bold"
        }

        var xPosNumber11 = 35.0
        for (i in 1 .. 10) {
            var tmp = Label(i.toString())
            tmp.layoutX = xPosNumber11
            tmp.layoutY = 25.0
            children.add(tmp)
            xPosNumber11 += 30
        }

        var xPos1 = 25.0
        var yPos1 = 45.0
        for (i in 'A'..'J') {
            var tmp1 = Label(i.toString())
            tmp1.layoutX = 9.0
            tmp1.layoutY = yPos1 + 5
            children.add(tmp1)

            for (j in 1 .. 10) {
                var square = Rectangle(xPos1, yPos1, 30.0,30.0)
                square.fill = Color.LIGHTBLUE
                square.stroke = Color.BLACK

                children.add(square)
                xPos1 += 30
            }

            var tmp2 = Label("$i  ")
            tmp2.layoutX = 335.0
            tmp2.layoutY = yPos1 + 5
            children.add(tmp2)
            yPos1 += 30
            xPos1 = 25.0
        }

        var xPosNumber12 = 35.0
        for (i in 1 .. 10) {
            var tmp = Label(i.toString())
            tmp.layoutX = xPosNumber12
            tmp.layoutY = 350.0
            children.add(tmp)
            xPosNumber12 += 30
        }
        children.add(header1)
        model.addView(this)

        // Opponent formation
        var header2 = Label("Opponent's Formation")
        with (header2) {
            layoutX = 620.0
            layoutY = 0.0
            textFill = Color.BLACK
            font = Font.font(16.0)
            style = "-fx-font-weight: bold"
        }

        var xPosNumber21 = 558.0
        for (i in 1 .. 10) {
            var tmp = Label(i.toString())
            tmp.layoutX = xPosNumber21
            tmp.layoutY = 25.0
            children.add(tmp)
            xPosNumber21 += 30
        }

        var xPos2 = 550.0
        var yPos2 = 45.0
        for (i in 'A'..'J') {
            var tmp1 = Label(i.toString())
            tmp1.layoutX = 530.0
            tmp1.layoutY = yPos2 + 5
            children.add(tmp1)

            for (j in 1 .. 10) {
                var square = Rectangle(xPos2, yPos2, 30.0,30.0)
                square.setOnMouseClicked {
                    if (model.gameLoop) {
                        var xCell = j - 1
                        var yCell = i - 17
                        println("$xCell , $yCell")
                        model.attack(j - 1, (i - 17).code)
                        square.fill = Color.LIGHTGRAY
                    }
                }
                square.fill = Color.LIGHTBLUE
                square.stroke = Color.BLACK
                children.add(square)
                xPos2 += 30
            }

            var tmp2 = Label("$i  ")
            tmp2.layoutX = 859.0
            tmp2.layoutY = yPos2 + 5
            children.add(tmp2)
            yPos2 += 30
            xPos2 = 550.0
        }

        var xPosNumber22 = 558.0
        for (i in 1 .. 10) {
            var tmp = Label(i.toString())
            tmp.layoutX = xPosNumber22
            tmp.layoutY = 350.0
            children.add(tmp)
            xPosNumber22 += 30
        }
        children.add(header2)
        model.addView(this)

        // Fleet view
        var header3 = Label("My Fleet")
        with (header3) {
            textFill = Color.BLACK
            font = Font.font(16.0)
            style = "-fx-font-weight: bold"
        }
        header3.layoutXProperty().bind(this.widthProperty().subtract(header3.widthProperty()).divide(2))
        children.add(header3)

        var mover1 = MovableManager(model,this, ShipType.Destroyer)
        with (destroyer) {
            fill = Color.DARKGRAY
            stroke = Color.BLACK
            mover1.makeMovable(this)
        }

        var mover2 = MovableManager(model,this, ShipType.Cruiser)
        with (cruiser) {
            fill = Color.DARKGRAY
            stroke = Color.BLACK
            mover2.makeMovable(this)
        }

        var mover3 = MovableManager(model,this, ShipType.Submarine)
        with (submarine) {
            fill = Color.DARKGRAY
            stroke = Color.BLACK
            mover3.makeMovable(this)
        }

        var mover4 = MovableManager(model,this, ShipType.Battleship)
        with (battleship) {
            fill = Color.DARKGRAY
            stroke = Color.BLACK
            mover4.makeMovable(this)
        }

        var mover5 = MovableManager(model,this, ShipType.Carrier)
        with (carrier) {
            fill = Color.DARKGRAY
            stroke = Color.BLACK
            mover5.makeMovable(this)
        }

        children.add(destroyer)
        children.add(cruiser)
        children.add(submarine)
        children.add(battleship)
        children.add(carrier)

        start.isDisable = true
        start.prefWidth = 150.0
        start.layoutXProperty().bind(this.widthProperty().subtract(start.widthProperty()).divide(2))
        start.layoutY = 295.0
        start.setOnAction {
            start.isDisable = true
            mover1.unMovable(destroyer)
            mover2.unMovable(cruiser)
            mover3.unMovable(submarine)
            mover4.unMovable(battleship)
            mover5.unMovable(carrier)
            model.startGame()
        }

        exit.prefWidth = 150.0
        exit.layoutXProperty().bind(this.widthProperty().subtract(exit.widthProperty()).divide(2))
        exit.layoutY = 320.0
        exit.setOnAction {
            Platform.exit()
        }

        children.add(start)
        children.add(exit)
        model.addView(this)
    }
}