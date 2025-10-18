package com.rubenbellido.appconecta4

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    val rows = 6
    val cols = 7
    var board = createBoard(rows, cols)
    var turnoRojo = true
    lateinit var tableLayout: TableLayout
    // val fichas = MutableList<MutableList<ImageView>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        generateTableLayout(tableLayout, board)
    }

    fun createBoard(rows: Int, cols: Int): Array<Array<Int>> {
        val board = Array(rows) { Array(cols) { 0 } }
        return board
    }

    fun generateTableLayout(tableLayout: TableLayout, board: Array<Array<Int>>) {
        tableLayout.removeAllViews()

        val rowsForTableLayout = board.size + 1

        // Ajustar tamaño de celda para ocupar toda la pantalla
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val cellSize = (screenWidth * 0.9 / (cols + 1)).toInt() // x0.9 es para dejar margen
        val cellParams = TableRow.LayoutParams(cellSize, cellSize)

        // Recorrer filas
        for (i in 0 until rowsForTableLayout) { // rows + 1 porque añadimos una fila para botones

            val fila = TableRow(tableLayout.context)

            // Recorrer columnas
            for (j in 0 until cols) {

                // Botones si es el encabezado
                if (i == 0) {
                    val button = Button(this)
                    button.text = ""
                    button.layoutParams = cellParams

                    button.setOnClickListener {
                        putChipOnCol(board, j)
                    }

                    fila.addView(button)
                    continue
                }

                else {
                    var ficha = ImageView(this)
                    val boardValue = board[i-1][j]
                    when (boardValue) {
                        0 -> ficha.setImageResource(R.drawable.oval_grey)
                        1 -> ficha.setImageResource(R.drawable.oval_red)
                        2 -> ficha.setImageResource(R.drawable.oval_yellow)
                    }
                    ficha.layoutParams = cellParams
                    fila.addView(ficha)

                }
            }

            tableLayout.addView(fila)
        }
    }

    fun putChipOnCol(board: Array<Array<Int>>, col: Int) {
        if (!checkCanPutChipInColumn(board, col)) {
            return
        }

        val row = getIndexOfEmptyRow(board, col)
        if (row == -1) return

        val value = if (turnoRojo) 1 else 2
        board[row][col] = value
        turnoRojo = !turnoRojo
        generateTableLayout(tableLayout, board)
    }

    fun checkCanPutChipInColumn(board: Array<Array<Int>>, colToPut: Int) : Boolean {
        return board[0][colToPut] == 0
    }

    fun getIndexOfEmptyRow(board: Array<Array<Int>>, colToPut: Int) : Int {
        for (i in (rows-1) downTo 0) {
            if (board[i][colToPut] == 0) {
                return i
            }
        }

        return -1
    }

    fun redrawTableLayout(tableLayout: TableLayout, board: Array<Array<Int>>) {

        for (i in 1..(rows+1)) {
            for (j in 0..cols) {

                if (board[i][j] == 0) {
                    continue
                }

            }
        }
    }
}