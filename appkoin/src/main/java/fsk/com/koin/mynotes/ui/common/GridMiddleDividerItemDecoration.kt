package fsk.com.koin.mynotes.ui.common

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round

/**
 * A decorator that places the divider between the rows and columns in the grid.
 */
class GridMiddleDividerItemDecoration : RecyclerView.ItemDecoration() {

    /**
     * THe divider drawable.  This must have an intrinsic height and width
     */
    var dividerDrawable: Drawable? = null

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        dividerDrawable?.let {

            (parent.layoutManager as? GridLayoutManager)?.let { gridLayoutManager ->
                //clip the canvas to prevent the dividers from drawing int the padding areas

                c.clipRect(
                    parent.paddingLeft, parent.paddingTop,
                    parent.width - parent.paddingRight,
                    parent.height - parent.paddingBottom
                )

                drawRowDivider(c, parent, gridLayoutManager)
                drawColumnDivider(c, parent, gridLayoutManager)
            }
        }
    }


    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) = dividerDrawable?.let {
        outRect.set(0, 0, it.intrinsicWidth, it.intrinsicHeight)
    } ?: outRect.set(0, 0, 0, 0)

    private fun drawRowDivider(
        canvas: Canvas,
        parent: RecyclerView,
        gridLayoutManager: GridLayoutManager
    ) {
        dividerDrawable?.let {
            canvas.save()

            var childCount = parent.childCount
            var leftItems = childCount % gridLayoutManager.spanCount
            if (leftItems == 0) {
                leftItems = gridLayoutManager.spanCount
            }

            //ignore left overs since they should be the bottom
            childCount -= leftItems

            for (i in 0 until childCount - 1) {
                parent.getChildAt(i)?.let { child ->
                    parent.getDecoratedBoundsWithMargins(child, it.bounds)
                    val bottom = (it.bounds.bottom + round(child.translationY)).toInt()
                    val top = bottom - it.intrinsicHeight
                    it.setBounds(
                        parent.paddingLeft,
                        top,
                        parent.right - parent.paddingRight,
                        bottom
                    )
                    it.draw(canvas)
                }
            }
        }
        canvas.restore()
    }

    private fun drawColumnDivider(
        canvas: Canvas,
        parent: RecyclerView,
        gridLayoutManager: GridLayoutManager
    ) {

        dividerDrawable?.let {
            canvas.save()
            for (i in 0 until parent.childCount) {
                if ((i + 1) % gridLayoutManager.spanCount != 0) {
                    val child = parent.getChildAt(i) ?: return
                    gridLayoutManager.getDecoratedBoundsWithMargins(child, it.bounds)
                    val right = (it.bounds.right + round(child.translationX)).toInt()
                    val left = right - it.intrinsicWidth
                    val top = child.y.toInt()
                    val bottom = top + child.height
                    it.setBounds(left, top, right, bottom)
                    it.draw(canvas)
                }
            }

            canvas.restore()
        }
    }
}