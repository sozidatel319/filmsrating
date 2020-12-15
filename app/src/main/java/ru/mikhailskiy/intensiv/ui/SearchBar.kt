package ru.mikhailskiy.intensiv.ui

import android.content.Context
import java.util.concurrent.TimeUnit
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Predicate
import kotlinx.android.synthetic.main.search_toolbar.view.*
import ru.mikhailskiy.intensiv.Constants.MIN_DENOUNCE_TIMEOUT
import ru.mikhailskiy.intensiv.Constants.MIN_LENGTH
import ru.mikhailskiy.intensiv.R
import ru.mikhailskiy.intensiv.addSchedulers

class SearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val editText: EditText by lazy { search_edit_text }

    private var hint: String = ""
    private var isCancelVisible: Boolean = true

    init {
        LayoutInflater.from(context).inflate(R.layout.search_toolbar, this)
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.SearchBar).apply {
                hint = getString(R.styleable.SearchBar_hint).orEmpty()
                isCancelVisible = getBoolean(R.styleable.SearchBar_cancel_visible, true)
                recycle()
            }
        }
    }

    fun setText(text: String?) {
        this.editText.setText(text)
    }

    fun clear() {
        this.editText.setText("")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        search_edit_text.hint = hint
        delete_text_button.setOnClickListener {
            search_edit_text.text.clear()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        search_edit_text.afterTextChanged { text ->
            if (!text.isNullOrEmpty() && !delete_text_button.isVisible) {
                delete_text_button.visibility = View.VISIBLE
            }
            if (text.isNullOrEmpty() && delete_text_button.isVisible) {
                delete_text_button.visibility = View.GONE
            }
        }
    }

    val onTextChangedObservable: Observable<String> by lazy {
        Observable
            .create(ObservableOnSubscribe<String> { subscriber ->
                editText.doAfterTextChanged { text ->
                    subscriber.onNext(text.toString())
                }
            })
            .map { it.trim() }
            .filter(Predicate<String> {
                return@Predicate it.isNotEmpty() and (it.length > MIN_LENGTH)
            })
            .debounce(MIN_DENOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addSchedulers()
    }
}