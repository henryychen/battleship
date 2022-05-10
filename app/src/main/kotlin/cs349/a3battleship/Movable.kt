package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.ships.ShipType
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import kotlin.math.roundToInt

class MovableManager(
    private val model: Model,
    parent: Node,
    shipType : ShipType
) : IView {

    private var movingNode: Node? = null
    private var ox = 0.0
    private var oy = 0.0
    var horizontal = false
    var shipType = shipType

    override fun updateView() {

    }

    init {
        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null && e.button != MouseButton.SECONDARY) {

                //println("drop '$node'")
                if (node.localToScene(node.boundsInLocal).minX < 23 || node.localToScene(node.boundsInLocal).maxX > 328 ||
                    node.localToScene(node.boundsInLocal).minY < 43 || node.localToScene(node.boundsInLocal).maxY > 347) {
                    node.translateX = 0.0
                    node.translateY = 0.0
//                    if (horizontal) {
//                        horizontal = false
//                        var midX = (node.localToScene(node.boundsInLocal).minX + node.localToScene(node.boundsInLocal).maxX) / 2
//                        var midY = (node.localToScene(node.boundsInLocal).minY + node.localToScene(node.boundsInLocal).maxY) / 2
//                        var rotateMid = Rotate(90.0, midX, midY)
//                        node.transforms.add(rotateMid)
//                    }
                } else if (false) { // already occupied

                } else {
                    snapToGrid(node, node.localToScene(node.boundsInLocal).minX, node.localToScene(node.boundsInLocal).minY)
                }
                movingNode = null
            }
        }

        parent.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            val node = movingNode
            if (node != null) {
                node.translateX = e.sceneX + ox
                node.translateY = e.sceneY + oy
                e.consume()
            }
        }
        model.addView(this)
    }

    fun makeMovable(node: Node) {
        node.onMouseClicked = EventHandler { e ->
            /*if (e.button == MouseButton.SECONDARY && movingNode != null) {
                node.transforms.add(Transform.rotate(90.0, e.x, e.y))
                horizontal = !horizontal
            } else*/ if (e.button == MouseButton.PRIMARY && movingNode == null) {
                //println("click '$node'")
                var cellX = node.localToScene(node.boundsInLocal).minX.toInt() / 30 - 1
                var cellY = node.localToScene(node.boundsInLocal).minY.toInt() / 30 - 1
                model.removeShip(Cell(cellX, cellY))
                this.movingNode = node
                ox = node.translateX - e.sceneX
                oy = node.translateY - e.sceneY
                e.consume()
            }
        }
    }

    fun unMovable(node: Node) {
        node.onMouseClicked = EventHandler {

        }
    }

    fun snapToGrid(node : Node, minX : Double, minY : Double) {
        var x = 30.0 * (minX / 30).roundToInt()
        var y = 30.0 * ((minY-15) / 30).roundToInt() + 15
        var offset = node.boundsInLocal.minX
        node.translateX = -offset + x
        node.translateY = -36.0 + y

        var cellX = node.localToScene(node.boundsInLocal).minX.toInt() / 30 - 1
        var cellY = node.localToScene(node.boundsInLocal).minY.toInt() / 30 - 1
        var cell = Cell(cellX, cellY)
        model.addShip(shipType, cs349.a3battleship.model.Orientation.VERTICAL, cell)
    }
}
