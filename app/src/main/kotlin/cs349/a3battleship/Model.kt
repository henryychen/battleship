package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType

class Model {
    //region Observer Related
    private val views: ArrayList<IView> = ArrayList()

    fun addView(view: IView) {
        views.add(view)
        view.updateView()
    }

    private fun notifyObservers() {
        for (view in views) {
            view.updateView()
        }
    }
    //endregion

    // model state
    var game = Game(10, true)
    var init = true
    var gameLoop = false

    fun addShip(shipType: ShipType, orientation: cs349.a3battleship.model.Orientation, bowCell : Cell) {
        if (init) {
            game.startGame()
            init = false
        }
        game.placeShip(Player.Human, shipType, orientation, bowCell)
        notifyObservers()
    }

    fun removeShip(cell:Cell) {
        game.removeShip(Player.Human, cell)
        notifyObservers()
    }

    fun startGame() {
        gameLoop = true
        game.startGame()
        game.startGame()
        notifyObservers()
    }

    fun attack(xCell: Int, yCell: Int) {
        var cell = Cell(xCell, yCell)
        game.attackCell(cell)
        var listOfCells = game.getAttackedCells(Player.Human)
        println(listOfCells.size)
        notifyObservers()
    }
}